/**
 * 
 */
package com.airgroup.services.impl.cebupacificair;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author hiepnt
 * 
 */
public class CebupacificairGetResponse extends FlightResponse {

	private final static String URL = "http://book.cebupacificair.com/Select.aspx";

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
