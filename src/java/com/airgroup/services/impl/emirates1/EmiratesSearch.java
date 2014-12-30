package com.airgroup.services.impl.emirates1;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class EmiratesSearch extends FlightResponse {

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
		return search.getParamValue("resultLink") + "/CAB/IBE/SearchAvailability.aspx";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

}
