package com.airgroup.services.impl.koreanair;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class KoreanResult extends FlightResponse {

	@Override
	public String getDateTimeFormatterPattern() {
		return null;
	}

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return "http://wftc3.e-travel.com/plnext/KEREG/Override.action";
	}

	@Override
	public String getHeaderValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params = (FluentStringsMap) search.getParamValue("resultParam");

		return params;
	}

}
