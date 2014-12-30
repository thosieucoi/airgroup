package com.airgroup.services.impl.koreanair;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class KoreanReservation extends FlightResponse {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("dd");

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("yyyyMMdd");

	private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormat.forPattern("yyyyMM");

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
		return "http://www.koreanair.com/global/bl/rs/rs_v9reservationController.jsp";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params.add("AIRLINE_1_1", "KE");
		params.add("AIRLINE_2_1", "KE");
		params.add("ARRANGE_BY", "N");
		params.add("Adult", search.getAdultsCountString());
		params.add("B_ANY_TIME_1", "TRUE");
		params.add("B_ANY_TIME_2", "TRUE");
		params.add("B_ANY_TIME_3", "TRUE");
		params.add("B_ANY_TIME_4", "TRUE");
		params.add("B_ANY_TIME_5", "TRUE");
		params.add("B_ANY_TIME_6", "TRUE");
		params.add("B_DATE_1", DATE_TIME_FORMATTER.print(search.getOutboundDate()) + "0000");
		params.add("B_DATE_2", DATE_TIME_FORMATTER.print(search.getInboundDate()) + "0000");
		params.add("B_DAY", DATE_FORMATTER.print(search.getOutboundDate()));
		params.add("B_LOCATION_1", search.getDepartureCode());
		params.add("B_MONTH", MONTH_FORMATTER.print(search.getOutboundDate()));
		params.add("B_TIME", "0000");
		params.add("COMMERCIAL_FARE_FAMILY_1", "ECONOMY1");
		params.add("Child", search.getChildrenCountString());
		params.add("DATE_RANGE_VALUE_1", "0");
		params.add("DIRECT_LOGIN", "NO");
		params.add("EXTERNAL_ID", "KE");
		params.add("E_DAY", DATE_FORMATTER.print(search.getInboundDate()));
		params.add("E_LOCATION_1", search.getArrivalCode());
		params.add("E_MONTH", MONTH_FORMATTER.print(search.getInboundDate()));
		params.add("E_TIME", "0000");

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

		// params.add("HAS_INFANT_1", "");
		// params.add("HAS_INFANT_2", "");
		// params.add("HAS_INFANT_3", "");
		// params.add("HAS_INFANT_4", "");
		// params.add("HAS_INFANT_5", "");
		// params.add("HAS_INFANT_6", "");
		// params.add("HAS_INFANT_7", "");
		// params.add("HAS_INFANT_8", "");
		// params.add("HAS_INFANT_9", "");
		params.add("H_Site", "SG");
		params.add("Infant", search.getInfantsCountString());
		params.add("LANGUAGE", "GB");
		params.add("SERVLET_NAME_KEY", "FlexPricerAvailability");
		params.add("SESSION_ID", "");
		params.add("SEVEN_DAY_SEARCH", "TRUE");
		params.add("SITE", "CAUOCAUO");
		params.add("SO_SITE_FP_BACKUP_TO_CAL", "TRUE");
		params.add("TRIP_TYPE", search.isRoundtrip() ? "R" : "O");
		params.add("T_Site", "CAUOCAUO");
		params.add("agentCode", "QES");
		params.add("date_range", "on");
		params.add("fb_flowcode", "");
		params.add("homeLoginPageName", "/index_sg_eng.jsp");
		params.add("lastCityB", "");
		params.add("lastCityE", "");
		params.add("lateLoginPageName", "/local/sg/gp/eng/ft/mb/mp_signIn_guest.jsp");
		params.add("ownerCode", "QES");
		params.add("selectedYYYY", "");
		params.add("selectedYYYY2", "");
		params.add("serverName", "www.koreanair.com");
		params.add("system", "kihe");
		params.add("topasURL", "http://www.koreanair.com/local/sg/gp/eng/tp/bo/eng_tp_bo_ifr.jsp");
		params.add("v7ResURL", "http://wftc3.e-travel.com/plnext/KEREG/Override.action");

		return params;
	}

}
