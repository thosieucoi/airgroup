package com.airgroup.services.impl.kenya;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class KenyaAirlineRequestForFare extends FlightResponse {
	private static final String URL = "https://wftc2.e-travel.com/plnext/kenyaairways/Fare.action;jsessionid=";
	//https://wftc2.e-travel.com/plnext/kenyaairways/Fare.action;jsessionid=zGP7SQLD9Y8ylcRcRlPyJDVFvZPsBZJqpkMwfT7VZv02q1nJByLm!-746832317!1874343953
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
		return URL + search.getParamValue("KENYA_SESSION");
	}

	@Override
	public String getHeaderValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public FluentStringsMap getParams(){
		FluentStringsMap params = new FluentStringsMap();
		params.add("LANGUAGE","GB");
		params.add("SITE","BASMBASM");
		if (search.isRoundtrip())
			params.add("TRIP_TYPE","R");
		else
			params.add("TRIP_TYPE","O");
		params.add("PAGE_TICKET","1");
		params.add("BANNER","");
		params.add("SKIN","");
		params.add("PRICING_TYPE","O");
		params.add("RECOMMENDATION_ID_1", FlightInformations.commendation_id);
		params.add("FLIGHT_ID_1", FlightInformations.flight_id_1); //search.getParamValue("FLIGHT_ID_1").toString()
		if (search.isRoundtrip())
			params.add("FLIGHT_ID_2", FlightInformations.flight_id_2); //search.getParamValue("FLIGHT_ID_2").toString()
		else
			params.add("FLIGHT_ID_2","");
		params.add("FLIGHTOUTBOUND","");
		params.add("FLIGHTINBOUND","");
		params.add("DISPLAY_TYPE","1");
		return params;
	}

}
