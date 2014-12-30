package com.airgroup.services.impl.koreanair;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class KoreanProcess extends FlightResponse {

	@Override
	public String getDateTimeFormatterPattern() {
		return null;
	}

	@Override
	public RequestType getType() {
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		return "http://www.koreanair.com/local/sg/gp/eng/tp/bo/eng_tp_bo_ifr.jsp";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params = (FluentStringsMap) search.getParamValue("processParam");

		return params;
	}

}
