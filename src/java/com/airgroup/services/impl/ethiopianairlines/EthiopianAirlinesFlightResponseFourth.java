package com.airgroup.services.impl.ethiopianairlines;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class EthiopianAirlinesFlightResponseFourth extends FlightResponse {

	private static final String URL = "https://wl-prod.sabresonicweb.com/SSW2010/ETET/webqtrip.html";

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return URL;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		
		params.add("_eventId_ajax","");
		params.add("execution","e1s3");
		params.add("ajaxSource","true");
		params.add("contextObject","{\"transferObjects\":[{\"componentType\":\"flc\",\"actionCode\":\"itineraryPartSelected\",\"queryData\":{\"componentId\":\"flc_1\",\"componentType\":\"flc\",\"actionCode\":\"itineraryPartSelected\",\"queryData\":null,\"basketHashRefs\":[-183839006,331040002],\"requestPartials\":[\"__unbundled_outbounds\"]}}]}");
		
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
