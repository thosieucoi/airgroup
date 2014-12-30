package com.airgroup.services.impl.airasia;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class AirAsiaSearch extends FlightResponse {

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
		return "http://booking.airasia.com/Search.aspx";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

}
