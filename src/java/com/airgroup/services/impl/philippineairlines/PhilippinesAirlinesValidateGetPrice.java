/**
 * 
 */
package com.airgroup.services.impl.philippineairlines;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author hiepnt
 * 
 */
public class PhilippinesAirlinesValidateGetPrice extends FlightResponse {

	private static final String URL = "https://onlinebooking.philippineairlines.com/flypal/ValidateFormAction.do";

	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestType getType() {
		return RequestType.POST;
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

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		params.add("validateAction", "AirPrice");
		params.add("flightId[0]", "0");
		params.add("selected0", "0");
		if (search.isRoundtrip()) {
			params.add("flightId[1]", "0");
			params.add("selected1", "0");
		}
		params.add("vsessionid", "");
		params.add("", "");
		return params;
	}
}
