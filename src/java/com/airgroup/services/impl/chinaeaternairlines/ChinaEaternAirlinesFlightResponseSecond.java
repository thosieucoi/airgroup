package com.airgroup.services.impl.chinaeaternairlines;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class ChinaEaternAirlinesFlightResponseSecond extends FlightResponse {

	private static final String URL = "http://en.ceair.com/booking/flight-select!getSimpleTaxInfo.shtml";
	private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormat.forPattern("MMM");

	@Override
	public RequestType getType() {
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		return URL;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params.add("rand",String.valueOf(System.currentTimeMillis() / 1000));
		params.add("tripIdx","0");
		params.add("routeIdx","0");
		params.add("productIdx","1");
		params.add("sessionId",search.getParamValue("sessionId").toString());
		
		return params;
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
