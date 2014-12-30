package com.airgroup.services.impl.britishairways;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import com.google.common.collect.Lists;
import com.ning.http.client.Response;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;

public class BritishAirwaysFlight extends SearchFlights {
	private static final DateTimeFormatter PARSER_DATE_TIME_FORMATTER = DateTimeFormat
			.forPattern("d MMM yyHH:mm");
	private static final DateTimeFormatter PARSER_DATE_TIME_FORMATTER_NO_PRICE = DateTimeFormat
			.forPattern("HH:mm-d MMM yy");
	protected static final String DEPARTURE_FLIGHT_NUMBER_SELECT = "DEPARTURE_FLIGHT_NUMBER_SELECT";
	protected static final String ARRIVAL_FLIGHT_NUMBER_SELECT = "ARRIVAL_FLIGHT_NUMBER_SELECT";

	@Override
	protected Response getResponse(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		BritishAirwaysFlightResponseFirst response = new BritishAirwaysFlightResponseFirst();
		return response.getResponse(search);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search)
			throws IOException, InterruptedException, ExecutionException {
		try{
		Response results = getResponse(search);
		String content = results.getResponseBody();
		Document doccumentFirstRequest = Jsoup.parse(content);
		search.createParam("eId", doccumentFirstRequest.select("input[name=eId]").val());
		BritishAirwaysFlightResponseSecond responseSecond = new BritishAirwaysFlightResponseSecond();
		Response newResults = responseSecond.getResponseWithCookies(search, results.getCookies());
		String contentSecondRequest = newResults.getResponseBody();
		
		String timespanSecondRequest = StringUtils.substringBetween(contentSecondRequest, "&timestamp=", "';");
		search.createParam("time", timespanSecondRequest);
		
		BritishAirwaysFlightResponseThird responseThird = new BritishAirwaysFlightResponseThird();
		String contentThirdRequest = responseThird.getResponseWithCookies(search, results.getCookies()).getResponseBody();
		Document doccumentThirdRequest = Jsoup.parse(contentThirdRequest);
		
		List<CuriosityFare> fares = Lists.newArrayList();
		List<CuriosityFare> fareDepartures = Lists.newArrayList();
		List<CuriosityFare> fareArrvials = Lists.newArrayList();
		Elements elements = doccumentThirdRequest.select("table.directFlight");
		if (elements.size()>0) {
			Elements flightDepartureElements = elements.get(0).select("tbody#outboundDates>tr");
			for (Element element : flightDepartureElements) {
				List<CuriositySegment> outboundSegments = parseSegments(element);
				CuriosityFare fare = new CuriosityFare();
				fare.setOutboundSegments(outboundSegments);
				Elements priceElements = element.select("label");
				Float price = Float.parseFloat(priceElements.get(0).text().substring(1));
				String currency = StringUtils.substringBetween(doccumentThirdRequest.toString(), "CountryCurrency='", "'");
				fare.setPrice(price);
				fare.setPricePerAdult(price/search.getPassengersCount());
				if (search.getChildrenCount()>0) {
					fare.setPricePerChild(price/search.getPassengersCount());
				}
				fare.setCurrencyCode(currency);
				fare.setAirlineCode("BA");
				fareDepartures.add(fare);
			}
			if (search.isRoundtrip()) {
				Elements flightArrivalElements = elements.get(1).select("tbody#inboundDates>tr");
				for (Element element : flightArrivalElements) {
					List<CuriositySegment> inboundSegments = parseSegments(element);
					CuriosityFare fare = new CuriosityFare();
					fare.setInboundSegments(inboundSegments);
					Elements priceElements = element.select("label");
					Float price = Float.parseFloat(priceElements.get(0).text().substring(1));
					String currency = StringUtils.substringBetween(doccumentThirdRequest.toString(), "CountryCurrency='", "'");
					fare.setPrice(price);
					fare.setCurrencyCode(currency);
					fareArrvials.add(fare);
				}
				for (CuriosityFare fareDeparture : fareDepartures) {
					for (CuriosityFare fareArrvial : fareArrvials) {
						CuriosityFare fare = new CuriosityFare();
						fare.setOutboundSegments(fareDeparture.getOutboundSegments());
						fare.setInboundSegments(fareArrvial.getInboundSegments());
						Float price = fareDeparture.getPrice() + fareArrvial.getPrice();
						fare.setPrice(price);
						fare.setPricePerAdult(price/search.getPassengersCount());
						if (search.getChildrenCount()>0) {
							fare.setPricePerChild(price/search.getPassengersCount());
						}
						fare.setPrice(price);
						fare.setCurrencyCode(fareDeparture.getCurrencyCode());
						fare.setAirlineCode("BA");
						fares.add(fare);
					}
				}
			}else{
				fares.addAll(fareDepartures);
			}
		}else{
			BritishAirwaysFlightResponseFourth responseFourth = new BritishAirwaysFlightResponseFourth();
			String contenFourthRequest = responseFourth.getResponseWithCookies(search, results.getCookies()).getResponseBody();
			Document documentFourthRequest = Jsoup.parse(contenFourthRequest);
			
			Elements NoPriceFlightDepartureElements = documentFourthRequest.select("div#out0boundCjs > div.outInboundCjs>div.journeys>div.cjHolder>table.cj");
			for (Element element : NoPriceFlightDepartureElements) {
				List<CuriositySegment> outboundSegments = parseSegmentsNoPrice(element);
				CuriosityFare fare = new CuriosityFare();
				fare.setOutboundSegments(outboundSegments);
				fare.setAirlineCode("BA");
				fareDepartures.add(fare);
			}
			if (search.isRoundtrip()) {
				//System.err.print(documentFourthRequest);
				Elements NoPriceFlightArrvialElements = documentFourthRequest.select("div#inboundCjs > div.outInboundCjs>div.journeys>div.cjHolder>table.cj");
				for (Element element : NoPriceFlightArrvialElements) {
					List<CuriositySegment> inboundSegments = parseSegmentsNoPrice(element);
					CuriosityFare fare = new CuriosityFare();
					fare.setInboundSegments(inboundSegments);
					fareArrvials.add(fare);
				}
			}
			if (fareArrvials.size()>0) {
				for (int i = 0; i < fareDepartures.size(); i++) { 
					int j = 0;
					if (i<fareArrvials.size()) {
						j=i;
					}
						search.getParams().clear();		
						search.createParam(DEPARTURE_FLIGHT_NUMBER_SELECT, i);
						search.createParam(ARRIVAL_FLIGHT_NUMBER_SELECT, j);
						
						String eId = documentFourthRequest.select("input[name=eId]").val();
						search.createParam("eId", eId);
						BritishAirwaysFlightResponseFifth responseFifth = new BritishAirwaysFlightResponseFifth();
						String contentSixthRequest = responseFifth.getResponseWithCookies(search, results.getCookies()).getResponseBody();
						
						String timespanSixth = StringUtils.substringBetween(contentSixthRequest, "&timestamp=", "';");
						search.getParams().clear();
						search.createParam("time", timespanSixth);
						BritishAirwaysFlightResponseSixth responseSixth = new BritishAirwaysFlightResponseSixth();
						Document newDocumentSixth = Jsoup.parse(responseSixth.getResponseWithCookies(search, results.getCookies()).getResponseBody());
						
						if (newDocumentSixth.select("p.important").size()>0) {
							String totalPriceString = newDocumentSixth.select("p.important").get(0).text().split(" ")[1];
							Float totalPriceFloat;
							if (totalPriceString.contains(",")) {
								totalPriceFloat = Float.parseFloat(totalPriceString.replace(",", ""));
							}else{
								totalPriceFloat = Float.parseFloat(totalPriceString);
							}
							Elements basePriceElement = newDocumentSixth.select("table.sqBreakdown>tbody>tr.nowrap");
							//System.err.print(basePriceElement);
							String adultBasePriceString = basePriceElement.get(0).select("td").get(1).text().split(" ")[1];
							String adultTax = basePriceElement.get(0).select("td").get(2).text().split(" ")[1].trim().replace("\u00A0", "");
							Float adultBasePriceFloat;
							if (adultBasePriceString.contains(",")||adultTax.contains(",")) {
								adultBasePriceFloat = Float.parseFloat(adultBasePriceString.replace(",", "")) + Float.parseFloat(adultTax.replace(",", ""));
							}else{
								adultBasePriceFloat = Float.parseFloat(adultBasePriceString) + Float.parseFloat(adultTax);
							}
							Float childBasePriceFloat = 0F;
							if (basePriceElement.size()>1) {
								String childBasePriceString = basePriceElement.get(1).select("td").get(1).text().split(" ")[1];
								String childTax = basePriceElement.get(1).select("td").get(2).text().split(" ")[1].trim().replace("\u00A0", "");
								if (childBasePriceString.contains(",")||childTax.contains(",")) {
									childBasePriceFloat = Float.parseFloat(childBasePriceString.replace(",", "")) + Float.parseFloat(childTax.replace(",", ""));
								}else{
									childBasePriceFloat = Float.parseFloat(childBasePriceString)+Float.parseFloat(childTax);
								}
							}
							String curency = StringUtils.substringBetween(newDocumentSixth.toString(), "CurrencyCode = '", "'");
							CuriosityFare fare = new CuriosityFare();
							fare.setPrice(totalPriceFloat);
							fare.setPricePerAdult(adultBasePriceFloat);
							fare.setPricePerChild(childBasePriceFloat);
							fare.setCurrencyCode(curency);
							fare.setOutboundSegments(fareDepartures.get(i).getOutboundSegments());
							fare.setInboundSegments(fareArrvials.get(j).getInboundSegments());
							fare.setAirlineCode("BA");
							fares.add(fare);
						}
				}
			}else{
				for (int i = 0; i < fareDepartures.size(); i++) {
					search.getParams().clear();		
					search.createParam(DEPARTURE_FLIGHT_NUMBER_SELECT, i);
					String eId = documentFourthRequest.select("input[name=eId]").val();
					search.createParam("eId", eId);
					BritishAirwaysFlightResponseFifth responseFifth = new BritishAirwaysFlightResponseFifth();
					String contentSixthRequest = responseFifth.getResponseWithCookies(search, results.getCookies()).getResponseBody();

					String timespanSixth = StringUtils.substringBetween(contentSixthRequest, "&timestamp=", "';");
					search.getParams().clear();
					search.createParam("time", timespanSixth);
					BritishAirwaysFlightResponseSixth responseSixth = new BritishAirwaysFlightResponseSixth();
					Document newDocumentSixth = Jsoup.parse(responseSixth.getResponseWithCookies(search, results.getCookies()).getResponseBody());
					if (newDocumentSixth.select("p.important").size()>0) {
						String totalPriceString = newDocumentSixth.select("p.important").get(0).text().split(" ")[1];
						Float totalPriceFloat;
						if (totalPriceString.contains(",")) {
							totalPriceFloat = Float.parseFloat(totalPriceString.replace(",", ""));
						}else{
							totalPriceFloat = Float.parseFloat(totalPriceString);
						}
						Elements basePriceElement = newDocumentSixth.select("table.sqBreakdown>tbody>tr.nowrap");
						String adultBasePriceString = basePriceElement.get(0).select("td").get(1).text().split(" ")[1];
						String adultTax = basePriceElement.get(0).select("td").get(2).text().split(" ")[1].trim().replace("\u00A0", "");
						Float adultBasePriceFloat;
						if (adultBasePriceString.contains(",")||adultTax.contains(",")) {
							adultBasePriceFloat = Float.parseFloat(adultBasePriceString.replace(",", "")) + Float.parseFloat(adultTax.replace(",", ""));
						}else{
							adultBasePriceFloat = Float.parseFloat(adultBasePriceString) + Float.parseFloat(adultTax);
						}
						Float childBasePriceFloat = 0F;
						if (basePriceElement.size()>1) {
							String childBasePriceString = basePriceElement.get(1).select("td").get(1).text().split(" ")[1];
							String childTax = basePriceElement.get(1).select("td").get(2).text().split(" ")[1].trim().replace("\u00A0", "");
							if (childBasePriceString.contains(",")||childTax.contains(",")) {
								childBasePriceFloat = Float.parseFloat(childBasePriceString.replace(",", "")) + Float.parseFloat(childTax.replace(",", ""));
							}else{
								childBasePriceFloat = Float.parseFloat(childBasePriceString)+Float.parseFloat(childTax);
							}
						}
						String curency = StringUtils.substringBetween(newDocumentSixth.toString(), "CurrencyCode = '", "'");
						fareDepartures.get(i).setPrice(totalPriceFloat);
						fareDepartures.get(i).setCurrencyCode(curency);
						fareDepartures.get(i).setPricePerAdult(adultBasePriceFloat);
						fareDepartures.get(i).setPricePerChild(childBasePriceFloat);
						fares.add(fareDepartures.get(i));
					}	
				}
			}
		}
		return fares;
		}catch (Exception e){
			return null;
		}
	}

	private List<CuriositySegment> parseSegments(Element element) {
		List<CuriositySegment> segments = Lists.newArrayList();
		CuriositySegment segment = new CuriositySegment();
		Elements timeElements = element.select("span.time");
		Elements dateElements = element.select("span.date");

		String depDateTime = element.select("span.depDate").text();
		String depYear = depDateTime.substring(depDateTime.length()-2);
		String depTime = timeElements.get(0).text();
		String depDateMonth = dateElements.get(0).text().trim();
		
		String arrTime = timeElements.get(1).text();
		String arrDateMonth = dateElements.get(1).text().trim();
		
		segment.setDepartureCode(element.select("span.from").text());
		segment.setArrivalCode(element.select("span.to").text());
		segment.setAirlineCode(element.select("span.Carrier").text());
		segment.setFlightNumber(element.select("span.FlightNumber").text());
		segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER,depDateMonth + " " + depYear+depTime);
		segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER,arrDateMonth + " " + depYear+arrTime);
		segments.add(segment);

		return segments;
	}
	private List<CuriositySegment> parseSegmentsNoPrice(Element element) {
		List<CuriositySegment> segments = Lists.newArrayList();
		Elements elementSegments = element.select("tbody>tr");
		elementSegments.remove(elementSegments.size()-1);
		for (Element elementSegment : elementSegments){
			CuriositySegment segment = new CuriositySegment();
			
			String depDateTime = elementSegment.select("td.dep").text().trim();
			String arrDateTime = elementSegment.select("td.arr").text().trim();
			
			
			String[] flightInfo = StringUtils.substringBetween(elementSegment.toString(), "openFlightDetails(", ")").split(",");
			
			segment.setAirlineCode(StringUtils.substringBetween(flightInfo[0], "'", "'"));
			segment.setFlightNumber(StringUtils.substringBetween(flightInfo[1], "'", "'"));
			
			segment.setDepartureCode(StringUtils.substringBetween(flightInfo[2], "'", "'"));
			segment.setArrivalCode(StringUtils.substringBetween(flightInfo[3], "'", "'"));
			String dateTimeFlight = StringUtils.substringBetween(flightInfo[4], "'", "'");
			segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER_NO_PRICE,depDateTime + " " + dateTimeFlight.substring(dateTimeFlight.length()-2));
			segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER_NO_PRICE,arrDateTime + " " + dateTimeFlight.substring(dateTimeFlight.length()-2));
			segments.add(segment);
		}
		return segments;
	}
}
