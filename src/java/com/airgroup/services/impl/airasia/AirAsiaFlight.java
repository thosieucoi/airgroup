package com.airgroup.services.impl.airasia;

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
import com.ning.http.client.Response;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;

public class AirAsiaFlight extends SearchFlights {

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat
		.forPattern("MM/dd/yyyy' 'HH:mm");

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		AirAsiaSearch airAsiaSearch = new AirAsiaSearch();
		Response airAsiaResponse = airAsiaSearch.getResponse(search);
		Document airAsiaDocument = Jsoup.parse(airAsiaResponse.getResponseBody());

		String viewState = airAsiaDocument.select("input[name=viewState]").val();

		search.createParam("viewState", viewState);

		AirAsiaRequest request = new AirAsiaRequest();
		request.getResponseWithCookies(search, airAsiaResponse.getCookies());

		AirAsiaResult result = new AirAsiaResult();
		Response resultResponse = result.getResponseWithCookies(
			search,
			airAsiaResponse.getCookies());

		return resultResponse;
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		Response response = getResponse(search);

		Document document = Jsoup.parse(response.getResponseBody());

		List<CuriosityFare> fares = Lists.newArrayList();

		Element outboundElement = document.select("div.depart-section").first();
		Elements outboundElements = outboundElement.select("div[class^=resultFare]");

		if (search.isRoundtrip()) {
			Element inboundElement = document.select("div.depart-section").last();
			Elements inboundElements = inboundElement.select("div[class^=resultFare]");
			for (Element outboundSegmentElement : outboundElements) {
				for (Element inboundSegmentElement : inboundElements) {
					CuriosityFare fare = CuriosityFare.newFromTrip(search);
					String outboundInformation = outboundSegmentElement
						.select(
							"input[id^=ControlGroupSelectView_AvailabilityInputSelectView_RadioButtonMkt]")
						.val();
					String inboundInformation = inboundSegmentElement
						.select(
							"input[id^=ControlGroupSelectView_AvailabilityInputSelectView_RadioButtonMkt]")
						.val();
					fare.setOutboundSegments(parseSegment(outboundInformation));
					fare.setInboundSegments(parseSegment(inboundInformation));

					Float adultPrice = Float.parseFloat(outboundSegmentElement
						.select("div:contains(Adult) + div.price")
						.text()
						.replaceAll(",", "")
						.split(" ")[0]) +
										Float.parseFloat(inboundSegmentElement
											.select("div:contains(Adult) + div.price")
											.text()
											.replaceAll(",", "")
											.split(" ")[0]);

					String currencyCode = outboundSegmentElement
						.select("div:contains(Adult) + div.price")
						.text()
						.split(" ")[1];

					fare.setPricePerAdult(adultPrice);
					fare.setCurrencyCode(currencyCode);

					if (search.hasChildren()) {
						Float childPrice = Float.parseFloat(outboundSegmentElement
							.select("div:contains(Kid) + div.price")
							.text()
							.replaceAll(",", "")
							.split(" ")[0]) +
											Float.parseFloat(inboundSegmentElement
												.select("div:contains(Kid) + div.price")
												.text()
												.replaceAll(",", "")
												.split(" ")[0]);

						fare.setPricePerChild(childPrice);
					}

					if (search.getInfantsCount() > 0) {
						Float infantPrice = Float.parseFloat(outboundSegmentElement
							.select("div:contains(Infant) + div.price")
							.text()
							.replaceAll(",", "")
							.split(" ")[0]) +
											Float.parseFloat(inboundSegmentElement
												.select("div:contains(Infant) + div.price")
												.text()
												.split(" ")[0]);

						fare.setPrice(fare.getPricePerAdult() *
										search.getAdultsCount() +
										fare.getPricePerChild() *
										search.getChildrenCount() +
										infantPrice *
										search.getInfantsCount());
					} else {
						fare.setPrice(fare.getPricePerAdult() *
										search.getAdultsCount() +
										fare.getPricePerChild() *
										search.getChildrenCount());
					}

					fare.setAirlineCode("AK");
					fares.add(fare);
				}
			}

		} else {
			for (Element outboundSegmentElement : outboundElements) {
				CuriosityFare fare = CuriosityFare.newFromTrip(search);
				String outboundInformation = outboundSegmentElement
					.select(
						"input[id^=ControlGroupSelectView_AvailabilityInputSelectView_RadioButtonMkt]")
					.val();
				fare.setOutboundSegments(parseSegment(outboundInformation));

				String adultPrice = outboundSegmentElement
					.select("div:contains(Adult) + div.price")
					.text()
					.replaceAll(",", "")
					.split(" ")[0];

				String currencyCode = outboundSegmentElement
					.select("div:contains(Adult) + div.price")
					.text()
					.split(" ")[1];

				fare.setPricePerAdult(Float.parseFloat(adultPrice));
				fare.setCurrencyCode(currencyCode);

				if (search.hasChildren()) {
					String childPrice = outboundSegmentElement
						.select("div:contains(Kid) + div.price")
						.text()
						.replaceAll(",", "")
						.split(" ")[0];

					fare.setPricePerChild(Float.parseFloat(childPrice));
				}

				if (search.getInfantsCount() > 0) {
					Float infantPrice = Float.parseFloat(outboundSegmentElement
						.select("div:contains(Infant) + div.price")
						.text()
						.replaceAll(",", "")
						.split(" ")[0]);
					fare.setPrice(fare.getPricePerAdult() *
									search.getAdultsCount() +
									fare.getPricePerChild() *
									search.getChildrenCount() +
									infantPrice *
									search.getInfantsCount());
				} else {
					fare.setPrice(fare.getPricePerAdult() *
									search.getAdultsCount() +
									fare.getPricePerChild() *
									search.getChildrenCount());
				}

				fare.setAirlineCode("AK");
				fares.add(fare);
			}
		}

		return fares;
	}

	private List<CuriositySegment> parseSegment(String string) {

		List<CuriositySegment> segments = Lists.newArrayList();
		String[] segmentStrings = string.split("\\|")[1].split("\\^");

		for (String segmentString : segmentStrings) {
			String[] fields = segmentString.split("~");
			CuriositySegment segment = new CuriositySegment();
			segment.setAirlineCode(fields[0]);
			segment.setFlightNumber(fields[1]);
			segment.setDepartureCode(fields[4]);
			segment.setArrivalCode(fields[6]);
			segment.setDepartureTime(TIME_FORMATTER, fields[5]);
			segment.setArrivalTime(TIME_FORMATTER, fields[7]);

			segments.add(segment);
		}

		return segments;
	}
}
