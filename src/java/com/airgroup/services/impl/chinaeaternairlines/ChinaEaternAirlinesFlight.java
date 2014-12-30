package com.airgroup.services.impl.chinaeaternairlines;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ning.http.client.Response;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;

public class ChinaEaternAirlinesFlight extends SearchFlights {

	private static final DateTimeFormatter PARSER_DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("yyyy-MM-dd HH:mm");

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		ChinaEaternAirlinesFlightResponse response = new ChinaEaternAirlinesFlightResponse();
		return response.getResponse(search);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		try{
		String content = getResponse(search).getResponseBody();
		//System.err.print(content);
		ObjectMapper mapper = new ObjectMapper();
		JsonParser jp = mapper.getFactory().createJsonParser(content.split(" = ")[1]);
		JsonNode map = jp.readValueAsTree();
		List<CuriosityFare> fares = Lists.newArrayList();
		JsonNode FlightItineraryList = map.findValue("airRoutingList");
		if (FlightItineraryList.size()>0) {
			for (JsonNode jsonNodeFlight : FlightItineraryList) {
				String cabin = "";
				if (jsonNodeFlight.findValue("priceDisp").findValue("economy").textValue()!="") {
					cabin="economy";
				}else if (jsonNodeFlight.findValue("priceDisp").findValue("business").textValue()!="") {
					cabin="business";
				}
				if (cabin!="") {
					Float priceAmountAdt=0F;
					Float priceAmountChd=0F;
					String currency = map.findValue("currency").textValue();
					for (JsonNode jsonNode : jsonNodeFlight.findValue("productInfoList")) {
						if (jsonNode.findValue("baseCabin").textValue().equalsIgnoreCase(cabin)) {
							priceAmountAdt = Float.parseFloat(jsonNode.findValue("priceAmountAdt").textValue());
							priceAmountChd = Float.parseFloat(jsonNode.findValue("priceAmountChd").textValue());
						}
					}
//					String sessionId = map.findValue("sessionId").textValue();
//					search.createParam("sessionId", sessionId);
//					ChinaEaternAirlinesFlightResponseSecond responseSecond = new ChinaEaternAirlinesFlightResponseSecond();
//					String contentSecond = responseSecond.getResponse(search).getResponseBody();
//					System.err.print(contentSecond);
					
					CuriosityFare fare = CuriosityFare.newFromTrip(search);
					Boolean check = true;
					List<CuriositySegment> outboundSegments = Lists.newArrayList();;
					List<CuriositySegment> inboundSegments = Lists.newArrayList();;
					for (JsonNode flightSegments : jsonNodeFlight.findValue("flightList")){
						
						if (search.isRoundtrip()) {
							if (flightSegments.findValue("deptCd").textValue().equalsIgnoreCase(search.getDepartureCode())) {
								check = true;
							}
							if (flightSegments.findValue("deptCd").textValue().equalsIgnoreCase(search.getArrivalCode())) {
								check = false;
							}
							if (check == true) {
								outboundSegments.add(parseSegments(flightSegments));
							}else if (check == false) {
								inboundSegments.add(parseSegments(flightSegments));
							}
						}else{
							outboundSegments.add(parseSegments(flightSegments));
						}
					}
					fare.setOutboundSegments(outboundSegments);
					if (inboundSegments.size()>0) {
						fare.setInboundSegments(inboundSegments);
					}
					Float priceTotal = (priceAmountAdt * search.getAdultsCount() + priceAmountChd*search.getChildrenCount()) * 1.67F ;
					fare.setPrice(priceTotal);
					fare.setCurrencyCode(currency);
					fare.setPricePerAdult(priceAmountAdt* 1.67F);
					if (search.getChildrenCount()>0) {
						fare.setPricePerChild(priceAmountChd* 1.67F);
					}
					fare.setAirlineCode("MU");
					fares.add(fare);
				}
			}
		}
		return fares;
		}catch(Exception e){
			return null;
		}
	}

	private CuriositySegment parseSegments(JsonNode elementJson) {

			CuriositySegment segment = new CuriositySegment();
			segment.setDepartureCode(elementJson.findValue("deptCd").textValue());
			segment.setArrivalCode(elementJson.findValue("arrCd").textValue());

			segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER,elementJson.findValue("arrTime").textValue());
			segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER,elementJson.findValue("deptTime").textValue());
			segment.setAirlineCode(elementJson.findValue("carrier").textValue());
			segment.setFlightNumber(elementJson.findValue("flightNo").textValue().substring(2));
		
		return segment;
	}
}
