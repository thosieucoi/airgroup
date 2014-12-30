/**
 * 
 */
package com.airgroup.services.impl.malaysiaairlines;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 * 
 */
public class MalaysiaAirlinesFamiliesForward extends FlightResponse {

	private static final String URL = "https://bookings.malaysiaairlines.com/MHOnline/AirFareFamiliesForward.do";
	protected static final String JSESSIONID = "MALAY_JSESSIONID";

	public String getDateTimeFormatterPattern() {
		return null;
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
		return null;
	}

}
