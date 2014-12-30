package com.airgroup.services.impl.allnipponairway;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class AllNipponAirwaysFlightResponseFirst extends FlightResponse{
	private static final String URL ="http://www.ana.co.jp/asw/wws/vn/e/";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("yyyyMMddHHmm");
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
		return params;
	}

	public String getHeaderValue() {
		return null;
	}

	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
