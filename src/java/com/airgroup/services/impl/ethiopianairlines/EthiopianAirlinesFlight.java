package com.airgroup.services.impl.ethiopianairlines;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ning.http.client.Response;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;

public class EthiopianAirlinesFlight extends SearchFlights {

	private static final DateTimeFormatter PARSER_DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("yyyy/MM/dd HH:mm:ss");
	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		EthiopianAirlinesFlightResponseFirst response = new EthiopianAirlinesFlightResponseFirst();
		return response.getResponse(search);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		try{
		List<CuriosityFare> fareDepartures = Lists.newArrayList();
		List<CuriosityFare> fareArrvials = Lists.newArrayList();
		List<CuriosityFare> fares = Lists.newArrayList();
		
		Response result = getResponse(search);
		Document document = Jsoup.parse(result.getResponseBody());
		String jsessionId = StringUtils.substringBetween(result.getHeaders().toString(), "Set-Cookie=", ";");
		search.createParam("jid", jsessionId);
		EthiopianAirlinesFlightResponseSecond responseSecond = new EthiopianAirlinesFlightResponseSecond();
		Response resultSecond = responseSecond.getResponse(search);
		String contentSecond = resultSecond.getResponseBody();
//		System.err.print(resultSecond.getHeaders());
		String jsessionIdSecond = StringUtils.substringBetween(resultSecond.getHeaders().toString(), "Set-Cookie=", ";");
		search.getParams().clear();
		search.createParam("jsessionId", jsessionIdSecond);
		EthiopianAirlinesFlightResponseThird responseThird = new EthiopianAirlinesFlightResponseThird();
		Response resultThird = responseThird.getResponseWithCookies(search, resultSecond.getCookies());
		String contentThird = resultThird.getResponseBody();
		Document documentThird = Jsoup.parse(contentThird);
		//System.err.print(documentThird);
		String dataContent = StringUtils.substringBetween(contentThird, "var templateData = {", "};");
		//System.err.print(dataContent);
		ObjectMapper mapper = new ObjectMapper();
		JsonParser jp = mapper.getFactory().createJsonParser(dataContent.replace("\"rootElement\":", ""));
		JsonNode map = jp.readValueAsTree();
		JsonNode flightItineraryList = map.findValue("offers");
		if (flightItineraryList==null) {
			JsonNode outboundFlights = map.findValue("outbounds");
			
			for (JsonNode outbound : outboundFlights) {
				//System.err.print(outbound);
				CuriosityFare fare = new CuriosityFare();
				List<CuriositySegment> outboundSegments = parseSegmentsJson(outbound);
				fare.setOutboundSegments(outboundSegments);
				Float price = 0F;
				String currency = "";
				JsonNode priceNode = outbound.findValue("ES");
				if (priceNode!=null) {
					price = Float.parseFloat(priceNode.findValue("amount").textValue());
					currency =priceNode.findValue("code").textValue();
				}else{
					priceNode = outbound.findValue("EF");
					if (priceNode!=null) {
						price = Float.parseFloat(priceNode.findValue("amount").textValue());
						currency =priceNode.findValue("code").textValue();
					}else{
						priceNode = outbound.findValue("CD");
						if (priceNode!=null) {
							price = Float.parseFloat(priceNode.findValue("amount").textValue());
							currency =priceNode.findValue("code").textValue();
						}
					}
				}
				fare.setCurrencyCode(currency);
				fare.addPrice(price);
				fare.setPricePerAdult(price/search.getPassengersCount());
				if (search.getChildrenCount()>0) {
					fare.setPricePerChild(price/search.getPassengersCount());
				}
				fare.setAirlineCode("ET");
				fareDepartures.add(fare);
			}
			if (search.isRoundtrip()) {
				JsonNode inboundFlights = map.findValue("inbounds");
				for (JsonNode inbound : inboundFlights) {
					CuriosityFare fare = new CuriosityFare();
					List<CuriositySegment> inboundSegments = parseSegmentsJson(inbound);
					fare.setInboundSegments(inboundSegments);
					Float price = 0F;
					String currency = "";
					JsonNode priceNode = inbound.findValue("ES");
					if (priceNode!=null) {
						price = Float.parseFloat(priceNode.findValue("amount").textValue());
						currency =priceNode.findValue("code").textValue();
					}else{
						priceNode = inbound.findValue("EF");
						if (priceNode!=null) {
							price = Float.parseFloat(priceNode.findValue("amount").textValue());
							currency =priceNode.findValue("code").textValue();
						}else{
							priceNode = inbound.findValue("CD");
							if (priceNode!=null) {
								price = Float.parseFloat(priceNode.findValue("amount").textValue());
								currency =priceNode.findValue("code").textValue();
							}
						}
					}
					fare.addPrice(price);
					fare.setCurrencyCode(currency);
					fareArrvials.add(fare);
				}
			}
			if (fareArrvials.size()>0) {
				for (CuriosityFare fareDep : fareDepartures) {
					for (CuriosityFare fareArr : fareArrvials) {
						CuriosityFare fare = new CuriosityFare();
						fare.setOutboundSegments(fareDep.getOutboundSegments());
						fare.setInboundSegments(fareArr.getInboundSegments());
						Float price = fareDep.getPrice() + fareArr.getPrice();
						fare.setPricePerAdult(price/search.getPassengersCount());
						if (search.getChildrenCount()>0) {
							fare.setPricePerChild(price/search.getPassengersCount());
						}
						fare.setPrice(price);
						fare.setCurrencyCode(fareDep.getCurrencyCode());
						fare.setAirlineCode("ET");
						fares.add(fare);
					}
				}
			}else{
				fares.addAll(fareDepartures);
			}
		}else{
		if (flightItineraryList.size()>0) {
			for (JsonNode jsonNodeFlight : flightItineraryList) {
				//System.err.print(jsonNodeFlight);
				
				JsonNode flightParts = jsonNodeFlight.findValue("parts");
				CuriosityFare fare = new CuriosityFare();
				if (flightParts.size()==1) {
					List<CuriositySegment> outboundSegments = parseSegmentsJson(flightParts.get(0));
					fare.setOutboundSegments(outboundSegments);
				}else if (flightParts.size()==2) {
					List<CuriositySegment> outboundSegments = parseSegmentsJson(flightParts.get(0));
					List<CuriositySegment> inboundSegments = parseSegmentsJson(flightParts.get(1));
					fare.setOutboundSegments(outboundSegments);
					fare.setInboundSegments(inboundSegments);
				}
				Float price = Float.parseFloat(jsonNodeFlight.findValue("offerPrices").findValue("amount").textValue());
				Float priceForAdult = price*search.getAdultsCount();
				Float priceForChild = 0F;
				Float priceForInf = 0F;
				if (search.getChildrenCount()>0) {
					priceForChild = price*0.81F*search.getChildrenCount();
				}
				if (search.getInfantsCount()>0) {
					priceForInf = price*0.1F*search.getInfantsCount();
				}
				fare.setPrice(priceForAdult+priceForChild+priceForInf);
				fare.setCurrencyCode("ETB");
				fare.setAirlineCode("ET");
				fare.setPricePerAdult(price);
				fare.setPricePerChild(priceForChild);
				fares.add(fare);
			}
		}else{
		
		//System.err.print(flightItineraryList);
		Elements depElements = documentThird.select("div#dtcontainer-outbounds");
		if (depElements.size()>0) {
			Elements depFlightInfo = depElements.get(0).select("table>tbody>tr[id~=tr-outbounds]");
			for (Element depElement : depFlightInfo) {
				List<CuriositySegment> outboundSegments = parseSegments(depElement, search);
				CuriosityFare fare = new CuriosityFare();
				fare.setOutboundSegments(outboundSegments);
				Float price = 0F;
				String currency = "";
				if (!depElement.select("td").get(5).text().equalsIgnoreCase("--")) {
					price = Float.parseFloat(depElement.select("td").get(5).text().split("\u00A0")[0]);
					currency = depElement.select("td").get(5).text().split("\u00A0")[1].trim().substring(0,3);
				}else if (!depElement.select("td").get(6).text().equalsIgnoreCase("--")){
					price = Float.parseFloat(depElement.select("td").get(6).text().split("\u00A0")[0]);
					currency = depElement.select("td").get(6).text().split("\u00A0")[1].trim().substring(0,3);
				}else if (!depElement.select("td").get(7).text().equalsIgnoreCase("--")) {
					price = Float.parseFloat(depElement.select("td").get(7).text().split("\u00A0")[0]);
					currency = depElement.select("td").get(7).text().split("\u00A0")[1].trim().substring(0,3);
				}
				fare.setPrice(price);
				fare.setCurrencyCode(currency);
				fareDepartures.add(fare);
			}
			
			if (search.isRoundtrip()) {
				Elements arrElements = documentThird.select("div#dtcontainer-inbounds");
				Elements arrFlightInfo = arrElements.get(0).select("table>tbody>tr[id~=tr-inbounds]");
				for (Element arrElement : arrFlightInfo) {
					List<CuriositySegment> inboundSegments = parseSegments(arrElement, search);
					CuriosityFare fare = new CuriosityFare();
					fare.setInboundSegments(inboundSegments);
					Float price = 0F;
					String currency = "";
					if (!arrElement.select("td").get(5).text().equalsIgnoreCase("--")) {
						price = Float.parseFloat(arrElement.select("td").get(5).text().split("\u00A0")[0]);
						currency = arrElement.select("td").get(5).text().split("\u00A0")[1].trim().substring(0,3);
					}else if (!arrElement.select("td").get(6).text().equalsIgnoreCase("--")){
						price = Float.parseFloat(arrElement.select("td").get(6).text().split("\u00A0")[0]);
						currency = arrElement.select("td").get(6).text().split("\u00A0")[1].trim().substring(0,3);
					}else if (!arrElement.select("td").get(7).text().equalsIgnoreCase("--")) {
						price = Float.parseFloat(arrElement.select("td").get(7).text().split("\u00A0")[0]);
						currency = arrElement.select("td").get(7).text().split("\u00A0")[1].trim().substring(0,3);
					}
					fare.setPrice(price);
					fare.setCurrencyCode(currency);
					fareArrvials.add(fare);
				}
			}
			
			if (search.isRoundtrip()) {
				for (CuriosityFare depFare : fareDepartures) {
					for (CuriosityFare arrFare : fareArrvials) {
						CuriosityFare fare = new CuriosityFare();
						fare.setOutboundSegments(depFare.getOutboundSegments());
						fare.setInboundSegments(arrFare.getInboundSegments());
						fare.setCurrencyCode(depFare.getCurrencyCode());
						fare.setPrice(depFare.getPrice() + arrFare.getPrice());
						fares.add(fare);
					}
				}
			}else{
				fares.addAll(fareDepartures);
			}
			
		}else if(documentThird.select("div#dtcontainer-both").size()>0 && !search.isRoundtrip()){
			Elements flightElements = documentThird.select("div#dtcontainer-both").get(0).select("table>tbody>tr[id~=tr-both]");
			for (Element depElement : flightElements) {
				List<CuriositySegment> outboundSegments = parseSegments(depElement, search);
				CuriosityFare fare = new CuriosityFare();
				fare.setOutboundSegments(outboundSegments);
				Float price = 0F;
				String currency = "";
				if (!depElement.select("td").get(5).text().equalsIgnoreCase("--")) {
					price = Float.parseFloat(depElement.select("td").get(5).text().split("\u00A0")[0]);
					currency = depElement.select("td").get(5).text().split("\u00A0")[1].trim().substring(0,3);
				}else if (!depElement.select("td").get(6).text().equalsIgnoreCase("--")){
					price = Float.parseFloat(depElement.select("td").get(6).text().split("\u00A0")[0]);
					currency = depElement.select("td").get(6).text().split("\u00A0")[1].trim().substring(0,3);
				}else if (!depElement.select("td").get(7).text().equalsIgnoreCase("--")) {
					price = Float.parseFloat(depElement.select("td").get(7).text().split("\u00A0")[0]);
					currency = depElement.select("td").get(7).text().split("\u00A0")[1].trim().substring(0,3);
				}
				fare.setTotalPrice(price,search);
				fare.setCurrencyCode(currency);
				fares.add(fare);
			}
		}else{
			Elements flightElements = documentThird.select("div#dtcontainer-bundled");
			if (flightElements.size()>0) {
				Elements flightInfo = flightElements.get(0).select("table.flights-multi>tbody>tr[id~=tr-]");
				if (search.isRoundtrip()) {
					for (int i = 0; i < flightInfo.size()-1; i=i+2) {
						List<CuriositySegment> outboundSegments = parseSegmentsInAmerica(flightInfo.get(i), search);
						List<CuriositySegment> inboundSegments = parseSegmentsInAmerica(flightInfo.get(i+1), search);
						Float price = 0F;
						String currency = "";
						String[] priceAndCurrency = flightInfo.get(i).select("td").get(6).select("span").text().split(" ")[0].split("\u00A0");
						if (priceAndCurrency.length>1) {
							price = Float.parseFloat(priceAndCurrency[0]);
							currency = priceAndCurrency[1];
						}
						CuriosityFare fare = new CuriosityFare();
						fare.setOutboundSegments(outboundSegments);
						fare.setInboundSegments(inboundSegments);
						fare.setTotalPrice(price,search);
						fare.setCurrencyCode(currency);
						fares.add(fare);
					} 
				}else{
					for (int i = 0; i < flightInfo.size(); i++) {
						List<CuriositySegment> outboundSegments = parseSegmentsInAmerica(flightInfo.get(i), search);
						Float price = 0F;
						String currency = "";
						String[] priceAndCurrency = flightInfo.get(i).select("td").get(6).select("span").text().split(" ")[0].split("\u00A0");
						if (priceAndCurrency.length>1) {
							price = Float.parseFloat(priceAndCurrency[0]);
							currency = priceAndCurrency[1];
						}
						CuriosityFare fare = new CuriosityFare();
						fare.setOutboundSegments(outboundSegments);
						fare.setTotalPrice(price,search);
						fare.setCurrencyCode(currency);
						fares.add(fare);
					} 
				}
				
			}
		}
		}
		}
		return fares;
		}catch (Exception e){
			return null;
		}
	}

	private List<CuriositySegment> parseSegments(Element element, CuriositySearch search) {
		List<CuriositySegment> segments = Lists.newArrayList();
		CuriositySegment segment = new CuriositySegment();
		Elements segmentElements = element.select("td");
		String[] flightCodeAndNumber = segmentElements.get(0).text().split(" ");
		segment.setAirlineCode(flightCodeAndNumber[0]);
		segment.setFlightNumber(flightCodeAndNumber[1]);
		String depCode = segmentElements.get(2).select("span.airport_code").text();
		String arrCode = segmentElements.get(3).select("span.airport_code").text();
		
		String depTime = segmentElements.get(2).select("span.wasTranslated").attr("wl:date").split(",")[0];
		String arrTime = segmentElements.get(3).select("span.wasTranslated").attr("wl:date").split(",")[0];
		segment.setDepartureCode(depCode);
		segment.setArrivalCode(arrCode);
		segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER,depTime);
		segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER,arrTime);
		segments.add(segment);
		return segments;
	}
	
	private List<CuriositySegment> parseSegmentsInAmerica(Element element, CuriositySearch search) {
		List<CuriositySegment> segments = Lists.newArrayList();
		CuriositySegment segment = new CuriositySegment();
		Elements segmentElements = element.select("td");
		String[] flightCodeAndNumber = segmentElements.get(1).text().split(" ");
		for (int i = 0; i < flightCodeAndNumber.length; i++) {
			if (flightCodeAndNumber[i].equalsIgnoreCase("ET")) {
				segment.setAirlineCode(flightCodeAndNumber[i]);
				segment.setFlightNumber(flightCodeAndNumber[i+1]);
				break;
			}
		}
		String depCode = segmentElements.get(3).select("span.airport_code").text();
		String arrCode = segmentElements.get(4).select("span.airport_code").text();
		
		String depTime = segmentElements.get(3).select("span.wasTranslated").attr("wl:date").split(",")[0];
		String arrTime = segmentElements.get(4).select("span.wasTranslated").attr("wl:date").split(",")[0];
		segment.setDepartureCode(depCode);
		segment.setArrivalCode(arrCode);
		segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER,depTime);
		segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER,arrTime);
		segments.add(segment);
		return segments;
	}
	private List<CuriositySegment> parseSegmentsJson(JsonNode elementJson) {
		List<CuriositySegment> segments = Lists.newArrayList();
		JsonNode flightSegments = elementJson.findValue("segments");
		for (JsonNode jsonNode : flightSegments) {
			CuriositySegment segment = new CuriositySegment();
			segment.setDepartureCode(jsonNode.findValue("departureCode").textValue());
			segment.setArrivalCode(jsonNode.findValue("arrivalCode").textValue());

			segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER,jsonNode.findValue("departureDate").textValue());
			segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER,jsonNode.findValue("arrivalDate").textValue());
			segment.setAirlineCode(jsonNode.findValue("airlineCodes").toString().split("\"")[1]);
			segment.setFlightNumber(StringUtils.substringBetween(jsonNode.findValue("flightNumber").toString(),"[","]"));
			segments.add(segment);
		}
		return segments;
	}
}
