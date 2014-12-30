package com.airgroup.services.impl.aeroflot;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.model.Search;
import com.airgroup.services.FlightResponse;

public class AeroflotFlightResponseSecond extends FlightResponse {

	private static final String URL = "https://booking.aeroflot.ru/meridia?posid=80T7&sid=";
	private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormat.forPattern("MMM");

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		String newURL = URL+(String)search.getParamValue("sid");
		return newURL;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params.add("page","nonFlexairRequestMessage_air");
		params.add("action","airItinSelection");
		params.add("actionType","nonFlex");
		params.add("actionPage","");
		params.add("posid","80T7");
		params.add("language","en");
		if (search.isRoundtrip()) {
			params.add("direction","returntravel");
		}else{
			params.add("direction","onewaytravel");
		}
		
		params.add("depDay", Integer.toString(search.getOutboundDate().getDayOfMonth()));
		params.add("depMonth", MONTH_FORMATTER.print(search.getOutboundDate()));
		params.add("depTime","0500");
		params.add("departTime","0500");
		if (search.isRoundtrip()) {
			params.add("retDay", Integer.toString(search.getInboundDate().getDayOfMonth()));
			params.add("retMonth", MONTH_FORMATTER.print(search.getInboundDate()));
		}
		params.add("retTime","0500");
		params.add("returnTime","0500");
		params.add("ADT",search.getAdultsCountString());
		//params.add("ADT_lnform",search.getAdultsCountString());
		params.add("CHD",search.getChildrenCountString());
		params.add("IFS",search.getInfantsCountString());
		params.add("OUT_FLIGHTS_SORTING_BY_DATE_TYPE","");
		params.add("IN_FLIGHTS_SORTING_BY_DATE_TYPE","");
		params.add("SHOW_FILTER_RANGE_FLIGHT_WARNING","");
		params.add("SHOW_FILTER_RANGE_OUTBOUND_FLIGHT_WARNING","");
		params.add("SHOW_FILTER_RANGE_INBOUND_FLIGHT_WARNING","");
		params.add("MATRIX_INTELLISELL","true");
		params.add("fareMatrixPrevURL","");
		params.add("out",search.getParamValue("out")+"|BE");
		if (search.isRoundtrip()) {
			params.add("in",search.getParamValue("in")+"|BE");
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
