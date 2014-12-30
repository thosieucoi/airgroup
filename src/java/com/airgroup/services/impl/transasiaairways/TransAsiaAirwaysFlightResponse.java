package com.airgroup.services.impl.transasiaairways;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class TransAsiaAirwaysFlightResponse extends FlightResponse {

	private static final String URL = "https://gessl.tna.com.tw/b2c/International/InterOrder/InterQueryFareServlet.do?lang=en_US";
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("yyyy/MM/dd");

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

		params.add("depstn2",search.getArrivalCode());
		params.add("arrstn2",search.getDepartureCode());
		params.add("adultno",search.getAdultsCountString());
		params.add("childno",search.getChildrenCountString());
		params.add("elderno",search.getInfantsCountString());
		params.add("depArea","05-SOUTHEAST ASIA");
		params.add("depstn",search.getDepartureCode());
		params.add("arrArea","01-TAIWAN");
		params.add("arrstn",search.getArrivalCode());
		
		if (search.isRoundtrip()) {
			params.add("itintype","R");
		}else{
			params.add("itintype","O");
		}
		params.add("cabin","Y");
		params.add("depdate",TIME_FORMATTER.print(search.getOutboundDate()));
		params.add("depstime","0000");
		params.add("depetime","2359");
		if (search.isRoundtrip()) {
			params.add("retdate",TIME_FORMATTER.print(search.getInboundDate()));
		
			params.add("retstime","0000");
			params.add("retetime","2359");
		}else{
			params.add("retdate","");
			
			params.add("retstime","0000");
			params.add("retetime","2359");
		}
		if(search.isRoundtrip()){
			params.add("imageField.x","34");
			params.add("imageField.y","14");
		}else{
			params.add("imageField.x","57");
			params.add("imageField.y","23");
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
