package com.airgroup.services.impl.emirates1;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.common.collect.Lists;
import com.ning.http.client.Cookie;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;

public class EmiratesFlight extends SearchFlights {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("EEE dd MMM yy HH:mm");

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		EmiratesHome home = new EmiratesHome();
		Response homeResponse = home.getResponse(search);

		Document homeDocument = Jsoup.parse(homeResponse.getResponseBody());

		String seoState = homeDocument.select("input[id=__SEOVIEWSTATE]").val();

		search.createParam("seoState", seoState);

		// System.err.println(homeResponse.getResponseBody());

		EmiratesBook book = new EmiratesBook();
		Response bookResponse = book.getResponseWithCookies(search, homeResponse.getCookies());

		Document bookDocument = Jsoup.parse(bookResponse.getResponseBody());
		Element bookElement = bookDocument.select("form[method=post]").first();
		String requestLink = bookElement.attr("action");

		String resultLink = StringUtils.substringBefore(requestLink, "/CAB");

		search.createParam("resultLink", resultLink);

		// System.err.println(bookDocument);

		FluentStringsMap params = new FluentStringsMap();
		for (Element inputElement : bookElement.select("input[type=hidden]")) {
			params.add(inputElement.attr("name"), inputElement.val());
		}

		search.createParam("params", params);
		search.createParam("link", requestLink);

		EmiratesRequest request = new EmiratesRequest();
		Response requestResponse = request
			.getResponseWithCookies(search, homeResponse.getCookies());

		// System.err.println(requestResponse.getResponseBody());

		List<Cookie> cookies = Lists.newArrayList();

		for (Cookie cookie : homeResponse.getCookies()) {
			cookies.add(cookie);
		}

		for (Cookie cookie : requestResponse.getCookies()) {
			cookies.add(cookie);
		}

		EmiratesSearch sea = new EmiratesSearch();
		Response seaResponse = sea.getResponseWithCookies(search, cookies);

		// System.err.println(seaResponse.getResponseBody());

		// List<Cookie> reCookies = Lists.newArrayList();
		//
		// for (Cookie cookie : homeResponse.getCookies()) {
		// reCookies.add(cookie);
		// }
		//
		// for (Cookie cookie : seaResponse.getCookies()) {
		// reCookies.add(cookie);
		// }

		EmiratesResults results = new EmiratesResults();
		Response resultsResponse = results.getResponseWithCookies(search, cookies);

		// System.err.println(resultsResponse.getResponseBody());
		return resultsResponse;
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		Response response = getResponse(search);
		Document document = Jsoup.parse(response.getResponseBody());

		// System.err.println(document);

		List<CuriosityFare> fares = Lists.newArrayList();

		int num = 1;

		for (Element element : document.select("div.sectionBox")) {
			fares.add(parseFare(search, element, num));
			num++;
		}

		return fares;
	}

	private CuriosityFare parseFare(CuriositySearch search, Element element, int num) {
		CuriosityFare fare = CuriosityFare.newFromTrip(search);

		fare.setOutboundSegments(parseSegments(element, "departRow", num));

		if (search.isRoundtrip()) {
			fare.setInboundSegments(parseSegments(element, "returnRow", num));
		}

		String price = element.select("div.fareHeader > div > span.price").text();

		String currencyCode = price.substring(0, 3);
		fare.setCurrencyCode(currencyCode);
		fare.setPrice(Float.parseFloat(price.substring(3)));
		fare.setPricePerAdult(fare.getPrice() / search.getPassengersCount());
		fare.setPricePerChild(fare.getPrice() / search.getPassengersCount());
		fare.setAirlineCode("EK");

		return fare;
	}

	private List<CuriositySegment> parseSegments(Element element, String id, int num) {
		List<CuriositySegment> segments = Lists.newArrayList();

		int numOfSegment = element
			.select(
				"table.displayTable > tbody > tr#" +
						id +
						" > td.connection:has(div.iconConnection)")
			.size();

		Element segmentElement = null;
		String code = "";

		if (id.equals("departRow")) {
			segmentElement = element.select("table.displayTable > tbody").first();
			code = num + "0";
		} else {
			segmentElement = element.select("table.displayTable > tbody").get(1);
			code = num + "1";
		}

		int j = 0;
		for (int i = 0; i <= numOfSegment * 2; i = i + 2) {
			CuriositySegment segment = new CuriositySegment();
			String departDate = segmentElement
				.select("tr#" + id + " > td[headers=depart_arrive" + code + "] > dl.flightDate")
				.get(i)
				.text();

			String departTime = segmentElement
				.select("tr#" + id + " > td[headers=time" + code + "]")
				.get(i)
				.text();

			String arriveDate = segmentElement
				.select("tr#" + id + " > td[headers=depart_arrive" + code + "] > dl.flightDate")
				.get(i + 1)
				.text();

			String arriveTime = segmentElement
				.select("tr#" + id + " > td[headers=time" + code + "]")
				.get(i + 1)
				.text();

			String departureCode = segmentElement
				.select("tr#" + id + " > td[headers=airport" + code + "]")
				.get(i)
				.text();

			String arriveCode = segmentElement
				.select("tr#" + id + " > td[headers=airport" + code + "]")
				.get(i + 1)
				.text();

			String flightInfo = segmentElement
				.select(
					"tr#" +
							id +
							" > td[headers=flight" +
							code +
							"] > strong > a[target=Flight_Info]")
				.get(j)
				.text();
			j++;

			segment.setDepartureCode(getCode(departureCode));
			segment.setArrivalCode(getCode(arriveCode));

			segment.setDepartureTime(DATE_TIME_FORMATTER, departDate + " " + departTime);
			segment.setArrivalTime(DATE_TIME_FORMATTER, arriveDate + " " + arriveTime);

			segment.setAirlineCode(flightInfo.substring(0, 2));
			segment.setFlightNumber(flightInfo.substring(2));
			segments.add(segment);
		}

		return segments;
	}

	private String getCode(String location) {
		int indexOfOpenBracket = location.indexOf("(");
		String result = location.substring(indexOfOpenBracket + 1, indexOfOpenBracket + 4);
		return result;
	}
}
