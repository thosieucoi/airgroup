package com.airgroup.services.impl.kenya;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ning.http.client.Cookie;
import com.ning.http.client.Response;
import com.ning.org.jboss.netty.util.internal.StringUtil;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;

public class KenyaAirlineFlight extends SearchFlights {
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd HH:mm");
	
	private static final SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy");
	private static final SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
	
	private String jSessionId;	// For request getting details of flight
	private List<Cookie> cookies;
	//private String jsonString;	// For get recommendation id
	
	public KenyaAirlineFlight(){
		cookies = Lists.newArrayList();
	}
	
	@Override
	protected Response getResponse(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		return null;
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search)
			throws IOException, InterruptedException, ExecutionException {
		
		List<CuriosityFare> fares = Lists.newArrayList();
		KenyaAirlineFirstRequest firstRequest = new KenyaAirlineFirstRequest();
		cookies = firstRequest.getResponse(search).getCookies();
		KenyaAirlineSecondRequest secondRequest = new KenyaAirlineSecondRequest();
		jSessionId = StringUtils.substringBetween(secondRequest.getResponseWithCookies(search, cookies).getResponseBody(),"jsessionid=","\"");
		if (jSessionId == null || jSessionId.equals(""))
			return null;
		search.createParam("KENYA_SESSION", jSessionId);
		KenyaAirlineRequestForResults requestForResult = new KenyaAirlineRequestForResults();
		Response response = requestForResult.getResponseWithCookies(search, cookies);
		
		String jsonString = StringUtils.substringBetween(response.getResponseBody(), "var generatedJSon = new String(\'", "\');");
		if (jsonString == null || jsonString.equals(""))
			return null;
		
		ArrayList<FlightInformations> listRequest = getRecommendationIds(jsonString, search);
		if (listRequest.size() == 0)
			return null;
		
		if (search.isRoundtrip()){
			for (int i = 0; i < listRequest.size();i++){
				FlightInformations tmpFlight = listRequest.get(i);
				FlightInformations.commendation_id = tmpFlight.getRecommendationId();
				FlightInformations.flight_id_1 = StringUtils.substringBetween(tmpFlight.getRow(), "\"", "\"");
				
				for (int j = 0; j < tmpFlight.getArrivalFlightList().size(); j ++){
					FlightInformations arrivalFlight = tmpFlight.getArrivalFlightList().get(j);
					FlightInformations.flight_id_2 = StringUtils.substringBetween(arrivalFlight.getRow(), "\"", "\"");
					KenyaAirlineRequestForFare result = new KenyaAirlineRequestForFare();
					Response responseDetails = result.getResponseWithCookies(search, cookies);
					String content =  responseDetails.getResponseBody();
					
					Document document  = Jsoup.parse(content);
					// ******** For get fare ***************//
					Element fareTable = document.select("html>body>div#container>table.layoutTable>tbody>tr")
							.get(1).select("tr>td.layoutBody>div.container>div.sectionHolder>table.tableConfText>tbody>tr>td>table").get(0);
					String tmpFare = fareTable.select("table >tbody>tr").last().select("td>span.textColorBold>span").text().trim();
					float pricePerAdult = getMoney(fareTable.select("table >tbody>tr").get(1).select("td").get(8).select("span").toString())/search.getAdultsCount();
					float pricePerChildren, pricePerInfant;
					if (search.getChildrenCount() > 0)
						pricePerChildren = getMoney(fareTable.select("table >tbody>tr").get(2).select("td").get(8).select("span").toString())/search.getChildrenCount();
					else pricePerChildren = 0;
					if (search.getInfantsCount() > 0 && pricePerChildren > 0)
						pricePerInfant = getMoney(fareTable.select("table >tbody>tr").get(3).select("td").get(8).select("span").toString())/search.getInfantsCount();
					else if (search.getInfantsCount() > 0 && search.getChildrenCount() == 0 )
						pricePerInfant = getMoney(fareTable.select("table >tbody>tr").get(2).select("td").get(8).select("span").toString())/search.getInfantsCount();
					else
						pricePerInfant = 0;
					
					String breakFare[] = tmpFare.split(" ");
					String faretmp = breakFare[0].trim().replace(",", "");
					float fareFinal = Float.parseFloat(faretmp);
					
					
					Elements tables = document.select("html>body>div#container>table.layoutTable>tbody>tr>td.layoutBody>div.container>div#sh_fltItinerary>table");
					// **  For outbound segments ***************//
					List<CuriositySegment> outbound = Lists.newArrayList();
					try {
						outbound = parseSegments(tables.get(0));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
					
					// ***** For inbound segments *************//
					List<CuriositySegment> inbound = Lists.newArrayList();
					try {
						inbound = parseSegments(tables.get(1));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
					if (outbound.size() == 0 || inbound.size() == 0)
						continue;
					
					CuriosityFare fare = new CuriosityFare();
					fare.setCurrencyCode("USD");
					fare.setOutboundSegments(outbound);
					fare.setInboundSegments(inbound);
					fare.setPrice(fareFinal);
					fare.setPricePerAdult(pricePerAdult);
					if (pricePerChildren > 0)
						fare.setPricePerChild(pricePerChildren);
					fares.add(fare);
				}
			}
		}
		
		else {	// If being the oneway flight
			FlightInformations.commendation_id = listRequest.get(0).getRecommendationId();
			for (int i = 0; i < listRequest.size();i++){
				CuriosityFare fare = new CuriosityFare();
				FlightInformations tmpFlight = listRequest.get(i);
				FlightInformations.flight_id_1 = StringUtils.substringBetween(tmpFlight.getRow(), "\"", "\"");
				KenyaAirlineRequestForFare result = new KenyaAirlineRequestForFare();
				Response responseDetails = result.getResponseWithCookies(search, cookies);
				String content =  responseDetails.getResponseBody();
				Document document  = Jsoup.parse(content);
				Element table = document.select("html>body>div#container>table.layoutTable>tbody>tr>td.layoutBody>div.container>div#sh_fltItinerary>table").get(0);
				List<CuriositySegment> outbound = Lists.newArrayList();
				try {
					outbound = parseSegments(table);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
				if (outbound.size() == 0)
					continue;
				
				//*** Get Fare ************ //
				Element fareTable = document.select("html>body>div#container>table.layoutTable>tbody>tr")
						.get(1).select("tr>td.layoutBody>div.container>div.sectionHolder>table.tableConfText>tbody>tr>td>table").get(0);
				String tmpFare = fareTable.select("table >tbody>tr").last().select("td>span.textColorBold>span").text().trim();
				String breakFare[] = tmpFare.split(" ");
				String faretmp = breakFare[0].trim().replace(",", "");
				float fareFinal = Float.parseFloat(faretmp);
				
				float pricePerAdult = getMoney(fareTable.select("table >tbody>tr").get(1).select("td").get(8).select("span").toString())/search.getAdultsCount();
				float pricePerChildren, pricePerInfant;
				if (search.getChildrenCount() > 0)
					pricePerChildren = getMoney(fareTable.select("table >tbody>tr").get(2).select("td").get(8).select("span").toString())/search.getChildrenCount();
				else pricePerChildren = 0;
				if (search.getInfantsCount() > 0 && pricePerChildren > 0)
					pricePerInfant = getMoney(fareTable.select("table >tbody>tr").get(3).select("td").get(8).select("span").toString())/search.getInfantsCount();
				else if (search.getInfantsCount() > 0 && search.getChildrenCount() == 0 )
					pricePerInfant = getMoney(fareTable.select("table >tbody>tr").get(2).select("td").get(8).select("span").toString())/search.getInfantsCount();
				else
					pricePerInfant = 0;
				fare.setOutboundSegments(outbound);
				fare.setPrice(fareFinal);
				fare.setPricePerAdult(pricePerAdult);
				if (pricePerChildren > 0)
					fare.setPricePerChild(pricePerChildren);
				fare.setCurrencyCode("USD");
				fares.add(fare);
			}
		}
		return fares;
	}
	
	
	@SuppressWarnings("deprecation")
	private List<CuriositySegment> parseSegments(Element table) throws ParseException{
 		List<CuriositySegment> segments = Lists.newArrayList();
		int countTr = table.child(0).children().size();
		Elements elements = table.child(0).children();
		String resultDate="";
		for (int i = 0; i < countTr; i++){
			CuriositySegment segment = new CuriositySegment();
			Elements tdElement = elements.get(i).children();
			// Get day, month and year informations
			if (tdElement.size() == 2 && tdElement.get(1).children().size() == 0 && tdElement.get(0).className().equals("textBold flight")){
				Element tmp= tdElement.get(1);
				String date = StringUtils.substringBetween(tmp.toString(), ">", "<").trim();
				if (date.length() > 0){
					String analyse[] = date.split(",");
					String dayAndMonth[] = analyse[1].trim().split(" ");
					String day = dayAndMonth[1].trim();
					String month = dayAndMonth[0].trim().substring(0, 3);
					String year = analyse[2].trim();
					resultDate = format2.format(format1.parse(day + " " + month + " " + year)).toString();
				}
			}
			
			if (tdElement.size() == 2 && tdElement.get(1).children().size() > 0){
				Element tableElement = tdElement.get(1).child(0).child(0);
				// For get Flight Number
				String flightInfo = tableElement.children().get(2).children().get(1).text();
				String tmp[] = flightInfo.split(" ");
				String tmpFlightName = tmp[tmp.length - 2];
				String flightName = tmpFlightName.split("s")[1].trim();
				String flightNumber = flightName.replaceAll("[\\D]", "").trim();
				String tmpAirlineCode = flightName.replaceAll(flightNumber, "").trim();
				String carrier = flightInfo.replaceAll(flightName+" e", "").trim();
				String airlineCode = StringUtils.substring(tmpAirlineCode, 1, tmpAirlineCode.length());
				
				Elements infor = tdElement.get(1).child(0).child(0).children();
				Elements tmpElements = infor.get(0).child(0).child(0).child(0).children();
				Elements forDepar = tmpElements.get(0).children();
				String deparTime = resultDate + " " + forDepar.get(1).text().trim();
				String deparName = getNameArrivalorDeparture(forDepar.get(2).text().trim());
				
				Elements forArr = tmpElements.get(1).children();
				String arrTime = resultDate + " " + forArr.get(1).text().trim();
				String arrName = getNameArrivalorDeparture(forArr.get(2).text().trim());
						
				segment.setDepartureName(deparName);
				segment.setDepartureTime(TIME_FORMATTER.parseDateTime(deparTime));
				segment.setArrivalName(arrName);
				segment.setArrivalTime(TIME_FORMATTER.parseDateTime(arrTime));
				segment.setFlightNumber(flightName);
				segment.setAirlineCode(airlineCode);
				segment.setFlightNumber(flightNumber);
				segment.setCarrier(carrier);
				segments.add(segment);
			}
		}
		return segments;
	}
	
	
	// For getting Recommendations IDs for the final request
	private ArrayList<FlightInformations> getRecommendationIds(String jsonString, CuriositySearch search) throws JsonParseException, IOException{
		ArrayList<FlightInformations> result = new ArrayList<FlightInformations>();
		ObjectMapper mapper = new ObjectMapper();
		JsonParser jp = mapper.getFactory().createJsonParser(jsonString);
		JsonNode map = jp.readValueAsTree();
		JsonNode recommendationIds = map.path("list_tab").path("list_recommendation");
		
		if (search.isRoundtrip()){
			for (int i = 0; i  < recommendationIds.size(); i++){
				JsonNode OneFlightInfo = recommendationIds.path(i);
				JsonNode firstList = OneFlightInfo.path("list_bound").get(0).path("list_flight");//.findValue("list_flight");
				
				String checkType = OneFlightInfo.path("list_bound").get(0).findValue("brand_name").toString().replaceAll("\"", "");
				if (checkType.equals("BEST BUY") || checkType.equals("ECONOMY")){
					for (int m = 0; m < firstList.size();m++){
						JsonNode flight = firstList.get(m);
						// Get Flight Number and Flight Row
						FlightInformations data = new FlightInformations();
						String flightNumber = flight.findValue("first_flight_number").toString();
						data.setFlightNumber(flightNumber);
						String flightRow = flight.findValue("flight_id").toString();
						data.setRow(flightRow);
						// Get list of Flight Arrival
						String arrivalType = OneFlightInfo.path("list_bound").get(1).path("fare_family").path("brand_name").toString().replaceAll("\"", "");
						if (arrivalType.equals("BEST BUY") || arrivalType.equals("ECONOMY")){
						JsonNode arrivalFlights = OneFlightInfo.path("list_bound").get(1).path("list_flight");
							for (int j = 0; j < arrivalFlights.size();j++){
								FlightInformations tmp = new FlightInformations();
								JsonNode tmpData = arrivalFlights.path(j);
								tmp.setFlightNumber(tmpData.findValue("first_flight_number").toString());
								tmp.setRow(tmpData.findValue("flight_id").toString());
								data.addArrivalFlight(tmp);
							}
						}
						String recId = StringUtils.substringBetween(recommendationIds.path(i).toString(), "recommendation_id\":\"","\"").trim();
						data.setRecommendationId(recId);
						if (data.getArrivalFlightList().size() > 0)
							result.add(data);
					}
				
				}
			}
			
			if (result.size() >0){
				for (int i = 0; i < result.size();i++){
					FlightInformations first = result.get(i);
					for (int j = i+1; j < result.size();j++){
						FlightInformations second = result.get(j);
						if (first.equals(second) && first.getRecommendatinIdAsInt() < second.getRecommendatinIdAsInt()){	// && first.getType().equals("BEST BY") && second.getType().equals("ECONOMY")
							result.remove(j);
							j--;
						}
					}
				}
				
				for (int i = 0; i < result.size();i++){
					FlightInformations first = result.get(i);
					for (int j = i+1; j < result.size();j++){
						FlightInformations second = result.get(j);
						if (first.getRow().equals(second.getRow())){
							for (int k = 0; k < first.getArrivalFlightList().size();k++){
								for(int l = 0; l < second.getArrivalFlightList().size();l++){
									if (first.getArrivalFlightList().get(k).equals(second.getArrivalFlightList().get(l))){
										second.removeArrivalFlight(l);
										l--;
									}
									
								}
							}
							
						}
					}
				}
			}

		}
		else {	// If being the oneway flight
			JsonNode OneFlightInfo = recommendationIds.path(0);
			JsonNode firstFlight = OneFlightInfo.path("list_bound").get(0).path("list_flight");
			String recommendationId = StringUtils.substringBetween(OneFlightInfo.toString(), "recommendation_id\":\"","\"").trim();
			for (int j = 0; j < firstFlight.size();j++){
				FlightInformations tmp = new FlightInformations();
				JsonNode tmpData = firstFlight.path(j);
				tmp.setFlightNumber(tmpData.findValue("first_flight_number").toString());
				tmp.setRow(tmpData.findValue("flight_id").toString());
				//tmp.setCommendationId(recommendationId);
				tmp.setRecommendationId(recommendationId);
				result.add(tmp);
			}
		}
		
		return result;
	}
	
	private String getNameArrivalorDeparture(String source){
		String array[] = source.split("-");
		return array[0];
	}
	
	private float getMoney(String string){
		String src = StringUtils.substringBetween(string, ">", "<").trim();
		String res[] = src.split(" ");
		String result = res[0].replaceAll(",", "").trim();
		return Float.parseFloat(result);
	}
}
