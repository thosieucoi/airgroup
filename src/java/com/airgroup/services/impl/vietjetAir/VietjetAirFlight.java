package com.airgroup.services.impl.vietjetAir;

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
import com.ning.http.client.Cookie;
import com.ning.http.client.Response;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;
import com.airgroup.util.Const;

public class VietjetAirFlight extends SearchFlights {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("dd/MM/yyyy E HH:mm");

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		VietjetAirRequest request = new VietjetAirRequest();
		Response requestResponse = request.getResponse(search);

		Document document = Jsoup.parse(requestResponse.getResponseBody());

		String viewState = document.select("input[id=__VIEWSTATE]").val();
		String debugId = document.select("input[id=DebugID]").val();

		search.createParam(Const.VIEWSTATE, viewState);
		search.createParam("debugId", debugId);

		List<Cookie> cookies = requestResponse.getCookies();
		VietjetAirProcess process = new VietjetAirProcess();
		process.getResponseWithCookies(search, cookies);

		VietjetAirResult result = new VietjetAirResult();
		Response response = result.getResponseWithCookies(search, cookies);

		return response;
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		Document document = Jsoup.parse(getResponse(search).getResponseBody());
		List<CuriosityFare> fares = Lists.newArrayList();
		List<CuriosityFare> outboundFares = Lists.newArrayList();
		String depDate = document
			.select("table.TrvOptGrid")
			.first()
			.select("tr:nth-child(3) > td > b")
			.text();
		
		Elements obElements = document.select("tr[id^=gridTravelOptDep]");

		outboundFares = parseFare(obElements, search, depDate, true);

		if (search.isRoundtrip()) {
			List<CuriosityFare> inboundFares = Lists.newArrayList();
			String arrDate = document
				.select("table.TrvOptGrid")
				.last()
				.select("tr:nth-child(3) > td > b")
				.text();

			Elements ibElements = document.select("tr[id^=gridTravelOptRet]");

			inboundFares = parseFare(ibElements, search, arrDate, false);

			if (search.isDomestic()) {
				for (CuriosityFare obFare : outboundFares) {
					fares.add(obFare);
				}

				for (CuriosityFare ibFare : inboundFares) {
					fares.add(ibFare);
				}
			} else {
				for (CuriosityFare obFare : outboundFares) {
					for (CuriosityFare ibFare : inboundFares) {
						CuriosityFare fare = CuriosityFare.newFromTrip(search);
						fare.setOutboundSegments(obFare.getOutboundSegments());
						fare.setInboundSegments(ibFare.getInboundSegments());
						fare.setCurrencyCode(Const.VND);
						fare.setPrice(ibFare.getPrice() + obFare.getPrice());
						float totalPrice = fare.getPrice();
						fare.setPricePerAdult(totalPrice / search.getPassengersCount());
						fare.setPricePerChild(totalPrice / search.getPassengersCount());
						fare.setAirlineCode("VJ");
						fares.add(fare);
					}
				}
			}

			// for (CuriosityFare obFare : outboundFares) {
			// CuriosityFare fare = CuriosityFare.newFromTrip(search);
			// fare.setOutboundSegments(obFare.getOutboundSegments());
			// for (CuriosityFare ibFare : inboundFares) {
			// fare.setInboundSegments(ibFare.getOutboundSegments());
			// fare.setCurrencyCode(Const.VND);
			// fare.setPrice(ibFare.getPrice() + obFare.getPrice());
			// fares.add(fare);
			// }
			// }

			return fares;
		} else {
			return outboundFares;
		}

	}

	public List<CuriosityFare> parseFare(Elements elements, CuriositySearch search, String date,
			boolean type) {
		List<CuriosityFare> fares = Lists.newArrayList();
		for (Element element : elements) {
			try {
				CuriosityFare fare = CuriosityFare.newFromTrip(search);
				if (type) {
					fare.setOutboundSegments(parseSegments(element, date));
				} else {
					fare.setInboundSegments(parseSegments(element, date));
				}
				
				
				
				Float totalPrice = Float.parseFloat(element
					.select("input[id=total_complete_charges]")
					.val()
					.replaceAll(",", "").replaceAll(" VND", ""));
				// Float basePrice = Float.parseFloat(element
				// .select("input[id=fare]")
				// .val()
				// .replaceAll(",", ""));
				fare.setCurrencyCode(Const.VND);
				// fare.setBasePrice(basePrice);
				fare.setPrice(totalPrice);
				fare.setPricePerAdult(totalPrice / search.getPassengersCount());
				fare.setPricePerChild(totalPrice / search.getPassengersCount());
				fare.setAirlineCode("VJ");
				fares.add(fare);
			} catch (Exception e) {
				System.out.println("========" + e.toString());
			}
		}
		return fares;
	}

	private List<CuriositySegment> parseSegments(Element element, String date) {
		List<CuriositySegment> segments = Lists.newArrayList();

		CuriositySegment segment = new CuriositySegment();

		String depInfo = element.select("td[class=SegInfo]").get(1).text();
		String arrInfo = element.select("td[class=SegInfo]").get(2).text();
		String flightInfo = element.select("td[class=SegInfo]").get(3).text();

		segment.setDepartureCode(depInfo.substring(6, 9));
		segment.setArrivalCode(arrInfo.substring(6, 9));

		segment.setDepartureTime(DATE_TIME_FORMATTER, date + " " + depInfo.substring(0, 5));
		segment.setArrivalTime(DATE_TIME_FORMATTER, date + " " + arrInfo.substring(0, 5));

		if (segment.getArrivalTime().isBefore(segment.getDepartureTime())) {
			segment.setArrivalTime(
				DATE_TIME_FORMATTER,
				DATE_TIME_FORMATTER.print(segment.getArrivalTime().plusDays(1)));
		}

		segment.setAirlineCode(flightInfo.substring(0, 2));
		segment.setFlightNumber(flightInfo.substring(2, 5));

		segments.add(segment);
		return segments;
	}

}
