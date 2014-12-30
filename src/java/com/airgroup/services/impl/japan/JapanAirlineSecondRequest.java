package com.airgroup.services.impl.japan;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class JapanAirlineSecondRequest extends FlightResponse  {
	private static final String URL1 = "https://ag.5931.jal.co.jp/jalint/vacant.do?org.apache.struts.taglib.html.TOKEN=e42d2044003255c6860a139af19ed6cd";
	private static final String URL2 = "https://ag.5931.jal.co.jp/jalint/vacantOneWay.do;JSID=qhqhSyvM!-311211284?org.apache.struts.taglib.html.TOKEN=d2c59b76625bd227f40e8413ed82a2ef";
	@Override
	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return "yyyyMMdd";
	}

	@Override
	public RequestType getType() {
		// TODO Auto-generated method stub
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		if (search.isRoundtrip())
			return URL1;
		else 
			return URL2;
	}

	@Override
	public String getHeaderValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public FluentStringsMap getParams(){
		FluentStringsMap params = new FluentStringsMap();
		if (search.isRoundtrip())
			params.add("org.apache.struts.taglib.html.TOKEN","e42d2044003255c6860a139af19ed6cd");
		else
			params.add("org.apache.struts.taglib.html.TOKEN","d2c59b76625bd227f40e8413ed82a2ef");
		return params;
	}
}
