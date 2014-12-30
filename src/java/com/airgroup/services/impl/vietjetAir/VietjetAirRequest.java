package com.airgroup.services.impl.vietjetAir;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.util.Const;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class VietjetAirRequest extends FlightResponse {

	private static final String REQUEST_URL = "https://ameliaweb5.intelisys.ca/VietJet/ameliapost.aspx?lang=vi";
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("dd");
	
	private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormat.forPattern("yyyy/MM");

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
		return REQUEST_URL;
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		params.add("chkRoundTrip", search.isRoundtrip() ? "on" : "");
		params.add("lstOrigAP", search.getDepartureCode());
		params.add("lstDestAP", search.getArrivalCode());
		params.add("dlstDepDate_Day", DATE_FORMATTER.print(search.getOutboundDate()));
		params.add("dlstDepDate_Month", MONTH_YEAR_FORMATTER.print(search.getOutboundDate()));
		params.add("dlstRetDate_Day", DATE_FORMATTER.print(search.getInboundDate()));
		params.add("dlstRetDate_Month", MONTH_YEAR_FORMATTER.print(search.getInboundDate()));
		params.add("lstCurrency", Const.VND);
		params.add("lstResCurrency", Const.VND);
		params.add("lstDepDateRange", "0");
		params.add("lstRetDateRange", "0");
		params.add("txtNumAdults", search.getAdultsCountString());
		params.add("txtNumChildren", search.getChildrenCountString());
		params.add("txtNumInfants", search.getInfantsCountString());
		params.add("lstLvlService", "1");
		params.add("txtPromoCode", "");
		return params;
	}
}
