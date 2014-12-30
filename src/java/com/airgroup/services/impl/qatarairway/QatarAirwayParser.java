/**
 * 
 */
package com.airgroup.services.impl.qatarairway;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ning.http.client.Response;
import com.airgroup.model.Cabin;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;
import com.airgroup.util.Const;
import com.airgroup.util.Utils;

/**
 * @author linhnd1
 * 
 */
public class QatarAirwayParser extends SearchFlights {

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd HH:mm");

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		String content = getResponse(search).getResponseBody();
		ObjectMapper mapper = new ObjectMapper();
		JsonParser jp = mapper.getFactory().createJsonParser(content);
		JsonNode map = jp.readValueAsTree();
		List<CuriosityFare> fares = Lists.newArrayList();
		List<CuriosityFare> obFares = Lists.newArrayList();
		List<CuriosityFare> ibFares = Lists.newArrayList();
		JsonNode inboundFlightItineraryList = map
			.findValue("inboundFlightItineraryList");

		JsonNode outboundFlightItineraryList = map.path("outboundFlightItineraryList");

		Cabin cabin = search.getCabin();
		// Parse OutBound segments

		for (int i = 0; i < outboundFlightItineraryList.size(); i++) {
			JsonNode fareFamily = outboundFlightItineraryList.path(i).findPath(
					"fareFamily");
			CuriosityFare outbounFare = CuriosityFare.newFromTrip(search);
			if(fareFamily.path(0).findPath("fareDisplayKey").toString().contains("ECORES") || fareFamily.path(0).findPath("fareDisplayKey").toString().contains("DAYSALE")){
				JsonNode obFareSegments = outboundFlightItineraryList.path(i).findValue("flightSegmentList");
				if(obFareSegments != null){
					List<CuriositySegment> outBoundSegments = parseSegments(
							obFareSegments,
							cabin);
					
					outbounFare.setOutboundSegments(outBoundSegments);
		
					String price = Utils.removeQuote(fareFamily
						.path(0)
						.path("amount")
						.toString());
					if (price != null && price.length() > 0) {
						outbounFare.setPrice(Utils.floatFromString(price.replaceAll(",", "")));
					} else {
						outbounFare.setPrice(0);
					}
					String currencyCode = Utils.removeQuote(outboundFlightItineraryList
							.path(i)
							.findValue("currencyCode")
							.toString());
					if (outboundFlightItineraryList.path(i).findValue("currencyCode") != null) {
						outbounFare.setCurrencyCode(currencyCode);
					} else {
						outbounFare.setCurrencyCode("");
					}
					outbounFare.setAirlineCode("QR");
					outbounFare.setPricePerAdult(outbounFare.getPrice()/search.getPassengersCount());
					outbounFare.setPricePerChild(outbounFare.getPrice()/search.getPassengersCount());
					obFares.add(outbounFare);
				}
			}
			if(search.isRoundtrip()){
				JsonNode ibList = outboundFlightItineraryList.path(i).findPath("relatedFareFamily");
				for (int j = 0; j < ibList.size(); j++) {
					CuriosityFare inbounFare = CuriosityFare.newFromTrip(search);
					JsonNode inFareFamily = ibList.path(j).findValue(
						"fareDisplayKey");
					if(inFareFamily.toString().contains("ECORES")){
						JsonNode ibFareSegments = inboundFlightItineraryList.path(j).findValue("flightSegmentList");
						if(ibFareSegments != null){
							List<CuriositySegment> inBoundSegments = parseSegments(
									ibFareSegments,
									cabin);
							
							inbounFare.setInboundSegments(inBoundSegments);
				
							String price = Utils.removeQuote(ibList.path(j)
								.path("amount")
								.toString());
							if (price != null && price.length() > 0) {
								inbounFare.setPrice(Utils.floatFromString(price.replaceAll(",", "")));
							} else {
								inbounFare.setPrice(0);
							}
							String currencyCode = "USD";
								inbounFare.setCurrencyCode(currencyCode);
							inbounFare.setAirlineCode("QR");
							ibFares.add(inbounFare);
						}
						CuriosityFare fare = CuriosityFare.newFromTrip(search);
						fare.setOutboundSegments(outbounFare.getOutboundSegments());
						fare.setInboundSegments(inbounFare.getInboundSegments());
						fare.setCurrencyCode(outbounFare.getCurrencyCode());
						fare.setAirlineCode("QR");
						fare.setPrice(inbounFare.getPrice() + outbounFare.getPrice());
						fare.setPricePerAdult(fare.getPrice()/search.getPassengersCount());
						fare.setPricePerChild(fare.getPrice()/search.getPassengersCount());
						fares.add(fare);
					}
				}
			}
			else{
				fares.add(outbounFare);
			}
		}
		return fares;
	}

	private List<CuriositySegment> parseSegments(JsonNode fareSegments, Cabin cabin) {
		List<CuriositySegment> segments = Lists.newArrayList();
		if (fareSegments.size() > 0) {
			for (int i = 0; i < fareSegments.size(); i++) {
				CuriositySegment segment = new CuriositySegment();
				segment.setAirlineCode(Utils.removeQuote(
					fareSegments.path(i).findValue("flightNo").toString()).substring(0, 2));
				segment.setFlightNumber(Utils.removeQuote(fareSegments.path(i)
					.findValue("flightNoOnly")
					.toString()));
				segment.setDepartureCode(Utils.removeQuote(fareSegments
					.path(i)
					.findValue("depIATACode")
					.toString()));
				segment.setArrivalCode(Utils.removeQuote(fareSegments
					.path(i)
					.findValue("arrIATACode")
					.toString()));

				segment.setDepartureTime(
					TIME_FORMATTER,
					Utils.parseDate(
						Utils.removeQuote(fareSegments.path(i).findValue("depDate").toString()) + " "
						+ Utils.removeQuote(fareSegments.path(i).findValue("depTime").toString()),
						"dd/MM/yyyy HH:mm"));
				segment.setArrivalTime(
					TIME_FORMATTER,
					Utils.parseDate(
						Utils.removeQuote(fareSegments.path(i).findValue("arrDate").toString()) + " "
						+ Utils.removeQuote(fareSegments.path(i).findValue("arrTime").toString()),
						"dd/MM/yyyy HH:mm"));
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
		}
		return segments;
	}

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		QatarAirwayTimeStamp stamp = new QatarAirwayTimeStamp();
		stamp.getResponse(search);
		String timeStampScript = stamp.getTimeStampScript();
		String jSessionID = stamp.getJSessionID();
		search.createParam(QatarAirwayRefNumber.TIMESTAMP, timeStampScript);
		search.createParam(QatarAirwayRefNumber.JSESSIONID, jSessionID);

		QatarAirwayRefNumber number = new QatarAirwayRefNumber();
		Response responseGetRefNumber = number.getResponse(search);

		search
			.createParam(QatarAirwayPassRefNumber.CONTENT, responseGetRefNumber.getResponseBody());

		QatarAirwayPassRefNumber passRefNumber = new QatarAirwayPassRefNumber();
		passRefNumber.getResponse();

		QatarAirwayGetItinerary getItinerary = new QatarAirwayGetItinerary();
		Response responseGetItinerary = getItinerary.getResponse(search);
		return responseGetItinerary;
	}
}
