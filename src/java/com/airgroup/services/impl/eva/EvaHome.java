package com.airgroup.services.impl.eva;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class EvaHome extends FlightResponse {

	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestType getType() {
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		return "http://eservice.evaair.com/EVAWEB/EVA/B2C/booking-online.aspx?lang=en-us";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

}
