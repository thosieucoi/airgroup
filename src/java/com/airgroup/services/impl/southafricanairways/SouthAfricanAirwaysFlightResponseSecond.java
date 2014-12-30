package com.airgroup.services.impl.southafricanairways;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class SouthAfricanAirwaysFlightResponseSecond extends FlightResponse{
	private static final String URL ="http://www.flysaa.com/us/en/pricing!priceItinerary.action";
	private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormat
			.forPattern("MMM yy");
	private static final DateTimeFormatter FROMDATE_FORMATTER = DateTimeFormat
			.forPattern("dd-MMM yy");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("dd");
	protected static final String JSESSIONID = "JSESSIONID";
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
		
		params.add("isDomesticFlight","N");
		params.add("pageName","availabilityPage");
		params.add("country","US");
		params.add("language","EN");
		params.add("forgotPwdLoginId","");
		params.add("forgotPwdEmailId","");
		params.add("searchInput","Search");
		params.add("selectedProductIs","FTS");
		params.add("mobileUser","");
		params.add("flow","loadChange");
		params.add("destDateSel",FROMDATE_FORMATTER.print(search.getInboundDate()));
		params.add("prod","FTS");
		params.add("dmDeparture","");
		params.add("dmDestination","");
		params.add("calendarSearchFlag","false");
		if (search.isRoundtrip()) {
			params.add("tripType","R");
		}else{
			params.add("tripType","O");
		}
		params.add("departCity",search.getDepartureCode());
		params.add("departDay",DATE_FORMATTER.print(search.getOutboundDate()));
		params.add("departMonthYear",MONTH_FORMATTER.print(search.getOutboundDate()));
		params.add("fromDate",FROMDATE_FORMATTER.print(search.getOutboundDate()));

		params.add("destCity",search.getArrivalCode());
		if (search.isRoundtrip()) {
			params.add("destDay",DATE_FORMATTER.print(search.getInboundDate()));
			params.add("destMonthYear",MONTH_FORMATTER.print(search.getInboundDate()));
			params.add("toDate",FROMDATE_FORMATTER.print(search.getInboundDate()));
		}else{
			params.add("destDay","");
			params.add("destMonthYear","");
			params.add("toDate","-");
		}
		params.add("adultCount",search.getAdultsCountString());
		params.add("childCount",search.getChildrenCountString());
		params.add("infantCount",search.getInfantsCountString());
		params.add("preferredClass","0");
		params.add("flexible","false");
		params.add("promoCode","");
		params.add("flight0",Integer.parseInt(search.getParamValue(SouthAfricanAirwaysFlight.DEPARTURE_FLIGHT_NUMBER_SELECT).toString())+"@@"+(String)search.getParamValue("kind"));
		if (search.isRoundtrip()) {
			params.add("flight1",Integer.parseInt(search.getParamValue(SouthAfricanAirwaysFlight.ARRIVAL_FLIGHT_NUMBER_SELECT).toString())+"@@"+(String)search.getParamValue("kind"));
		}
		
//		params.add("isDomesticFlight","N");
//		params.add("pageName","availabilityPage");
//		params.add("country","US");
//		params.add("language","EN");
//		params.add("forgotPwdLoginId","");
//		params.add("forgotPwdEmailId","");
//		params.add("searchInput","Search");
//		params.add("selectedProductIs","FTS");
//		params.add("mobileUser","");
//		params.add("flow","loadChange");
//		params.add("destDateSel","30-Oct 13");
//		params.add("prod","FTS");
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
////		params.add("fltDtls1","25 October 2013 06:00@@25 October 2013 07:20");
////		params.add("fltDtls1","25 October 2013 11:15@@26 October 2013 08:15");
////		params.add("fltDtls1","26 October 2013 10:55@@26 October 2013 13:25");
////		params.add("fltDtls2","25 October 2013 17:28@@25 October 2013 19:00");
////		params.add("fltDtls2","26 October 2013 11:15@@27 October 2013 08:15");
////		params.add("fltDtls2","27 October 2013 10:55@@27 October 2013 13:25");
//		params.add("flight0",Integer.parseInt(search.getParamValue(SouthAfricanAirwaysFlight.DEPARTURE_FLIGHT_NUMBER_SELECT).toString())+"@@SchedNonY");
////		params.add("fltDtls3","25 October 2013 14:05@@25 October 2013 15:26");
////		params.add("fltDtls3","26 October 2013 11:15@@27 October 2013 08:15");
////		params.add("fltDtls3","27 October 2013 10:55@@27 October 2013 13:25");
////		params.add("fltDtls4","25 October 2013 14:18@@25 October 2013 15:56");
////		params.add("fltDtls4","25 October 2013 17:40@@26 October 2013 17:05");
////		params.add("fltDtls4","27 October 2013 10:55@@27 October 2013 13:25");
////		params.add("fltDtls5","25 October 2013 11:10@@25 October 2013 12:25");
////		params.add("fltDtls5","26 October 2013 11:15@@27 October 2013 08:15");
////		params.add("fltDtls5","27 October 2013 10:55@@27 October 2013 13:25");
////		params.add("fltDtls6","25 October 2013 19:13@@25 October 2013 20:52");
////		params.add("fltDtls6","26 October 2013 17:40@@27 October 2013 17:05");
////		params.add("fltDtls6","28 October 2013 10:55@@28 October 2013 13:25");
////		params.add("fltDtls7","25 October 2013 19:13@@25 October 2013 20:52");
////		params.add("fltDtls7","26 October 2013 17:40@@27 October 2013 17:05");
////		params.add("fltDtls7","28 October 2013 10:55@@28 October 2013 13:25");
////		params.add("fltDtls8","30 October 2013 14:00@@30 October 2013 16:35");
////		params.add("fltDtls8","30 October 2013 17:45@@31 October 2013 06:15");
////		params.add("fltDtls8","31 October 2013 09:10@@31 October 2013 10:25");
////		params.add("fltDtls9","30 October 2013 14:00@@30 October 2013 16:35");
////		params.add("fltDtls9","30 October 2013 18:00@@31 October 2013 06:25");
////		params.add("fltDtls9","31 October 2013 12:15@@31 October 2013 13:44");
////		params.add("fltDtls10","30 October 2013 14:00@@30 October 2013 16:35");
////		params.add("fltDtls10","30 October 2013 17:45@@31 October 2013 06:15");
////		params.add("fltDtls10","31 October 2013 17:00@@31 October 2013 18:16");
////		params.add("fltDtls11","30 October 2013 14:00@@30 October 2013 16:35");
////		params.add("fltDtls11","30 October 2013 18:00@@31 October 2013 06:25");
////		params.add("fltDtls11","31 October 2013 16:54@@31 October 2013 18:26");
//		params.add("flight1",Integer.parseInt(search.getParamValue(SouthAfricanAirwaysFlight.ARRIVAL_FLIGHT_NUMBER_SELECT).toString())+"@@SchedNonY");
////		params.add("fltDtls12","30 October 2013 14:00@@30 October 2013 16:35");
////		params.add("fltDtls12","30 October 2013 18:00@@31 October 2013 06:25");
////		params.add("fltDtls12","31 October 2013 12:15@@31 October 2013 13:44");
		return params;
	}

	public String getHeaderValue() {
		return (String) search.getParamValue(JSESSIONID);
	}

	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
