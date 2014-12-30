/**
 * 
 */
package com.airgroup.services.impl.malaysiaairlines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
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
 * @author linhnd1
 * 
 */
public class MalaysiaAirlinesFlight extends SearchFlights {

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat
		.forPattern("yyyyMMdd HH:mm");
	private static final String COOKIES = "MALAY_COOKIES";
	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		MalaysiaAirlinesStartAction startAction = new MalaysiaAirlinesStartAction();
		List<Cookie> cookies = startAction.getResponse(search).getCookies();
		search.createParam(COOKIES, cookies);
		MalaysiaAirlinesFareSearch fareSearch = new MalaysiaAirlinesFareSearch();
		fareSearch.getResponseWithCookies(search, cookies);
		
		MalaysiaAirlinesFamiliesForward familiesFoward = new MalaysiaAirlinesFamiliesForward();

		return familiesFoward.getResponseWithCookies(search, cookies);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		String content = getResponse(search).getResponseBody();
		Document document = Jsoup.parse(content);
		List<CuriosityFare> fares = Lists.newArrayList();
		String obPrice = "0";
		String taxElement = StringUtils.substringBetween(content, "<th>Taxes", "/td>");
		try{
			taxElement = StringUtils.substringBetween(taxElement.toString(), "USD ", "<");
		}catch(Exception ex){
			taxElement = StringUtils.substringBetween(content, "<th>Surcharges", "/td>");
			taxElement = StringUtils.substringBetween(taxElement.toString(), "USD ", "<");
		}
		float tax = Utils.floatFromString(taxElement);
		Elements listElementsOb = document.select("div[id=resultsFFBlock1] > table > tbody > tr");
		List<Element> listObE = new ArrayList<Element>();
		List<CuriosityFare> obFares = Lists.newArrayList();
		List<CuriosityFare> ibFares = Lists.newArrayList();
		for (Element element : listElementsOb) {
			try{
				if (element.select("tr.combineRows").size() > 0) {
					listObE.add(element);
					obPrice = element.select("div.colPrice > label").first().text();
				} else {
					if (element.select("div.colPrice > label").size() > 0)
						obPrice = element.select("div.colPrice > label").first().text();
					listObE.add(element);
					List<CuriositySegment> segments = parseSegments(listObE, search.getCabin());
					String obCurrency = "USD";
					CuriosityFare obFare = CuriosityFare.newFromTrip(search);
					obFare.setOutboundSegments(segments);
					obFare.setPrice(Utils.floatFromString(obPrice));
					obFare.setCurrencyCode(obCurrency);
					obFare.setAirlineCode("MH");
					obFares.add(obFare);
					listObE.clear();
				}
			}
			catch(Exception ex){
			}
		}
		if (search.isRoundtrip()) {
			String ibPrice = "0";
			Elements listElementsIb = document
				.select("div[id=resultsFFBlock2] > table > tbody > tr");
			List<Element> listIbE = new ArrayList<Element>();
			for (Element element : listElementsIb) {
				try{
					if (element.select("tr.combineRows").size() > 0) {
						listIbE.add(element);
						ibPrice = element.select("div.colPrice > label").first().text();
					} else {
						listIbE.add(element);
						if (element.select("div.colPrice > label").size() > 0)
							ibPrice = element.select("div.colPrice > label").first().text();
						List<CuriositySegment> segments = parseSegments(listIbE, search.getCabin());
						String ibCurrency = "USD";
						CuriosityFare ibFare = CuriosityFare.newFromTrip(search);
						ibFare.setInboundSegments(segments);
						ibFare.setPrice(Utils.floatFromString(ibPrice));
						ibFare.setCurrencyCode(ibCurrency);
						ibFare.setAirlineCode("MH");
						ibFares.add(ibFare);
						listIbE.clear();
					}
				}catch(Exception ex){
					
				}
			}
		}
		if(search.isDomestic()){
			for (CuriosityFare obFare : obFares) {
				obFare.setPrice(obFare.getPrice() + tax);
				obFare.setPricePerAdult(obFare.getPrice()/search.getPassengersCount());
				obFare.setPricePerChild(obFare.getPrice()/search.getPassengersCount());
				fares.add(obFare);
			}

			for (CuriosityFare ibFare : ibFares) {
				ibFare.setPrice(ibFare.getPrice() + tax);
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
						fare.setAirlineCode("MH");
						fare.setPrice(ibFare.getPrice() + obFare.getPrice() + tax);
						fare.setPricePerAdult(fare.getPrice()/search.getPassengersCount());
						fare.setPricePerChild(fare.getPrice()/search.getPassengersCount());
						fares.add(fare);
					}
				}
				else{
					obFare.setPrice(obFare.getPrice() + tax);
					obFare.setPricePerAdult(obFare.getPrice()/search.getPassengersCount());
					obFare.setPricePerChild(obFare.getPrice()/search.getPassengersCount());
					fares.add(obFare);
				}
			}
		}
		return fares;
	}

	private List<CuriositySegment> parseSegments(List<Element> elements, Cabin cabin) {
		List<CuriositySegment> segments = Lists.newArrayList();
		for (Element eSegment : elements) {
			String[] fields = StringUtils
				.substringBetween(
					eSegment
						.select("td.colFlight > div > a")
						.first()
						.toString()
						.replaceAll("amp;", ""),
					"AirFlightDetailsGetAction.do?",
					"')")
				.split("&");
			String depTime = eSegment.select("td.colDepart > div").text();
			String arrTime = eSegment.select("td.colArrive > div").text();
			CuriositySegment segment = new CuriositySegment();
			segment.setAirlineCode(fields[0].split("=")[1]);
			segment.setFlightNumber(fields[1].split("=")[1]);
			segment.setDepartureCode(fields[2].split("=")[1]);
			segment.setArrivalCode(fields[3].split("=")[1]);
			
			
			segment.setDepartureTime(
				TIME_FORMATTER,
				Utils.parseDate(timeConvert(fields, depTime), "dd/MM/yyyy HH:mm"));
			segment.setArrivalTime(
				TIME_FORMATTER,
				Utils.parseDate(timeConvert(fields, arrTime), "dd/MM/yyyy HH:mm"));
			
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

	private String timeConvert(String[] timeField, String hour) {
		StringBuilder time = new StringBuilder();
		String month = String.valueOf(Integer.parseInt(timeField[5].split("=")[1]) + 1);
		time.append(timeField[6].split("=")[1]);
		time.append("-");
		time.append(numberNormalize(month));
		time.append("-");
		time.append(numberNormalize(timeField[4].split("=")[1]));
		DateTimeFormatter parser = ISODateTimeFormat.date();
		DateTime date = parser.parseDateTime(time.toString());
		if (hour.contains("+")) {
			date = date.plusDays(Integer.parseInt(hour.split("\\+")[1].trim()));
		}
		StringBuilder newTime = new StringBuilder();
		newTime.append(numberNormalize(String.valueOf(date.getDayOfMonth())) +
						"/" +
						numberNormalize(String.valueOf(date.getMonthOfYear())) +
						"/" +
						date.getYear());
		newTime.append(" ");
		newTime.append(hour.split("\\+")[0]);
		return newTime.toString();
	}

	private String numberNormalize(String str) {
		try {
			if (Integer.parseInt(str) < 10) {
				str = "0" + str;
			}
			return str;
		} catch (NumberFormatException ex) {
			return "00";
		}
	}
}