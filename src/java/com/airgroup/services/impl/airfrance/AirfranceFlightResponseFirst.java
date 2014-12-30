package com.airgroup.services.impl.airfrance;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class AirfranceFlightResponseFirst extends FlightResponse {

	private static final String URL = "http://www.airfrance.com/cgi-bin/AF/VN/en/local/process/standardbooking/ValidateSearchAction.do";
	private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormat.forPattern("yyyyMM");

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

		params.add("idDepartureTrip1Lib",search.getDepartureCode());
		params.add("departure",search.getDepartureCode());
		params.add("idArrivalTrip1LibContainer",search.getArrivalCode());
		params.add("idArrivalTrip1Lib",search.getArrivalCode());
		params.add("arrival",search.getArrivalCode());
	
		if (search.isRoundtrip()) {
			params.add("typeTrip","2");
			params.add("departure",search.getArrivalCode());
			params.add("arrival",search.getDepartureCode());
		}else{
			params.add("typeTrip","1");
			params.add("departure","");
			params.add("arrival","");
		}
		params.add("selectPreviousSearch","");
		params.add("dayDate",""+search.getOutboundDate().getDayOfMonth());
		params.add("yearMonthDate",MONTH_YEAR_FORMATTER.print(search.getOutboundDate()));
		if (search.isRoundtrip()) {
			params.add("dayDate",""+search.getInboundDate().getDayOfMonth());
			params.add("yearMonthDate",MONTH_YEAR_FORMATTER.print(search.getInboundDate()));
		}
		int count = search.getAdultsCount()+search.getChildrenCount()+search.getInfantsCount();
		params.add("nbPassenger",count+"");
		for (int i = 0; i < search.getAdultsCount(); i++) {
			params.add("paxTypoList","ADT");
		}
		for (int i = 0; i < search.getChildrenCount(); i++) {
			params.add("paxTypoList","CHD");
		}
		for (int i = 0; i < search.getInfantsCount(); i++) {
			params.add("paxTypoList","INF");
		}
		params.add("nbEnfants","");
		params.add("selectCabin","Y_MCHER");
		params.add("selectCabin","Y_MCHER");
		params.add("selectCabin","Y_MCHER");
		params.add("selectCabin","Y_MCHER");
		params.add("cabin","Y");
		params.add("subCabin","MCHER");
		params.add("haul","LH");
		params.add("familyTrip","NON");
		params.add("jourAllerOrigine",""+DateTime.now().getDayOfMonth());
		params.add("jourAllerFin",""+DateTime.now().getDayOfMonth());
		params.add("moisAllerOrigine",MONTH_YEAR_FORMATTER.print(DateTime.now()));
		params.add("moisAllerFin",MONTH_YEAR_FORMATTER.print(DateTime.now().plusMonths(11)));
		params.add("plusOptions","");
		params.add("isUM","");
		
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
