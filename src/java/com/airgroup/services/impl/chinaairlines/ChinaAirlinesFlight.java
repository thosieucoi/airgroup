/**
 * 
 */
package com.airgroup.services.impl.chinaairlines;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ning.http.client.Cookie;
import com.ning.http.client.Response;
import com.airgroup.model.Cabin;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;
import com.airgroup.util.Utils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author linhnd1
 *
 */
public class ChinaAirlinesFlight extends SearchFlights{

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd HH:mm");
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMM-dd HH:mm");
	private static final String AIRLINE_CODE = "CI";
	@Override
	protected Response getResponse(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		ChinaAirlinesRetrieveCookie retrieveCookie = new ChinaAirlinesRetrieveCookie();
		List<Cookie> cookies = retrieveCookie.getResponse(search).getCookies();
		ChinaAirlinesRequest request = new ChinaAirlinesRequest();
		return request.getResponseWithCookies(search, cookies);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search)
			throws IOException, InterruptedException, ExecutionException {
		List<CuriosityFare> fares = Lists.newArrayList();
		try{
		ChinaAirlinesResponseFirst responseFirst = new ChinaAirlinesResponseFirst();
 		Response contentFirst = responseFirst.getResponse(search);
		//System.err.print(contentFirst.getHeaders());
		ChinaAirlinesResponseSecond responseSecond = new ChinaAirlinesResponseSecond();
		Response resultSecond = responseSecond.getResponseWithCookies(search, contentFirst.getCookies());
		
		String content = resultSecond.getResponseBody();
		Document document = Jsoup.parse(content);
		//System.err.print(document);
		Elements priceElements = document.select("table#priceTotalTable").first().select("tbody>tr");
		Float pricePerAdult = 0F;
		Float pricePerChild = 0F;
		List<CuriosityFare> depFares = Lists.newArrayList();
		List<CuriosityFare> arrFares = Lists.newArrayList();
		for (int i = 1; i < priceElements.size(); i++) {
			Elements priceEle = priceElements.get(i).select("td");
			if (priceEle.size()>=4) {
				String[] kind = priceEle.get(2).text().split("x");
				if (kind[0].trim().contains("Adult")) {
					pricePerAdult = Float.parseFloat(priceEle.get(3).text().replace(",", ""))/Float.parseFloat(kind[1].trim());
				}else if (kind[0].trim().contains("Child")) {
					pricePerChild = Float.parseFloat(priceEle.get(3).text().replace(",", ""))/Float.parseFloat(kind[1].trim());
				}
			}
		}
		Float totalPrice = Float.parseFloat(document.select("span.totalPrice").text().replace(",", ""));
		String currency = StringUtils.substringBetween(document.select("span.total").text(), "(", ")");
		Elements flightElements = document.select("table#gvFlight");
		Elements flightDepElements = flightElements.get(0).select("tr");
		Elements flightArrElements = new Elements();
		if (search.isRoundtrip()) {
			flightArrElements = flightElements.get(1).select("tr");
		}
//		System.err.print(flightDepElements);
//		System.err.print(flightArrElements);
		for (int i = 1; i < flightDepElements.size(); i++) {
			if (flightDepElements.get(i).select("input[name=selectFlightDep]").val()!=null&&!flightDepElements.get(i).select("input[name=selectFlightDep]").val().equalsIgnoreCase("")) {
				CuriosityFare fare = CuriosityFare.newFromTrip(search);
				List<CuriositySegment> outBoundSegments = parseSegmentsNew(flightDepElements.get(i),search.getOutboundDate().getYear()+"");
				fare.setOutboundSegments(outBoundSegments);
				fare.setPrice(totalPrice);
				fare.setPricePerAdult(pricePerAdult);
				if (search.getChildrenCount()>0) {
					fare.setPricePerChild(pricePerChild);
				}
				fare.setCurrencyCode(currency);
				fare.setAirlineCode("CI");
				depFares.add(fare);
			}
		}
		if (search.isRoundtrip()) {
			for (int i = 1; i < flightArrElements.size(); i++) {
				if (flightArrElements.get(i).select("input[name=selectFlightRet]").val()!=null && !flightArrElements.get(i).select("input[name=selectFlightRet]").val().equalsIgnoreCase("")) {
					CuriosityFare fare = CuriosityFare.newFromTrip(search);
					List<CuriositySegment> inBoundSegments = parseSegmentsNew(flightArrElements.get(i),search.getInboundDate().getYear()+"");
					fare.setInboundSegments(inBoundSegments);
					arrFares.add(fare);
				}
			}
			for (CuriosityFare depfare : depFares) {
				for (CuriosityFare arrfare : arrFares) {
					CuriosityFare fare = CuriosityFare.newFromTrip(search);
					fare.setOutboundSegments(depfare.getOutboundSegments());
					fare.setInboundSegments(arrfare.getInboundSegments());
					fare.setPrice(totalPrice);
					fare.setPricePerAdult(pricePerAdult);
					if (search.getChildrenCount()>0) {
						fare.setPricePerChild(pricePerChild);
					}
					fare.setCurrencyCode(currency);
					fare.setAirlineCode("CI");
					fares.add(fare);
				}
			}
		}else{
			fares.addAll(depFares);
		}
		//System.err.print(priceElements);

//		boolean check = true;
//		int count = 0;
//		while(check == true && count < 20 ){
//			try{
//				ObjectMapper mapper = new ObjectMapper();
//				JsonParser jp = mapper.getFactory().createJsonParser(content);
//				JsonNode map = jp.readValueAsTree();
//				JsonNode segments = map.path("content");
//				JsonNode faresNodes = map.path("fare");
//				JsonNode faresMap = map.path("mapping").path("onward");
//				List<CuriosityFare> obFares = Lists.newArrayList();
//				List<CuriosityFare> ibFares = Lists.newArrayList();
//				for (int i = 1; i<= faresNodes.size(); i++) {
//					
//					if(checkAirline(faresNodes.path(String.valueOf(i)).findPath("fk").toString())){
//						List<List<String>> listMap = listMapping(faresMap.path(i-1).path("c").toString());
//						List<JsonNode> obListNode = Lists.newArrayList();
//						List<JsonNode> ibListNode = Lists.newArrayList();
//						for (String fareM : listMap.get(0)) {
//							obListNode.add(segments.path(Utils.removeQuote(fareM)));
//						}
//						if(search.isRoundtrip()){
//							for (String fareM : listMap.get(1)) {
//								ibListNode.add(segments.path(Utils.removeQuote(fareM)));
//							}
//						}
//						CuriosityFare ibFare = CuriosityFare.newFromTrip(search);
//						CuriosityFare obFare = CuriosityFare.newFromTrip(search);
//						String price = faresNodes.path(String.valueOf(i)).findPath("pr").toString();
//						String currency = Utils.removeQuote(map.findPath("disp_currency").toString());
//						obFare.setPrice(Utils.floatFromString(price));
//						obFare.setCurrencyCode(currency);
//						List<CuriositySegment> outBoundSegments = parseSegments(obListNode, search.getCabin());
//						obFare.setOutboundSegments(outBoundSegments);
//						obFare.setAirlineCode("CI");
//						obFares.add(obFare);
//						if(search.isRoundtrip())
//						{
//							List<CuriositySegment> inBoundSegments = parseSegments(ibListNode, search.getCabin());
//							ibFare.setPrice(Utils.floatFromString(price));
//							ibFare.setCurrencyCode(currency);
//							ibFare.setInboundSegments(inBoundSegments);
//							ibFare.setAirlineCode("CI");
//							ibFares.add(ibFare);
//						}
//						if(search.isDomestic()){
//							fares.add(obFare);
//							if(search.isRoundtrip()){
//								fares.add(ibFare);
//							}
//						}
//						else{
//							CuriosityFare fare = CuriosityFare.newFromTrip(search);
//							fare.setOutboundSegments(obFare.getOutboundSegments());
//							if(search.isRoundtrip()){
//								fare.setInboundSegments(ibFare.getInboundSegments());
//							}
//							fare.setCurrencyCode(obFare.getCurrencyCode());
//							fare.setPrice(obFare.getPrice());
//							fare.setAirlineCode("CI");
//							fares.add(fare);
//						}
//		
//					}
//				}
//				if(fares.size() <=0){
//					content = getResponse(search).getResponseBody();
//					count ++;
//				}
//				else 
//					check = false;
			}catch(Exception ex){
				return null;
			}
//		}
		return fares;
	}

	private List<CuriositySegment> parseSegmentsNew(Element element,String year) {
		List<CuriositySegment> segments = Lists.newArrayList();
		CuriositySegment segment = new CuriositySegment();
		Elements segmentElements = element.select("td");
		String flightNumberAndCode = segmentElements.get(0).text();
		segment.setAirlineCode(flightNumberAndCode.substring(0, 2));
		segment.setFlightNumber(flightNumberAndCode.substring(3));
		String[] depAndArrCode = segmentElements.get(1).text().split(" ");
		segment.setDepartureCode(depAndArrCode[0]);
		segment.setArrivalCode(depAndArrCode[1]);
		String[] dateTime = segmentElements.get(2).text().split("/");
		segment.setDepartureTime(DATE_TIME_FORMATTER,year+dateTime[0]);
		segment.setArrivalTime(DATE_TIME_FORMATTER,year+dateTime[1]);
		segments.add(segment);
		return segments;
	}
	
	private List<CuriositySegment> parseSegments(List<JsonNode> fareSegments, Cabin cabin) {
		List<CuriositySegment> segments = Lists.newArrayList();
			for (int i = 0; i < fareSegments.size(); i++) {
				CuriositySegment segment = new CuriositySegment();
				String[] tmp = fareSegments.get(i).findValue("fk").toString().split("_");
				String depTime = Utils.removeQuote(fareSegments.get(i).findValue("ad").toString()) + " " + tmp[4];
				String arrTime = Utils.removeQuote(fareSegments.get(i).findValue("ad").toString()) + " " + Utils.removeQuote(fareSegments.get(i).findValue("a").toString());
				segment.setAirlineCode(tmp[2].substring(0, 2));
				segment.setFlightNumber(tmp[2].substring(3));
				segment.setDepartureCode(Utils.removeQuote(fareSegments.get(i).findValue("fr").toString()));
				segment.setArrivalCode(Utils.removeQuote(fareSegments.get(i).findValue("to").toString()));

				segment.setDepartureTime(
					TIME_FORMATTER,
					Utils.parseDate(depTime,
						"dd/MM/yyyy HH:mm"));
				segment.setArrivalTime(
					TIME_FORMATTER,
					Utils.parseDate(arrTime,"dd/MM/yyyy HH:mm"));
				segment.setCabin(cabin);
				segments.add(segment);
			}
		return segments;
	}
	
	private Boolean checkAirline(String value){
		String []flights = value.split("\\|");
		String[] tmps = flights[flights.length-1].split("fk_retail_");
		for (int i = 1; i < tmps.length; i++) {
			if(!tmps[i].substring(0,2).equals(AIRLINE_CODE)){
				return false;
			}
		}
		return true;
	}
	private List<List<String>> listMapping(String value){
		List<List<String>> list1 = Lists.newArrayList();
		String tmp1[] = value.split("],");
		for (String string : tmp1) {
			List<String> list2 = Lists.newArrayList();
			String[] arrString = string.replaceAll("\\[", "").replaceAll("]", "").split(",");
			for (String string2 : arrString) {
				list2.add(string2);
			}
			list1.add(list2);
		}
		return list1;
	}
}
