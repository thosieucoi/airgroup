package com.airgroup.services.impl.japan;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class JapanAirlineFirstRequest extends FlightResponse{
	private static final String URL = "https://ag.5931.jal.co.jp/jalint/toVacant.do";
	@Override
	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return "yyyyMMdd";
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
		if (search.isRoundtrip()){
			params.add("PRM_DEPARTURE", search.getDepartureCode());
			params.add("PRM_ARRIVAL",search.getArrivalCode());
			params.add("PRM_MEETINGMONTH", ""+search.getOutboundDate().getMonthOfYear());
			params.add("PRM_MEETINGDATE", ""+search.getOutboundDate().getDayOfMonth());
			params.add("PRM_SENDINGMONTH", ""+search.getInboundDate().getMonthOfYear());
			params.add("PRM_SENDINGDATE", ""+search.getInboundDate().getDayOfMonth());
			params.add("PRM_SEARCH_KIND2","1");
			params.add("PRM_SEARCH_KIND1","0");
			params.add("PRM_SEATADULT", search.getAdultsCountString());
			params.add("PRM_SEATCHILD", search.getChildrenCountString());
			params.add("PRM_SEATINFANT", search.getInfantsCountString());
			params.add("PRM_SEARCH_BUSINESS","");
			params.add("PRM_ET_TARGET","");
			params.add("PRM_EXT_LINK_ID","086");
			params.add("PRM_LANGUAGE","en");
			params.add("PRM_SEARCH_CALENDAR","0");
		}
		else {
			params.add("PRM_MEETINGMONTH", "" + search.getOutboundDate().getMonthOfYear());
			params.add("PRM_MEETINGDATE","" + search.getOutboundDate().getDayOfMonth());
			params.add("PRM_DEPARTURE",search.getDepartureCode());
			params.add("PRM_ARRIVAL",search.getArrivalCode());
			params.add("PRM_SEARCH_KIND2","2");
			params.add("PRM_SEARCH_KIND1","4");
			params.add("PRM_SEATADULT", search.getAdultsCountString());
			params.add("PRM_SEATCHILD", search.getChildrenCountString());
			params.add("PRM_SEATINFANT",search.getInfantsCountString());
			params.add("PRM_ET_TARGET","");
			params.add("PRM_EXT_LINK_ID","");
			params.add("PRM_LANGUAGE","en");
			params.add("PRM_SEARCH_CALENDAR","0");
		}
		return params;
	}

}
