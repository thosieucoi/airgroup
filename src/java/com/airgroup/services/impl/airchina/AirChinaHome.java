package com.airgroup.services.impl.airchina;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class AirChinaHome extends FlightResponse {

	public String getDateTimeFormatterPattern() {
		return null;
	}

	@Override
	public RequestType getType() {
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		return "http://ots.airchina.com.cn/website/GetCryptCode.do";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params.add("B_LOCATION_1", search.getDepartureCode());
		params.add("E_LOCATION_1", search.getArrivalCode());

		return params;
	}
}
