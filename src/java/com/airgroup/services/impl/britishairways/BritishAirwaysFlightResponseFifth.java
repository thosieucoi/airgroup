package com.airgroup.services.impl.britishairways;


import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class BritishAirwaysFlightResponseFifth extends FlightResponse{
	private static final String URL ="http://www.britishairways.com/travel/fx/public/en_vn";
	protected static final String JSESSIONID = "JSESSIONID";
	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return URL;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		
		params.add("eId",search.getParamValue("eId").toString());
		params.add("outboundSort","depTime");
		params.add("RadioSelected0",search.getParamValue(BritishAirwaysFlight.DEPARTURE_FLIGHT_NUMBER_SELECT).toString());
		params.add("outboundSort","depTime");
		if (search.isRoundtrip()) {
			params.add("RadioSelected1",search.getParamValue(BritishAirwaysFlight.ARRIVAL_FLIGHT_NUMBER_SELECT).toString());
		}
		return params;
	}

	public String getHeaderValue() {
		return null;
	}

	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
