package com.airgroup.services.impl.airchina;

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
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;

public class AirChinaFlight extends SearchFlights {

	private static final String URL = "http://book.eu2.amadeus.com";

	private AsyncHttpClient asyncHttpClient;

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("EEEE, MMMM dd, yyyy HH:mm");

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		AirChinaHome home = new AirChinaHome();
		Response homeResponse = home.getResponse(search);

		String enc = StringUtils.substringBetween(homeResponse.getResponseBody(), "('", "')");

		search.createParam("ENC", enc);

		AirChinaRequest request = new AirChinaRequest();
		Response requestResponse = request.getResponse(search);

		// System.err.println(response.getResponseBody());

		return requestResponse;
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		List<CuriosityFare> fares = Lists.newArrayList();
	
		Document requestDocument = Jsoup.parse(getResponse(search).getResponseBody());
		try{
			asyncHttpClient = new AsyncHttpClient();
			Elements errorElements = requestDocument.select("li[class=WDSErrorE]");
			if (errorElements.size() == 0) {
	
				String fareLink = requestDocument.select("form[name=SDAIForm]").first().attr("action");
	
				int obNum = requestDocument.select("td[id^=tdRadioBtIndex_0]").size();
	
				if (search.isRoundtrip()) {
					int ibNum = requestDocument.select("td[id^=tdRadioBtIndex_1]").size();
					for (int i = 0; i < obNum; i++) {
						for (int j = 0; j < ibNum; j++) {
							
							Response response = asyncHttpClient
								.preparePost(URL + fareLink)
								.setParameters(getParams(search, String.valueOf(i), String.valueOf(j)))
								.execute()
								.get();
	
							Document fareDocument = Jsoup.parse(response.getResponseBody());
	
							fares.add(parseFare(search, fareDocument));
						
						}
					}
				} else {
					for (int i = 0; i < obNum; i++) {
					
						Response response = asyncHttpClient
							.preparePost(URL + fareLink)
							.setParameters(getParams(search, String.valueOf(i), ""))
							.execute()
							.get();
	
						Document fareDocument = Jsoup.parse(response.getResponseBody());
	
						fares.add(parseFare(search, fareDocument));
						
					}
				}
	
				
			} else {
				fares=null;
			}
		}catch(Exception ex){
			if(asyncHttpClient!=null){
				asyncHttpClient.close();
			}
			fares= null;
			
		}finally{
			if(asyncHttpClient!=null){
				asyncHttpClient.close();
			}
		}
		return fares;
	}

	private FluentStringsMap getParams(CuriositySearch search, String obId, String ibId) {
		FluentStringsMap params = new FluentStringsMap();

		if (search.isRoundtrip()) {
			params.add("ROW_2", ibId);
		}

		params.add("CABIN", "E");
		params.add("PAGE_TICKET", "0");
		params.add("PLTG_SEARCH_RECAP_UPDATED", "true");
		params.add("RESTRICTION", "true");
		params.add("ROW_1", obId);
		params.add("TRIP_TYPE", search.isRoundtrip() ? "R" : "O");

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
		fare.setAirlineCode("CA");
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

			segment.setDepartureName(StringUtils.substringBefore(depName, ", terminal"));
			segment.setArrivalName(StringUtils.substringBefore(arrName, ", terminal"));

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
