package com.airgroup.services.impl.emirates;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class EmiratesFlightReponse extends FlightResponse{
	private static final String URL = "http://www.lastminute.com/trips/partialHtml";
	private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormat
			.forPattern("dd");
	private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormat
			.forPattern("yyyy-MM");
	private static final String AIRLINE_CODE = "EK";
	
	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return "yyyy-MMM-dd";
	}

	@Override
	public RequestType getType() {
		// TODO Auto-generated method stub
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return URL;
	}

	@Override
	public String getHeaderValue() {
		// TODO Auto-generated method stub
		return null;
	}
		
	@Override
	public FluentStringsMap getParams(){
		FluentStringsMap params = new FluentStringsMap();
		params.add("srchSnr","0");
		if(search.isRoundtrip()){
			params.add("retMo",MONTH_YEAR_FORMATTER.print(search.getInboundDate()));
			params.add("retDy",DAY_FORMATTER.print(search.getInboundDate()));	
		}
		params.add("arrAp",search.getArrivalCode());
		params.add("fadi",search.getArrivalCode());
		params.add("fado",search.getArrivalCode());
		params.add("depDy",DAY_FORMATTER.print(search.getOutboundDate()));
		params.add("depAp",search.getDepartureCode());
		params.add("flexDate","False");
		params.add("srchAdt",search.getAdultsCountString());
		params.add("path","flights");
		params.add("airline","NONE");
		params.add("pTxId",search.getParamValue("pTxId").toString());
		params.add("drctTrip","false");
		params.add("ad_dest","");
		params.add("depMo",MONTH_YEAR_FORMATTER.print(search.getOutboundDate()));
		params.add("numLegs",search.isRoundtrip()?"2":"1");
		params.add("srchChld",search.getChildrenCountString());
		params.add("configId","S72722479");
		params.add("redirectOnly","false");
		params.add("intcmp","");
		params.add("intcmp","");
		params.add("source","");
		params.add("source","");
		params.add("cabins","Y");
		params.add("srchInf","0");
		params.add("ad_country","");
		params.add("pageId","flightsFaregroupingList");
		params.add("action","index");
		params.add("controller","pcp");
		params.add("requestId",search.getParamValue("requestId").toString());
		params.add("startIndex","0");
		params.add("filterAirlines",AIRLINE_CODE);
		params.add("faoi",search.getDepartureCode());
		params.add("faoo",search.getDepartureCode());
		params.add("ca","");
		params.add("airlinesUsed","true");
		params.add("isFilter","true");
		params.add("modules","product_list,omniture_tags,results_summary");
		return params;
	}
	
	protected String getJSessionID()
	{
		String jSessionID = "JSESSIONID"
				+ StringUtils.substringBetween(getResponse().getHeaders()
						.toString(), "JSESSIONID", ";");
		return jSessionID;
	}
}
