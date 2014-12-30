package com.airgroup.services.impl.koreanair;

import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class KoreanHome extends FlightResponse {

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
		return "http://www.koreanair.com/index_sg_eng.jsp";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

}
