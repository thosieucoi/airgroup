package com.airgroup.services.impl.aeroflot;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.ning.http.client.Response;
import com.airgroup.model.Search;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;
import com.airgroup.util.Utils;

public class AeroflotFlight extends SearchFlights {

	private static final DateTimeFormatter PARSER_DATE_TIME_FORMATTER = DateTimeFormat
			.forPattern("dd-MM-yyyyHH:mm");
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
			.forPattern("dd-MM-yyyy");
	private static final DateTimeFormatter HOUR_FORMATTER = DateTimeFormat
			.forPattern("HH:mm");
	private static final DateTimeFormatter NEW_DATE_TIME_FORMATTER = DateTimeFormat
			.forPattern("dd MMM yyyy");

	@Override
	protected Response getResponse(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		AeroflotFlightResponse response = new AeroflotFlightResponse();
		return response.getResponse(search);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search)
			throws IOException, InterruptedException, ExecutionException {
		Response result = getResponse(search);
		String content = result.getResponseBody();
		Document document = Jsoup.parse(content);
		String sid = StringUtils.substringBetween(content, "&sid=", "&");
		
		
		try {
			Boolean isDepFlight = true;
			DateTime flightDate = getFlightInfo(document, isDepFlight);
			List<CuriosityFare> fareDepartures = Lists.newArrayList();
			List<CuriosityFare> fareArrvials = Lists.newArrayList();
			List<CuriosityFare> fares = Lists.newArrayList();
			for (Element element : document.select(getElementLink(isDepFlight))) {
				CuriosityFare fare = CuriosityFare.newFromTrip(search);
				List<CuriositySegment> outboundSegments = parseSegments(
						element, search, flightDate, isDepFlight);
				fare.setOutboundSegments(outboundSegments);
				fare.setAirlineCode("SU");
				fareDepartures.add(fare);
			}

			if (search.isRoundtrip()) {
				isDepFlight = false;
				flightDate = getFlightInfo(document, isDepFlight);
				int i = 0;
				int j = 0;
				for (Element element : document
						.select(getElementLink(isDepFlight))) {
					List<CuriositySegment> inboundSegments = parseSegments(
							element, search, flightDate, isDepFlight);
					for (CuriosityFare fare : fareDepartures) {
						fare.setInboundSegments(inboundSegments);
						fare.setAirlineCode("SU");
						search.getParams().clear();
						search.createParam("out", i);
						search.createParam("in", j);
						search.createParam("sid", sid);
						AeroflotFlightResponseSecond responseSecond = new AeroflotFlightResponseSecond();
						Document documentSecond = Jsoup.parse(responseSecond.getResponse(search).getResponseBody());
//						System.err.print(documentSecond);
						String priceTotal = documentSecond.select("span.totalfareamount").text();
						if (priceTotal.split(" ").length>=2) {
							Float price = Float.parseFloat(priceTotal.split(" ")[0]);
							String currency = priceTotal.split(" ")[1];
							fare.setPrice(price);
							fare.setPricePerAdult(price/search.getPassengersCount());
							if (search.getChildrenCount()>0) {
								fare.setPricePerChild(price/search.getPassengersCount());
							}
							fare.setCurrencyCode(currency);
						}
						
						fares.add(fare);
						j++;
					}
					i++;
				}
			} else {
				int i = 0;
				for (CuriosityFare fare : fareDepartures) {
					search.getParams().clear();
					search.createParam("out", i);
					search.createParam("sid", sid);
					fare.setAirlineCode("SU");
					AeroflotFlightResponseSecond responseSecond = new AeroflotFlightResponseSecond();
					Document documentSecond = Jsoup.parse(responseSecond.getResponse(search).getResponseBody());
					String priceTotal = documentSecond.select("span.totalfareamount").text();
					if (priceTotal.split(" ").length>=2) {
						Float price = Float.parseFloat(priceTotal.split(" ")[0]);
						String currency = priceTotal.split(" ")[1];
						fare.setPrice(price);
						fare.setPricePerAdult(price/search.getPassengersCount());
						if (search.getChildrenCount()>0) {
							fare.setPricePerChild(price/search.getPassengersCount());
						}
						fare.setCurrencyCode(currency);
					}
					
					fares.add(fare);
					i++;
				}
			}
			return fares;
		} catch (Exception e) {
			return null;
		}
	}

	private List<CuriositySegment> parseSegments(Element element,
			CuriositySearch search, DateTime flightDate, Boolean isDepFlight) {
		List<CuriositySegment> segments = Lists.newArrayList();

		Elements segmentElements = element
				.select("td.matrix_cal_body_dep > div.depTimeSR");
		int i = 0;
		for (Element segmentElement : segmentElements) {
			CuriositySegment segment = new CuriositySegment();
			segment.setDepartureCode(segmentElement.select("a").text());
			segment.setArrivalCode(element
					.select("td.matrix_cal_body_arr > div.arrTimeSR").get(i)
					.select("a").text());

			String depTime = segmentElement.select("p").text();
			String arrTime = element
					.select("td.matrix_cal_body_arr > div.arrTimeSR").get(i)
					.select("p").text();
			segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER,
					DATE_TIME_FORMATTER.print(flightDate) + depTime);
			if (HOUR_FORMATTER.parseDateTime(depTime).getMillis() >= HOUR_FORMATTER
					.parseDateTime(arrTime).getMillis()) {
				flightDate = flightDate.plusDays(1);
			}
			segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER,
					DATE_TIME_FORMATTER.print(flightDate) + arrTime);

			String airlineCodeAndNumber = element
					.select("td.matrix_cal_body_flifo > div.flightInfoDiv > span.matrix_flight_num > span.matrix_airline_code")
					.text();
			String[] airlineCodeAndNumberArray = airlineCodeAndNumber
					.split(" ");
			segment.setAirlineCode(airlineCodeAndNumberArray[0]);
			segment.setFlightNumber(airlineCodeAndNumberArray[1]);
			i++;
			segments.add(segment);
		}
		return segments;
	}

	private Element checkBestBuy(Element element) {
		Element priceElement = null;
		String[] cabin = { "RE", "BE", "VE", "PE", "PC", "VB", "PB" };
		for (int i = 0; i < cabin.length; i++) {
			priceElement = element.select(
					"td.fare_" + cabin[i] + " > div.hoverTaxes").first();
			if (priceElement != null) {
				break;
			}
		}
		return priceElement;
	}

	private CuriosityFare addPriceAndCurrency(Element priceElement,
			CuriosityFare fare,CuriositySearch search) {
		if (priceElement != null) {
			String priceAndCur = priceElement.select("table > tbody > tr")
					.get(2).select("td").get(1).select("span").first().text();
			String[] priceCur = priceAndCur.split(" ");
			fare.setTotalPrice(fare.getPrice() + Utils.floatFromString(priceCur[0]),search);
			fare.setCurrencyCode(priceCur[1]);
		}
		return fare;
	}

	private DateTime getFlightInfo(Document document, Boolean isDepFlight) {
		String flightInfoOut;
		if (isDepFlight) {
			flightInfoOut = document.select(
					"table.bfm_table_out > tbody > tr > td > p.flightInfo")
					.text();
		} else {
			flightInfoOut = document.select(
					"table.bfm_table_in > tbody > tr > td > p.flightInfo")
					.text();
		}
		String[] flightInfoOutArray = flightInfoOut.split(",");
		DateTime fDate = NEW_DATE_TIME_FORMATTER
				.parseDateTime(flightInfoOutArray[flightInfoOutArray.length - 1]
						.trim());
		return fDate;
	}

	private String getElementLink(Boolean isDepFlight) {
		String link;
		if (isDepFlight) {
			String partLink = "table.bfm_table_out > tbody > tr > td > table.bfmTbl > tbody ";
			link = partLink + " > tr._out ," + partLink
					+ " > tr.connectingFlight_out";
		} else {
			String partLink = "table.bfm_table_in > tbody > tr > td > table.bfmTbl > tbody ";
			link = partLink + " > tr._in ," + partLink
					+ " > tr.connectingFlight_in";
		}
		return link;
	}
}
