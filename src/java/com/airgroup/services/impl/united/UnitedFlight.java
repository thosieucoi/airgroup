package com.airgroup.services.impl.united;

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
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Cookie;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.airgroup.helper.RequestBuilderHelper;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;
import com.airgroup.util.Const;

public class UnitedFlight extends SearchFlights {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("E., MMM. d, yyyyhh:mm a");

	private static final String INBOUND_URL = "http://www.united.com/web/en-US/apps/booking/flight/searchResult2.aspx?";

	private AsyncHttpClient asyncHttpClient;

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		UnitedGetCookie cookie = new UnitedGetCookie();
		Response cookieResponse = cookie.getResponse(search);

		Document document = Jsoup.parse(cookieResponse.getResponseBody());

		String hdnServer = document.select("input[id=hdnServer]").val();
		String hdnSID = document.select("input[id=hdnSID]").val();
		String hdnClient = document.select("input[id=hdnClient]").val();
		String hdnTiming = document.select("input[id=hdnTiming]").val();

		String viewState = document.select("input[id=__VIEWSTATE]").val();

		search.createParam(Const.VIEWSTATE, viewState);
		search.createParam(UnitedRequest.HDNSERVER, hdnServer);
		search.createParam(UnitedRequest.HDNSID, hdnSID);
		search.createParam(UnitedRequest.HDNCLIENT, hdnClient);
		search.createParam(UnitedRequest.HDNTIMING, hdnTiming);

		search.createParam("Cookies", cookieResponse.getCookies());
		List<Cookie> cookies = cookieResponse.getCookies();
		UnitedRequest request = new UnitedRequest();
		request.getResponseWithCookies(search, cookies);

		UnitedResults results = new UnitedResults();
		Response response = results.getResponseWithCookies(search, cookies);

		return response;
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		Document document = Jsoup.parse(getResponse(search).getResponseBody());

		// System.err.println(document);

		List<CuriosityFare> fares = Lists.newArrayList();

		for (Element element : document.select("td[class=tdSegmentBlock]")) {
			// Get fare Url
			String url = element.select("td.tdPrice > div.divSelect > input").attr("onclick");
			String inboundUrl = StringUtils.substringBetween(url, "searchResult1.aspx?", "\"");

			List<CuriositySegment> outboundSegments = parseSegments(element);

			// Get inbound fare
			if (search.isRoundtrip()) {
				List<CuriosityFare> inboundFares = getInboundFares(search, inboundUrl);
				for (CuriosityFare fare : inboundFares) {
					fare.setCurrencyCode(Const.USD);
					fare.setOutboundSegments(outboundSegments);
					// Linh them vao
					fare.setAirlineCode("UA");
					// if (checkFare(fare, search)) {
					fares.add(fare);
					// }
				}
			} else {
				// Oneway
				CuriosityFare fare = CuriosityFare.newFromTrip(search);
				fare.setCurrencyCode(Const.USD);
				fare.setOutboundSegments(outboundSegments);
				if (search.getPassengersCount() > 1) {
					String price = element
						.select("td.tdPrice > div > span.fSort")
						.text()
						.substring(1)
						.replaceAll(",", "")
						.replaceAll(" All Travelers", "");
					fare.setPrice(Float.parseFloat(price));
					fare.setPricePerAdult(fare.getPrice() / search.getPassengersCount());
					if (search.hasChildren()) {
						fare.setPricePerChild(fare.getPrice() / search.getPassengersCount());
					}
				} else {
					String price = element
						.select("td.tdPrice > div > span.fResultsPrice")
						.text()
						.substring(1)
						.replaceAll(",", "");
					fare.setPrice(Float.parseFloat(price));
					fare.setPricePerAdult(fare.getPrice() / search.getPassengersCount());
				}
				// Linh them vao
				fare.setAirlineCode("UA");
				// if (checkFare(fare, search)) {
				fares.add(fare);
				// }
			}
		}
		return fares;
	}

	private List<CuriosityFare> getInboundFares(CuriositySearch search, String url)
			throws InterruptedException, ExecutionException, IOException {
		List<CuriosityFare> fareList = Lists.newArrayList();
		List<Cookie> cookies = (List<Cookie>) search.getParamValue("Cookies");
		try {
			RequestBuilder requestBuilder = RequestBuilderHelper
				.getRequestBuilderWithCookies(cookies)
				.setMethod("GET")
				.setUrl((INBOUND_URL + url.replaceAll("&fp=1", "")).replaceAll("\\|", "%7C"));

			asyncHttpClient = new AsyncHttpClient();
			Response inboundFareResponse = asyncHttpClient
				.executeRequest(requestBuilder.build())
				.get();

			Document document = Jsoup.parse(inboundFareResponse.getResponseBody());

			for (Element element : document.select("td[class=tdSegmentBlock]")) {
				CuriosityFare fare = CuriosityFare.newFromTrip(search);
				List<CuriositySegment> inboundSegments = parseSegments(element);
				fare.setInboundSegments(inboundSegments);

				if (search.getPassengersCount() > 1) {
					String price = element
						.select("td.tdPrice > div > span.fSort")
						.text()
						.substring(1)
						.replaceAll(",", "")
						.replaceAll(" All Travelers", "");
					fare.setPrice(Float.parseFloat(price));
					fare.setPricePerAdult(fare.getPrice() / search.getPassengersCount());
					if (search.hasChildren()) {
						fare.setPricePerChild(fare.getPrice() / search.getPassengersCount());
					}
				} else {
					String price = element
						.select("td.tdPrice > div > span.fResultsPrice")
						.text()
						.substring(1)
						.replaceAll(",", "");
					fare.setPrice(Float.parseFloat(price));
					fare.setPricePerAdult(fare.getPrice());
				}

				fareList.add(fare);
			}
		} catch (Exception e) {
			if (asyncHttpClient != null) {
				asyncHttpClient.close();
			}
			fareList = null;

		} finally {
			if (asyncHttpClient != null) {
				asyncHttpClient.close();
			}
		}
		return fareList;
	}

	private List<CuriositySegment> parseSegments(Element element) {
		List<CuriositySegment> segments = Lists.newArrayList();

		Elements segmentElements = element.select("tr:has(td.tdDepart)");

		for (Element segmentElement : segmentElements) {
			CuriositySegment segment = new CuriositySegment();
			segment.setDepartureCode(getAirportCode(segmentElement
				.select("td.tdDepart")
				.last()
				.text()));
			segment.setArrivalCode(getAirportCode(segmentElement
				.select("td.tdArrive")
				.last()
				.text()));

			String departTime = segmentElement
				.select("td.tdDepart > div > strong.timeDepart")
				.text()
				.replaceAll("\\.", "")
				.trim();
			String arriveTime = segmentElement
				.select("td.tdArrive > div > strong.timeArrive")
				.text()
				.replaceAll("\\.", "")
				.trim();

			String departDay = segmentElement.select("td.tdDepart > div > b").text();
			String arriveDay = segmentElement.select("td.tdArrive > div > b").text();

			segment.setDepartureTime(DATE_TIME_FORMATTER, departDay + departTime);
			segment.setArrivalTime(DATE_TIME_FORMATTER, arriveDay + arriveTime);

			String designator = segmentElement.select("td.tdSegmentDtl > div > b").text();

			segment.setAirlineCode(designator.substring(0, 2));
			segment.setFlightNumber(designator.substring(2, 6).trim());

			segments.add(segment);
		}

		return segments;
	}

	/**
	 * <p>
	 * Get AirportCode from full Airport name. ex, NewYork (NYC - All Airport)
	 * will return NYC string.
	 * </p>
	 * 
	 * @param airportName
	 *            full name of Airport
	 * @return AirportCode
	 */

	private String getAirportCode(String airportName) {
		int indexOfOpenBracket = airportName.indexOf("(");
		String result = airportName.substring(indexOfOpenBracket + 1, indexOfOpenBracket + 4);
		return result;
	}
}
