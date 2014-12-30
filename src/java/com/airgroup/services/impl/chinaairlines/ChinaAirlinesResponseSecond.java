/**
 * 
 */
package com.airgroup.services.impl.chinaairlines;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 *
 */
public class ChinaAirlinesResponseSecond extends FlightResponse{
	
	private static final String URL = "https://caleb.china-airlines.com/olbn/Fare.aspx";

	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return "dd/MM/yyyy";
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

	public FluentStringsMap getParams(){
		FluentStringsMap params = new FluentStringsMap();
		return params;
	}
}
