/**
 * 
 */
package com.airgroup.services.impl.philippineairlines;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 * 
 */
public class PhilippineAirlinesValidateFlow extends FlightResponse {
	private static final String URL = "https://onlinebooking.philippineairlines.com/flypal/ValidateFlow.do";
	protected static final String JSESSIONID = "PHILIPINES_JSESSIONID";

	public String getDateTimeFormatterPattern() {
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
		return (String) search.getParamValue(JSESSIONID);
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		params.add("validator", "SHOPPING_ON_SUBMIT");
		return params;
	}
}
