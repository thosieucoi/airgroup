/**
 * 
 */
package com.airgroup.services.impl.philippineairlines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
public class PhilippineAirlinesFlight extends SearchFlights {

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat
		.forPattern("yyyyMMdd HH:mm");
	private static final String COOKIES = "COOKIES";
	private static final String DATA_TYPE = "DATA_TYPE";

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		PhilippineAirlinesStartAction startAction = new PhilippineAirlinesStartAction();
		startAction.getResponse(search);

		PhilippineAirlinesValidateFlow validateFlow = new PhilippineAirlinesValidateFlow();
		validateFlow.getResponse(search);
		List<Cookie> cookies = validateFlow.getResponse(search).getCookies();
		search.createParam(COOKIES, cookies);

		PhilippineAirlinesValidareForm validateForm = new PhilippineAirlinesValidareForm();
		validateForm.getResponseWithCookies(search, cookies);

		PhilippineAirlinesFareSearch fareSearch = new PhilippineAirlinesFareSearch();
		fareSearch.getResponseWithCookies(search, cookies);

		PhilippinesAirAvailabilitySearchForward availabilitySearch = new PhilippinesAirAvailabilitySearchForward();

		String responseString = availabilitySearch
			.getResponseWithCookies(search, cookies)
			.getResponseBody();
		if (responseString.contains("We are unable to display requested page")) {
			PhilippineAirlinesAirlinesFamiliesForward familiesFoward = new PhilippineAirlinesAirlinesFamiliesForward();
			search.createParam(DATA_TYPE, "0");
			return familiesFoward.getResponseWithCookies(search, cookies);
		} else {
			search.createParam(DATA_TYPE, "1");
			return availabilitySearch.getResponseWithCookies(search, cookies);
		}
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		String content = getResponse(search).getResponseBody();
		List<CuriosityFare> fares = Lists.newArrayList();
		List<CuriosityFare> obFares = Lists.newArrayList();
		List<CuriosityFare> ibFares = Lists.newArrayList();
		String dataType = search.getParamValue(DATA_TYPE).toString();
		Document document = Jsoup.parse(content);
		if (dataType.equals("0")) {
			String obPrice = "0";
			Elements listElementsOb = document
				.select("div[id=resultsFFBlock1] > table > tbody > tr");
			List<Element> listObE = new ArrayList<Element>();
			for (Element element : listElementsOb) {
				if (element.select("tr.combineRows").size() > 0) {
					listObE.add(element);
					obPrice = element.select("div.colPrice > label").first().text();
				} else {
					listObE.add(element);
					List<CuriositySegment> segments = parseSegmentsFamilies(
						listObE,
						search.getCabin());
					if (element.select("div.colPrice > label").size() > 0)
						obPrice = element.select("div.colPrice > label").first().text();
					String obCurrency = "USD";
					CuriosityFare outboundFare = CuriosityFare.newFromTrip(search);
					outboundFare.setOutboundSegments(segments);
					outboundFare.setPrice(Utils.floatFromString(obPrice));
					outboundFare.setCurrencyCode(obCurrency);
					outboundFare.setAirlineCode("PR");
					obFares.add(outboundFare);
					listObE.clear();
				}
			}
			if (search.isRoundtrip()) {
				String ibPrice = "0";
				Elements listElementsIb = document
					.select("div[id=resultsFFBlock2] > table > tbody > tr");
				List<Element> listIbE = new ArrayList<Element>();
				for (Element element : listElementsIb) {
					if (element.select("tr.combineRows").size() > 0) {
						listIbE.add(element);
						ibPrice = element.select("div.colPrice > label").first().text();
					} else {
						listIbE.add(element);
						List<CuriositySegment> segments = parseSegmentsFamilies(
							listIbE,
							search.getCabin());
						if (element.select("div.colPrice > label").size() > 0)
							ibPrice = element.select("div.colPrice > label").first().text();
						String ibCurrency = "USD";
						CuriosityFare ibboundFare = CuriosityFare.newFromTrip(search);
						ibboundFare.setInboundSegments(segments);
						ibboundFare.setPrice(Utils.floatFromString(ibPrice));
						ibboundFare.setCurrencyCode(ibCurrency);
						ibboundFare.setAirlineCode("PR");
						ibFares.add(ibboundFare);
						listIbE.clear();
					}
				}
			}
		} else {
			String priceString = getPrice(search);
			List<Elements> listElementsOb = new ArrayList<Elements>();
			if (document.select("div[id=defaultSelection_0] > table > tbody > tr.rowFirst") != null ||
				document.select("div[id=resultsFFBlock1] > table > tbody > tr.rowFirst").size() > 0) {
				listElementsOb.add(document
					.select("div[id=resultsFFBlock1] > table > tbody > tr.rowFirst"));
			}
			if (document.select("div[id=resultsFFBlock1] > table > tbody > tr.rowEven") != null ||
				document.select("div[id=resultsFFBlock1] > table > tbody > tr.rowEven").size() > 0) {
				listElementsOb.add(document
					.select("div[id=resultsFFBlock1] > table > tbody > tr.rowEven"));
			}
			if (document.select("div[id=defaultSelection_1] > table > tbody > tr.rowLast") != null ||
				document.select("div[id=resultsFFBlock1] > table > tbody > tr.rowLast").size() > 0) {
				listElementsOb.add(document
					.select("div[id=resultsFFBlock1] > table > tbody > tr.rowLast"));
			}
			Elements fareElements = document.select("div.flightLeg");
			for (Element element : fareElements) {
				if (element != null && !element.equals("")) {
					List<CuriositySegment> segments = parseSegmentsAvailability(
						element,
						search.getCabin());
					String price = priceString.substring(3).trim();
					String currency = priceString.substring(0, 3).trim();
					if (segments.get(0).getDepartureCode().equals(search.getDepartureCode())) {
						CuriosityFare obFare = CuriosityFare.newFromTrip(search);
						obFare.setPrice(Utils.floatFromString(price));
						obFare.setCurrencyCode(currency);
						obFare.setOutboundSegments(segments);
						obFares.add(obFare);
					} else {
						CuriosityFare ibFare = CuriosityFare.newFromTrip(search);
						ibFare.setPrice(Utils.floatFromString(price));
						ibFare.setCurrencyCode(currency);
						ibFare.setInboundSegments(segments);
						ibFares.add(ibFare);
					}
				}
			}
		}
		if (search.isDomestic()) {
			for (CuriosityFare obFare : obFares) {
				obFare.setPricePerAdult(obFare.getPrice());
				obFare.setPricePerChild((obFare.getPrice() - 75) * 3 / 4 + 69);
				obFare.setPrice(obFare.getPrice() *
								search.getAdultsCount() +
								((obFare.getPrice() - 75) * 3 / 4 + 69) *
								search.getChildrenCount() +
								((obFare.getPrice() - 75) * 3 / 4 + 69) *
								search.getInfantsCount());
				fares.add(obFare);
			}

			for (CuriosityFare ibFare : ibFares) {
				ibFare.setPricePerAdult(ibFare.getPrice());
				ibFare.setPricePerChild((ibFare.getPrice() - 75) * 3 / 4 + 69);
				ibFare.setPrice(ibFare.getPrice() *
								search.getAdultsCount() +
								((ibFare.getPrice() - 75) * 3 / 4 + 69) *
								search.getChildrenCount() +
								((ibFare.getPrice() - 75) * 3 / 4 + 69) *
								search.getInfantsCount());
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
						fare.setPricePerAdult(ibFare.getPrice() + obFare.getPrice());
						fare.setPricePerChild(((ibFare.getPrice() - 75) * 3 / 4 + 69) +
												((obFare.getPrice() - 75) * 3 / 4 + 69));
						fare
							.setPrice((ibFare.getPrice() *
										search.getAdultsCount() +
										((ibFare.getPrice() - 75) * 3 / 4 + 69) *
										search.getChildrenCount() + ((ibFare.getPrice() - 75) * 3 / 4 + 69) *
																	search.getInfantsCount()) +
										(obFare.getPrice() *
											search.getAdultsCount() +
											((obFare.getPrice() - 75) * 3 / 4 + 69) *
											search.getChildrenCount() + ((obFare.getPrice() - 75) * 3 / 4 + 69) *
																		search.getInfantsCount()));
						fare.setAirlineCode("PR");
						fares.add(fare);
					}
				} else {
					obFare.setPricePerAdult(obFare.getPrice());
					obFare.setPricePerChild((obFare.getPrice() - 75) * 3 / 4 + 69);
					obFare.setPrice(obFare.getPrice() *
									search.getAdultsCount() +
									((obFare.getPrice() - 75) * 3 / 4 + 69) *
									search.getChildrenCount() +
									((obFare.getPrice() - 75) * 3 / 4 + 69) *
									search.getInfantsCount());
					fares.add(obFare);
				}
			}
		}
		return fares;
	}

	private List<CuriositySegment> parseSegmentsAvailability(Element fareElement, Cabin cabin) {
		List<CuriositySegment> segments = Lists.newArrayList();
		Elements listElement = fareElement.select("tr");
		for (Element eSegment : listElement) {
			String[] fields = StringUtils.substringBetween(
				eSegment.select("td.colFlight > div> span > a").toString().replaceAll("amp;", ""),
				"AirFlightDetailsGetAction.do?",
				"')").split("&");
			String depTime = eSegment.select("td.colDeparture > div > span.time").text();
			String arrTime = eSegment.select("td.colArrival > div > span.time").text();
			CuriositySegment segment = new CuriositySegment();
			segment.setAirlineCode(fields[0].split("=")[1]);
			segment.setFlightNumber(fields[1].split("=")[1]);
			segment.setDepartureCode(fields[2].split("=")[1]);
			segment.setArrivalCode(fields[3].split("=")[1]);
			segment.setDepartureTime(
				TIME_FORMATTER,
				Utils.parseDate(timeConvertAvailability(fields, depTime), "dd/MM/yyyy HH:mm"));
			segment.setArrivalTime(
				TIME_FORMATTER,
				Utils.parseDate(timeConvertAvailability(fields, arrTime), "dd/MM/yyyy HH:mm"));
			DateTime newArrTime = segment.getArrivalTime().plusDays(1);
			if (segment.getArrivalTime().isBefore(segment.getDepartureTime())) {
				segment.setArrivalTime(TIME_FORMATTER, TIME_FORMATTER.print(newArrTime));
			}
			segment.setCabin(cabin);
			segments.add(segment);
		}
		if (segments.size() >= 2) {
			for (int i = 0; i <= segments.size() - 2; ++i) {
				CuriositySegment segment = segments.get(i);
				CuriositySegment nextSegment = segments.get(i + 1);
				if (segment.getArrivalTime().isAfter(nextSegment.getDepartureTime())) {
					nextSegment.setDepartureTime(
						TIME_FORMATTER,
						TIME_FORMATTER.print(nextSegment.getDepartureTime().plusDays(1)));
					nextSegment.setArrivalTime(
						TIME_FORMATTER,
						TIME_FORMATTER.print(nextSegment.getArrivalTime().plusDays(1)));
				}
			}
		}
		return segments;
	}

	private List<CuriositySegment> parseSegmentsFamilies(List<Element> elements, Cabin cabin) {
		List<CuriositySegment> segments = Lists.newArrayList();
		for (Element eSegment : elements) {
			String[] fields = StringUtils
				.substringBetween(
					eSegment
						.select("td.colFlight > div > a")
						.first()
						.toString()
						.replaceAll("amp;", ""),
					"AirFlightDetailsGetAction.do?",
					"')")
				.split("&");
			String depTime = eSegment.select("td.colDepart > div").text();
			String arrTime = eSegment.select("td.colArrive > div").text();
			CuriositySegment segment = new CuriositySegment();
			segment.setAirlineCode(fields[0].split("=")[1]);
			segment.setFlightNumber(fields[1].split("=")[1]);
			segment.setDepartureCode(fields[2].split("=")[1]);
			segment.setArrivalCode(fields[3].split("=")[1]);
			segment.setDepartureTime(
				TIME_FORMATTER,
				Utils.parseDate(timeConvertFamilies(fields, depTime), "dd/MM/yyyy HH:mm"));
			segment.setArrivalTime(
				TIME_FORMATTER,
				Utils.parseDate(timeConvertFamilies(fields, arrTime), "dd/MM/yyyy HH:mm"));
			DateTime newArrTime = segment.getArrivalTime().plusDays(1);
			if (segment.getArrivalTime().isBefore(segment.getDepartureTime())) {
				segment.setArrivalTime(TIME_FORMATTER, TIME_FORMATTER.print(newArrTime));
			}
			segment.setCabin(cabin);
			segments.add(segment);
		}
		if (segments.size() >= 2) {
			for (int i = 0; i <= segments.size() - 2; ++i) {
				CuriositySegment segment = segments.get(i);
				CuriositySegment nextSegment = segments.get(i + 1);
				if (segment.getArrivalTime().isAfter(nextSegment.getDepartureTime())) {
					nextSegment.setDepartureTime(
						TIME_FORMATTER,
						TIME_FORMATTER.print(nextSegment.getDepartureTime().plusDays(1)));
					nextSegment.setArrivalTime(
						TIME_FORMATTER,
						TIME_FORMATTER.print(nextSegment.getArrivalTime().plusDays(1)));
				}
			}
		}
		return segments;
	}

	private String timeConvertAvailability(String[] timeField, String hour) {
		StringBuilder time = new StringBuilder();
		String month = String.valueOf(Integer.parseInt(timeField[5].split("=")[1]) + 1);
		time.append(timeField[6].split("=")[1]);
		time.append("-");
		time.append(numberNormalize(month));
		time.append("-");
		time.append(numberNormalize(timeField[4].split("=")[1]));
		DateTimeFormatter parser = ISODateTimeFormat.date();
		DateTime date = parser.parseDateTime(time.toString());
		if (hour.contains("+")) {
			date = date.plusDays(Integer.parseInt(hour.split("\\+")[1].trim()));
		}
		StringBuilder newTime = new StringBuilder();
		newTime.append(numberNormalize(String.valueOf(date.getDayOfMonth())) +
						"/" +
						numberNormalize(String.valueOf(date.getMonthOfYear())) +
						"/" +
						date.getYear());
		newTime.append(" ");
		newTime.append(hour.split("\\+")[0]);
		return newTime.toString();
	}

	private String numberNormalize(String str) {
		try {
			if (Integer.parseInt(str) < 10) {
				str = "0" + str;
			}
			return str;
		} catch (NumberFormatException ex) {
			return "00";
		}
	}

	private String timeConvertFamilies(String[] timeField, String hour) {
		StringBuilder time = new StringBuilder();
		time.append(timeField[6].split("=")[1]);
		time.append("-");
		time.append(numberNormalize(timeField[5].split("=")[1]));
		time.append("-");
		time.append(numberNormalize(timeField[4].split("=")[1]));
		DateTimeFormatter parser = ISODateTimeFormat.date();
		DateTime date = parser.parseDateTime(time.toString());
		if (hour.contains("+")) {
			date = date.plusDays(Integer.parseInt(hour.split("\\+")[1].trim()));
		}
		StringBuilder newTime = new StringBuilder();
		newTime.append(numberNormalize(String.valueOf(date.getDayOfMonth())) +
						"/" +
						numberNormalize(String.valueOf(date.getMonthOfYear())) +
						"/" +
						date.getYear());
		newTime.append(" ");
		newTime.append(hour.split("\\+")[0]);
		return newTime.toString();
	}

	private String getPrice(CuriositySearch search) throws IOException {

		@SuppressWarnings("unchecked")
		List<Cookie> cookies = (List<Cookie>) search.getParamValue(COOKIES);

		PhilippinesAirlineGetPrice getPrice = new PhilippinesAirlineGetPrice();
		getPrice.getResponseWithCookies(search, cookies);

		PhilippinesAirlineGetPriceResponse getPriceResponse = new PhilippinesAirlineGetPriceResponse();

		String content = getPriceResponse.getResponseWithCookies(search, cookies).getResponseBody();

		Document document = Jsoup.parse(content);
		String price = document.select(
			"div[id=TOTAL_PRICE_SECTION] > table >tbody > tr > td.colTotal > div.total").text();
		return price;
	}
}