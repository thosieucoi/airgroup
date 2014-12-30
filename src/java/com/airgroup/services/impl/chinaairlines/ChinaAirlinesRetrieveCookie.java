/**
 * 
 */
package com.airgroup.services.impl.chinaairlines;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 *
 */
public class ChinaAirlinesRetrieveCookie extends FlightResponse{
	
	private static final String URL = "http://www.cleartrip.com/flights";

	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestType getType() {
		// TODO Auto-generated method stub
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return URL;
	}

	@Override
	public String getHeaderValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
