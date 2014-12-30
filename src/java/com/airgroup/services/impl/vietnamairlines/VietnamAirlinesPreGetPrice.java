package com.airgroup.services.impl.vietnamairlines;

import java.net.MalformedURLException;

import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class VietnamAirlinesPreGetPrice extends FlightResponse {
	private static final String GET_PRICE = "https://wl-prod.sabresonicweb.com/SSW2010/B3QE/webqtrip.html";

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
		return GET_PRICE;
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		String obId = search.getParamValue("VNObId").toString();
		
		Response response = (Response) search.getParamValue("response");
		params.add("_eventId_ajax", "");
		params.add("ajaxSource", "true");
		if(search.isRoundtrip()){
			String ibId = search.getParamValue("VNIbId").toString();
			params
				.add(
					"contextObject",
					"{\"transferObjects\":[{\"componentType\":\"cart\",\"actionCode\":\"checkPrice\",\"queryData\":{\"componentId\":\"cart_1\",\"componentType\":\"cart\",\"actionCode\":\"checkPrice\",\"queryData\":null,\"requestPartials\":[\"initialized\"],\"selectedBasketRefs\":[" +
							obId +
							"," +
							ibId +
							"]}}]}");
		}else{
			params
			.add(
				"contextObject",
				"{\"transferObjects\":[{\"componentType\":\"cart\",\"actionCode\":\"checkPrice\",\"queryData\":{\"componentId\":\"cart_1\",\"componentType\":\"cart\",\"actionCode\":\"checkPrice\",\"queryData\":null,\"requestPartials\":[\"initialized\"],\"selectedBasketRefs\":[" +
						obId +
						"]}}]}");
			
		}
		try {
			params.add("execution", response.getUri().getQuery().split("=")[1]);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return params;
	}
}
