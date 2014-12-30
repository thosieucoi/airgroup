package com.airgroup.services.impl.emirates;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class EmiratesFlightDetail extends FlightResponse{

	private static final String URL = "http://www.lastminute.com/trips/flightlegdetailsoverlay";
	protected static final String JSESSIONID = "EMIRATES_JSESSIONID";
	private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormat
			.forPattern("dd");
	private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormat
			.forPattern("yyyy-MM");
	private static final String AIRLINE_CODE = "EK";
	
	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestType getType() {
		// TODO Auto-generated method stub
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return URL;
	}

	@Override
	public String getHeaderValue() {
		// TODO Auto-generated method stub
		return null;//(String) search.getParamValue(JSESSIONID);
	}
	@Override
	public FluentStringsMap getParams(){
		FluentStringsMap params = new FluentStringsMap();
		String linkParams = search.getParamValue("LINK_DETAIL").toString();
		params.add("isFilter","true");
		params.add("srchSnr","0");
		params.add("ca","");
		if(search.isRoundtrip()){
			params.add("retDy",DAY_FORMATTER.print(search.getInboundDate()));
			params.add("retMo",MONTH_YEAR_FORMATTER.print(search.getInboundDate()));
		}
		params.add("filterAirlines",AIRLINE_CODE);
		params.add("depDy",DAY_FORMATTER.print(search.getOutboundDate()));
		params.add("fadi",search.getArrivalCode());
		params.add("faoi",search.getDepartureCode());
		params.add("flexDate","False");
		params.add("srchAdt",search.getAdultsCountString());
		params.add("fado",search.getArrivalCode());
		params.add("path","flights");
		params.add("faoo",search.getDepartureCode());
		params.add("airline","NONE");
		params.add("pTxId",StringUtils.substringsBetween(linkParams, "pTxId=", "&"));
		params.add("startIndex","0");
		params.add("drctTrip","false");
		params.add("requestId",StringUtils.substringsBetween(linkParams, "requestId=", "&"));
		params.add("ad_dest","");
		params.add("depMo",MONTH_YEAR_FORMATTER.print(search.getOutboundDate()));
		params.add("tabId","no_fare_rules");
		params.add("numLegs",StringUtils.substringsBetween(linkParams, "numLegs=", "&"));
		params.add("srchChld","0");
		params.add("arrAp",search.getArrivalCode());
		params.add("configId",StringUtils.substringsBetween(linkParams, "configId=", "&"));
		params.add("intcmp","");
		params.add("intcmp","");
		params.add("intcmp","");
		params.add("intcmp","");
		params.add("redirectOnly","false");
		params.add("source","");
		params.add("source","");
		params.add("legItineraryIds",StringUtils.substringsBetween(linkParams, "legItineraryIds=", "&"));
		params.add("legIndex",StringUtils.substringsBetween(linkParams, "legIndex=", "&"));
		params.add("cabins","Y");
		params.add("airlinesUsed","true");
		params.add("srchInf","0");
		params.add("ad_country","");
		params.add("isModal","true");
		params.add("_",linkParams.substring(linkParams.lastIndexOf("=") + 1));
		return params;
	}
}
