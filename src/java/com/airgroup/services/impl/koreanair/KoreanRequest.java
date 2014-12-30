package com.airgroup.services.impl.koreanair;

import java.util.HashMap;
import java.util.Map;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class KoreanRequest extends FlightResponse {

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
		return "http://www.koreanair.com/global/bl/rs/tripflow_log_enct_xwkc.jsp";
	}

	@Override
	public String getHeaderValue() {
		return "http://www.koreanair.com/global/bl/rs/rs_v9reservationController.jsp";
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params = (FluentStringsMap) search.getParamValue("requestParam");

		return params;
	}

	@Override
	public Map<String, String> getHeader() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Referer", getHeaderValue());
		return map;
	}
}
