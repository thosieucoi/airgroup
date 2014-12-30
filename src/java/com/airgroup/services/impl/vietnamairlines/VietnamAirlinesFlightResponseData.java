package com.airgroup.services.impl.vietnamairlines;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class VietnamAirlinesFlightResponseData  extends FlightResponse{
	private static final String RETRIEVE_COOKIE_URL = "https://wl-prod.sabresonicweb.com/SSW2010/B3QE/webqtrip.html";
	public String getDateTimeFormatterPattern() {
		return "yyyy/MM/dd";
	}

	@Override
	public RequestType getType() {
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		return RETRIEVE_COOKIE_URL;
	}

	@Override
	public String getHeaderValue() {
		return null;
	}
	
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		params.add("execution", "e1s1");
		return params;
	}
}
