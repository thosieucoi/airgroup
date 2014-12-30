package com.airgroup.services.impl.koreanair;

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

public class KoreanFlight extends SearchFlights {

	private AsyncHttpClient asyncHttpClient;

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("EEEE, MMMM dd, yyyy HH:mm");

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		// Reservation
		KoreanReservation reservation = new KoreanReservation();
		Response reservationResponse = reservation.getResponse(search);

		FluentStringsMap requestParams = new FluentStringsMap();

		Document reservationDocument = Jsoup.parse(reservationResponse.getResponseBody());

		for (Element element : reservationDocument.select("input[type=hidden]")) {
			requestParams.add(element.attr("name"), element.val());
		}

		search.createParam("requestParam", requestParams);

		// Request
		KoreanRequest request = new KoreanRequest();
		Response requestResponse = request.getResponseWithCookies(
			search,
			reservationResponse.getCookies());

		Document requestDocument = Jsoup.parse(requestResponse.getResponseBody());

		FluentStringsMap processParams = new FluentStringsMap();

		for (Element element : requestDocument.select("input[type=hidden]")) {
			processParams.add(element.attr("name"), element.val());
		}

		search.createParam("processParam", processParams);

		// Process
		KoreanProcess process = new KoreanProcess();

		Response processResponse = process.getResponseWithCookies(
			search,
			reservationResponse.getCookies());

		Document processDocument = Jsoup.parse(processResponse.getResponseBody());

		FluentStringsMap resultParams = new FluentStringsMap();

		for (Element element : processDocument.select("input[type=hidden]")) {
			resultParams.add(element.attr("name"), element.val());
		}

		search.createParam("resultParam", resultParams);

		// Results
		KoreanResult result = new KoreanResult();

		Response resultResponse = result.getResponseWithCookies(
			search,
			reservationResponse.getCookies());

		// System.err.println(resultResponse.getResponseBody());

		return resultResponse;
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		Document document = Jsoup.parse(getResponse(search).getResponseBody());

		// System.err.println(document);
		List<CuriosityFare> fares = Lists.newArrayList();
		try {

			asyncHttpClient = new AsyncHttpClient();
			String jsonString = StringUtils.substringBetween(
				document.toString(),
				"String('{",
				"}');").replace("\"listRecos\":", "");
			ObjectMapper mapper = new ObjectMapper();
			JsonParser jp = mapper.getFactory().createJsonParser(jsonString);
			JsonNode map = jp.readValueAsTree();
			JsonNode listRecos = map.path("listRecos").get(0);
			String ibId = "";
			String obId = "";
			String colorId = "";

			int count = 0;
			float bestPrice = 100000;
			String merge = "";

			for (JsonNode jsonNode : map) {
				String outboundFlightIDs = Utils.removeQuote(
					jsonNode.findPath("outboundFlightIDs").toString()).replaceAll(",", "");
				String priceString = Utils.removeQuote(
					jsonNode.findPath("price").findPath("price").toString()).replaceAll(",", "");
				Float price = Float.parseFloat(priceString.replace("$", ""));
				if (search.isRoundtrip()) {
					String inboundFlightIDs = Utils.removeQuote(
						jsonNode.findPath("inboundFlightIDs").toString()).replaceAll(",", "");
					merge = inboundFlightIDs + outboundFlightIDs;
					String[] tmp = merge.split("\\|");
					if (count <= tmp.length && bestPrice > price) {
						count = tmp.length;
						bestPrice = price;
						ibId = inboundFlightIDs;
						obId = outboundFlightIDs;
						colorId = Utils.removeQuote(jsonNode.findPath("id").toString());
					}
				} else {
					merge = outboundFlightIDs;
					String[] tmp = merge.split("\\|");

					if (count <= tmp.length && bestPrice > price) {
						count = tmp.length;
						bestPrice = price;
						obId = outboundFlightIDs;
						colorId = Utils.removeQuote(jsonNode.findPath("id").toString());
					}
				}
			}

			Element requestForm = document.select("form[id=FDFF_FORM]").first();

			String link = requestForm.attr("action");

			String[] ob = obId.substring(1).split("\\|");

			if (search.isRoundtrip()) {
				String[] ib = ibId.substring(1).split("\\|");
				for (int i = 0; i < ob.length; i++) {
					for (int j = 0; j < ib.length; j++) {
						Response response = asyncHttpClient
							.preparePost(link)
							.setParameters(getParams(search, ob[i], ib[j], colorId))
							.execute()
							.get();
						Document fareDocument = Jsoup.parse(response.getResponseBody());
						fares.add(parseFare(search, fareDocument));
					}
				}
			} else {
				for (int i = 0; i < ob.length; i++) {
					Response response = asyncHttpClient
						.preparePost(link)
						.setParameters(getParams(search, ob[i], String.valueOf(0), colorId))
						.execute()
						.get();
					Document fareDocument = Jsoup.parse(response.getResponseBody());

					fares.add(parseFare(search, fareDocument));
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

	public FluentStringsMap getParams(CuriositySearch search, String obId, String ibId,
			String colorId) {
		FluentStringsMap params = new FluentStringsMap();

		params.add("0fdffRadioOut", "radOut");
		params.add("BANNER", "");
		params.add("DISPLAY_TYPE", "2");
		if (search.isRoundtrip()) {
			params.add("1fdffRadioOut", "radOut");
			params.add("FLIGHTINBOUND", ibId);
			params.add("FLIGHT_ID_2", ibId);
			params.add("IS_DIRTY_INBOUND", "Y");
		}

		params.add("FLIGHTOUTBOUND", obId);
		params.add("FLIGHT_ID_1", obId);

		params.add("IS_DIRTY_OUTBOUND", "Y");
		params.add("LANGUAGE", "GB");
		params.add("PAGE_TICKET", "0");
		params.add("PRICING_TYPE", "I");
		params.add("RECOMMENDATION_ID_1", colorId);
		params.add("SITE", "CAUOCAUO");
		params.add("SKIN", "");
		params.add("TRIP_TYPE", search.isRoundtrip() ? "R" : "O");
		params.add("selectSortFlights_out", "E");

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
		fare.setAirlineCode("KE");

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
			segment.setFlightNumber(airline[1].substring(2));

			segments.add(segment);
		}

		return segments;
	}
}
