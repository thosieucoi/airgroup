/**
 * 
 */
package com.airgroup.services.impl.philippineairlines;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author hiepnt
 * 
 */
public class PhilippinesAirlineGetPriceResponse extends FlightResponse {
	private static final String URL = "https://onlinebooking.philippineairlines.com/flypal/ItinerarySummary.do";

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
		return URL;
	}

	@Override
	public String getHeaderValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
