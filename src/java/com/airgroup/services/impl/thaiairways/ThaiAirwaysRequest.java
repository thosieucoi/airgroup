package com.airgroup.services.impl.thaiairways;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class ThaiAirwaysRequest extends FlightResponse {

	@Override
	public String getDateTimeFormatterPattern() {
		return "yyyyMMdd";
	}

	@Override
	public RequestType getType() {
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		return "https://wftc3.e-travel.com/plnext/tgpnext/Override.action";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		if (search.isOneway()) {
			params.add("B_ANY_TIME_1", "TRUE");
			params.add("B_DATE_1", getDateTimeFormatter().print(search.getOutboundDate()) + "0000");
			params.add("B_LOCATION_1", search.getDepartureCode());
			params.add("EMBEDDED_TRANSACTION", "AirAvailability");
			params.add("EXTERNAL_ID", "NORMAL");
			params.add("E_LOCATION_1", search.getArrivalCode());
			params.add("LANGUAGE", "GB");
			params.add("PAYMENT_TYPE", "CON");
			params.add("SESSION_ID", "");
			params.add("SITE", "CATRCATR");
			params.add("SO_SITE_FD_DISPLAY_MODE", "0");
			params.add("SO_SITE_MINIMAL_TIME", "H6");
			params.add("SO_SITE_MOD_E_TICKET", "TRUE");
			params.add("SO_SITE_MOD_PICK_CITY", "FALSE");
			params.add("SO_SITE_TK_ARRANGEMENT", "TL");
			params.add("SO_SITE_TK_OFFICE_ID", "BKKTG08AA");
			params.add("SO_SITE_TK_TIME_PERIOD", "H6");

			int j = 0;
			for (int i = 1; i <= search.getAdultsCount(); i++) {
				params.add("TRAVELLER_TYPE_" + i, "ADT");
				j = i;
			}
			for (int i = 1; i <= search.getChildrenCount(); i++) {
				params.add("TRAVELLER_TYPE_" + (i + j), "CHD");
			}

			// params.add("TRAVELLER_TYPE_1", "ADT");
			// params.add("TRAVELLER_TYPE_2", "CHD");
			params.add("TRIP_FLOW", "YES");
			params.add("TRIP_TYPE", "O");
			params.add("TYPE", "AIR_TRIP_FARE");
			params
				.add(
					"SO_GL",
					"<?xml version=\"1.0\" encoding=\"iso-8859-1\"?><SO_GL><GLOBAL_LIST mode=\"complete\"><NAME>SO_SINGLE_MULTIPLE_COMMAND_BUILDER</NAME><LIST_ELEMENT><CODE>1</CODE><LIST_VALUE><![CDATA[OS YY IP <CLIENT_IP_ADDRESS>]]></LIST_VALUE><LIST_VALUE>S</LIST_VALUE></LIST_ELEMENT></GLOBAL_LIST></SO_GL>");
		} else {
			params.add("B_ANY_TIME_1", "TRUE");
			params.add("B_ANY_TIME_2", "TRUE");
			params.add("B_DATE_1", getDateTimeFormatter().print(search.getOutboundDate()) + "0000");
			params.add("B_DATE_2", getDateTimeFormatter().print(search.getInboundDate()) + "0001");
			params.add("B_LOCATION_1", search.getDepartureCode());
			params.add("COMMERCIAL_FARE_FAMILY_1", "VNECONOMY");
			params.add("DATE_RANGE_QUALIFIER_1", "C");
			params.add("DATE_RANGE_QUALIFIER_2", "C");
			params.add("DATE_RANGE_VALUE_1", "0");
			params.add("DATE_RANGE_VALUE_2", "0");
			params.add("DISPLAY_TYPE", "1");
			params.add("EMBEDDED_TRANSACTION", "FlexPricerAvailability");
			params.add("EXTERNAL_ID", "NORMAL");
			params.add("E_LOCATION_1", search.getArrivalCode());
			params.add("LANGUAGE", "GB");
			params.add("PAYMENT_TYPE", "CON");
			params.add("PRICING_TYPE", "I");
			params.add("SESSION_ID", "");
			params.add("SITE", "CATRCATR");
			params.add("SO_SITE_FD_DISPLAY_MODE", "0");
			params.add("SO_SITE_MINIMAL_TIME", "H6");
			params.add("SO_SITE_MOD_E_TICKET", "TRUE");
			params.add("SO_SITE_MOD_PICK_CITY", "FALSE");
			params.add("SO_SITE_TK_ARRANGEMENT", "TL");
			params.add("SO_SITE_TK_OFFICE_ID", "BKKTG08AA");
			params.add("SO_SITE_TK_TIME_PERIOD", "H6");

			int j = 0;
			for (int i = 1; i <= search.getAdultsCount(); i++) {
				params.add("TRAVELLER_TYPE_" + i, "ADT");
				j = i;
			}
			for (int i = 1; i <= search.getChildrenCount(); i++) {
				params.add("TRAVELLER_TYPE_" + (i + j), "CHD");
			}

			// params.add("TRAVELLER_TYPE_1", "ADT");
			// params.add("TRAVELLER_TYPE_2", "CHD");
			params.add("TRIP_FLOW", "YES");
			params.add("TRIP_TYPE", "R");
			params.add("TYPE", "AIR_TRIP_FARE");
			params
				.add(
					"SO_GL",
					"<?xml version=\"1.0\" encoding=\"iso-8859-1\"?><SO_GL><GLOBAL_LIST mode=\"complete\"><NAME>SO_SINGLE_MULTIPLE_COMMAND_BUILDER</NAME><LIST_ELEMENT><CODE>1</CODE><LIST_VALUE><![CDATA[OS YY IP <CLIENT_IP_ADDRESS>]]></LIST_VALUE><LIST_VALUE>S</LIST_VALUE></LIST_ELEMENT></GLOBAL_LIST></SO_GL>");

		}
		return params;
	}
}
