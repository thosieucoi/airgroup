package com.airgroup.services.impl.britishairways;


import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class BritishAirwaysFlightResponseThird extends FlightResponse{
	private static final String URL ="http://www.britishairways.com/travel/fx/public/en_vn";
	protected static final String JSESSIONID = "JSESSIONID";
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
		
		params.add("eId","111011");
		params.add("timestamp",search.getParamValue("time").toString());
		return params;
	}

	public String getHeaderValue() {
		return null;
	}

	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
