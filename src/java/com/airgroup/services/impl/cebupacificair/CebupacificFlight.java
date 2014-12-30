/**
 * 
 */
package com.airgroup.services.impl.cebupacificair;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
 * @author hiepnt
 * 
 */
public class CebupacificFlight extends SearchFlights {

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat
		.forPattern("yyyyMMdd HH:mm");

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		CebupacificairStartAction startAction = new CebupacificairStartAction();
		List<Cookie> cookies = startAction.getResponse(search).getCookies();

		CebupacificairSearch cebuSearch = new CebupacificairSearch();
		cebuSearch.getResponseWithCookies(search, cookies);

		CebupacificairGetResponse getData = new CebupacificairGetResponse();
		return getData.getResponseWithCookies(search, cookies);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		String content = getResponse(search).getResponseBody();
		Document document = Jsoup.parse(content);
		Elements elements = document.select("table.flights");
		List<CuriosityFare> fares = Lists.newArrayList();
		List<CuriosityFare> obFares = Lists.newArrayList();
		List<CuriosityFare> ibFares = Lists.newArrayList();
		for (Element element : elements) {
			Elements fareElements = element.select("tbody > tr");
			for (Element fareElement : fareElements) {
				List<CuriositySegment> segments = parseSegments(
					fareElement.select("td.fareCol5 > div.newFare > input").val(),
					search.getCabin());
				String price = fareElement
					.select("td.fareCol5 > div.farePrices > span")
					.text()
					.substring(5);
				String currency = fareElement
					.select("td.fareCol5 > div.farePrices > span")
					.text()
					.substring(0, 3)
					.trim();
				if(segments.get(0).getDepartureCode().equals(search.getDepartureCode())){
					CuriosityFare obFare = CuriosityFare.newFromTrip(search);
					obFare.setPrice(Utils.floatFromString(price.split(" ")[0]));
					obFare.setCurrencyCode(currency);
					obFare.setOutboundSegments(segments);
					obFare.setAirlineCode("5J");
					obFares.add(obFare);
				}else{
					CuriosityFare ibFare = CuriosityFare.newFromTrip(search);
					ibFare.setPrice(Utils.floatFromString(price.split(" ")[0]));
					ibFare.setCurrencyCode(currency);
					ibFare.setInboundSegments(segments);
					ibFare.setAirlineCode("5J");
					ibFares.add(ibFare);
				}
			}
		}
		if(search.isDomestic()){
			for (CuriosityFare obFare : obFares) {
				obFare.setPricePerAdult(obFare.getPrice()/search.getPassengersCount());
				obFare.setPricePerChild(obFare.getPrice()/search.getPassengersCount());
				fares.add(obFare);
			}

			for (CuriosityFare ibFare : ibFares) {
				ibFare.setPricePerAdult(ibFare.getPrice()/search.getPassengersCount());
				ibFare.setPricePerChild(ibFare.getPrice()/search.getPassengersCount());
				fares.add(ibFare);
			}
		}
		else{
			for (CuriosityFare obFare : obFares) {
				if(search.isRoundtrip()){
					for (CuriosityFare ibFare : ibFares) {
						CuriosityFare fare = CuriosityFare.newFromTrip(search);
						fare.setOutboundSegments(obFare.getOutboundSegments());
						fare.setInboundSegments(ibFare.getInboundSegments());
						fare.setCurrencyCode(obFare.getCurrencyCode());
						fare.setPrice((ibFare.getPrice() + obFare.getPrice())*search.getPassengersCount());
						fare.setPricePerAdult(fare.getPrice()/search.getPassengersCount());
						fare.setPricePerChild(fare.getPrice()/search.getPassengersCount());
						fare.setAirlineCode("5J");
						fares.add(fare);
					}
				}
				else{
					obFare.setPricePerAdult(obFare.getPrice()/search.getPassengersCount());
					obFare.setPricePerChild(obFare.getPrice()/search.getPassengersCount());
					fares.add(obFare);
				}
			}
		}
		return fares;
	}

	private List<CuriositySegment> parseSegments(String value, Cabin cabin) {
		List<CuriositySegment> segments = Lists.newArrayList();
		String[] tmps = value.split("\\|", -1)[1].split("\\^");
		for (String segmentString : tmps) {
			String[] fields = segmentString.split("~");
			CuriositySegment segment = new CuriositySegment();
			segment.setAirlineCode(fields[0]);
			segment.setFlightNumber(fields[1].trim());
			segment.setDepartureCode(fields[4].trim());
			segment.setArrivalCode(fields[6].trim());

			segment.setDepartureTime(
				TIME_FORMATTER,
				Utils.parseDate(fields[5].trim(), "MM/dd/yyyy HH:mm"));
			segment.setArrivalTime(
				TIME_FORMATTER,
				Utils.parseDate(fields[7].trim(), "MM/dd/yyyy HH:mm"));
			
			DateTime newArrTime = segment.getArrivalTime().plusDays(1);
			if (segment.getArrivalTime().isBefore(segment.getDepartureTime())) {
				segment.setArrivalTime(TIME_FORMATTER,TIME_FORMATTER.print(newArrTime));
			}
			
			segment.setCabin(cabin);
			segments.add(segment);
		}
		
		if (segments.size() >= 2) {
			for (int i = 0; i <= segments.size() - 2; ++i) {
				CuriositySegment segment = segments.get(i);
				CuriositySegment nextSegment = segments.get(i + 1);
				if (segment.getArrivalTime().isAfter(nextSegment.getDepartureTime())) {
					nextSegment.setDepartureTime(TIME_FORMATTER, TIME_FORMATTER.print(nextSegment.getDepartureTime().plusDays(1)));
					nextSegment.setArrivalTime(TIME_FORMATTER, TIME_FORMATTER.print(nextSegment.getArrivalTime().plusDays(1)));
				}
			}
		}
		return segments;
	}
}
