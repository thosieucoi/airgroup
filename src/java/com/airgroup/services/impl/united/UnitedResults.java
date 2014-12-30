package com.airgroup.services.impl.united;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class UnitedResults extends FlightResponse {

	private static final String URL = "http://www.united.com/web/en-US/apps/booking/flight/searchResult1.aspx";
	
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
		return URL;
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

}
