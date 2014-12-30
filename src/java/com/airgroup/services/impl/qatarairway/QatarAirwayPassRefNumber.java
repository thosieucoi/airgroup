/**
 * 
 */
package com.airgroup.services.impl.qatarairway;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;
import com.airgroup.util.Utils;

/**
 * @author linhnd1
 * 
 */
public class QatarAirwayPassRefNumber extends FlightResponse {
	private static final String PASS_REF_NUM_URL = "https://booking.qatarairways.com/qribe-web/public/showCalanderSaveSelectedCalendar.action";
	protected static final String CONTENT = "content";
	protected static final String TIMESTAMP = "timestamp";
	protected static final String JSESSIONID = "QATAR_JSESSIONID";

	public String getDateTimeFormatterPattern() {
		return "dd-MMM-yyyy";
	}

	@Override
	public RequestType getType() {
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		return PASS_REF_NUM_URL;
	}

	@Override
	public String getHeaderValue() {
		return (String) search.getParamValue(JSESSIONID);
	}

	@Override
	public FluentStringsMap getParams() {
		ObjectMapper mapper = new ObjectMapper();
		JsonParser jp = null;
		try {
			jp = mapper.getFactory().createJsonParser(getResponse().getResponseBody());
		} catch (JsonParseException e) {
			Utils.printLogg(e.toString());
		} catch (IOException e) {
			Utils.printLogg(e.toString());
		}
		JsonNode map = null;
		try {
			map = jp.readValueAsTree();
		} catch (JsonProcessingException e) {
			Utils.printLogg(e.toString());
		} catch (IOException e) {
			Utils.printLogg(e.toString());
		}
		JsonNode arrivalFlightsMap = map.path("arrivalFlights");
		String outbound = "";
		JsonNode departureFlightsMap = map.path("departueFlights");
		for (int i = 0; i < departureFlightsMap.size(); i++) {
			JsonNode flightInfo = departureFlightsMap.path(i).path("availableFlightInfo");

			String val = flightInfo.findValue("selected").toString();

			if (Boolean.parseBoolean(val)) {
				outbound = flightInfo
					.findValue("segments")
					.get(0)
					.findValue("flightRefNumber")
					.toString();
				break;
			}
		}
		FluentStringsMap params = new FluentStringsMap();
		Date date = new Date();
		params.add("TIMESTAMP", (String) search.getParamValue(TIMESTAMP));
		params.add("_", date.getTime() + "");
		params.add("outBound", outbound.replaceAll("\"", ""));
		if (search.isRoundtrip()) {
			String inbound = "";
			for (int i = 0; i < arrivalFlightsMap.size(); i++) {
				JsonNode flightInfo = arrivalFlightsMap.path(i).path("availableFlightInfo");
				String val = flightInfo.findValue("selected").toString();
				if (Boolean.parseBoolean(val)) {
					inbound = flightInfo
						.findValue("segments")
						.get(0)
						.findValue("flightRefNumber")
						.toString();
					break;
				}
			}
			params.add("intBound", inbound.replaceAll("\"", ""));
		}

		return params;
	}
}
