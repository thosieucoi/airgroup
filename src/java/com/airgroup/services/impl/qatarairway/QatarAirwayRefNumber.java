package com.airgroup.services.impl.qatarairway;

import java.util.Date;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class QatarAirwayRefNumber extends FlightResponse {
	private static final String GET_REF_NUM_URL = "https://booking.qatarairways.com/qribe-web/public/showCalanderInitialCalanderLoad.action";
	protected static final String TIMESTAMP = "timestamp";
	protected static final String JSESSIONID = "QATAR_JSESSIONID";

	public String getDateTimeFormatterPattern() {
		return "dd-MMM-yyyy";
	}

	@Override
	public RequestType getType() {
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		return GET_REF_NUM_URL;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		Date date = new Date();
		params.add("TIMESTAMP", (String) search.getParamValue(TIMESTAMP));
		params.add("_", date.getTime() + "");
		return params;
	}

	@Override
	public String getHeaderValue() {
		return (String) search.getParamValue(JSESSIONID);
	}
}
