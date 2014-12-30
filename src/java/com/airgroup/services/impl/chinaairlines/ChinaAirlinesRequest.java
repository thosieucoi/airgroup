/**
 * 
 */
package com.airgroup.services.impl.chinaairlines;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 *
 */
public class ChinaAirlinesRequest extends FlightResponse{
	
	private static final String URL = "http://www.cleartrip.com/flights/results/airjson";

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
		params.add("from",search.getDepartureCode());
		params.add("to",search.getArrivalCode());
		params.add("depart_date",getDateTimeFormatter().print(search.getOutboundDate()));
		if(search.isRoundtrip()){
			params.add("return_date",getDateTimeFormatter().print(search.getInboundDate()));
		}
		params.add("adults",search.getAdultsCountString());
		params.add("childs",search.getChildrenCountString());
		params.add("infants",search.getInfantsCountString());
		params.add("class","Economy");
		params.add("airline","");
		params.add("carrier","");
		params.add("intl","y");
		params.add("search_ver","V2");
		params.add("cc","1");
		params.add("type","json");
		params.add("ver","V2");
		return params;
	}
}
