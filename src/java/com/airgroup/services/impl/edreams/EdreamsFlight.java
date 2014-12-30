package com.airgroup.services.impl.edreams;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import com.airgroup.model.Cabin;
import com.airgroup.model.RequestType;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.FlightResponse;
import com.airgroup.services.SearchFlights;

public class EdreamsFlight extends SearchFlights {

	private static final String URL = "http://www.edreams.com/engine/xmlSearch";

	private static final DateTimeFormatter PARSER_DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("dd/MM/yyyyHH:mm");

	@Override
	public Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		EdreamFlightResponse response = new EdreamFlightResponse();
		return response.getResponse(search);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		Document document = Jsoup.parse(getResponse(search).getResponseBody());

		List<CuriosityFare> fares = Lists.newArrayList();

		for (Element root : document.select("flight")) {
			Elements outboundElements = root.select("going > option");

			for (Element obElement : outboundElements) {
				CuriosityFare fare = CuriosityFare.newFromTrip(search);
				List<CuriositySegment> outboundSegments = parseSegments(
					obElement,
					search.getCabin());
				fare.setOutboundSegments(outboundSegments);

				if (search.isRoundtrip()) {
					Elements inboundElements = root.select("return > option");
					for (Element ibElement : inboundElements) {
						List<CuriositySegment> inboundSegments = parseSegments(
							ibElement,
							search.getCabin());
						fare.setInboundSegments(inboundSegments);

						fare.setUrl(root.select("url").text());
						String price = root.select("totalPrice").text().replaceAll(",", "");

						fare.setPrice(Float.parseFloat(price.substring(price.indexOf(" "))));

						fare.setCurrencyCode("EUR");

						if (checkFare(fare, search)) {
							fares.add(fare);
						}
					}
				} else {
					fare.setUrl(root.select("url").text());

					String price = root.select("totalPrice").text().replaceAll(",", "");

					fare.setPrice(Float.parseFloat(price.substring(price.indexOf(" "))));

					fare.setCurrencyCode("EUR");

					if (checkFare(fare, search)) {
						fares.add(fare);
					}
				}
			}
		}
		return fares;
	}

	private String getAirportCode(String city) {
		int indexOfOpenBracket = city.indexOf("(");
		int indexOfLastBracket = city.indexOf(")");
		String result = city.substring(indexOfOpenBracket + 1, indexOfLastBracket);
		return result;
	}

	private List<CuriositySegment> parseSegments(Element element, Cabin cabin) {
		List<CuriositySegment> segments = Lists.newArrayList();

		Elements segmentElements = element.select("segment");

		for (Element segmentElement : segmentElements) {
			CuriositySegment segment = new CuriositySegment();
			segment.setDepartureCode(getAirportCode(segmentElement.select("depCity").text()));
			segment.setArrivalCode(getAirportCode(segmentElement.select("arrCity").text()));

			String depDate = segmentElement.select("depDate").text();
			String arrDate = segmentElement.select("arrDate").text();

			String depTime = segmentElement.select("depHour").text();
			String arrTime = segmentElement.select("arrHour").text();

			segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER, depDate + depTime);
			segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER, arrDate + arrTime);

			String designator = segmentElement.select("flightNumber").text();
			String carrier = segmentElement.select("carrier").text();

			segment.setAirlineCode(designator.substring(0, 2));
			segment.setFlightNumber(designator.substring(2));
			segment.setCabin(cabin);
			segment.setCarrier(carrier);

			segments.add(segment);
		}

		return segments;
	}
	
	private class EdreamFlightResponse extends FlightResponse{

		@Override
		public RequestType getType() {
			return RequestType.GET;
		}

		@Override
		public String getURL() {
			return URL;
		}

		@Override
		public FluentStringsMap getParams() {
			FluentStringsMap params = new FluentStringsMap();

			params.add("language", "EN");
			params.add("website", "GB");
			params.add("departureLocation", search.getDepartureCode());
			params.add("arrivalLocation", search.getArrivalCode());
			params.add("departureDate", getDateTimeFormatter().print(search.getOutboundDate()));
			if (search.isRoundtrip()) {
				params.add("returnDate", getDateTimeFormatter().print(search.getInboundDate()));
			}
			params.add("numAdults", search.getAdultsCountString());
			params.add("numChilds", search.getChildrenCountString());
			params.add("numInfants", search.getInfantsCountString());
			params.add("live", "true");
			params.add("mktportal", "hpv-GB");
			params.add("utm_medium", "metasearch");
			params.add("utm_source", "hpv-GB");
			params.add("utm_campaign", "hpv-GB" +
										"-metasearch-" +
										search.getDepartureCode() +
										"-" +
										search.getArrivalCode());

			return params;
		}

		public String getHeaderValue() {
			return null;
		}
		
		public String getDateTimeFormatterPattern() {
			return "yyyyMMdd";
		}

	}
}
