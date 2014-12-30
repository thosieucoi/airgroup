package com.airgroup.services.impl.aeroflot;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class AeroflotFlightResponse extends FlightResponse {

	private static final String URL = "https://booking.aeroflot.ru/meridia";
	private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormat.forPattern("MMM");

	@Override
	public RequestType getType() {
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		return URL;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params.add("mode", "booking");
		params.add("action", "airRequest");
		params.add("page", "requestAirMessage_air");
		params.add("realRequestAir", "realRequestAir");
		params.add("rem1", "aeroflotmain");
		params.add("rem2", "(direct)");
		params.add("rem3", "(direct)");
		params.add("rem4", "(none)");
		params.add("rem5", "");
		params.add("language", "en");
		params.add("kiosk", "0");
		params.add("posid", "80T7");
		params.add("geoCountry", "vn");
		params.add("utm_source", "(direct)");
		params.add("utm_medium", "(none)");
		params.add("utm_campaign", "(direct)");
		params.add("utm_content", "");
		if(search.isRoundtrip()){
			params.add("direction", "returntravel");
		}else{
			params.add("direction", "onewaytravel");
		}
		params.add("actionType", "nonFlex");
		params.add("departCity", search.getDepartureCode());
		params.add("returnCity", search.getArrivalCode());
		params.add("depDay", Integer.toString(search.getOutboundDate().getDayOfMonth()));
		params.add("depMonth", MONTH_FORMATTER.print(search.getOutboundDate()));
		params.add("depTime", "0500");
		if (search.isRoundtrip()) {
			params.add("retDay", Integer.toString(search.getInboundDate().getDayOfMonth()));
			
		}
		params.add("retMonth", MONTH_FORMATTER.print(search.getInboundDate()));
		params.add("retTime", "0500");
		params.add("ADT", search.getAdultsCountString());
		params.add("CHD", search.getChildrenCountString());
		params.add("IFS", search.getInfantsCountString());
		params.add("INF", "0");
		params.add("YTH", "0");
		params.add("classService", "CoachClass");
		params.add("flightType", "1");
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
