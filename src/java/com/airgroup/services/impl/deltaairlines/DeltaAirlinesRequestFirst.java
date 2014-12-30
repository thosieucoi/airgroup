package com.airgroup.services.impl.deltaairlines;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class DeltaAirlinesRequestFirst extends FlightResponse {

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
		return "https://abacuswebstart.abacus.com.sg/vietnam-booking/flight-search.aspx?fare=FAREX";
	}
	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		return params;
	}
	@Override
	public String getHeaderValue() {
		return null;
	}

}
