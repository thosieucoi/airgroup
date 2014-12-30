package com.airgroup.services.impl.southafricanairways;

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
import com.airgroup.model.Fare;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;
import com.airgroup.services.impl.aeroflot.AeroflotFlightResponse;

public class SouthAfricanAirwaysFlight extends SearchFlights {
	private static final DateTimeFormatter PARSER_DATE_TIME_FORMATTER = DateTimeFormat
			.forPattern("dd MMM yyyyHH:mm");
	private static final int DEPARTURE_TABLE_NUMBER = 1;
	private static final int ARRIVAL_TABLE_NUMBER = 3;
	private static final int STEP_RUN = 2;
	protected static final String DEPARTURE_FLIGHT_NUMBER_SELECT = "DEPARTURE_FLIGHT_NUMBER_SELECT";
	protected static final String ARRIVAL_FLIGHT_NUMBER_SELECT = "ARRIVAL_FLIGHT_NUMBER_SELECT";

	@Override
	protected Response getResponse(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		SouthAfricanAirwaysFlightResponseLanguage responseLanguage = new SouthAfricanAirwaysFlightResponseLanguage();
		Response resultLanguage = responseLanguage.getResponse(search);
		String Jsession = SouthAfricanAirwaysFlightResponseSecond.JSESSIONID
				+ StringUtils.substringBetween(resultLanguage.getHeaders().toString(), "JSESSIONID", ";");
		SouthAfricanAirwaysFlightResponseFirst response = new SouthAfricanAirwaysFlightResponseFirst();
		return response.getResponse(search);
	}

	private Response getFlightPriceAndCurr(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		SouthAfricanAirwaysFlightResponseSecond response = new SouthAfricanAirwaysFlightResponseSecond();
		return response.getResponse(search);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search)
			throws IOException, InterruptedException, ExecutionException {
		Response results = getResponse(search);
		String jsession = SouthAfricanAirwaysFlightResponseSecond.JSESSIONID
				+ StringUtils.substringBetween(results.getHeaders().toString(), "JSESSIONID", ";");
		String content = results.getResponseBody();
		String kind;
		if (content.contains("Non-Voyager Upgradeable")) {
			kind = "SchedNonY";
		}else{
			kind = "SchedY";
		}
		Document document = Jsoup.parse(content);
		String basicLink = getBasicElementLink();
		List<CuriosityFare> fares = Lists.newArrayList();
		List<CuriosityFare> fareDepartures = Lists.newArrayList();
		List<CuriosityFare> fareArrvials = Lists.newArrayList();

		if (document.select(basicLink).size() > 0) {
			Elements elemenDepartureFlights = document.select(basicLink)
					.get(DEPARTURE_TABLE_NUMBER).select("tbody>tr.odd,tbody>tr.even");
			Elements elemenDepartureFlightInfo = document.select(basicLink)
					.get(DEPARTURE_TABLE_NUMBER).select("tbody>tr.collapsed");
			for (int i = 0; i < elemenDepartureFlights.size(); i++) {
				CuriosityFare fare = CuriosityFare.newFromTrip(search);
				List<CuriositySegment> outboundSegments = parseSegments(
						elemenDepartureFlights.get(i),
						elemenDepartureFlightInfo.get(i));
				fare.setOutboundSegments(outboundSegments);
				fare.setAirlineCode("SA");
				fareDepartures.add(fare);
			}
			if (search.isRoundtrip()) {
				Elements elemenArrivalFlights = document.select(basicLink)
						.get(ARRIVAL_TABLE_NUMBER)
						.select("tbody>tr.odd,tbody>tr.even");
				Elements elemenArrivalFlightInfo = document
						.select(basicLink).get(ARRIVAL_TABLE_NUMBER)
						.select("tbody>tr.collapsed");
				for (int i = 0; i < elemenArrivalFlights.size(); i++) {
					CuriosityFare fareArrival = CuriosityFare
							.newFromTrip(search);
					List<CuriositySegment> inboundSegments = parseSegments(
							elemenArrivalFlights.get(i),
							elemenArrivalFlightInfo.get(i));
					fareArrival.setInboundSegments(inboundSegments);
					fareArrvials.add(fareArrival);
				}
				boolean check = false;
				for (int i = 0; i < fareDepartures.size(); i++) {
					if (check) {
						break;
					}
					for (int j = 0; j < fareArrvials.size(); j++) {
						Response results1 = getResponse(search);
						String jsession1 = SouthAfricanAirwaysFlightResponseSecond.JSESSIONID
								+ StringUtils.substringBetween(results1.getHeaders().toString(), "JSESSIONID", ";");
						search.getParams().clear();
						//search.getParamValue("JSESSIONID");
						search.createParam("JSESSIONID",jsession1);
						search.createParam(DEPARTURE_FLIGHT_NUMBER_SELECT, i);
						search.createParam(ARRIVAL_FLIGHT_NUMBER_SELECT, j);
						search.createParam("kind", kind);
						String[] priceAndCurr = getPriceAndCurrArray(getFlightPriceAndCurr(search));
						if (priceAndCurr.length == 2) {
							check=true;
							Float priceTotal = Float.parseFloat(priceAndCurr[1]);
							CuriosityFare fare = CuriosityFare.newFromTrip(search);
							fare.addPrice(priceTotal);
							fare.setPricePerAdult(priceTotal/search.getPassengersCount());
							if (search.getChildrenCount()>0) {
								fare.setPricePerChild(priceTotal/search.getPassengersCount());
							}
							fare.setCurrencyCode(priceAndCurr[0]);
							fare.setOutboundSegments(fareDepartures.get(i).getOutboundSegments());
							fare.setInboundSegments(fareArrvials.get(j).getInboundSegments());
							fare.setAirlineCode("SA");
							fares.add(fare);
						}
						if (check) {
							break;
						}
					}
				}
				
			}else{
				boolean check = false;
				for (int i = 0; i < fareDepartures.size(); i++) {
					Response results1 = getResponse(search);
					String jsession1 = SouthAfricanAirwaysFlightResponseSecond.JSESSIONID
							+ StringUtils.substringBetween(results1.getHeaders().toString(), "JSESSIONID", ";");
					search.getParams().clear();
					//search.getParamValue("JSESSIONID");
					search.createParam("JSESSIONID",jsession1);
					search.createParam(DEPARTURE_FLIGHT_NUMBER_SELECT, i);
					search.createParam("kind", kind);
					String[] priceAndCurr = getPriceAndCurrArray(getFlightPriceAndCurr(search));
					if (priceAndCurr.length == 2) {
						check = true;
						fareDepartures.get(i).setCurrencyCode(priceAndCurr[0]);
						Float priceTotal = Float.parseFloat(priceAndCurr[1]);
						fareDepartures.get(i).setPrice(priceTotal);
						fareDepartures.get(i).setPricePerAdult(priceTotal/search.getPassengersCount());
						if (search.getChildrenCount()>0) {
							fareDepartures.get(i).setPricePerChild(priceTotal/search.getPassengersCount());
						}
						fares.add(fareDepartures.get(i));
					}
					if (check) {
						break;
					}
				}
				
			}
		}
		return fares;
	}

	private List<CuriositySegment> parseSegments(Element element,
			Element element2) {
		List<CuriositySegment> segments = Lists.newArrayList();

		Elements segmentElements = element
				.select("td.sector > div > a  > span");
		String[] AirlineCodeAndNumberArray = element.select("td.flight> div")
				.text().split(" ");
		String[] departureTimeArray = element.select("td.departure> div")
				.text().split(" ");
		String[] arrivingTimeArray = element.select("td.arriving> div").text()
				.split(" ");

		for (int i = 0; i < segmentElements.size(); i = i + STEP_RUN) {
			CuriositySegment segment = new CuriositySegment();
			segment.setDepartureCode(StringUtils.substringBetween(
					segmentElements.get(i).text(), "(", ")"));
			segment.setArrivalCode(StringUtils.substringBetween(segmentElements
					.get(i + 1).text(), "(", ")"));

			String depTime = departureTimeArray[i / 2];
			String arrTime = arrivingTimeArray[i / 2];
			Elements dateElements = element2.select("td>div");
			dateElements.remove(0);
			String depandarrDate = dateElements.get(i / 2).text();
			String depDate = StringUtils.substringBetween(depandarrDate,
					"Departing:", ",");
			String arrDate = StringUtils.substringBetween(depandarrDate,
					"Arriving:", ",");

			segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER, depDate
					+ depTime);
			segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER, arrDate
					+ arrTime);

			segment.setAirlineCode(AirlineCodeAndNumberArray[i].substring(0, 2));
			segment.setFlightNumber(AirlineCodeAndNumberArray[i + 1].trim());

			segments.add(segment);
		}
		return segments;
	}

	private String getBasicElementLink() {
		String link = "div.pageInner>div.boxBlue>div.boxInner>div.whiteOnBlue>div.whiteOnBlueInner>table.withVoyager";
		return link;
	}

	private String[] getPriceAndCurrArray(Response results) throws IOException,
			InterruptedException, ExecutionException {
		//System.err.print(results.getHeaders());
		Document documentFlightPriceAndCur = Jsoup.parse(results
				.getResponseBody());
		//System.err.print(documentFlightPriceAndCur);
		String[] priceAndCurr = documentFlightPriceAndCur
				.select("table.onWhite>tbody>tr.total>td.currency").text()
				.split(" ");
		return priceAndCurr;
	}
}
