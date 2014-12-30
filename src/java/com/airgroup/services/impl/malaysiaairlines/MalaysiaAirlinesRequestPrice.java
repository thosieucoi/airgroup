package com.airgroup.services.impl.malaysiaairlines;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class MalaysiaAirlinesRequestPrice extends FlightResponse{
	
	private static final String URL = "https://bookings.malaysiaairlines.com/MHOnline/AirSelectOWCFlight.do";
	protected static final String JSESSIONID = "MALAY_JSESSIONID";
	@Override
	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestType getType() {
		// TODO Auto-generated method stub
		return RequestType.POST;
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
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		String[] itinerary = search.getParamValue("ITINERARY").toString().split(";");
		params.add("isFareFamilySearchResult","true");
		params.add("selectedItineraries","0," + itinerary[0]);
		params.add("selectedItineraries","1," + itinerary[1]);
		params.add("selectedFlightIds","0,"+ itinerary[0]+",1,"+ itinerary[1]);
		params.add("combinabilityReloadRequired","true");
		params.add("alignment","vertical");
		params.add("context","airSelection");
		return params;
	}
}
