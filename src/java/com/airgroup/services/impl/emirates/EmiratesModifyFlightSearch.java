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
public class EmiratesModifyFlightSearch extends FlightResponse{

	private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormat
			.forPattern("dd");
	private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormat
			.forPattern("yyyy-MM");
	private static final String URL = "http://www.lastminute.com/trips/interstitial/modifyFlightSearchInternal";
	
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
		params.add("ac_arrival_airport_name","");
		params.add("ac_arrival_airport",search.getArrivalCode());
		params.add("ac_arrival_city","");
		params.add("ac_arrival_country","");
		params.add("ac_arrival_state","");
		params.add("ac_departure_airport_name","");
		params.add("ac_departure_airport",search.getDepartureCode());
		params.add("ac_departure_city","");
		params.add("ac_departure_country","");
		params.add("ac_departure_state","");
		params.add("ad_country","");
		params.add("ad_dest","");
		params.add("arrivalAirports",search.getArrivalCode());
		params.add("configId","S72722479");
		params.add("depart_day",DAY_FORMATTER.print(search.getOutboundDate()));
		params.add("depart_month",MONTH_YEAR_FORMATTER.print(search.getOutboundDate()));
		params.add("departureAirports",search.getDepartureCode());
		params.add("redirectOnly","false");
		if(search.isRoundtrip()){
			params.add("return_day",DAY_FORMATTER.print(search.getInboundDate()));
			params.add("return_month",MONTH_YEAR_FORMATTER.print(search.getInboundDate()));
		}
		params.add("search_adults",search.getAdultsCountString());
		params.add("search_children",search.getChildrenCountString());
		params.add("search_child_1_age","0");
		params.add("search_child_2_age","0");
		params.add("search_child_3_age","0");
		params.add("search_child_4_age","0");
		params.add("search_child_5_age","0");
		params.add("search_child_6_age","0");
		params.add("search_child_7_age","0");
		params.add("search_child_8_age","0");
		params.add("search_infants","0");
		params.add("search_seniors","0");
		params.add("direct_trip","false");
		params.add("flexDate","False");
		params.add("event","");
		params.add("numLegs",search.isRoundtrip()?"2":"1");
		params.add("cabins","Y");
		params.add("airline","NONE");
		params.add("source","");
		params.add("intcmp","");		
		return params;
	}
}
