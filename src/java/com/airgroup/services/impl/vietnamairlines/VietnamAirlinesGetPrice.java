package com.airgroup.services.impl.vietnamairlines;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class VietnamAirlinesGetPrice extends FlightResponse {

	private static final String GET_PRICE = "https://wl-prod.sabresonicweb.com/SSW2010/B3QE/webqtrip.html";

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
		return GET_PRICE;
	}

	@Override
	public String getHeaderValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		String obId = search.getParamValue("VNObId").toString();
		String ibId = search.getParamValue("VNIbId").toString();
		params.add("_eventId_ajax", "");
		params.add("ajaxSource", "true");
		params
			.add(
				"contextObject",
				"{\"transferObjects\":[{\"componentType\":\"cart\",\"actionCode\":\"checkPrice\",\"queryData\":{\"componentId\":\"cart_1\",\"componentType\":\"cart\",\"actionCode\":\"checkPrice\",\"queryData\":null,\"requestPartials\":[\"initialized\"],\"selectedBasketRefs\":[-" +
						obId +
						"," +
						ibId +
						"]}}]}");
		params.add("execution", "e1s2");

		return params;
	}
}
