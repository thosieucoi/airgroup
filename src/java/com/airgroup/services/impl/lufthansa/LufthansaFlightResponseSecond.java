package com.airgroup.services.impl.lufthansa;


import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class LufthansaFlightResponseSecond extends FlightResponse{
	private static final String URL ="http://book.lufthansa.com/pl/Lufthansa/wds/Fare.action";
	
	protected static final String JSESSIONID = "JSESSIONID";
	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		String newURL = "http://book.lufthansa.com/pl/Lufthansa/wds/Override.action" + ";" + search.getParamValue("JSESSION");
		return newURL;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		
		params.add("EMBEDDED_TRANSACTION","Fare");
		params.add("SO_GL","<?xml version='1.0' encoding='iso-8859-1'?><SO_GL><GLOBAL_LIST><NAME>SITE_LIST_OB_FEE_CODE_TO_EXEMPT</NAME><LIST_ELEMENT><CODE>FCA</CODE><LIST_VALUE>FCA</LIST_VALUE></LIST_ELEMENT></GLOBAL_LIST></SO_GL>");
		params.add("SKIN","LH");
		params.add("BANNER","FALSE");
		params.add("SITE","5AHB5AHB");
		params.add("LANGUAGE","GB");
		params.add("IS_RSB","false");
		params.add("IS_CUG","");
		params.add("STEPS","2");
		params.add("WDS_FROM_PAGE","AVAI");
		if (search.isRoundtrip()) {
			params.add("TRIP_TYPE","R");
		}else{
			params.add("TRIP_TYPE","O");
		}
		params.add("PAGE_TICKET","0");
		params.add("RESTRICTION","TRUE");
		params.add("ROW_1",search.getParamValue(LufthansaFlight.DEPARTURE_FLIGHT_NUMBER_SELECT).toString());
		if (search.isRoundtrip()) {
			params.add("ROW_2",search.getParamValue(LufthansaFlight.ARRIVAL_FLIGHT_NUMBER_SELECT).toString());
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
