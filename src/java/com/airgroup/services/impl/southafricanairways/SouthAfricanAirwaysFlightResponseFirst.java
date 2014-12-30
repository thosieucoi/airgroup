package com.airgroup.services.impl.southafricanairways;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class SouthAfricanAirwaysFlightResponseFirst extends FlightResponse{
	private static final String URL ="http://www.flysaa.com/us/en/flightSearch.action?request_locale=en_US";
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
		
		params.add("country","US");
		params.add("language","EN");
		params.add("forgotPwdLoginId","");
		params.add("forgotPwdEmailId","");
		params.add("searchInput","Search");
		params.add("selectedProductIs","FTS");
		params.add("flow","normal");
		params.add("dmDeparture","");
		params.add("dmDestination","");
		params.add("calendarSearchFlag","false");
		
		params.add("departCity",search.getDepartureCode());
		params.add("departDay",DATE_FORMATTER.print(search.getOutboundDate()));
		params.add("departMonthYear",MONTH_FORMATTER.print(search.getOutboundDate()));
		params.add("fromDate",FROMDATE_FORMATTER.print(search.getOutboundDate()));
		params.add("destCity",search.getArrivalCode());
		if (search.isRoundtrip()) {
			params.add("tripType","R");
			params.add("destDay",DATE_FORMATTER.print(search.getInboundDate()));
			params.add("destMonthYear",MONTH_FORMATTER.print(search.getInboundDate()));
			params.add("toDate",FROMDATE_FORMATTER.print(search.getInboundDate()));
		}else{
			params.add("tripType","O");
			params.add("destMonthYear","");
			params.add("toDate","-");
		}
		params.add("adultCount",search.getAdultsCountString());
		params.add("childCount",search.getChildrenCountString());
		params.add("infantCount",search.getInfantsCountString());
		params.add("preferredClass","0");
		params.add("flexible","false");
		params.add("promoCode","");
		params.add("pickupLoc","Type your Pick-up point here");
		params.add("pickUpLocation","");
		params.add("dropoffLoc","Type your Drop-off point here");
		params.add("dropoffLocation","");
		params.add("pickDay",DATE_FORMATTER.print(DateTime.now()));
		params.add("pickMonthYear",MONTH_FORMATTER.print(DateTime.now()));
		params.add("pickupDate",FROMDATE_FORMATTER.print(DateTime.now()));
		params.add("pickUpTime","1000");
		params.add("dropoffDay","02");
		params.add("dropoffMonthYear",MONTH_FORMATTER.print(search.getOutboundDate()));
		params.add("dropoffDate",FROMDATE_FORMATTER.print(search.getOutboundDate()));
		params.add("dropOffTime","1000");
		params.add("carCountry","US");
		
//		params.add("country","US");
//		params.add("language","EN");
//		params.add("forgotPwdLoginId","");
//		params.add("forgotPwdEmailId","");
//		params.add("searchInput","Search");
//		params.add("selectedProductIs","FTS");
//		params.add("flow","normal");
//		params.add("dmDeparture","");
//		params.add("dmDestination","");
//		params.add("calendarSearchFlag","false");
//		params.add("tripType","R");
//		params.add("departCity","BTV");
//		params.add("departDay","25");
//		params.add("departMonthYear","Oct 13");
//		params.add("fromDate","25-Oct 13");
//		params.add("destCity","APL");
//		params.add("destDay","30");
//		params.add("destMonthYear","Oct 13");
//		params.add("toDate","30-Oct 13");
//		params.add("adultCount","1");
//		params.add("childCount","0");
//		params.add("infantCount","0");
//		params.add("preferredClass","0");
//		params.add("flexible","false");
//		params.add("promoCode","");
//		params.add("pickupLoc","Type your Pick-up point here");
//		params.add("pickUpLocation","");
//		params.add("dropoffLoc","Type your Drop-off point here");
//		params.add("dropoffLocation","");
//		params.add("pickDay","14");
//		params.add("pickMonthYear","Oct 13");
//		params.add("pickupDate","14-Oct 13");
//		params.add("pickUpTime","1000");
//		params.add("dropoffDay","21");
//		params.add("dropoffMonthYear","Oct 13");
//		params.add("dropoffDate","21-Oct 13");
//		params.add("dropOffTime","1000");
//		params.add("carCountry","US");
		return params;
	}

	public String getHeaderValue() {
		return null;
	}

	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
