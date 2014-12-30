package com.airgroup.services.impl.airfrance;


import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class AirfranceFlightResponseSecond extends FlightResponse {

	private static final String URL = "http://www.airfrance.com/cgi-bin/AF/VN/en/local/process/standardbooking/FindOutboundFlightAction.do";

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
		params = (FluentStringsMap)search.getParamValue("requestParam");
		return params;
	}

	@Override
	public String getHeaderValue() {
		return (String)search.getParamValue("sid");
	}

	@Override
	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
