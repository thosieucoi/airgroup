/**
 * 
 */
package com.airgroup.services.impl.vietjetAir;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author hiepnt
 *
 */
public class VietjetAirResult extends FlightResponse{

	private static final String URL = "https://ameliaweb5.intelisys.ca/VIETJET/TravelOptions.aspx?lang=vi&st=pb&sesid=";
	
	@Override
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
		// TODO Auto-generated method stub
		return null;
	}

}
