package com.airgroup.services.impl.airfrance;

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
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;

public class AirfranceFlight extends SearchFlights {

	private static final DateTimeFormatter PARSER_DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("dd-MM-yyyyHH:mm");
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("dd-MM-yyyy");

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		AirfranceFlightResponseFirst response = new AirfranceFlightResponseFirst();
		return response.getResponse(search);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		try{
		List<CuriosityFare> fareDepartures = Lists.newArrayList();
		List<CuriosityFare> fareArrvials = Lists.newArrayList();
		List<CuriosityFare> fares = Lists.newArrayList();
		AirfranceFlightResponseFirst responseFirst = new AirfranceFlightResponseFirst();
		Response resultFirst = responseFirst.getResponse(search);
		Document document = Jsoup.parse(resultFirst.getResponseBody());
		
		FluentStringsMap requestParams = new FluentStringsMap();
	    for (Element element : document.select("input[type=hidden]")) {
	        requestParams.add(element.attr("name"), element.val());
	    }

	    search.createParam("requestParam", requestParams);
		  
		String sId = StringUtils.substringBetween(document.toString(), "var Session='", "';");
		search.createParam("sid", "JSESSIONID="+sId);
		AirfranceFlightResponseSecond responseSecond = new AirfranceFlightResponseSecond();
		
		Response resultSecond = responseSecond.getResponse(search);
		String contentSecond = resultSecond.getResponseBody();
		
		AirfranceFlightResponseThird responseThird = new AirfranceFlightResponseThird();
		String contentThird = responseThird.getResponse(search).getResponseBody();
		Document documentThird = Jsoup.parse(contentThird);
		String currency = StringUtils.substringBetween(contentThird, "tc_vars_miniRecap[\"currency\"] = \"", "\";");
		Float pricePerChild = 0F;
		Float basePriceDep = 0F;
		Float basePriceArr = 0F;
		Float basePricePerAdult = 0F;
		//System.err.print(documentThird);
		if (search.getChildrenCount()>0) {
			String[] basePricePerAdultArr = documentThird.select("div#idUpsellTotalPrice").text().split(" ");
			
			if (basePricePerAdultArr.length==3) {
				basePricePerAdult =Float.parseFloat(basePricePerAdultArr[0]+basePricePerAdultArr[1]);
			}
			String[] totalBaseArr = documentThird.select("div#idUpsellTotalPriceMultiPax").text().split(" ");
			Float totalBase = 0F;
			if (totalBaseArr.length==3) {
				totalBase =Float.parseFloat(totalBaseArr[0]+totalBaseArr[1]);
			}
			pricePerChild = (totalBase-(basePricePerAdult*search.getAdultsCount()))/search.getChildrenCount();
		}
		String depContent = StringUtils.substringBetween(contentThird, "</script> <dl> <dt class=\"etape\"> <div class=\"titre\" id=\"idUpsellTitle0\">", "tc_product_css = new Array();");
		if (depContent!=null&&!depContent.equals("")) {
			Document depDocument = Jsoup.parse(depContent);
			Elements depElements = depDocument.select("table.upsellRow");
			for (Element element : depElements) {
				Elements segmentElements = element.select("tbody>tr");
				Float pricePerAdult = 0F;
				if (segmentElements.size()>0) {
					pricePerAdult = Float.parseFloat(segmentElements.get(0).select("input[name=price]").val());
				}
				Float priceTotal = Float.parseFloat(segmentElements.get(0).select("input[name=priceMultiPax]").val());
				List<CuriositySegment> outboundSegments = parseSegments(element);
				CuriosityFare fare = new CuriosityFare();
				
				//fare.setTotalPrice(pricePerAdult*search.getAdultsCount(),search);
				fare.setOutboundSegments(outboundSegments);
				fare.setCurrencyCode(currency);
				fare.setAirlineCode("AF");
				fare.setPrice(priceTotal);
				fare.setPricePerAdult(priceTotal/search.getPassengersCount());
				if (search.getChildrenCount()>0) {
					fare.setPricePerChild(priceTotal/search.getPassengersCount());
				}
				fareDepartures.add(fare);
			}
			
			if (search.isRoundtrip()) {
				//System.err.print(contentThird);
				int i=0;
				for (CuriosityFare depFare : fareDepartures) {
					search.getParams().clear();
					search.createParam("sid", "JSESSIONID="+sId);
					search.createParam("selected", i);
					AirfranceFlightResponseFour responseFour = new AirfranceFlightResponseFour();
					String contentFour = responseFour.getResponse(search).getResponseBody();
					//System.err.print(contentFour);
//					AirfranceFlightResponseThird newResponseThird = new AirfranceFlightResponseThird();
//					String newContentThird = newResponseThird.getResponse(search).getResponseBody();
				//	System.err.print(newContentThird);
					String arrContent = StringUtils.substringBetween(contentThird, "</script> <dl> <dt class=\"etape\"> <div class=\"titre\" id=\"idUpsellTitle1\">", "tc_product_css = new Array();");
					Document arrpDocument = Jsoup.parse(contentFour);
					Elements arrElements = arrpDocument.select("table.upsellRow");
					//System.err.print(arrElements);
					for (Element element : arrElements) {
						String isCheap = element.select("tbody>tr").get(0).select("td.upsellRadio>span.upsellFareCheap").text();
						if (isCheap!=null && !isCheap.equalsIgnoreCase("")) {
							Elements segmentElements = element.select("tbody>tr");
							Float pricePerAdult = 0F;
							if (segmentElements.size()>0) {
								pricePerAdult =Float.parseFloat(segmentElements.get(0).select("input[name=price]").val());
							}
							List<CuriositySegment> inboundSegments = parseSegments(element);
							CuriosityFare fare = new CuriosityFare();
							fare.setOutboundSegments(depFare.getOutboundSegments());
							fare.setInboundSegments(inboundSegments);
							Float basePricePerAdultRT = depFare.getPricePerAdult();
							fare.setPricePerAdult(depFare.getPrice()/search.getPassengersCount());
							if (search.getChildrenCount()>0) {
								fare.setPricePerChild(depFare.getPrice()/search.getPassengersCount());
							}
							fare.setPrice(depFare.getPrice());
							fare.setCurrencyCode(currency);
							fare.setAirlineCode("AF");
							fares.add(fare);
						}
					}
					i++;
				}
				
			}
			else{
//				if (search.getChildrenCount()>0) {
//					for (CuriosityFare fareDep : fareDepartures) {
//						Float basePricePerAdultOW = fareDep.getBasePricePerAdult();
//						Float price = fareDep.getPrice();
//						price += ((basePricePerAdultOW/basePricePerAdult)*pricePerChild)*search.getChildrenCount();
//						fareDep.setPrice(price);
//						fares.add(fareDep);
//					}
//				}else{
					fares.addAll(fareDepartures);
//				}
			}
		}
		return fares;
		}catch (Exception e){
			return null;
		}
	}

	private List<CuriositySegment> parseSegments(Element element) {
		List<CuriositySegment> segments = Lists.newArrayList();
		DateTime flightDate = new DateTime();
		Elements segmentElements = element.select("tbody>tr");
		for (Element segmentElement : segmentElements) {
			if (!segmentElement.select("td.upsellCity").text().equalsIgnoreCase("")&&segmentElement.select("td.upsellCity").text()!=null) {
				CuriositySegment segment = new CuriositySegment();
				String airlineCode = segmentElement.select("td[id~=idUpsellIti]").text();
				String depCode = segmentElement.select("td.upsellCity").get(0).text();
				String arrCode = segmentElement.select("td.upsellCity").get(1).text();
				String depTime = segmentElement.select("td.upsellTime").get(0).text();
				String arrTime = segmentElement.select("td.upsellTime").get(1).text();
				segment.setDepartureName(depCode);
				segment.setArrivalName(arrCode);

				if (depTime.contains("+")) {
					flightDate = flightDate.plusDays(Integer.parseInt(depTime.substring(depTime.indexOf("+")+1, depTime.indexOf("+")+2)));
					depTime = depTime.substring(0, (depTime.indexOf("+")-1));
				}
				segment.setDepartureTime(PARSER_DATE_TIME_FORMATTER,DATE_TIME_FORMATTER.print(flightDate) + depTime);
				if (arrTime.contains("+")) {
					flightDate = flightDate.plusDays(Integer.parseInt(arrTime.substring(arrTime.indexOf("+")+1, arrTime.indexOf("+")+2)));
					arrTime = arrTime.substring(0, (arrTime.indexOf("+")-1));
				}
				
				segment.setArrivalTime(PARSER_DATE_TIME_FORMATTER,DATE_TIME_FORMATTER.print(flightDate) + arrTime);
				segment.setAirlineCode(airlineCode.substring(0,2));
				segment.setFlightNumber(airlineCode.substring(2));
				segments.add(segment);
			}
		}
		return segments;
	}
}
