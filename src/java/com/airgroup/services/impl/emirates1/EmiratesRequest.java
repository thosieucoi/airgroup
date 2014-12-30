package com.airgroup.services.impl.emirates1;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class EmiratesRequest extends FlightResponse {

	@Override
	public String getDateTimeFormatterPattern() {
		return null;
	}

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return (String) search.getParamValue("link");
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		return (FluentStringsMap) search.getParamValue("params");
	}

}
