package com.airgroup.services.impl.allnipponairway;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
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

public class AllNipponAirwaysFlight extends SearchFlights {
	private static final DateTimeFormatter PARSER_DATE_TIME_FORMATTER = DateTimeFormat
			.forPattern("dd-MM-yyyyHH:mm");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("M/dd/yyyy");
	private static final DateTimeFormatter DATE_MONTH_FORMATTER = DateTimeFormat
			.forPattern("M/d");
	private static final DateTimeFormatter DATE_FORMATTER_PARSER = DateTimeFormat
			.forPattern("dd-MM-yyyy");
	protected static final String DEPARTURE_FLIGHT_NUMBER_SELECT = "DEPARTURE_FLIGHT_NUMBER_SELECT";
	protected static final String ARRIVAL_FLIGHT_NUMBER_SELECT = "ARRIVAL_FLIGHT_NUMBER_SELECT";
	protected static DateTime dateFlightDep = new DateTime();
	protected static DateTime dateFlightArr = new DateTime();

	@Override
	protected Response getResponse(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		AllNipponAirwaysFlightResponseFirst response = new AllNipponAirwaysFlightResponseFirst();
		return response.getResponse(search);
	}

	private Response getFlightPriceAndCurr(CuriositySearch search)
			throws InterruptedException, ExecutionException, IOException {
		AllNipponAirwaysFlightResponseSecond response = new AllNipponAirwaysFlightResponseSecond();
		return response.getResponse(search);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search)
			throws IOException, InterruptedException, ExecutionException {
		try{
		List<CuriosityFare> fares = Lists.newArrayList();
		Response results = getResponse(search);
		String content = results.getResponseBody();
		// String jsessionId = "jsessionid=" +
		// StringUtils.substringBetween(content, "jsessionid=", "?'");
		Document document = Jsoup.parse(content);

		String url = document.select("form[id=TABLE]").attr("action");
		if (search.isRoundtrip()) {
			search.createParam("newurl", url);
		}else{
			search.createParam("newurl", url.replace("preReSearchRoundtripResult","preReSearchAvailResult"));
		}

		AllNipponAirwaysFlightResponseSecond responseSecond = new AllNipponAirwaysFlightResponseSecond();
		Response newResults = responseSecond.getResponse(search);
		String contentSecond = newResults.getResponseBody();
		//System.err.print(contentSecond);
		Document documentSecond = Jsoup.parse(contentSecond);
		String jsessionId = StringUtils.substringBetween(newResults
				.getHeaders().toString(), "cookie=", ";");
		
		String urlSecond = "";
		if (search.isRoundtrip()) {
			urlSecond = documentSecond.select(
					"form[name=reSearchRtResultForm]").attr("action");
		}else{
			urlSecond = documentSecond.select("form[name=reSearchAvailItinListForm]").attr("action");
		}
		
		search.createParam("newurlSecond", urlSecond);
		search.createParam("jsessionId", jsessionId);
		String btnVar = documentSecond.select("input[name=button.var]").get(0)
				.val();
		String token = documentSecond.select("input[name=TOKEN]").get(0).val();
		String DISPBEAN_KEY = documentSecond.select("input[name=DISPBEAN_KEY]")
				.get(0).val();
		String fromRadio = "";
		String toRadio = "";
		//System.err.print(documentSecond);
		Elements elementDeps = documentSecond
				.select("input[name=selectFlightFromRadio]");
		Elements elementArrs = documentSecond
				.select("input[name=selectFlightToRadio]");
		if (search.isRoundtrip()) {
		for (Element eDep : elementDeps) {
			
				for (Element eArr : elementArrs) {
					fromRadio = eDep.val();
					toRadio = eArr.val();
					search.createParam("fromRadio", fromRadio);
					search.createParam("toRadio", toRadio);
					search.createParam("btnVar", btnVar);
					search.createParam("token", token);
					search.createParam("DISPBEAN_KEY", DISPBEAN_KEY);
					// search.createParam("jid", jsessionId);
					//
					AllNipponAirwaysFlightResponseThird responseThird = new AllNipponAirwaysFlightResponseThird();
					String contentThird = responseThird.getResponseWithCookies(
							search, newResults.getCookies()).getResponseBody();
					Document documentThird = Jsoup.parse(contentThird);
					//System.err.print(documentThird);
					String totalPrice = "";
					totalPrice = StringUtils.substringBetween(contentThird,"<font color=\"#000000\" style=\"font-size:small\">","</font>");

					if (totalPrice != null && !totalPrice.equalsIgnoreCase("")) {
						String currency = totalPrice.trim().substring(0, 3);
						String priceString = totalPrice.trim().substring(3);
						Float price = 0F;
						if (priceString.contains(",")) {
							priceString = priceString.replace(",", "");
						}
						price = Float.parseFloat(priceString);
						Elements elements = documentThird.select("table");
						Element elementData = null;
						for (Element element : elements) {
							if (!element.select("tbody>tr>td[bgcolor=#AAAAAA]")
									.toString().equalsIgnoreCase("")) {
								elementData = element;
								break;
							}
						}
						//System.err.print(elementData);
						Elements segementElements = elementData
								.select("tbody>tr>td[bgcolor=#AAAAAA]>table>tbody>tr");
						if (segementElements.size() > 0) {
							segementElements.remove(0);
							List<CuriositySegment> outboundSegments = Lists
									.newArrayList();
							List<CuriositySegment> inboundSegments = Lists
									.newArrayList();
							dateFlightDep = search.getOutboundDate();
							dateFlightArr = search.getInboundDate();
							for (Element element : segementElements) {
								CuriositySegment segment = new CuriositySegment();
								if (element.select("td").get(0).text().contains(DATE_MONTH_FORMATTER.print(search.getInboundDate()))) {
									segment = parseSegments(element, search, 1);
									inboundSegments.add(segment);
								} else if (element
										.select("td")
										.get(0)
										.text()
										.contains(
												DATE_MONTH_FORMATTER.print(search
														.getOutboundDate()))) {
									segment = parseSegments(element, search, 0);
									outboundSegments.add(segment);
								}
							}
							CuriosityFare fare = new CuriosityFare();
							fare.setOutboundSegments(outboundSegments);
							fare.setInboundSegments(inboundSegments);
							fare.setPrice(price);
							fare.setPricePerAdult(price/search.getPassengersCount());
							if (search.getChildrenCount()>0) {
								fare.setPricePerChild(price/search.getPassengersCount());
							}
							fare.setCurrencyCode(currency);
							fare.setAirlineCode("NH");
							fares.add(fare);
						}
					}
				}
			}
	
		}else{
			Elements flightItemElements = documentSecond.select("table.itineraryTtl");
			for (int i = 0; i < flightItemElements.size()-1; i++) {
				search.createParam("selected", ""+i);
				search.createParam("btnVar", btnVar);
				search.createParam("token", token);
				search.createParam("DISPBEAN_KEY", DISPBEAN_KEY);
				
				AllNipponAirwaysFlightResponseThird responseThird = new AllNipponAirwaysFlightResponseThird();
				String contentThird = responseThird.getResponseWithCookies(
						search, newResults.getCookies()).getResponseBody();
				Document documentThird = Jsoup.parse(contentThird);
				String priceContent = StringUtils.substringBetween(contentThird, "var xyPrice = {", "};");
				Document priceDocument = Jsoup.parse(priceContent);
				Elements priceElement = priceDocument.select("span");
				String curAndPrice = priceElement.get(0).text();
				String priceString = curAndPrice.substring(3);
				Float price = 0F;
				if (priceString.contains(",")) {
					price = Float.parseFloat(priceString.replace(",", ""));
				}else{
					price = Float.parseFloat(priceString);
				}
				String curency = curAndPrice.substring(0, 3);
				Element elementData = null;
				Elements tableElements = documentThird.select("table");
				for (Element element : tableElements) {
					if (element.select("tbody>tr>td").size()>=2) {
						if (element.select("tbody>tr>td").get(1).text().equalsIgnoreCase("Date")) {
							elementData = element;
							break;
						}
					}
				}
				if (elementData!=null) {
					Elements segementElements = elementData
							.select("tbody>tr");
					if (segementElements.size() > 0) {
						segementElements.remove(0);
						List<CuriositySegment> outboundSegments = Lists
								.newArrayList();
						for (Element element : segementElements) {
							//System.err.print(element);
							CuriositySegment segment = new CuriositySegment();
							Elements elements = element.select("td");
								if (elements.size()>=7) {
									segment = parseSegmentsOneWay(element, search);
									outboundSegments.add(segment);
								}	
							}
						CuriosityFare fare = new CuriosityFare();
						fare.setOutboundSegments(outboundSegments);
						fare.setPrice(price);
						fare.setPricePerAdult(price/search.getPassengersCount());
						if (search.getChildrenCount()>0) {
							fare.setPricePerChild(price/search.getPassengersCount());
						}
						fare.setCurrencyCode(curency);
						fare.setAirlineCode("NH");
						fares.add(fare);
					}
				}	
			}
			
		}
		return fares;
		}catch (Exception e){
			return null;
		}
	}

	private CuriositySegment parseSegments(Element element,
			CuriositySearch search, int check) {
		CuriositySegment segment = new CuriositySegment();
		Elements elements = element.select("td");

		DateTime dateFlight = new DateTime();
		if (check == 0) {
			dateFlight = DATE_FORMATTER.parseDateTime(elements.get(0).text().split(" ")[0]
					+ "/" + dateFlightDep.getYear());
		} else {
			dateFlight = DATE_FORMATTER.parseDateTime(elements.get(0).text().split(" ")[0]
					+ "/" + dateFlightArr.getYear());
		}
		String flightCodeAndNumber = elements.get(1).text();
		segment.setAirlineCode(flightCodeAndNumber.substring(0, 2));
		segment.setFlightNumber(flightCodeAndNumber.substring(2));
		Elements cityElements = elements.get(2).select("table>tbody>tr").get(0)
				.select("td");
		if (cityElements.size() >= 3) {
			segment.setDepartureName(cityElements.get(0).text());
			segment.setArrivalName(cityElements.get(2).text());
		}
		String date = elements.get(0).text().split(" ")[0];
		// elements.remove(2);
		String depTime = "";
		String arrTime = "";
		if (elements.get(6).text().contains(":")) {
			String timedep = elements.get(6).text();
			if (timedep.contains("*")) {
				depTime = timedep.split(" ")[0];
			}else{
				depTime = timedep;
			}
			String timearr = elements.get(7).text();
			if (timedep.contains("*")) {
				arrTime = timearr.split(" ")[0];
			}else{
				arrTime = timearr;
			}
		} else if (elements.get(7).text().contains(":")) {
			String timedep = elements.get(7).text();
			if (timedep.contains("*")) {
				depTime = timedep.split(" ")[0];
			}else{
				depTime = timedep;
			}
			String timearr = elements.get(8).text();
			if (timedep.contains("*")) {
				arrTime = timearr.split(" ")[0];
			}else{
				arrTime = timearr;
			}
		} else if (elements.get(8).text().contains(":")) {
			String timedep = elements.get(8).text();
			if (timedep.contains("*")) {
				depTime = timedep.split(" ")[0];
			}else{
				depTime = timedep;
			}
			String timearr = elements.get(9).text();
			if (timedep.contains("*")) {
				arrTime = timearr.split(" ")[0];
			}else{
				arrTime = timearr;
			}
		}
		segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER,
				DATE_FORMATTER_PARSER.print(dateFlight) + depTime);
		if (arrTime.contains("+")) {
			String[] arrTimeArr = arrTime.split(" ");
			dateFlight = dateFlight.plusDays(Integer.parseInt(arrTimeArr[1]
					.substring(1)));
			arrTime = arrTimeArr[0];
			if (check==0) {
				dateFlightDep = dateFlightDep.plusDays(Integer.parseInt(arrTimeArr[1]
					.substring(1)));
			}else{
				dateFlightArr = dateFlightArr.plusDays(Integer.parseInt(arrTimeArr[1]
						.substring(1)));
			}
		}else if(arrTime.contains("-")){
			String[] arrTimeArr = arrTime.split(" ");
			dateFlight = dateFlight.minusDays(Integer.parseInt(arrTimeArr[1]
					.substring(1)));
			arrTime = arrTimeArr[0];
			if (check==0) {
				dateFlightDep = dateFlightDep.minusDays(Integer.parseInt(arrTimeArr[1]
					.substring(1)));
			}else{
				dateFlightArr = dateFlightArr.minusDays(Integer.parseInt(arrTimeArr[1]
						.substring(1)));
			}
		}
		segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER,
				DATE_FORMATTER_PARSER.print(dateFlight) + arrTime);
		return segment;
	}
	private CuriositySegment parseSegmentsOneWay(Element element,
			CuriositySearch search) {
		CuriositySegment segment = new CuriositySegment();
		Elements elements = element.select("td");

		if (elements.get(1).text().contains("/")) {
			DateTime dateFlight = new DateTime();
			dateFlight = DATE_FORMATTER.parseDateTime(elements.get(1).text().split(" ")[0]
						+ "/" + dateFlightDep.getYear());
			
			String flightCodeAndNumber = elements.get(2).text();
			segment.setAirlineCode(flightCodeAndNumber.substring(0, 2));
			segment.setFlightNumber(flightCodeAndNumber.substring(2));
			Elements cityElements = elements.get(3).select("table>tbody>tr").get(0)
					.select("td");
			if (cityElements.size() >= 3) {
				segment.setDepartureName(cityElements.get(0).text());
				segment.setArrivalName(cityElements.get(2).text());
			}

			// elements.remove(2);
			String depTime = "";
			String arrTime = "";
			if (elements.get(7).text().contains(":")) {
				String timedep = elements.get(7).text();
				if (timedep.contains("*")) {
					depTime = timedep.split(" ")[0];
				}else{
					depTime = timedep;
				}
				String timearr = elements.get(8).text();
				if (timedep.contains("*")) {
					arrTime = timearr.split(" ")[0];
				}else{
					arrTime = timearr;
				}
			} else if (elements.get(8).text().contains(":")) {
				String timedep = elements.get(8).text();
				if (timedep.contains("*")) {
					depTime = timedep.split(" ")[0];
				}else{
					depTime = timedep;
				}
				String timearr = elements.get(9).text();
				if (timedep.contains("*")) {
					arrTime = timearr.split(" ")[0];
				}else{
					arrTime = timearr;
				}
			}
			segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER,
					DATE_FORMATTER_PARSER.print(dateFlight) + depTime);
			if (arrTime.contains("+")) {
				String[] arrTimeArr = arrTime.split(" ");
				dateFlight = dateFlight.plusDays(Integer.parseInt(arrTimeArr[1]
						.substring(1)));
				arrTime = arrTimeArr[0];
				dateFlightDep = dateFlightDep.plusDays(Integer.parseInt(arrTimeArr[1]
						.substring(1)));
			}else if(arrTime.contains("-")){
				String[] arrTimeArr = arrTime.split(" ");
				dateFlight = dateFlight.minusDays(Integer.parseInt(arrTimeArr[1]
						.substring(1)));
				arrTime = arrTimeArr[0];
				dateFlightDep = dateFlightDep.minusDays(Integer.parseInt(arrTimeArr[1]
						.substring(1)));
			}
			segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER,
					DATE_FORMATTER_PARSER.print(dateFlight) + arrTime);
		}else{
			DateTime dateFlight = new DateTime();
			dateFlight = DATE_FORMATTER.parseDateTime(elements.get(0).text().split(" ")[0]
						+ "/" + dateFlightDep.getYear());
			
			String flightCodeAndNumber = elements.get(1).text();
			segment.setAirlineCode(flightCodeAndNumber.substring(0, 2));
			segment.setFlightNumber(flightCodeAndNumber.substring(2));
			Elements cityElements = elements.get(2).select("table>tbody>tr").get(0)
					.select("td");
			if (cityElements.size() >= 3) {
				segment.setDepartureName(cityElements.get(0).text());
				segment.setArrivalName(cityElements.get(2).text());
			}

			// elements.remove(2);
			String depTime = "";
			String arrTime = "";
			if (elements.get(6).text().contains(":")) {
				String timedep = elements.get(6).text();
				if (timedep.contains("*")) {
					depTime = timedep.split(" ")[0];
				}else{
					depTime = timedep;
				}
				String timearr = elements.get(7).text();
				if (timedep.contains("*")) {
					arrTime = timearr.split(" ")[0];
				}else{
					arrTime = timearr;
				}
			} else if (elements.get(7).text().contains(":")) {
				String timedep = elements.get(7).text();
				if (timedep.contains("*")) {
					depTime = timedep.split(" ")[0];
				}else{
					depTime = timedep;
				}
				String timearr = elements.get(8).text();
				if (timedep.contains("*")) {
					arrTime = timearr.split(" ")[0];
				}else{
					arrTime = timearr;
				}
			}
			segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER,
					DATE_FORMATTER_PARSER.print(dateFlight) + depTime);
			if (arrTime.contains("+")) {
				String[] arrTimeArr = arrTime.split(" ");
				dateFlight = dateFlight.plusDays(Integer.parseInt(arrTimeArr[1]
						.substring(1)));
				arrTime = arrTimeArr[0];
				dateFlightDep = dateFlightDep.plusDays(Integer.parseInt(arrTimeArr[1]
						.substring(1)));
			}else if(arrTime.contains("-")){
				String[] arrTimeArr = arrTime.split(" ");
				dateFlight = dateFlight.minusDays(Integer.parseInt(arrTimeArr[1]
						.substring(1)));
				arrTime = arrTimeArr[0];
				dateFlightDep = dateFlightDep.minusDays(Integer.parseInt(arrTimeArr[1]
						.substring(1)));
			}
			segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER,
					DATE_FORMATTER_PARSER.print(dateFlight) + arrTime);
		}
	
		return segment;
	}
}
