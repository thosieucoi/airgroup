package com.airgroup.services.impl.southafricanairways;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class SouthAfricanAirwaysFlightResponseLanguage extends FlightResponse{
	private static final String URL ="http://www.flysaa.com/us/en/home!loadHome.action";
	private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormat
			.forPattern("MMM dd");
	private static final DateTimeFormatter FROMDATE_FORMATTER = DateTimeFormat
			.forPattern("dd-MMM yy");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("dd");
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
		params.add("mobileUser","false");
		params.add("selLanguage","EN");
		params.add("countrySeltd","US");
		params.add("country","US");

		return params;
	}

	public String getHeaderValue() {
		return null;
	}

	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
