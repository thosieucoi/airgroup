/**
 * 
 */
package com.airgroup.services.impl.malaysiaairlines;

import org.apache.commons.lang3.StringUtils;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 *
 */
public class MalaysiaAirlinesStartAction extends FlightResponse{

	private static final String URL = "https://bookings.malaysiaairlines.com/MHOnline/ApplicationStartAction.do";
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
	protected String getJSessionID()
	{
		String jSessionID = "JSESSIONID"
				+ StringUtils.substringBetween(getResponse().getHeaders()
						.toString(), "JSESSIONID", ";");
		return jSessionID;
	}
}
