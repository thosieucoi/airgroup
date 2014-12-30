package com.airgroup.impl.japan;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ning.http.client.Response;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;
import com.airgroup.util.Utils;

public class JapanFlight extends SearchFlights{
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat
			.forPattern("yyyyMMdd HH:mm");
	private static final DateTimeFormatter TIME_FORMATTER_NORMAL = DateTimeFormat.forPattern("yyyyMMdd");
	
	@Override
	protected Response getResponse(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		JapanAirlinesStart request = new JapanAirlinesStart();
		Response requestResponse = request.getResponse(search);
		return requestResponse;
	}
	
	@Override
	public List<CuriosityFare> getFare(CuriositySearch search)
			throws IOException, InterruptedException, ExecutionException {
		String content = getResponse(search).getResponseBody();
		Document document = Jsoup.parse(content);	
		//System.out.println(document);
		
		// The value returned 
		List<CuriosityFare> fares = Lists.newArrayList();
		
		if (search.isRoundtrip()){
			List<CuriosityFare> outboundFares = Lists.newArrayList();
			List<CuriosityFare> inboundFares = Lists.newArrayList();
			

			ObjectMapper mapper = new ObjectMapper();
			JsonParser jp = mapper.getFactory().createJsonParser(convertToJSONString(content));
			JsonNode map = jp.readValueAsTree();
			//JsonNode jsonNode = map.path("JAL101");
			
			//List<CuriosityFare> fares = Lists.newArrayList();	// For save the fares 
			List<CuriositySegment> departureSegments = getOutboundSegements(document, search);
			List<CuriositySegment> retourSegments = getInboundSegments(document, search);
			
			/*---------------------------Get Fares of all Retours Flights
			  													          ---------------------------------*/
			CuriositySegment firstSegment = departureSegments.get(0);
			//System.out.println(firstSegment.getFlightNumber());
			JsonNode jsonNode = map.path(firstSegment.getFlightNumber());
			
			// Set Inbound Fare
			for (CuriositySegment tmp : retourSegments){
				JsonNode node = jsonNode.path(tmp.getFlightNumber()).path(tmp.getFlightNumber());
				String fareYY = node.path("YY").get("fare").toString();
				//String fareYJ = node.path("YJ").get("fare").toString();
				//String fareJY = node.path("JY").get("fare").toString();
				//String fareJJ = node.path("JJ").get("fare").toString();
				CuriosityFare ibFare = new CuriosityFare();
				ibFare.setPrice(Utils.floatFromString(fareYY));
				List<CuriositySegment> temp = Lists.newArrayList();
				temp.add(tmp);
				ibFare.setInboundSegments(temp);
				inboundFares.add(ibFare);
			}
			
			// Set outbound Fare
			CuriositySegment retourForCheck = retourSegments.get(0);	
			for (CuriositySegment tmp : departureSegments){
				String fareYY = map.path(tmp.getFlightNumber())
						.path(retourForCheck.getFlightNumber())
						.path(tmp.getFlightNumber())
						.path("YY").path("fare").toString();
				CuriosityFare obFare = new CuriosityFare();
				obFare.setPrice(Utils.floatFromString(fareYY));
				List<CuriositySegment> temp = Lists.newArrayList();
				temp.add(tmp);
				obFare.setOutboundSegments(temp);
				outboundFares.add(obFare);
			}
			
			fares.addAll(outboundFares);
			fares.addAll(inboundFares);
		}
		else {
			List<CuriositySegment> segments = Lists.newArrayList();
			segments = getOnewayFlightSegments(document, search);
			ObjectMapper mapper = new ObjectMapper();
			JsonParser jp = mapper.getFactory().createJsonParser(convertToJSONString(content));
			JsonNode map = jp.readValueAsTree();
			for (CuriositySegment segment : segments){
				List<CuriositySegment> temp = Lists.newArrayList(); // For flight and
				JsonNode node = map.path(segment.getFlightNumber()).path(segment.getFlightNumber());
				String fareY = node.path("Y").path("fare").toString();
				CuriosityFare fare = new CuriosityFare();
				fare.setPrice(Utils.floatFromString(fareY));
				temp.add(segment);
				fare.setOutboundSegments(temp);
				fares.add(fare);
			}
		}
		return fares;
	}
	
	
		
	// ============== Adding Segment =========================================================//
	
	private CuriositySegment parseSegments(String departureTime, String arrivalTime, String airlineCode,
				String flightNumber, 
				String departureCode, String arrivalCode) {

			CuriositySegment segment = new CuriositySegment();
			segment.setAirlineCode(airlineCode);
			segment.setFlightNumber(flightNumber);
			segment.setDepartureCode(departureCode);
			segment.setArrivalCode(arrivalCode);

			segment.setDepartureTime(
				TIME_FORMATTER,
				Utils.parseDate(departureTime, "yyyyMMdd HH:mm"));
			segment.setArrivalTime(
				TIME_FORMATTER,
				Utils.parseDate(arrivalTime, "yyyyMMdd HH:mm"));
		return segment;
	}
	
	
	// ========= Get Segments of Departure Flights ==========================================//
	
	private List<CuriositySegment> getOutboundSegements(Document document, CuriositySearch search){
		List<CuriositySegment> outboundSegments = Lists.newArrayList();
		for (Element element : document.select("div.bookBtm > form > div.floLef > table.tableA01 > tbody > tr")){
			// For segment in each flight
			CuriositySegment segment = new CuriositySegment();
			
			//============ Get Departure time =========================
			String departureTime = TIME_FORMATTER_NORMAL.print(search.getOutboundDate()) + " " + element.select("td.tFr").first().text();
			
			// ========================= Get Arrival Time =============================================//
			String[] tmpArrivalTime = element.select("td.tTo")
					.first()
					.text()
					.split(" ");
			
			String arrivalTime =  TIME_FORMATTER_NORMAL.print(search.getOutboundDate()) + " " + tmpArrivalTime[0];
			
			// ======== Get others informations ================================
			String allInformation = element.select("td.tEc > span.clearfix > span.floLef > input").toString();
			String tmpAllInformation = StringUtils.substringBetween(allInformation, "[", "]");
			String tmpInformations = tmpAllInformation.replaceAll("'", "");

			// ============== Get Datas of flight ===============================
			String [] result = tmpInformations.split(",");
			String flightCode = result[0].replaceAll("\\s","");
			String departureLocationCode = result[3].replaceAll("\\s","");
			String arrivalLocationCode = result[4].replaceAll("\\s","");
			//String departureLocationName = result[5].replaceAll("\\s","");
			//String arrivalLocationName = result[6].replaceAll("\\s","");
			segment = parseSegments(departureTime, arrivalTime, flightCode, flightCode, departureLocationCode, arrivalLocationCode);
			outboundSegments.add(segment);
			
		}
		return outboundSegments;
	}
	
	
	// ============ Get segments of Arrival Flights ===============================
	private List<CuriositySegment> getInboundSegments(Document document, CuriositySearch search){
		List<CuriositySegment> inboundSegments = Lists.newArrayList(); // For save information of returned flights
		for (Element element : document.select("div.bookBtm > form > div.floRig > table.tableA01 > tbody > tr")){
			CuriositySegment segment = new CuriositySegment();
			// Departuture time 
			String[] tmpDepartureTime =  element.select("td.tFr").first().text().split(" ");
			String departureTime = TIME_FORMATTER_NORMAL.print(search.getInboundDate()) + " " + tmpDepartureTime[0];
			
			// Get arrival time
			String[]  tmpArrivalTime = element.select("td.tTo").first().text().split(" ");
			String arrivalTime =  TIME_FORMATTER_NORMAL.print(search.getOutboundDate()) + " " + tmpArrivalTime[0];
			
			// Get Data Flights Informations
			String allInformation = element.select("td.tEc > span.clearfix > span.floLef > input").toString();
			String tmpAllInformation = StringUtils.substringBetween(allInformation, "[", "]");
			String tmpInformations = tmpAllInformation.replaceAll("'", "");
			String [] result = tmpInformations.split(",");
			String flightCode = result[0].replaceAll("\\s","");
			String departureLocationCode = result[3].replaceAll("\\s","");
			String arrivalLocationCode = result[4].replaceAll("\\s","");
			//String departureLocationName = result[5].replaceAll("\\s","");
			//String arrivalLocationName = result[6].replaceAll("\\s","");
			segment = parseSegments(departureTime, arrivalTime, flightCode, flightCode, departureLocationCode, arrivalLocationCode);
			inboundSegments.add(segment);
			
		}
		return inboundSegments;
	}
	
	//========================Get Segments for oneway flight ===============================//
	
	private List<CuriositySegment> getOnewayFlightSegments(Document document, CuriositySearch search){
		List<CuriositySegment> segments = Lists.newArrayList();
		for (Element element:document.select("body > div[id=wrap] > div[id=contents] > div.bookBtm > form > div.floNon > table.tableA01 > tbody > tr") ){
			CuriositySegment segment = new CuriositySegment();
			// Departuture time 
			String[] tmpDepartureTime =  element.select("td.tFr").first().text().split(" ");
			String departureTime = TIME_FORMATTER_NORMAL.print(search.getInboundDate()) + " " + tmpDepartureTime[0];
			//System.out.println(departureTime);
			// Get arrival time
			String[]  tmpArrivalTime = element.select("td.tTo").first().text().split(" ");
			String arrivalTime =  TIME_FORMATTER_NORMAL.print(search.getOutboundDate()) + " " + tmpArrivalTime[0];
			//System.out.println(arrivalTime);
			//=================== Get all Informations =====================================//
			String allInformation = element.select("td.tEc > span.clearfix > span.floLef > input").toString();
			String tmpAllInformation = StringUtils.substringBetween(allInformation, "[", "]");
			String tmpInformations = tmpAllInformation.replaceAll("'", "");
			String [] result = tmpInformations.split(",");
			String flightCode = result[0].replaceAll("\\s","");
			String departureLocationCode = result[3].replaceAll("\\s","");
			String arrivalLocationCode = result[4].replaceAll("\\s","");
			segment = parseSegments(departureTime, arrivalTime, flightCode, flightCode, departureLocationCode, arrivalLocationCode);
			segments.add(segment);
			
		}
		return segments;
	}
	
	
	//  ===================  Convert data to JSON  =========================================//
	
	private String convertToJSONString(String content){
		String tmp1 = StringUtils.substringBetween(content, "JLJS_LFSData=", ";");
		String tmp2 = StringUtils.deleteWhitespace(tmp1);
		int index = 0;
		int length = tmp2.length();
		for (int i = 0; i < length;i++){
			if (tmp2.charAt(i) == ','){
				index = i+1;
				break;
			}
		}
		
		tmp1 = tmp2.substring(index);
		tmp1 = "{" + tmp1;
		StringBuffer str1 = new StringBuffer(tmp1);
		for (int i = 0; i < str1.length();i++){
			if (str1.charAt(i) == '{'){
				str1.insert(i+1, '\"');
				i++;
				length++;
			}
			else if (str1.charAt(i) == ':'){
				str1.insert(i, '\"');
				i++;
				length++;
			}
			else if (str1.charAt(i) == ','){
				str1.insert(i+1, '\"');
				i++;
				length++;
			}
		}
		return str1.toString();
	}
}
