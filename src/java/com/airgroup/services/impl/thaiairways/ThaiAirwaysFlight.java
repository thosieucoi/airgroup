package com.airgroup.services.impl.thaiairways;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;
import com.airgroup.util.Utils;

public class ThaiAirwaysFlight extends SearchFlights {

	private static final String URL = "https://wftc3.e-travel.com";

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("EEEE, MMMM dd, yyyy HH:mm");

	private AsyncHttpClient asyncHttpClient;

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		ThaiAirwaysRequest request = new ThaiAirwaysRequest();
		Response requestResponse = request.getResponse(search);

		return requestResponse;
	}

	public List<CuriosityFare> getOnewayFare(CuriositySearch search, Document document)
			throws IllegalArgumentException, InterruptedException, ExecutionException, IOException {
		List<CuriosityFare> fares = Lists.newArrayList();
		try {

			Element requestForm = document.select("form[name=SDAIForm]").first();

			String link = requestForm.attr("action");

			int obNum = document.select("td[id^=tdRadioBtIndex]").size();

			for (int i = 0; i < obNum; i++) {

				asyncHttpClient = new AsyncHttpClient();
				try {
					Response response = asyncHttpClient
						.preparePost(URL + link)
						.setParameters(getOneWayParams(String.valueOf(i)))
						.execute()
						.get();

					// System.err.println(response.getResponseBody());

					Document fareDocument = Jsoup.parse(response.getResponseBody());

					try {
						fares.add(parseFare(search, fareDocument));
					} catch (Exception e) {

					}
				} finally {
					asyncHttpClient.close();
				}
			}

		} catch (Exception e) {
			if (asyncHttpClient != null) {
				asyncHttpClient.close();
			}
			fares = null;

		} finally {
			if (asyncHttpClient != null) {
				asyncHttpClient.close();
			}
		}
		return fares;
	}

	public List<CuriosityFare> getRoundTripFare(CuriositySearch search, Document document)
			throws IllegalArgumentException, InterruptedException, ExecutionException, IOException {
		List<CuriosityFare> fares = Lists.newArrayList();
		try {

			asyncHttpClient = new AsyncHttpClient();
			// System.err.println(document);

			String jsonString = StringUtils.substringBetween(
				document.toString(),
				"String('{",
				"}');").replace("\"listRecos\":", "");
			ObjectMapper mapper = new ObjectMapper();
			JsonParser jp = mapper.getFactory().createJsonParser(jsonString);
			JsonNode map = jp.readValueAsTree();
			// JsonNode listRecos = map.path("listRecos").get(0);
			String ibId = "";
			String obId = "";
			String colorId = "";

			// int count = 0;
			float bestPrice = 100000;

			for (JsonNode jsonNode : map) {
				String inboundFlightIDs = Utils.removeQuote(
					jsonNode.findPath("inboundFlightIDs").toString()).replaceAll(",", "");
				String outboundFlightIDs = Utils.removeQuote(
					jsonNode.findPath("outboundFlightIDs").toString()).replaceAll(",", "");
				String priceString = Utils.removeQuote(
					jsonNode.findPath("price").findPath("price").toString()).replaceAll(",", "");
				Float price = Float.parseFloat(priceString.replace("$", ""));
				// String merge = inboundFlightIDs + outboundFlightIDs;
				// String[] tmp = merge.split("\\|");
				if (bestPrice > price) {
					// count = tmp.length;
					bestPrice = price;
					ibId = inboundFlightIDs;
					obId = outboundFlightIDs;
					colorId = Utils.removeQuote(jsonNode.findPath("id").toString());
				}
			}

			Element requestForm = document.select("form[id=FDFF_FORM]").first();

			String selectLink = requestForm.attr("action");

			String link = URL + selectLink;

			String[] ob = obId.substring(1).split("\\|");
			String[] ib = ibId.substring(1).split("\\|");

			for (int i = 0; i < ob.length; i++) {
				for (int j = 0; j < ib.length; j++) {

					if (fares.size() < 10) {

						Response response = asyncHttpClient
							.preparePost(link)
							.setParameters(getRoundTripParams(colorId, ob[i], ib[j]))
							.execute()
							.get();

						// System.err.println(response.getResponseBody());

						Document fareDocument = Jsoup.parse(response.getResponseBody());

						try {
							fares.add(parseFare(search, fareDocument));
						} catch (Exception e) {

						}

					}
				}
			}
		} catch (Exception e) {
			if (asyncHttpClient != null) {
				asyncHttpClient.close();
			}
			fares = null;

		} finally {
			if (asyncHttpClient != null) {
				asyncHttpClient.close();
			}
		}
		return fares;
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		Document document = Jsoup.parse(getResponse(search).getResponseBody());

		// System.err.println(document);

		if (search.isRoundtrip()) {
			return getRoundTripFare(search, document);
		} else {
			return getOnewayFare(search, document);
		}
	}

	public FluentStringsMap getOneWayParams(String obId) {
		FluentStringsMap params = new FluentStringsMap();

		params.add("CABIN", "E");
		params.add("PAGE_TICKET", "0");
		params.add("PLTG_SEARCH_RECAP_UPDATED", "true");
		params.add("RESTRICTION", "true");
		params.add("ROW_1", obId);
		params.add("TRIP_TYPE", "O");

		return params;
	}

	public FluentStringsMap getRoundTripParams(String colorId, String obId, String ibId) {
		FluentStringsMap params = new FluentStringsMap();

		params.add("0fdffRadioOut", "radOut");
		params.add("1fdffRadioOut", "radOut");
		params.add("BANNER", "");
		params.add("DISPLAY_TYPE", "1");
		params.add("FLIGHTINBOUND", ibId);
		params.add("FLIGHTOUTBOUND", obId);
		params.add("FLIGHT_ID_1", obId);
		params.add("FLIGHT_ID_2", ibId);
		// params.add("FamilyButton", "0|1");
		params.add("IS_DIRTY_INBOUND", "Y");
		params.add("IS_DIRTY_OUTBOUND", "Y");
		params.add("LANGUAGE", "GB");
		params.add("PAGE_TICKET", "0");
		params.add("PRICING_TYPE", "I");
		params.add("RECOMMENDATION_ID_1", colorId);
		params.add("SITE", "CATRCATR");
		params.add("SKIN", "");
		params.add("TRIP_TYPE", "R");
		params.add("selectSortFlights_out", "N");

		return params;
	}

	public CuriosityFare parseFare(CuriositySearch search, Document document) {
		CuriosityFare fare = new CuriosityFare();

		fare.setOutboundSegments(parseSegments(document.select("table#tabFgtReview_0").first()));

		if (search.isRoundtrip()) {
			fare.setInboundSegments(parseSegments(document.select("table#tabFgtReview_1").first()));
		}
		String currencyCode = document.select("span.currencyIcon").first().text();

		Float totalAdultPrice = Float.parseFloat(document
			.select("span#totalForATravellerType_ADT")
			.first()
			.text()
			.replaceAll(",", "")
			.replaceAll(currencyCode, "")
			.trim());

		if (search.hasChildren()) {
			Float totalChildPrice = Float.parseFloat(document
				.select("span#totalForATravellerType_CHD")
				.first()
				.text()
				.replaceAll(",", "")
				.replaceAll(currencyCode, "")
				.trim());
			fare.setPricePerChild(totalChildPrice / search.getChildrenCount());
		}

		Float totalPrice = Float.parseFloat(document
			.select("span#spanTotalPriceOfAllPax")
			.first()
			.text()
			.replaceAll(",", "")
			.replaceAll(currencyCode, "")
			.trim());

		fare.setCurrencyCode(currencyCode);
		fare.setPricePerAdult(totalAdultPrice / search.getAdultsCount());
		fare.setPrice(totalPrice);
		// Linh them vao
		fare.setAirlineCode("TG");
		return fare;
	}

	private List<CuriositySegment> parseSegments(Element element) {
		List<CuriositySegment> segments = Lists.newArrayList();

		for (int i = 0; i < element.select("tr > td.flight").size(); i++) {
			CuriositySegment segment = new CuriositySegment();

			String date = element.select("tbody > tr:has(td.flight) > td:last-child").get(i).text();

			Element infoElement = element.select(
				"tbody > tr:has(td.textColorBold) > td:last-child > table > tbody").get(i);

			Element segmentElement = infoElement
				.select("tr:nth-child(1) > td > table > tbody")
				.first();
			String airlineInfo = infoElement
				.select("tr:nth-child(3) > td:nth-child(2)")
				.first()
				.text();

			String[] airline = airlineInfo.split("\u00A0");

			String depName = segmentElement.select("tr:first-child > td:nth-child(3)").text();
			String arrName = segmentElement.select("tr:last-child > td:nth-child(3)").text();

			String depTime = segmentElement
				.select("tr:first-child > td:nth-child(2)")
				.text()
				.replace(" +1 day(s)", "")
				.replace("\u00A0", "")
				.trim();
			String arrTime = segmentElement
				.select("tr:last-child > td:nth-child(2)")
				.text()
				.replace(" +1 day(s)", "")
				.replace("\u00A0", "")
				.trim();

			segment.setDepartureName(depName);
			segment.setArrivalName(arrName);

			segment.setDepartureTime(DATE_TIME_FORMATTER, date + " " + depTime);
			segment.setArrivalTime(DATE_TIME_FORMATTER, date + " " + arrTime);

			if (segment.getArrivalTime().isBefore(segment.getDepartureTime())) {
				segment.setArrivalTime(
					DATE_TIME_FORMATTER,
					DATE_TIME_FORMATTER.print(segment.getArrivalTime().plusDays(1)));
			}

			segment.setCarrier(airline[0]);
			segment.setAirlineCode(airline[1].substring(0, 2));
			segment.setFlightNumber(airline[1].substring(2, 5));

			segments.add(segment);
		}

		return segments;
	}
}
