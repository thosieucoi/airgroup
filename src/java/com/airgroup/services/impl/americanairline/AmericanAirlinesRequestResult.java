package com.airgroup.services.impl.americanairline;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class AmericanAirlinesRequestResult extends FlightResponse {

	public String getDateTimeFormatterPattern() {
		return null;
	}

	@Override
	public RequestType getType() {
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		return "https://abacuswebstart.abacus.com.sg/khang-vuong/flight-result.aspx";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

}
