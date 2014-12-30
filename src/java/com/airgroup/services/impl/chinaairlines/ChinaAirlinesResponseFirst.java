/**
 * 
 */
package com.airgroup.services.impl.chinaairlines;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 *
 */
public class ChinaAirlinesResponseFirst extends FlightResponse{
	
	private static final String URL = "https://caleb.china-airlines.com/olbn/travel.aspx";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return "dd/MM/yyyy";
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

	public FluentStringsMap getParams(){
		FluentStringsMap params = new FluentStringsMap();
		params.add("lang","en-US");
		params.add("depstn",search.getDepartureCode());
		params.add("arrstn",search.getArrivalCode());
		params.add("cls","Y");
		params.add("adult_no",search.getAdultsCountString());
		params.add("child_no",search.getChildrenCountString());
		params.add("Dep_Date",DATE_FORMATTER.print(search.getOutboundDate()));
		params.add("Ret_Date",DATE_FORMATTER.print(search.getInboundDate()));
		if (search.isRoundtrip()) {
			params.add("Trip","RT");
		}else{
			params.add("Trip","OW");
		}
		
		params.add("url","FARE");
		params.add("auto","Y");
		params.add("afntcode","");
		return params;
	}
}
