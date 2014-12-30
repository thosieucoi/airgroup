/**
 * 
 */
package com.airgroup.services.impl.malaysiaairlines;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 *
 */
public class MalaysiaAirlinesValidateFlow extends FlightResponse{
	private static final String URL = "https://bookings.malaysiaairlines.com/MHOnline/ValidateFlow.do";
	protected static final String JSESSIONID = "MALAY_JSESSIONID";
	
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

    public FluentStringsMap getParams()
    {
        FluentStringsMap params = new FluentStringsMap();
        params.add("validator","SHOPPING");
        return params;
    }
}
