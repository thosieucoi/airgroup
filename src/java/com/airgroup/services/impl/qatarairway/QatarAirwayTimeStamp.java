package com.airgroup.services.impl.qatarairway;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;
import com.airgroup.util.Utils;

public class QatarAirwayTimeStamp extends FlightResponse {

	private static final String TIMESTAMP_URL = "https://booking.qatarairways.com/qribe-web/public/showBooking.action?widget=BF&amp;selLang=en";
	
	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return TIMESTAMP_URL;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		params.add("minPurTime", "24 ^");
		params.add("addTaxToFare", "Y");
		params.add("fromStation", search.getDepartureCode());
		params.add("toStation", search.getArrivalCode());
		params.add("tripType", search.isRoundtrip() ? "R" : "O");
		params.add("departing", getDateTimeFormatter().print(search.getOutboundDate()));
		params.add("adults", search.getAdultsCountString());
		params.add("children", search.getChildrenCountString());
		params.add("infants", search.getInfantsCountString());
		params.add("bookingClass", "economy");
		params.add("searchType", "F");
		if (search.isRoundtrip()) {
			params.add("returning", getDateTimeFormatter().print(search.getInboundDate()));
		}
		return params;
	}

	@Override
	public String getHeaderValue() {
		return null;
	}
	
	public String getDateTimeFormatterPattern() {
		return "dd-MMM-yyyy";
	}
	
	protected String getTimeStampScript()
	{
		String timeStampScript = null;
		try {
			String content = getResponse().getResponseBody();
			timeStampScript = StringUtils.substringBetween(content,
					"var timeStamp = \'", "\';");
		} catch (IOException e) {
			Utils.printLogg(e.toString());
		}
		return timeStampScript;
	}
	
	protected String getJSessionID()
	{
		String jSessionID = "JSESSIONID"
				+ StringUtils.substringBetween(getResponse().getHeaders()
						.toString(), "JSESSIONID", ";");
		return jSessionID;
	}
}
