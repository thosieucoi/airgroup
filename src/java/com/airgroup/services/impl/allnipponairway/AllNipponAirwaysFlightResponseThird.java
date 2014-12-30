package com.airgroup.services.impl.allnipponairway;


import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class AllNipponAirwaysFlightResponseThird extends FlightResponse{
	private static final String URL ="https://aswbe-i.ana.co.jp/f2/p_per/sky_ip_per_jp/reSearchRtResult.do";
	protected static final String JSESSIONID = "JSESSIONID";
	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		String newURL = "https://aswbe-i.ana.co.jp" + (String) search.getParamValue("newurlSecond");
		return newURL;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		
		params.add("button.var",(String) search.getParamValue("btnVar"));
		params.add("TOKEN",(String) search.getParamValue("token"));
		params.add("DISPBEAN_KEY",(String) search.getParamValue("btnVar"));
		
		if (search.isRoundtrip()) {
			params.add("tmpSelectFlightFromRadio",(String)search.getParamValue("fromRadio"));
			params.add("tmpSelectFlightToRadio",(String)search.getParamValue("toRadio"));
			params.add("officeCd","QVN001");
			params.add("selectFareRadio","FE");
			params.add("selectFlightFromRadio",(String)search.getParamValue("fromRadio"));
			params.add("selectFlightToRadio",(String)search.getParamValue("toRadio"));
			params.add("buttons.toNextConfirmButton","Continue");
		}else{
			params.add("buttons.selectButton["+(String)search.getParamValue("selected")+"]","Select");
		}
		
		
		return params;
	}

	public String getHeaderValue() {
		return null;
		//return (String) search.getParamValue("jsessionId");
	}

	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
