package com.airgroup.services.impl.eva;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import com.airgroup.model.Cabin;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;
import com.airgroup.util.Utils;

public class EvaFlight extends SearchFlights {
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat
		.forPattern("yyyyMMdd HH:mm");

	private AsyncHttpClient asyncHttpClient;

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		EvaHome home = new EvaHome();
		Response homeResponse = home.getResponse(search);
		search.createParam("EVA_DOCUMENT", home.getResponse(search).getResponseBody());
		EvaRequest request = new EvaRequest();
		Response requestResponse = request
			.getResponseWithCookies(search, homeResponse.getCookies());
		Document requestDocument = Jsoup.parse(requestResponse.getResponseBody());

		// System.err.println(requestDocument);

		Element requestElement = requestDocument.select("form[id=PostForm]").first();
		String link = requestElement.attr("action");
		FluentStringsMap fareParams = new FluentStringsMap();
		for (Element element : requestElement.select("input[type=hidden]")) {
			fareParams.add(element.attr("name"), element.val());
		}
		Response fareResponse = null;
		try {
			asyncHttpClient = new AsyncHttpClient();
			fareResponse = asyncHttpClient
				.preparePost(link)
				.setParameters(fareParams)
				.execute()
				.get();
		} catch (Exception e) {
			if (asyncHttpClient != null) {
				asyncHttpClient.close();
			}

		} finally {
			if (asyncHttpClient != null) {
				asyncHttpClient.close();
			}
		}
		return fareResponse;
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		String content = getResponse(search).getResponseBody();
		return gteData(content, search);
	}

	private List<CuriosityFare> gteData(String content, CuriositySearch search)
			throws JsonParseException, IOException {
		String jsonString = StringUtils.substringsBetween(
			content,
			"var clientSideData =",
			"; </script>")[0];
		// System.err.println(jsonString);
		ObjectMapper mapper = new ObjectMapper();
		JsonParser jp = mapper.getFactory().createJsonParser(jsonString);
		JsonNode map = jp.readValueAsTree();
		List<CuriosityFare> fares = Lists.newArrayList();
		List<CuriosityFare> obFares = Lists.newArrayList();
		List<CuriosityFare> ibFares = Lists.newArrayList();
		JsonNode listOutBound = map.path("ITUP").path("LIST_BOUND").path(0).path("LIST_FLIGHT");
		for (JsonNode jsonNode : listOutBound) {
			List<CuriositySegment> outBoundSegments = parseSegments(
				jsonNode.path("LIST_SEGMENT"),
				search.getCabin());
			float price = Utils.floatFromString(jsonNode
				.path("RECOMMENDATIONS")
				.path(0)
				.path("LIST_PNR")
				.path("LIST_PNR_PRICE")
				.path(0)
				.path("TOTAL_AMOUNT")
				.toString());
			String currency = Utils.removeQuote(jsonNode
				.path("RECOMMENDATIONS")
				.path(0)
				.path("LIST_PNR")
				.path("LIST_TRAVELLER_TYPE")
				.path(0)
				.path("LIST_TRAVELLER_PRICE")
				.path(0)
				.path("CURRENCY")
				.path("CODE")
				.toString());
			CuriosityFare outbounFare = CuriosityFare.newFromTrip(search);
			outbounFare.setOutboundSegments(outBoundSegments);
			outbounFare.setPrice(price);
			outbounFare.setCurrencyCode(currency);
			outbounFare.setAirlineCode("BR");
			obFares.add(outbounFare);
		}
		if (search.isRoundtrip()) {
			JsonNode listInBound = map.path("ITUP").path("LIST_BOUND").path(1).path("LIST_FLIGHT");
			for (JsonNode jsonNode : listInBound) {
				List<CuriositySegment> inBoundSegments = parseSegments(
					jsonNode.path("LIST_SEGMENT"),
					search.getCabin());
				float price = Utils.floatFromString(jsonNode
					.path("RECOMMENDATIONS")
					.path(0)
					.path("LIST_PNR")
					.path("LIST_PNR_PRICE")
					.path(0)
					.path("TOTAL_AMOUNT")
					.toString());
				String currency = Utils.removeQuote(jsonNode
					.path("RECOMMENDATIONS")
					.path(0)
					.path("LIST_PNR")
					.path("LIST_TRAVELLER_TYPE")
					.path(0)
					.path("LIST_TRAVELLER_PRICE")
					.path(0)
					.path("CURRENCY")
					.path("CODE")
					.toString());
				CuriosityFare inbounFare = CuriosityFare.newFromTrip(search);
				inbounFare.setInboundSegments(inBoundSegments);
				inbounFare.setPrice(price);
				inbounFare.setCurrencyCode(currency);
				inbounFare.setAirlineCode("BR");
				ibFares.add(inbounFare);
			}
		}
		if (search.isDomestic()) {
			for (CuriosityFare obFare : obFares) {
				obFare.setPricePerAdult(obFare.getPrice() / search.getPassengersCount());
				obFare.setPricePerChild(obFare.getPrice() / search.getPassengersCount());
				fares.add(obFare);
			}

			for (CuriosityFare ibFare : ibFares) {
				ibFare.setPricePerAdult(ibFare.getPrice() / search.getPassengersCount());
				ibFare.setPricePerChild(ibFare.getPrice() / search.getPassengersCount());
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
						fare.setPrice(obFare.getPrice());
						fare.setAirlineCode("BR");
						fare.setPricePerAdult(fare.getPrice() / search.getPassengersCount());
						fare.setPricePerChild(fare.getPrice() / search.getPassengersCount());
						fares.add(fare);
					}
				} else {
					fares.add(obFare);
					obFare.setPricePerAdult(obFare.getPrice() / search.getPassengersCount());
					obFare.setPricePerChild(obFare.getPrice() / search.getPassengersCount());
				}
			}
		}
		return fares;
	}

	private List<CuriositySegment> parseSegments(JsonNode fareSegments, Cabin cabin) {
		List<CuriositySegment> segments = Lists.newArrayList();
		Format format = new SimpleDateFormat("yyyyMMdd HH:mm");
		for (JsonNode node : fareSegments) {
			String depDate = format
				.format(new Date(Long.parseLong(node.path("B_DATE").toString())))
				.toString();
			String arrDate = format
				.format(new Date(Long.parseLong(node.path("E_DATE").toString())))
				.toString();
			CuriositySegment segment = new CuriositySegment();
			segment.setAirlineCode(Utils.removeQuote(node.path("AIRLINE").path("CODE").toString()));
			segment.setFlightNumber(Utils.removeQuote(node.path("FLIGHT_NUMBER").toString()));
			segment.setCarrier(Utils.removeQuote(node.path("AIRLINE").path("NAME").toString()));
			segment.setDepartureCode(Utils.removeQuote(node
				.path("B_LOCATION")
				.path("LOCATION_CODE")
				.toString()));
			segment.setArrivalCode(Utils.removeQuote(node
				.path("E_LOCATION")
				.path("LOCATION_CODE")
				.toString()));
			segment.setDepartureTime(TIME_FORMATTER, depDate);
			segment.setArrivalTime(TIME_FORMATTER, arrDate);
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

}
