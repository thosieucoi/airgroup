package com.airgroup.services.impl.lufthansa;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.ning.http.client.Response;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;

public class LufthansaFlight extends SearchFlights {
	private static final DateTimeFormatter PARSER_DATE_TIME_FORMATTER = DateTimeFormat
			.forPattern("dd-MM-yyyyHH:mm");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("dd-MM-yyyy");
	protected static final String DEPARTURE_FLIGHT_NUMBER_SELECT = "DEPARTURE_FLIGHT_NUMBER_SELECT";
	protected static final String ARRIVAL_FLIGHT_NUMBER_SELECT = "ARRIVAL_FLIGHT_NUMBER_SELECT";

	@Override
	protected Response getResponse(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		LufthansaFlightResponseFirst response = new LufthansaFlightResponseFirst();
		return response.getResponse(search);
	}

	private Response getFlightPriceAndCurr(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		LufthansaFlightResponseSecond response = new LufthansaFlightResponseSecond();
		return response.getResponse(search);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search)
			throws IOException, InterruptedException, ExecutionException {
		try {
			Response results = getResponse(search);
			String content = results.getResponseBody();
			String jsessionId = "jsessionid="
					+ StringUtils
							.substringBetween(content, "jsessionid=", "?'");
			Document document = Jsoup.parse(content);
			Elements elements = document
					.select("table#holderTable > tbody > tr > td > table");
			List<CuriosityFare> fares = Lists.newArrayList();
			List<CuriosityFare> fareDepartures = Lists.newArrayList();
			List<CuriosityFare> fareArrvials = Lists.newArrayList();
			if (elements.size() > 0) {
				Elements elementDepartures = elements.get(0).select("tbody>tr");

				Elements flightSegments = new Elements();
				for (int i = 2; i < elementDepartures.size(); i++) {
					if (elementDepartures.get(i).select("input[name=ROW_1]")
							.val() != "") {
						if (flightSegments.size() > 0) {
							List<CuriositySegment> outboundSegments = parseSegments(
									flightSegments, search, true);
							CuriosityFare fare = new CuriosityFare();
							fare.setOutboundSegments(outboundSegments);
							fare.setAirlineCode("LH");
							fareDepartures.add(fare);
							flightSegments.clear();
						}
						flightSegments.add(elementDepartures.get(i));
					} else {
						if (flightSegments.size() > 0) {
							flightSegments.add(elementDepartures.get(i));
						}
						if (i == elementDepartures.size() - 1) {
							List<CuriositySegment> outboundSegments = parseSegments(
									flightSegments, search, true);
							CuriosityFare fare = new CuriosityFare();
							fare.setOutboundSegments(outboundSegments);
							fare.setAirlineCode("LH");
							fareDepartures.add(fare);
							flightSegments.clear();
						}
					}
				}
				if (search.isRoundtrip()) {
					Elements elementArrvials = elements.get(1).select(
							"tbody>tr");
					for (int i = 2; i < elementArrvials.size(); i++) {
						if (elementArrvials.get(i).select("input[name=ROW_2]")
								.val() != "") {
							if (flightSegments.size() > 0) {
								List<CuriositySegment> inboundSegments = parseSegments(
										flightSegments, search, false);
								CuriosityFare fare = new CuriosityFare();
								fare.setInboundSegments(inboundSegments);
								fareArrvials.add(fare);
								flightSegments.clear();
							}
							flightSegments.add(elementArrvials.get(i));
						} else {
							if (flightSegments.size() > 0) {
								flightSegments.add(elementArrvials.get(i));
							}
							if (i == elementArrvials.size() - 1) {
								List<CuriositySegment> inboundSegments = parseSegments(
										flightSegments, search, false);
								CuriosityFare fare = new CuriosityFare();
								fare.setInboundSegments(inboundSegments);
								fareArrvials.add(fare);
								flightSegments.clear();
							}
						}
					}
				}
				if (fareArrvials.size() > 0) {
					if (fareArrvials.size() > fareDepartures.size()) {
						for (int i = 0; i < fareArrvials.size(); i++) {
							search.getParams().clear();
							search.createParam("JSESSION", "jsessionid="
									+ jsessionId);
							search.createParam("ARRIVAL_FLIGHT_NUMBER_SELECT",
									i);
							if (i < fareDepartures.size()) {
								search.createParam(
										"DEPARTURE_FLIGHT_NUMBER_SELECT", i);
							} else {
								search.createParam(
										"DEPARTURE_FLIGHT_NUMBER_SELECT", 0);
							}

							Response infoPrice = getFlightPriceAndCurr(search);
							String contentPrice = infoPrice.getResponseBody();
							Document documentPrice = Jsoup.parse(contentPrice);
						//	System.err.print(documentPrice);
							Float pricePerAdult = 0F;
							Float pricePerChild = 0F;
							Float pricePerIns = 0F;
							Elements priceElements = documentPrice
									.select("table#fareBreakdown>tbody>tr");
							//priceElements.remove(priceElements.size() - 1);
							for (int j = 1; j < priceElements.size(); j++) {
								Float basePrice = Float
										.parseFloat(priceElements.get(j)
												.select("td.price").text())
										+ Float.parseFloat(priceElements.get(j)
												.select("td.tax").text());
								String value = priceElements.get(j)
										.select("td").get(6).text().split(" ")[1];
								if (value.contains("Adult")) {
									pricePerAdult = basePrice;
								} else if (value.contains("Child")) {
									pricePerChild = basePrice;
								} else if (value.contains("Infant")) {
									pricePerIns = basePrice;
								}
							}
							Float totalPrice = 0f;
							if (!documentPrice.select("td#totalPriceCell")
									.text().equalsIgnoreCase("")
									&& documentPrice
											.select("td#totalPriceCell").text() != null) {
								totalPrice = Float.parseFloat(documentPrice
										.select("td#totalPriceCell").text()
										.trim());
								String currency = StringUtils.substringBetween(
										contentPrice, "BFCurrency=", ";")
										.trim();
								CuriosityFare fare = new CuriosityFare();
								fare.setPrice(totalPrice);
								fare.setPricePerAdult(pricePerAdult);
								fare.setPricePerChild(pricePerChild);
								fare.setCurrencyCode(currency.substring(1,
										currency.length() - 1));
								fare.setInboundSegments(fareArrvials.get(i)
										.getInboundSegments());
								if (i < fareDepartures.size()) {
									fare.setOutboundSegments(fareDepartures
											.get(i).getOutboundSegments());
								} else {
									fare.setOutboundSegments(fareDepartures
											.get(0).getOutboundSegments());
								}
								fare.setAirlineCode("LH");
								fares.add(fare);
							}
						}
					} else {
						for (int i = 0; i < fareDepartures.size(); i++) {
							search.getParams().clear();
							search.createParam("JSESSION", jsessionId);
							if (i < fareArrvials.size()) {
								search.createParam(
										"ARRIVAL_FLIGHT_NUMBER_SELECT", i);
							} else {
								search.createParam(
										"ARRIVAL_FLIGHT_NUMBER_SELECT", 0);
							}
							search.createParam(
									"DEPARTURE_FLIGHT_NUMBER_SELECT", i);
							Response infoPrice = getFlightPriceAndCurr(search);
							String contentPrice = infoPrice.getResponseBody();
							Document documentPrice = Jsoup.parse(contentPrice);
//							System.err.print(documentPrice);
							Float pricePerAdult = 0F;
							Float pricePerChild = 0F;
							Float pricePerIns = 0F;
							Elements priceElements = documentPrice
									.select("table#fareBreakdown>tbody>tr");
							//priceElements.remove(priceElements.size() - 1);
							for (int j = 1; j < priceElements.size()-1; j++) {
								Float basePrice = Float
										.parseFloat(priceElements.get(j)
												.select("td.price").text())
										+ Float.parseFloat(priceElements.get(j)
												.select("td.tax").text());
								String value = priceElements.get(j)
										.select("td").get(6).text().split(" ")[1];
								if (value.contains("Adult")) {
									pricePerAdult = basePrice;
								} else if (value.contains("Child")) {
									pricePerChild = basePrice;
								} else if (value.contains("Infant")) {
									pricePerIns = basePrice;
								}
							}
							Float totalPrice = 0f;
							if (!documentPrice.select("td#totalPriceCell")
									.text().equalsIgnoreCase("")
									&& documentPrice
											.select("td#totalPriceCell").text() != null) {
								totalPrice = Float.parseFloat(documentPrice
										.select("td#totalPriceCell").text()
										.trim());
								String currency = StringUtils.substringBetween(
										contentPrice, "BFCurrency=", ";")
										.trim();
								CuriosityFare fare = new CuriosityFare();
								fare.setPrice(totalPrice);
								fare.setPricePerAdult(pricePerAdult);
								fare.setPricePerChild(pricePerChild);
								fare.setCurrencyCode(currency.substring(1,
										currency.length() - 1));
								fare.setOutboundSegments(fareDepartures.get(i)
										.getOutboundSegments());
								if (i < fareArrvials.size()) {
									fare.setInboundSegments(fareArrvials.get(i)
											.getInboundSegments());
								} else {
									fare.setInboundSegments(fareArrvials.get(0)
											.getInboundSegments());
								}
								fare.setAirlineCode("LH");
								fares.add(fare);
							}

						}
					}
				} else {
					for (int i = 0; i < fareDepartures.size(); i++) {
						search.getParams().clear();
						search.createParam("JSESSION", jsessionId);
						search.createParam("DEPARTURE_FLIGHT_NUMBER_SELECT", i);
						Response infoPrice = getFlightPriceAndCurr(search);
						String contentPrice = infoPrice.getResponseBody();
						Document documentPrice = Jsoup.parse(contentPrice);
						Float pricePerAdult = 0F;
						Float pricePerChild = 0F;
						Float pricePerIns = 0F;
						Elements priceElements = documentPrice
								.select("table#fareBreakdown>tbody>tr");
						//System.err.print(priceElements);
						//priceElements.remove(priceElements.size() - 1);
						for (int j = 1; j < priceElements.size()-1; j++) {
							Float basePrice = Float.parseFloat(priceElements
									.get(j).select("td.price").text())
									+ Float.parseFloat(priceElements.get(j)
											.select("td.tax").text());
							String value = priceElements.get(j).select("td")
									.get(6).text().split(" ")[1];
							if (value.contains("Adult")) {
								pricePerAdult = basePrice;
							} else if (value.contains("Child")) {
								pricePerChild = basePrice;
							} else if (value.contains("Infant")) {
								pricePerIns = basePrice;
							}
						}
						Float totalPrice = 0f;
						if (!documentPrice.select("td#totalPriceCell").text()
								.equalsIgnoreCase("")
								&& documentPrice.select("td#totalPriceCell")
										.text() != null) {
							totalPrice = Float.parseFloat(documentPrice
									.select("td#totalPriceCell").text().trim());
							String currency = StringUtils.substringBetween(
									contentPrice, "BFCurrency=", ";").trim();
							fareDepartures.get(i).setPrice(totalPrice);
							fareDepartures.get(i).setPricePerAdult(
									pricePerAdult);
							fareDepartures.get(i).setPricePerChild(
									pricePerChild);
							fareDepartures.get(i)
									.setCurrencyCode(
											currency.substring(1,
													currency.length() - 1));
							
						}
					}
					fares.addAll(fareDepartures);
				}
			}
			return fares;
		} catch (Exception e) {
			return null;
		}
	}

	private List<CuriositySegment> parseSegments(Elements elements,
			CuriositySearch search, Boolean check) {
		List<CuriositySegment> segments = Lists.newArrayList();
		DateTime dateDefault = new DateTime();
		if (check) {
			dateDefault = search.getOutboundDate();
		}else{
			dateDefault = search.getInboundDate();
		}
		
		for (int i = 0; i < elements.size(); i++) {
			CuriositySegment segment = new CuriositySegment();
			String flightInfo = StringUtils.substringBetween(elements.get(i)
					.toString(), "popFLIF(", ")");
			String[] flightInfoArray = flightInfo.split(",");

			segment.setDepartureCode(StringUtils.substringBetween(
					flightInfoArray[1].trim(), "'", "'"));
			segment.setArrivalCode(StringUtils.substringBetween(
					flightInfoArray[2].trim(), "'", "'"));
			segment.setAirlineCode(StringUtils.substringBetween(
					flightInfoArray[3].trim(), "'", "'"));
			segment.setFlightNumber(StringUtils.substringBetween(
					flightInfoArray[4].trim(), "'", "'"));
			String depTime;

			Elements dateElements = elements.get(i).select("var");
			if (dateElements.get(0).toString().contains("strong")) {
				depTime = StringUtils
						.substringBetween(dateElements.get(0).toString(),
								"<strong>", "</strong>").trim();
			} else {
				depTime = StringUtils.substringBetween(
						dateElements.get(0).toString(), "<br />", "</var>")
						.trim();
			}
			if (depTime.contains("+")) {
				dateDefault = dateDefault
						.plusDays(Integer.parseInt(depTime.substring(
								depTime.indexOf("+") + 1, (depTime.length()))));
				depTime = depTime.substring(0, (depTime.indexOf("+") - 1));
			}
			segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER,
					DATE_FORMATTER.print(dateDefault) + depTime);
			String arrTime;
			if (dateElements.get(1).toString().contains("strong")) {
				arrTime = StringUtils
						.substringBetween(dateElements.get(1).toString(),
								"<strong>", "</strong>").trim();
			} else {
				arrTime = StringUtils.substringBetween(
						dateElements.get(1).toString(), "<br />", "</var>")
						.trim();
			}
			if (arrTime.contains("+")) {
				dateDefault = dateDefault
						.plusDays(Integer.parseInt(arrTime.substring(
								arrTime.indexOf("+") + 1, (arrTime.length()))));
				arrTime = arrTime.substring(0, (arrTime.indexOf("+") - 1));
			}
			segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER,
					DATE_FORMATTER.print(dateDefault) + arrTime);
			segments.add(segment);
		}
		return segments;
	}
}
