package com.airgroup.services.impl.emirates1;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class EmiratesHome extends FlightResponse {

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
		return "http://www.emirates.com/vn/English/index.aspx";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}
}
