package com.airgroup.services.impl.cathaypacific;

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
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;

public class CathayPacificFlight extends SearchFlights {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("dd/MM/yyyy HH:mm");

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("dd/MM/yyyy");

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		CathayPacificRequestFirst requestFirst = new CathayPacificRequestFirst();
		Response requestFirstResponse = requestFirst.getResponse(search);
		Document requestFirstDocument = Jsoup.parse(requestFirstResponse.getResponseBody());
		String viewState = requestFirstDocument.select("input[name=__VIEWSTATE]").val();
		String eventValidation = requestFirstDocument.select("input[name=__EVENTVALIDATION]").val();
		search.createParam("viewState", viewState);
		search.createParam("eventValidation", eventValidation);

		CathayPacificLanguage language = new CathayPacificLanguage();
		Response languageResponse = language.getResponse(search);

		CathayPacificRequest request = new CathayPacificRequest();
		Response cookieResponse = request.getResponseWithCookies(
			search,
			languageResponse.getCookies());

		CathayPacificProcess process = new CathayPacificProcess();
		process.getResponseWithCookies(search, languageResponse.getCookies());

		CathayPacificRequestResult requestResult = new CathayPacificRequestResult();
		requestResult.getResponseWithCookies(search, languageResponse.getCookies());

		CathayPacificResult result = new CathayPacificResult();
		Response resultResponse = result.getResponseWithCookies(
			search,
			languageResponse.getCookies());

		return resultResponse;
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		Response response = getResponse(search);

		List<CuriosityFare> fares = Lists.newArrayList();

		String doc = StringUtils.substringBetween(response.getResponseBody(), "<![CDATA[", "]]>");

		Document document = Jsoup.parse(doc);

		Elements flightElements = document.select("div[class=flightResult]");

		for (Element flightElement : flightElements) {
			CuriosityFare fare = CuriosityFare.newFromTrip(search);

			List<CuriositySegment> outboundSegments = parseSegments(flightElement, search, false);
			fare.setOutboundSegments(outboundSegments);

			if (search.isRoundtrip()) {
				List<CuriositySegment> inboundSegments = parseSegments(flightElement, search, true);
				fare.setInboundSegments(inboundSegments);
			}

			Element priceElement = flightElement
				.select("div[id^=divFareInfo] > table > tbody")
				.first();

			String tax = priceElement
				.select("tr:has(td:contains(Est.)) > td.textright")
				.text()
				.replaceAll(",", "");

			String adultPrice = priceElement
				.select("tr:has(td:contains(Adult)) > td.textright")
				.text()
				.replaceAll(",", "");
			fare.setPricePerAdult(Float.parseFloat(adultPrice) /
									search.getAdultsCount() +
									(Float.parseFloat(tax) / search.getPassengersCount()));

			if (search.hasChildren()) {
				String childPrice = priceElement
					.select("tr:has(td:contains(Child)) > td.textright")
					.text()
					.replaceAll(",", "");

				fare.setPricePerChild(Float.parseFloat(childPrice) /
										search.getChildrenCount() +
										(Float.parseFloat(tax) / search.getPassengersCount()));
			}
			String totalPrice = priceElement
				.select("tr:has(td:contains(Total)) > td.textright")
				.text()
				.replaceAll(",", "");

			String currencyCode = flightElement
				.select("td.flightResultPricePn > span")
				.get(1)
				.text();
			fare.setCurrencyCode(currencyCode);
			fare.setPrice(Float.parseFloat(totalPrice));
			fare.setAirlineCode("CX");

			fares.add(fare);
		}

		return fares;
	}

	private List<CuriositySegment> parseSegments(Element element, CuriositySearch search,
			boolean isReturn) {
		List<CuriositySegment> segments = Lists.newArrayList();

		String text = "";

		if (!isReturn) {
			text = element
				.select(
					"table > tbody > tr > td:first-child > table:first-child > tbody > tr:nth-child(3)")
				.html();
		} else {
			text = element
				.select(
					"table > tbody > tr > td:first-child > table:last-child > tbody > tr:nth-child(3)")
				.html();
		}

		String[] segmentInfo = StringUtils
			.substringBetween(text, "</div> ", " <div>")
			.replaceAll("\n", "")
			.split("<br /> Economy \\([A-Z]\\)<br /> ");

		for (int i = 0; i < segmentInfo.length; i++) {
			try {
				CuriositySegment segment = new CuriositySegment();

				String[] info = segmentInfo[i].replaceAll("to (\\D*) to", "to").split("<br /> ");
				String depName = info[0].split(" to ")[0];
				String arrName = info[0].split(" to ")[1];

				String designator = StringUtils.substringBefore(info[1], " operated");
				String airlineName = designator.replaceAll("\\d", "");
				String flightNumber = designator.replaceAll("\\D", "");

				Elements timeElements = new Elements();

				if (!isReturn) {
					timeElements = element
						.select("table > tbody > tr > td:first-child > table:first-child > tbody > tr:nth-child(4) > td.flightDetail > div > div:has(b + br)");
				} else {
					timeElements = element
						.select("table > tbody > tr > td:first-child > table:last-child > tbody > tr:nth-child(4) > td.flightDetail > div > div:has(b + br)");
				}

				String date = "";

				if (!isReturn) {
					date = DATE_FORMATTER.print(search.getOutboundDate());
				} else {
					date = DATE_FORMATTER.print(search.getInboundDate());
				}

				String timeInfo = StringUtils.substringBetween(
					timeElements.get(i).text(),
					"Depart ",
					" Duration");

				String[] time = timeInfo.split(" Arrive ");

				DateTime depTime = DATE_TIME_FORMATTER.parseDateTime(date + " " + time[0]);
				DateTime arrTime = DATE_TIME_FORMATTER.parseDateTime(date + " " + time[1]);

				if (arrTime.isBefore(depTime)) {
					arrTime = arrTime.plusDays(1);
				}

				segment.setDepartureName(depName);
				segment.setArrivalName(arrName);
				segment.setDepartureTime(DATE_TIME_FORMATTER, DATE_TIME_FORMATTER.print(depTime));
				segment.setArrivalTime(DATE_TIME_FORMATTER, DATE_TIME_FORMATTER.print(arrTime));
				segment.setCarrier(airlineName);
				segment.setFlightNumber(flightNumber);

				segments.add(segment);
			} catch (Exception ex) {
			}
		}
		if (segments.size() >= 2) {
			for (int i = 0; i <= segments.size() - 2; ++i) {
				CuriositySegment segment = segments.get(i);
				CuriositySegment nextSegment = segments.get(i + 1);
				if (segment.getArrivalTime().isAfter(nextSegment.getDepartureTime())) {
					nextSegment.setDepartureTime(
						DATE_TIME_FORMATTER,
						DATE_TIME_FORMATTER.print(nextSegment.getDepartureTime().plusDays(1)));
					nextSegment.setArrivalTime(
						DATE_TIME_FORMATTER,
						DATE_TIME_FORMATTER.print(nextSegment.getArrivalTime().plusDays(1)));
				}
			}
		}
		return segments;
	}
}
