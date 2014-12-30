/**
 * 
 */
package com.airgroup.services.impl.jetstar;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.common.collect.Lists;
import com.ning.http.client.Cookie;
import com.ning.http.client.Response;
import com.airgroup.model.Cabin;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;
import com.airgroup.util.Utils;

/**
 * @author linhnd1
 * 
 */
public class JetstarFlight extends SearchFlights {
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat
		.forPattern("yyyyMMdd HH:mm");

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		JetstarStartAction startAction = new JetstarStartAction();
		List<Cookie> cookies = startAction.getResponse(search).getCookies();
		JetstarRequest jetRequest = new JetstarRequest();
		jetRequest.getResponseWithCookies(search, cookies);
		JetstarGet jetGet = new JetstarGet();
		return jetGet.getResponseWithCookies(search, cookies);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		String content = getResponse(search).getResponseBody().toString();
		Document document = Jsoup.parse(content);
		List<CuriosityFare> fares = Lists.newArrayList();
		List<CuriosityFare> obFares = Lists.newArrayList(); 
		List<CuriosityFare> ibFares = Lists.newArrayList();
		for (Element element : document
			.select("div.depart > div > table > tbody > tr > td.starter > div.field")) {
			Element eSegment = element.select("input[type=radio]").first();
			List<CuriositySegment> segments = parseSegments(
				eSegment.attr("value"),
				search.getCabin());
			float discfeesChd = Utils.floatFromString(eSegment.attr("data-fees-chd").replaceAll(
				",",
				"."));
			float childFee = (Utils.floatFromString(eSegment.attr("data-price-chd").replaceAll(
				",",
				".")) + discfeesChd) *
								search.getChildrenCount();
			float discfeesAdt = Utils.floatFromString(eSegment.attr("data-fees-adt").replaceAll(
				",",
				"."));
			float adtFee = (Utils.floatFromString(eSegment.attr("data-price").replaceAll(",", ".")) + discfeesAdt) *
							search.getAdultsCount();
			String obPrice = element
				.select("label")
				.text()
				.substring(3)
				.trim()
				.replaceAll("\\.", "");
			String obCurrency = element.select("label").text().substring(0, 3).trim();
			CuriosityFare outboundFare = CuriosityFare.newFromTrip(search);
			outboundFare.setOutboundSegments(segments);
			outboundFare.setPrice(adtFee + childFee);
			outboundFare.setCurrencyCode(obCurrency);
			outboundFare.setAirlineCode("BL");
			outboundFare.setPricePerAdult(adtFee/search.getAdultsCount());
			if(search.hasChildren()){
				outboundFare.setPricePerChild(childFee/search.getChildrenCount());
			}
			outboundFare.setBasePricePerAdult(adtFee);
			outboundFare.setBasePricePerChild(childFee);
			obFares.add(outboundFare);
		}
		if (search.isRoundtrip()) {
			for (Element element : document
				.select("div.return > div > table > tbody > tr > td.starter > div.field")) {
				Element eSegment = element.select("input[type=radio]").first();
				List<CuriositySegment> segments = parseSegments(
					eSegment.attr("value"),
					search.getCabin());
				float discfeesChd = Utils.floatFromString(eSegment
					.attr("data-fees-chd")
					.replaceAll(",", "."));
				float childFee = (Utils.floatFromString(eSegment.attr("data-price-chd").replaceAll(
					",",
					".")) + discfeesChd) *
									search.getChildrenCount();
				float discfeesAdt = Utils.floatFromString(eSegment
					.attr("data-fees-adt")
					.replaceAll(",", "."));
				float adtFee = (Utils.floatFromString(eSegment.attr("data-price").replaceAll(
					",",
					".")) + discfeesAdt) *
								search.getAdultsCount();
				String ibPrice = element
					.select("label")
					.text()
					.substring(3)
					.trim()
					.replaceAll("\\.", "");
				String ibCurrency = element.select("label").text().substring(0, 3).trim();
				CuriosityFare inboundFare = CuriosityFare.newFromTrip(search);
				inboundFare.setInboundSegments(segments);
				inboundFare.setPrice(adtFee + childFee);
				inboundFare.setCurrencyCode(ibCurrency);
				inboundFare.setAirlineCode("BL");
				inboundFare.setPricePerAdult(adtFee/search.getAdultsCount());
				if(search.hasChildren()){
					inboundFare.setPricePerChild(childFee/search.getChildrenCount());
				}
				inboundFare.setBasePricePerAdult(adtFee);
				inboundFare.setBasePricePerChild(childFee);
				ibFares.add(inboundFare);
			}
		}
		if (search.isDomestic()) {
			for (CuriosityFare obFare : obFares) {
				fares.add(obFare);
			}

			for (CuriosityFare ibFare : ibFares) {
				fares.add(ibFare);
			}
		} else {
			for (CuriosityFare obFare : obFares) {
				if (search.isRoundtrip()) {
					for (CuriosityFare ibFare : ibFares) {
						CuriosityFare fare = CuriosityFare.newFromTrip(search);
						fare.setOutboundSegments(obFare.getOutboundSegments());
						fare.setInboundSegments(ibFare.getInboundSegments());
						fare.setCurrencyCode(obFare.getCurrencyCode());
						fare.setPrice(ibFare.getBasePricePerAdult() +
										ibFare.getBasePricePerChild() +
										obFare.getBasePricePerAdult() +
										obFare.getBasePricePerChild());
						fare.setAirlineCode("BL");
						fare.setBasePricePerAdult(ibFare.getBasePricePerAdult() +
													obFare.getBasePricePerAdult());
						fare.setBasePricePerChild(ibFare.getBasePricePerChild() +
													obFare.getBasePricePerChild());
						
						fare.setPricePerAdult(ibFare.getPricePerAdult() + obFare.getPricePerAdult());
						if(search.hasChildren()){
							fare.setPricePerChild(ibFare.getPricePerChild() +
													obFare.getPricePerChild());
						}
						fares.add(fare);
					}
				} else {
					fares.add(obFare);
				}
			}
		}
		return fares;
	}

	private List<CuriositySegment> parseSegments(String value, Cabin cabin) {
		List<CuriositySegment> segments = Lists.newArrayList();
		String[] tmps = value.split("\\|", -1)[1].split("\\^");
		for (String segmentString : tmps) {
			String[] fields = segmentString.split("~");
			CuriositySegment segment = new CuriositySegment();
			segment.setAirlineCode("BL");
			segment.setFlightNumber(fields[1].trim());
			segment.setDepartureCode(fields[4].trim());
			segment.setArrivalCode(fields[6].trim());

			segment.setDepartureTime(
				TIME_FORMATTER,
				Utils.parseDate(fields[5].trim(), "MM/dd/yyyy HH:mm"));
			segment.setArrivalTime(
				TIME_FORMATTER,
				Utils.parseDate(fields[7].trim(), "MM/dd/yyyy HH:mm"));
			
			DateTime newArrTime = segment.getArrivalTime().plusDays(1);
			if (segment.getArrivalTime().isBefore(segment.getDepartureTime())) {
				segment.setArrivalTime(TIME_FORMATTER,TIME_FORMATTER.print(newArrTime));
			}
			segment.setCabin(cabin);
			segments.add(segment);
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
}
