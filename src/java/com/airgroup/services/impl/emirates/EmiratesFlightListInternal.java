/**
 * 
 */
package com.airgroup.services.impl.emirates;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 *
 */
public class EmiratesFlightListInternal extends FlightResponse{

	private static final String URL = "http://www.lastminute.com/trips/flightlist/faregroupListInternal";
	private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormat
			.forPattern("dd");
	private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormat
			.forPattern("yyyy-MM");
	
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
		return null;
	}
	@Override
	public FluentStringsMap getParams(){
		FluentStringsMap params = new FluentStringsMap();
		params.add("srchSnr","0");
		if(search.isRoundtrip()){
			params.add("retMo", MONTH_YEAR_FORMATTER.print(search.getInboundDate()));
			params.add("retDy",DAY_FORMATTER.print(search.getInboundDate()));
		}
		params.add("arrAp",search.getArrivalCode());
		params.add("depDy",DAY_FORMATTER.print(search.getOutboundDate()));
		params.add("depAp",search.getDepartureCode());
		params.add("flexDate","False");
		params.add("srchAdt",search.getAdultsCountString());
		params.add("event","");
		params.add("path","flights");
		params.add("airline","NONE");
		params.add("pTxId",search.getParamValue("pTxId").toString());
		params.add("drctTrip","false");
		params.add("depMo",MONTH_YEAR_FORMATTER.print(search.getOutboundDate()));
		params.add("ad_dest","");
		params.add("numLegs",search.isRoundtrip()?"2":"1");
		params.add("srchChld","0");
		params.add("configId","S72722479");
		params.add("intcmp","");
		params.add("redirectOnly","false");
		params.add("source","");
		params.add("cabins","Y");
		params.add("srchInf","0");
		params.add("ad_country","");
		return params;
	}
}
