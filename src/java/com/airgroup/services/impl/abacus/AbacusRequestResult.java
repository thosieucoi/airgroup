package com.airgroup.services.impl.abacus;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class AbacusRequestResult extends FlightResponse {

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
		return "https://abacuswebstart.abacus.com.sg/hpv-vietnam/flight-result.aspx";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

}
