package com.airgroup.services.impl.transasiaairways;

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
import com.ning.http.client.Response;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;

public class TransAsiaAirwaysFlight extends SearchFlights {

	private static final DateTimeFormatter PARSER_DATE_TIME_FORMATTER = DateTimeFormat
			.forPattern("yyyy/MM/dd (HH:mm)");

	@Override
	protected Response getResponse(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		TransAsiaAirwaysFlightResponse response = new TransAsiaAirwaysFlightResponse();
		return response.getResponse(search);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search)
			throws IOException, InterruptedException, ExecutionException {
		//try{
		String content = getResponse(search).getResponseBody();
//		System.err.println(content);
		Document document = Jsoup.parse(content);
		List<CuriosityFare> fares = Lists.newArrayList();
		int count = 0;
		if(search.isRoundtrip())
			count = 5;
		else
			count = 4;
		if (document.select("table>tbody>tr").size() > 1) {
			Element flightElement = document.select("table>tbody>tr").get(1)
					.select("td").get(0).select("table>tbody>tr>td").get(2)
					.select("table>tbody>tr").get(1).select("td").get(0)
					.select("table>tbody>tr>td").get(0)
					.select("table>tbody>tr").get(7).select("td").get(0);
			Element flightElement1 = flightElement.select("table>tbody>tr")
					.get(0).select("td").get(1).select("table>tbody>tr").get(7)
					.select("td").get(0).select("table>tbody>tr").get(0)
					.select("td").get(0);

			for (int i = 0; i < 100; i++) {
				//System.err.println(i);
				Elements segmentElements = flightElement1
						.select("table>tbody>tr.fareKind" + i);
				if (segmentElements.size() > 0) {
					List<CuriosityFare> fareDepartures = Lists.newArrayList();
					List<CuriosityFare> fareArrvials = Lists.newArrayList();
					if (segmentElements.size() >= count) {
						Element e = segmentElements.get(count-1);
						Elements elementTrs = e
								.select("td>table>tbody>tr>td>table>tbody");
						Float pricePerAdult = 0F;
						Float pricePerChild = 0F;
						Float pricePerIns = 0F;
						for (int j = 0; j < elementTrs.select("tr.black_2")
								.size(); j++) {
							if (j == 0) {
								Elements priceElements = elementTrs
										.select("tr.black_2").get(j)
										.select("td");
								pricePerAdult = Float.parseFloat(priceElements
										.get(priceElements.size() - 2).text()
										.split("/")[0]);
							} else if (j == 1) {
								Elements priceElements = elementTrs
										.select("tr.black_2").get(j)
										.select("td");
								pricePerChild = Float.parseFloat(priceElements
										.get(priceElements.size() - 2).text()
										.split("/")[0]);
							} else if (j == 2) {
								Elements priceElements = elementTrs
										.select("tr.black_2").get(j)
										.select("td");
								pricePerIns = Float.parseFloat(priceElements
										.get(priceElements.size() - 2).text()
										.split("/")[0]);
							}
						}
						Elements priceTotalTrs = elementTrs.select("tr");
						Element priceTotalTr = priceTotalTrs.get(priceTotalTrs
								.size() - 1);
						Elements priceTotalTds = priceTotalTr.select("td");
						Element priceTotalTd = priceTotalTds.get(priceTotalTds
								.size() - 1);

						Float priceTotal = Float
								.parseFloat(priceTotalTd.text());

						Element currencyTr = priceTotalTrs.get(0);
						Elements currencyTds = currencyTr.select("td");
						Element currencyTd = currencyTds
								.get(currencyTds.size() - 1);

						String currency = StringUtils.substringBetween(
								currencyTd.text(), "Total(", ")");

						// lay thong tin fare
						Elements segmentElement = segmentElements.get(
								segmentElements.size() - 1).select(
								"td[bgcolor=#999999]");

						Boolean check;
						for (Element element : segmentElement) {
							if (!element.select("input[name~=depArrTime]")
									.val().equalsIgnoreCase("")) {
								check = true;
								fareDepartures = parseSegments(element, search,
										check);
							} else if (!element
									.select("input[name~=arrDepTime]").val()
									.equalsIgnoreCase("")) {
								check = false;
								fareArrvials = parseSegments(element, search,
										check);
							}
						}
						if (fareArrvials.size()>0) {
							for (CuriosityFare fareDep : fareDepartures) {
								for (CuriosityFare fareArr : fareArrvials) {
									CuriosityFare fare = CuriosityFare
											.newFromTrip(search);
									fare.setOutboundSegments(fareDep
											.getOutboundSegments());
									fare.setInboundSegments(fareArr
											.getInboundSegments());
									fare.setPrice(priceTotal);
									fare.setPricePerAdult(pricePerAdult);
									fare.setPricePerChild(pricePerChild);
									fare.setCurrencyCode(currency);
									fare.setAirlineCode("GE");
									fares.add(fare);
								}
							}
						}else{
							for (CuriosityFare fareDep : fareDepartures) {
								CuriosityFare fare = CuriosityFare
										.newFromTrip(search);
								fare.setOutboundSegments(fareDep
										.getOutboundSegments());
								fare.setPrice(priceTotal);
								fare.setPricePerAdult(pricePerAdult);
								fare.setPricePerChild(pricePerChild);
								fare.setCurrencyCode(currency);
								fare.setAirlineCode("GE");
								fares.add(fare);
							}
						}
						
					}
				} else {
					break;
				}
			}
		}

		return fares;
//		}catch (Exception e){
//			return null;
//		}
	}

	private List<CuriosityFare> parseSegments(Element element,
			CuriositySearch search, Boolean segmentType) {
		List<CuriosityFare> fares = Lists.newArrayList();

		Elements segmentElements = element.select("table > tbody > tr");

		String depCode;
		String arrCode;
		String[] depArrInfo = segmentElements.get(0).select("td").text().trim()
				.split("to");
		depCode = depArrInfo[0].trim().substring(
				depArrInfo[0].trim().length() - 5,
				depArrInfo[0].trim().trim().length() - 2);
		arrCode = depArrInfo[1].trim().substring(
				depArrInfo[1].trim().length() - 4,
				depArrInfo[1].trim().trim().length() - 1);

		for (int i = 2; i < segmentElements.size(); i++) {
			CuriositySegment segment = new CuriositySegment();
			segment.setDepartureCode(depCode);
			segment.setArrivalCode(arrCode);
			CuriosityFare fare = CuriosityFare.newFromTrip(search);
			List<CuriositySegment> segments = Lists.newArrayList();
			Elements flightInfoElements = segmentElements.get(i).select("td");
			String[] flightCodeAndNumber = flightInfoElements.get(0).text()
					.split(" ");
			segment.setAirlineCode(flightCodeAndNumber[0].trim());
			segment.setFlightNumber(flightCodeAndNumber[1].trim());
			segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER,
					flightInfoElements.get(1).text());
			segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER,
					flightInfoElements.get(2).text());
			segments.add(segment);

			if (segmentType) {
				fare.setOutboundSegments(segments);
			} else {
				fare.setInboundSegments(segments);
			}
			// fare.getOutboundSegments().get(0).getFlightNumber()
			fares.add(fare);
		}
		return fares;
	}
}
