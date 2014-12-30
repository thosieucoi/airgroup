package com.airgroup.services.impl.japan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.common.collect.Lists;
import com.ning.http.client.Cookie;
import com.ning.http.client.Response;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;
import com.airgroup.util.Utils;

public class JapanAirlineFlight extends SearchFlights{
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat
			.forPattern("yyyyMMdd HH:mm");
	private static final DateTimeFormatter TIME_FORMATTER_NORMAL = DateTimeFormat.forPattern("yyyyMMdd");
	
	@Override
	protected Response getResponse(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		JapanAirlineFirstRequest firstRequest = new JapanAirlineFirstRequest();
		List<Cookie> cookies = firstRequest.getResponse(search).getCookies();
		JapanAirlineSecondRequest secondRequest = new JapanAirlineSecondRequest();
		Response response = secondRequest.getResponseWithCookies(search, cookies);
		return response;
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search)
			throws IOException, InterruptedException, ExecutionException {
		String content = getResponse(search).getResponseBody();
		List<CuriosityFare> fares = Lists.newArrayList();
		String departureString = StringUtils.substringBetween(content, "var depFlightHtml      = [[\"", "\"]];");
		if (departureString == null)
			return null;
		String adultFare = StringUtils.substringBetween(content, "var adultFare  = [[","]]");
		if (adultFare == null)
			return null;
		
		String adultTax = StringUtils.substringBetween(content, "var adultTax   = [[","]]");
		String childFare = StringUtils.substringBetween(content,"var childFare  = [[","]]");
		String childTax = StringUtils.substringBetween(content,"var childTax   = [[","]]");
		String infantFare = StringUtils.substringBetween(content,"var infantFare = [[","]]");
		String infantTax = StringUtils.substringBetween(content,"var infantTax  = [[","]]");
		
		Map<String,Integer> adult = new HashMap<String, Integer>();
		adult = getFareList(adultFare, adultTax);
				
		Map<String,Integer> child = new HashMap<String, Integer>();
		child = getFareList(childFare, childTax);
				
		Map<String,Integer> infant = new HashMap<String, Integer>();
		infant = getFareList(infantFare, infantTax);
		
		// ****************  FOR ROUNDTRIP FLIGHT ***********************//
		if (search.isRoundtrip()){
			String retourString = StringUtils.substringBetween(content, "var ariFlightHtml      = [[\"", "\"]];");
			//*********************** For Outbound and Inbound Flight*******************************//
			String outboundListSegments[] = departureString.split("\",\"");	// OutBOund
			String inboundListSegments[] = retourString.split("\",\"");		// Inbound
			
			// ************ Fare for Adults *******************		
			
			int countOut = 0; // For get row value of Outbound flight
			for (String a: outboundListSegments){
				int countIn = 0;	// For get row value of Inbound flight
				String tmpOut = "<table><tbody>" + a + "</tbody></table>";
				Document docOut = Jsoup.parse(tmpOut);
				List<CuriositySegment> listOutbound = Lists.newArrayList();
				listOutbound = parseSegments(docOut, search,1);
				// Get Fare
				if (listOutbound == null)
					countOut++;
				else if (listOutbound.size() > 0){
					countOut++;
					String keyOut = "" + countOut;//outTmp.getCarrier();
					for(String b : inboundListSegments){
						String tmpIn =  "<table><tbody>" + b + "</tbody></table>";
						Document docIn = Jsoup.parse(tmpIn);
						List<CuriositySegment> listInbound = Lists.newArrayList();
						listInbound = parseSegments(docIn, search,0);
							
						if (listInbound == null)
							countIn++;
						else if (listInbound.size() > 0) {
							countIn++;
							String keyIn = "" + countIn;
							CuriosityFare fare = new CuriosityFare();
							fare.setOutboundSegments(listOutbound);
							fare.setInboundSegments(listInbound);
							String keyToGetFare = keyOut + keyIn;
							// ======== Get Fare ==========================================//
							
							float pricePerAdult = adult.get(keyToGetFare);
							float pricePerChildren = child.get(keyToGetFare);
							float pricePerInfant = infant.get(keyToGetFare);
							float fareValue = search.getAdultsCount() * pricePerAdult
										+ search.getChildrenCount() * pricePerChildren
										+ search.getInfantsCount() * pricePerInfant;
							fare.setAirlineCode("JL");							
							fare.setPrice(fareValue);
							//fare.setPricePerAdult(pricePerAdult);
							fare.setPricePerAdult(pricePerAdult);
							//fare.setPricePerChild(pricePerChildren);
							fare.setPricePerChild(pricePerChildren);
							fare.setCurrencyCode("JPY");
							fare.setInboundSegments(listInbound);
							fares.add(fare);
						}	
					}
				}
			}
			for (int i = 0; i < fares.size();i++){
				if(fares.get(i).getOutboundSegments().size() == 0 || fares.get(i).getInboundSegments().size() ==0 ){
					fares.remove(i);
					i--;
				}
			}
		}
		
		
		// For one way Flight
		else if (!search.isRoundtrip()) {
			String outboundListSegments[] = departureString.split("\",\"");
			int countOut = 0;
			for (String a: outboundListSegments){
				
				String tmpOut = "<table><tbody>" + a + "</tbody></table>";
				Document docOut = Jsoup.parse(tmpOut);
				List<CuriositySegment> listOutbound = Lists.newArrayList();
				listOutbound = parseSegments(docOut, search,1);
				// Get Fare
				if (listOutbound == null)
					countOut++;
				else if (listOutbound.size() > 0){
					countOut++;
					String keyOut = "" + countOut;//outTmp.getCarrier();
					CuriosityFare fare = new CuriosityFare();
					String keyToGetFare = keyOut + "0";
					float pricePerAdult = adult.get(keyToGetFare);
					float pricePerChildren = child.get(keyToGetFare);
					float pricePerInfant = infant.get(keyToGetFare);
					
					float fareValue = search.getAdultsCount() * pricePerAdult
							+ search.getChildrenCount() *  pricePerChildren 
							+ search.getInfantsCount() * pricePerInfant;
					fare.setPrice(fareValue);
					fare.setCurrencyCode("JPY");
					fare.setPricePerAdult(pricePerAdult);
					fare.setPricePerChild(pricePerChildren);
					fare.setOutboundSegments(listOutbound);
					fare.setAirlineCode("JL");
					fares.add(fare);
				}
			}
		}
		
		// Return the fares
		return fares;
	}
	
	
	// ------------ Get List Of oubound segments for roundtrip flight ---------------------------------// 
	private List<CuriositySegment> parseSegments(Document doc, CuriositySearch search, int isOutbound){
		
		List<CuriositySegment> segments = new ArrayList<CuriositySegment>();
		boolean click = false;
		
		for (Element element: doc.select("html > body > table > tbody > tr")){
			CuriositySegment segment = new CuriositySegment();
			Elements elements = element.select("tr > td");
			int elementsSize = elements.size();
			if ((elementsSize == 4 && click == false) || (elementsSize == 6 && click == false)){
				if (seatIsFull(elements.get(0).toString())== true){
					return null;
				}
				if (seatIsFull(elements.get(1).toString())== true){
					return null;
				}
				if (elementsSize == 6){
					// For check conditions
					if (isDisable(elements.get(0).toString()) || isDisable(elements.get(1).toString())){
						return null;
					}
					
					String flightName = StringUtils.substringBetween(elements.get(2).toString(), ">","<").trim();
					if (flightName == null){
						click = true;
					}
					else if (flightName.equals("")){
						String findFlight = elements.get(2).select("a").toString();
						flightName = StringUtils.substringBetween(findFlight,">","<");
					}
					
					
					try {
						String departureName = StringUtils.substringBetween(elements.get(3).toString(), ">","<").trim();
						String tmpDepartureTime = StringUtils.substringBetween(elements.get(3).toString(), "<strong>","</strong>").trim();
						String arrivalName = StringUtils.substringBetween(elements.get(4).toString(), ">","<").trim();
							
						if (arrivalName.equals("")){
							click = true;
						}
						String tmpArrivalTime = StringUtils.substringBetween(elements.get(4).toString(), "<strong>","</strong>").split(" ")[0].trim();
						// If the
						int cmpDepar = Integer.parseInt(tmpDepartureTime.split(":")[0]);
						int cmpArr = Integer.parseInt(tmpArrivalTime.split(":")[0]);
						String departureTime;
						String arrivalTime;
						if (isOutbound == 1){
							if (segments.size() == 0){
								departureTime = TIME_FORMATTER_NORMAL.print(search.getOutboundDate() ) + " " + tmpDepartureTime;
							}
							else {
								departureTime = segments.get(segments.size()-1).getArrivalTime().toString();
							}
							
							if (cmpDepar > cmpArr){
								int tmpY = Integer.parseInt(StringUtils.substring(departureTime, 0, 4));
								int tmpM = Integer.parseInt(StringUtils.substring(departureTime, 4, 6));
								int tmpD = Integer.parseInt(StringUtils.substring(departureTime, 6, 8));
								if ((tmpD == 30)  && (tmpM == 1 || tmpM == 3 || tmpM == 5 || tmpM == 7 || tmpM == 8 || tmpM ==10 || tmpM ==12)){
									tmpD++;
								}
								else if ((tmpD == 31)  && (tmpM == 1 || tmpM == 3 || tmpM == 5 || tmpM == 7 || tmpM == 8 || tmpM ==10 || tmpM ==12)){
									tmpD = 1;
									tmpM++;
								}
								else if (tmpM == 12 && (tmpD == 31)){
									tmpY++;
									tmpM = 1;
									tmpD = 1;
								}
								else if ((tmpD == 30) && (tmpM == 4 || tmpM == 6 || tmpM == 9 || tmpM == 11) && (tmpM < 12)){
									tmpD = 1;
									tmpM++;
								}
								else if ((tmpD==28) && (tmpM==2)){
									tmpD = 1;
									tmpM++;
								}
								else {
									tmpD++;
								}
								
								String yResult,mResult,dResult;
								if (tmpY < 10)
									yResult = "0"+tmpY;
								else 
									yResult = ""+tmpY;
								if (tmpM < 10)
									mResult = "0"+tmpM;
								else 
									mResult = ""+tmpM;
								if(tmpD < 10)
									dResult = "0" + tmpD;
								else 
									dResult = "" + tmpD;
								arrivalTime = yResult+ mResult+dResult + " " + tmpArrivalTime;
								
							}
							else {
								arrivalTime = TIME_FORMATTER_NORMAL.print(search.getOutboundDate()) + " " + tmpArrivalTime;
							}
						}
						else {
							//if (segments.size() == 0){
								departureTime = TIME_FORMATTER_NORMAL.print(search.getInboundDate() ) + " " + tmpDepartureTime;
							//}
							//else {
								//departureTime = segments.get(segments.size()-1).getArrivalTime().toString();
							//}
							
							if (cmpDepar > cmpArr){
								int tmpY = Integer.parseInt(StringUtils.substring(departureTime, 0, 4));
								int tmpM = Integer.parseInt(StringUtils.substring(departureTime, 4, 6));
								int tmpD = Integer.parseInt(StringUtils.substring(departureTime, 6, 8));
								if ((tmpD == 30)  && (tmpM == 1 || tmpM == 3 || tmpM == 5 || tmpM == 7 || tmpM == 8 || tmpM ==10 || tmpM ==12)){
									tmpD++;
								}
								else if ((tmpD == 31)  && (tmpM == 1 || tmpM == 3 || tmpM == 5 || tmpM == 7 || tmpM == 8 || tmpM ==10 || tmpM ==12)){
									tmpD = 1;
									tmpM++;
								}
								else if (tmpM == 12 && (tmpD == 31)){
									tmpY++;
									tmpM = 1;
									tmpD = 1;
								}
								else if ((tmpD == 30) && (tmpM == 4 || tmpM == 6 || tmpM == 9 || tmpM == 11) && (tmpM < 12)){
									tmpD = 1;
									tmpM++;
								}
								else if ((tmpD==28) && (tmpM==2)){
									tmpD = 1;
									tmpM++;
								}
								else {
									tmpD++;
								}
								
								String yResult,mResult,dResult;
								if (tmpY < 10)
									yResult = "0"+tmpY;
								else 
									yResult = ""+tmpY;
								if (tmpM < 10)
									mResult = "0"+tmpM;
								else 
									mResult = ""+tmpM;
								if(tmpD < 10)
									dResult = "0" + tmpD;
								else 
									dResult = "" + tmpD;
								arrivalTime = yResult+ mResult+dResult + " " + tmpArrivalTime;
							}
							else {
								arrivalTime = TIME_FORMATTER_NORMAL.print(search.getInboundDate()) + " " + tmpArrivalTime;
							}
						}
							
						//departureTime = TIME_FORMATTER_NORMAL.print(search.getOutboundDate() ) + " " + tmpDepartureTime;
						segment = parseSegment(departureName, arrivalName, flightName, departureTime, arrivalTime);
					} catch (NullPointerException e) {
						// TODO: handle exception
						click = true;
					}
				}
				
				else if (elementsSize == 4){
					String flightName = StringUtils.substringBetween(elements.get(1).toString(), ">","<").trim();
					String tmpTime=null;
					if (flightName.equals("")){
						try {
							String findFlight = elements.get(1).select("a").toString();
							flightName = StringUtils.substringBetween(findFlight,">","<");
							String data = StringUtils.substringBetween(findFlight,"(",")");
							String tmpData[] = data.split(",");
							tmpTime = tmpData[1].replaceAll("\'", "");
						} catch (NullPointerException e) {
							click = true;
						}
						
					}
					
					try {
						String departureName = StringUtils.substringBetween(elements.get(2).toString(), ">","<").trim();
						String arrivalName = StringUtils.substringBetween(elements.get(3).toString(), ">","<").trim();
						String tmpDepartureTime = StringUtils.substringBetween(elements.get(2).toString(), "<strong>","</strong>").trim();
						String tmpArrivalTime = StringUtils.substringBetween(elements.get(3).toString(), "<strong>","</strong>").trim();
						if (tmpTime == null && isOutbound==1){
							String departureTime = TIME_FORMATTER_NORMAL.print(search.getOutboundDate()) + " " + tmpDepartureTime;
							String arrivalTime = TIME_FORMATTER_NORMAL.print(search.getOutboundDate()) + " " + tmpArrivalTime;
							segment = parseSegment(departureName, arrivalName, flightName, departureTime, arrivalTime);
						}
						else if (tmpTime == null && isOutbound==0){
							String departureTime = TIME_FORMATTER_NORMAL.print(search.getInboundDate()) + " " + tmpDepartureTime;
							String arrivalTime = TIME_FORMATTER_NORMAL.print(search.getInboundDate()) + " " + tmpArrivalTime;
							segment = parseSegment(departureName, arrivalName, flightName, departureTime, arrivalTime);
						}
						else {
							String departureTime = tmpTime + " " + tmpDepartureTime;
							String arrivalTime = tmpTime + " " + tmpArrivalTime;
							segment = parseSegment(departureName, arrivalName, flightName, departureTime, arrivalTime);
						}
					} catch (Exception e) {
						click = true;
					}
					
				}
				
				if (click == false){
					segments.add(segment);
				}
				else {
					List<CuriositySegment>tmpSegments = Lists.newArrayList();
					return tmpSegments;
				}
			}
		}
		if (segments.size() >= 2) {
			for (int i = 0; i <= segments.size() - 2; ++i) {
				CuriositySegment segment = segments.get(i);
				CuriositySegment nextSegment = segments.get(i + 1);
				if (segment.getArrivalTime().isAfter(nextSegment.getDepartureTime())) {
					nextSegment.setDepartureTime(TIME_FORMATTER, TIME_FORMATTER.print(nextSegment.getDepartureTime().plusDays(1)));
					nextSegment.setArrivalTime(TIME_FORMATTER, TIME_FORMATTER.print(nextSegment.getArrivalTime().plusDays(1)));
				}
			}
		}
		return segments;
	}
	
	
	// Test Function
	
	
	// ************** Search Is full **********************************
	private boolean seatIsFull(String tdTag){
		if (tdTag.contains("Full") || tdTag.contains("depFilledSeat"))
			return true;
		return false;
	}
	
	private boolean isDisable(String tdTag){
		return tdTag.contains("[-]");
	}
	
	//===================== Parse One Segment =========================================================//
	private CuriositySegment parseSegment(String departureName, String arrivalName, String flightInfo, 
			String departureTime, String arrivalTime) {
		CuriositySegment segment = new CuriositySegment();
		segment.setDepartureName(departureName);
		segment.setArrivalName(arrivalName);
		//segment.setFlightNumber(flightNumber);
		segment.setDepartureTime(
			TIME_FORMATTER,
			Utils.parseDate(departureTime, "yyyyMMdd HH:mm"));
		segment.setArrivalTime(
			TIME_FORMATTER,
			Utils.parseDate(arrivalTime, "yyyyMMdd HH:mm"));
		String tmpFlight = flightInfo;
		String flightNumber = flightInfo.replaceAll("[\\D]", "").trim();
		String airlineCode = tmpFlight.replaceAll(flightNumber, "");
		segment.setAirlineCode(airlineCode);
		segment.setFlightNumber(flightNumber);
		return segment;
	}	
	
	
	//*************************** Parsing Fare Array from HTML to HashMap *********************************//
	private HashMap<String, Integer> getFareList(String tmpGetFare, String tmpGetTax){
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		String fare[] = tmpGetFare.split(",");
		String tax[] = tmpGetTax.split(",");
		int leng = fare.length;
		for (int i = 0; i < leng;i++){
			fare[i] = fare[i].replaceAll("\"", "");
			tax[i] = tax[i].replaceAll("\"", "");
			String tmpFare[] = fare[i].split("@");
			String hashmapKey = tmpFare[0]+ tmpFare[1];
			String tmpTax[] = tax[i].split("@");
			int hashmapValue = Integer.parseInt(tmpFare[2]) + Integer.parseInt(tmpTax[2]) ;
			result.put(hashmapKey, hashmapValue);
		}
		return result;
	}
}



