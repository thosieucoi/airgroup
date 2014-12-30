/**
 * 
 */
package com.airgroup.services.impl.philippineairlines;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 * 
 */
public class PhilippineAirlinesAirlinesFamiliesForward extends FlightResponse {

	private static final String URL = "https://onlinebooking.philippineairlines.com/flypal/AirFareFamiliesForward.do";
	protected static final String JSESSIONID = "PHILIPINES_JSESSIONID";

	public String getDateTimeFormatterPattern() {
		return "MM/dd/yyyy";
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
		return (String) search.getParamValue(JSESSIONID);
	}

}
