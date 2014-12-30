/**
 * 
 */
package com.airgroup.services.impl.qatarairway;

import java.util.Date;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 * 
 */
public class QatarAirwayGetItinerary extends FlightResponse {

	private static final String ITINERARY_URL = "https://booking.qatarairways.com/qribe-web/public/showFareDrivenComposeResults.action";
	protected static final String TIMESTAMP = "timestamp";
	protected static final String JSESSIONID = "QATAR_JSESSIONID";

	public String getDateTimeFormatterPattern() {
		return "dd-MMM-yyyy";
	}

	@Override
	public RequestType getType() {
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		return ITINERARY_URL;
	}

	@Override
	public String getHeaderValue() {
		return (String) search.getParamValue(JSESSIONID);
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		Date date = new Date();
		params.add("TIMESTAMP", (String) search.getParamValue(TIMESTAMP));
		params.add("_", date.getTime() + "");
		return params;
	}
}
