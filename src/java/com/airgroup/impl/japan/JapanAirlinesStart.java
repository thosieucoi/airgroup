package com.airgroup.impl.japan;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class JapanAirlinesStart extends FlightResponse{
	private static final String URL = "http://www.5971.jal.co.jp/eng/LFSSearchDispatch.do";
	@Override
	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return null;
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
		return "dd-MM-yyyy";
	}
	
	@Override
	public FluentStringsMap getParams(){
		FluentStringsMap params = new FluentStringsMap();
	
		params.add("searchType","1");
		params.add("linkId","208");
		params.add("departureAirportCode1",search.getDepartureCode());
		params.add("arrivalAirportCode1",search.getArrivalCode());
		params.add("segmentType",search.isRoundtrip() ?"2" : "1");
		params.add("departureMonth1","" + search.getOutboundDate().getMonthOfYear());
		params.add("departureDay1","" + search.getOutboundDate().getDayOfMonth());
		params.add("departureTime1","00002359");
		
		if (search.isRoundtrip()){
			params.add("departureMonth2","" + search.getInboundDate().getMonthOfYear());
			params.add("departureDay2", "" + search.getInboundDate().getDayOfMonth());
			params.add("departureTime2","00002359");
		}
		params.add("adult", search.getAdultsCountString());
		params.add("child", search.getChildrenCountString());
		params.add("infant",search.getInfantsCountString());
		params.add("class1","sclassJAndNormal");
		params.add("searchOption","lowestPrice");
		return params;
	}
}
