/**
 * 
 */
package com.airgroup.services.impl.emirates;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 *
 */
public class EmiratesFlightOneWay extends FlightResponse{

	private static final String URL = "http://www.lastminute.com/site/main/AdCampaign_homepage.html?CATEGORY=flights&SEARCH=basic&DP1WF=1";
	
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
