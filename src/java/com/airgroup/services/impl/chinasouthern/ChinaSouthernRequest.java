package com.airgroup.services.impl.chinasouthern;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class ChinaSouthernRequest extends FlightResponse {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("yyyyMMdd");

	@Override
	public String getDateTimeFormatterPattern() {
		return null;
	}

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return (String) search.getParamValue("requestLink");
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params.add("B_LOCATION_1", search.getDepartureCode());

		params.add("DATE_RANGE_VALUE_1", "0");
		params.add("HTTPTRIPFLOW_TIMEOUT", "1000");

		params.add("E_LOCATION_1", search.getArrivalCode());

		params.add("ENCT", "1");

		int j = 0;
		for (int i = 1; i <= search.getAdultsCount(); i++) {
			params.add("TRAVELLER_TYPE_" + i, "ADT");
			j = i;
		}
		for (int i = 1; i <= search.getChildrenCount(); i++) {
			params.add("TRAVELLER_TYPE_" + (i + j), "CHD");
		}
		for (int i = 1; i <= search.getInfantsCount(); i++) {
			params.add("HAS_INFANT_" + i, "TRUE");
		}

		params.add("SITE", "CAXQCAXQ");
		params.add("SEVEN_DAY_SEARCH", "TRUE");
		params.add("TRIP_FLOW", "[YES, YES]");
		params.add("AIRLINE_4_1", "CZ");
		params.add("EXTERNAL_ID", "TWEN");
		params.add("TRIP_TYPE", search.isRoundtrip() ? "R" : "O");
		params.add("DATE_RANGE_QUALIFIER_1", "C");
		params.add("DATE_RANGE_QUALIFIER_2", "C");
		params.add("OFFICE_ID", "TPECZ12TK");
		params.add("AIRLINE_3_1", "CZ");

		params.add("B_DATE_1", DATE_TIME_FORMATTER.print(search.getOutboundDate()) + "0000");
		if (search.isRoundtrip()) {
			params.add("B_DATE_2", DATE_TIME_FORMATTER.print(search.getInboundDate()) + "0000");
			params.add("DATE_RANGE_VALUE_2", "0");
		}
		params.add("COMMERCIAL_FARE_FAMILY_1", "NCZECO");
		params.add("B_ANY_TIME_1", "TRUE");

		params.add("LANGUAGE", "GB");

		params.add("HTTPTRIPFLOW_STRICT", "false");
		params.add("ARRANGE_BY", "D");
		params.add("HTTP_ENCRYPTION_KEY", (String) search.getParamValue("httpKey"));
		params.add("CABIN", "E");
		params.add("B_ANY_TIME_2", "TRUE");
		params.add("PLTG_IS_UPSELL", "true");
		params.add("PAGE_TICKET", "0");
		params.add("PRICING_TYPE", "I");
		params.add("DISPLAY_TYPE", "1");
		params.add("BACKUP_PRICING_TYPE", "I");

		return params;
	}
}
