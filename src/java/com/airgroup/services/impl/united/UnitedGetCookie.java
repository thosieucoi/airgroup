package com.airgroup.services.impl.united;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class UnitedGetCookie extends FlightResponse {

	private static final String ONEWAY_URL = "http://www.united.com/web/en-US/apps/booking/flight/searchOW.aspx?CS=N";

	private static final String ROUNDTRIP_URL = "http://www.united.com/web/en-US/apps/booking/flight/searchRT.aspx?CS=N";
	
	@Override
	public String getDateTimeFormatterPattern() {
		return null;
	}

	@Override
	public RequestType getType() {
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		if(search.isOneway()){
			return ONEWAY_URL;
		}else{
			return ROUNDTRIP_URL;
		}
	}

	@Override
	public String getHeaderValue() {
		return null;
	}
	
	public FluentStringsMap getParams() {
		return null;
	}

}
