package com.airgroup.services.impl.chinaeaternairlines;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class ChinaEaternAirlinesFlightResponse extends FlightResponse {

	private static final String URL = "http://en.ceair.com/booking/flight-search!doFlightSearch.shtml";
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return URL;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		if (search.isRoundtrip()) {
			String request = "{\"segmentList\":[{\"deptCd\":\"" + search.getDepartureCode() +"#\",\"arrCd\":\""+search.getArrivalCode()
					+"#\",\"deptDt\":\""+ TIME_FORMATTER.print(search.getOutboundDate()) +"\"},{\"deptCd\":\""+search.getArrivalCode()
					+"#\",\"arrCd\":\"" + search.getDepartureCode() +"#\",\"deptDt\":\""+ TIME_FORMATTER.print(search.getInboundDate())
					+"\"}],\"tripType\":\"RT\",\"adtCount\":" + search.getAdultsCountString() +",\"chdCount\":" + search.getChildrenCountString() +",\"currency\":\"CNY\",\"sortType\":\"t\"}";
			params.add("searchCond", request);
		}else{
			String request = "{\"segmentList\":[{\"deptCd\":\"" + search.getDepartureCode() +"#\",\"arrCd\":\""+search.getArrivalCode()
					+"#\",\"deptDt\":\""+ TIME_FORMATTER.print(search.getOutboundDate()) +"\"}],\"tripType\":\"OW\",\"adtCount\":" + search.getAdultsCountString() +",\"chdCount\":" + search.getChildrenCountString() +",\"currency\":\"CNY\",\"sortType\":\"t\"}";
			params.add("searchCond", request);
		}
		return params;
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
