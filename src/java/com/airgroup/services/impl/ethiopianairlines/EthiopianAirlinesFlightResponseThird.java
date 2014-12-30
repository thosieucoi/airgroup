package com.airgroup.services.impl.ethiopianairlines;


import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class EthiopianAirlinesFlightResponseThird extends FlightResponse {

	private static final String URL = "https://wl-prod.sabresonicweb.com/SSW2010/ETET/webqtrip.html";

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
		
		params.add("execution","e1s1");
		
		return params;
	}

	@Override
	public String getHeaderValue() {
		return (String) search.getParamValue("jsessionId");
	}

	@Override
	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
