package com.airgroup.services.impl.emirates;

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

import com.google.common.collect.Lists;
import com.ning.http.client.Response;
import com.airgroup.model.Cabin;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;
import com.airgroup.util.Utils;

public class EmiratesFlight extends SearchFlights {

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd HH:mm");
	
	@Override
	protected Response getResponse(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		String content = "";
		if(search.isRoundtrip()){
			EmiratesModifyFlightSearch modifySearch = new EmiratesModifyFlightSearch();
			content = modifySearch.getResponse(search).getResponseBody();
		}
		else{
			EmiratesFlightOneWay searchOneway = new EmiratesFlightOneWay();
			content = searchOneway.getResponse(search).getResponseBody();
		}
		//System.err.println(content);
		String pTxId = StringUtils.substringBetween(content, "pTxId=", "&");
		search.createParam("pTxId", pTxId);
		EmiratesFlightListInternal getRequestId = new EmiratesFlightListInternal();
		String contentRequestId = getRequestId.getResponse(search).getResponseBody();
		String requestId = StringUtils.substringBetween(contentRequestId, "requestId\":\"", "\"");
		search.createParam("requestId", requestId);
		EmiratesFlightReponse response = new EmiratesFlightReponse();
		return response.getResponse(search);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search)
			throws IOException, InterruptedException, ExecutionException {
		String content = getResponse(search).getResponseBody();
		Document document = Jsoup.parse(content);
		Elements flights = document.select("form.product");
		List<CuriosityFare> fares = Lists.newArrayList();
		for (Element fareElement : flights) {
			String idFilter = fareElement.select("div.call-to-action > input[name=transactionId]").val();
			Elements fareLeg = fareElement.select("div.flight-information > div.leg");
			String priceString = fareElement.select("tr.total > td").text();
			float totalPrice = Utils.floatFromString(priceString.substring(1));
			String currencyCode = "GBP";
			List<List<CuriositySegment>> allObSegments = Lists.newArrayList();
			List<List<CuriositySegment>> allIbSegments = Lists.newArrayList();
			for (Element element : fareLeg) {
				List<CuriositySegment> segments = parseSegments(
						getDetail(search, element, idFilter), search.getCabin());
				if(segments.size() > 0){
					if(segments.get(0).getDepartureCode().equals(search.getDepartureCode())){
						allObSegments.add(segments);
					}
					else if(segments.get(0).getDepartureCode().equals(search.getArrivalCode())){
						allIbSegments.add(segments);
					}
				}
			}
			for (List<CuriositySegment> obsegment : allObSegments) {
				if(search.isRoundtrip()){
					for (List<CuriositySegment> ibsegment : allIbSegments) {
						CuriosityFare fare = CuriosityFare.newFromTrip(search);
						fare.setPrice(totalPrice);
						fare.setCurrencyCode(currencyCode);
						fare.setOutboundSegments(obsegment);
						fare.setInboundSegments(ibsegment);
						fare.setAirlineCode("EK");
						fares.add(fare);
					}
				}
				else{
					CuriosityFare fare = CuriosityFare.newFromTrip(search);
					fare.setPrice(totalPrice);
					fare.setCurrencyCode(currencyCode);
					fare.setOutboundSegments(obsegment);
					fare.setAirlineCode("EK");
					fares.add(fare);
				}
			}
		}
		return fares;
	}

	private String getDetail(CuriositySearch search, Element element, String idFilter) throws IOException {
		String linkDetail = StringUtils
				.substringBetween(
						element.select("div.leg > div.more >div.details").toString(),
						"<a href=\"/trips/flightlegdetails?", "\"").replaceAll("&amp", "")
				.replaceAll(";", "&") + "&isModal=true" + "&_=" + idFilter;
		search.createParam("LINK_DETAIL", linkDetail);
		EmiratesFlightDetail detail = new EmiratesFlightDetail();
		return detail.getResponse(search).getResponseBody();
	}

	private List<CuriositySegment> parseSegments(String segmentString , Cabin cabin) {
		List<CuriositySegment> segments = Lists.newArrayList();
		Document document = Jsoup.parse(segmentString);
		Elements segmentElements = document.select("div.flight-information");
		//System.err.println("-----------");
		for (Element element : segmentElements) {
			if(element.text().length() > 0){
				CuriositySegment segment = new CuriositySegment();
				segment.setAirlineCode(element.select("div.airline > h4 >span.flight-number").text().substring(0,2));
				segment.setFlightNumber(element.select("div.airline > h4 > span.flight-number").text().substring(2));
				segment.setDepartureCode(element.select("div.departure > ul > li.airport > span.code").text().substring(1,4));
				segment.setArrivalCode(element.select("div.arrival > ul > li.airport > span.code").text().substring(1,4));
				segment.setDepartureTime(TIME_FORMATTER, Utils.parseDate(element.select("div.departure > ul >li.date-time").text().substring(4),"dd MMM yyyy HH:mm"));
				segment.setArrivalTime(TIME_FORMATTER, Utils.parseDate(element.select("div.arrival > ul >li.date-time").text().substring(4),"dd MMM yyyy HH:mm"));
				segment.setCabin(cabin);
				segments.add(segment);
			}
		}
		return segments;
	}
}
