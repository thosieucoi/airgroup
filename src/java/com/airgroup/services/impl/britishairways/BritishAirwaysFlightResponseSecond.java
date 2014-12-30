package com.airgroup.services.impl.britishairways;


import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class BritishAirwaysFlightResponseSecond extends FlightResponse{
	private static final String URL ="http://www.britishairways.com/travel/fx/public/en_vn";
	protected static final String JSESSIONID = "JSESSIONID";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("dd/MM/yy");
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
		
		params.add("WebApplicationID","BOD");
		params.add("Output","");
		params.add("eId",search.getParamValue("eId").toString());
		params.add("eIdStartAgain","111008");
		params.add("toCode","");
		params.add("startPage","PLANTRIP");
		params.add("hostURL","http://www.britishairways.com");
		params.add("saleOption","FO");
		params.add("depCountry","GB");
		params.add("depCountryPkg","GB");
		params.add("from",search.getDepartureCode());
		params.add("fromPkg",search.getDepartureCode());
		params.add("to",search.getArrivalCode());
		params.add("packageTo","");
		params.add("ojDd","");
		params.add("ojGw","");
		params.add("ojCountry","");
		params.add("ojDropOffDd","");
		params.add("ojDropOffGw","");
		params.add("ojDropOffCountry","");
		params.add("depDate",DATE_TIME_FORMATTER.print(search.getOutboundDate()));
		if (search.isRoundtrip()) {
			params.add("retDate",DATE_TIME_FORMATTER.print(search.getInboundDate()));
		}
		params.add("cabin","M");
		params.add("restrictionType","LOWEST");
		params.add("ad",""+search.getAdultsCountString());
		params.add("ch",search.getChildrenCountString());
		params.add("inf",search.getInfantsCountString());
		params.add("beta","FALSE");
		params.add("hotelGOTo","");
		params.add("checkInGO",DATE_TIME_FORMATTER.print(search.getOutboundDate()));
		if (search.isRoundtrip()) {
			params.add("checkOutGO",DATE_TIME_FORMATTER.print(search.getInboundDate()));
			params.add("numNightsGO",""+ (search.getInboundDate().getDayOfYear()- search.getOutboundDate().getDayOfYear()));
		}
		params.add("carGOTo","");
		params.add("carDropOff","");
		params.add("pickUpGO",DATE_TIME_FORMATTER.print(search.getOutboundDate()));
		params.add("pickUpTimeHr","09");
		params.add("pickUpTimeMin","00");
		if (search.isRoundtrip()) {		
			params.add("dropOffGO",DATE_TIME_FORMATTER.print(search.getInboundDate()));
			params.add("dropOffTimeHr","09");
			params.add("dropOffTimeMin","00");
		}		
		params.add("expGOTo","");
		params.add("expFromGO",DATE_TIME_FORMATTER.print(search.getOutboundDate()));
		if (search.isRoundtrip()) {
			params.add("expUntilGO",DATE_TIME_FORMATTER.print(search.getInboundDate()));
		}
		params.add("expNumPaxGO","2");
		params.add("flightHotelDate","Y");
		params.add("checkInPkg","DD/MM/YY");
		if (search.isRoundtrip()) {
			params.add("checkOutPkg","DD/MM/YY");
		}
		params.add("numNightsPkg","");
		params.add("roomsRqd","1");
		params.add("adultsRoom1","2");
		params.add("childrenRoom1","0");
		params.add("infantsRoom1","0");
		params.add("adcar","1");
		params.add("chcar","0");
		params.add("infcar","0");
		params.add("flightCarDate","Y");
		params.add("pickUpPkg","DD/MM/YY");
		params.add("pickUpPkgTimeHr","09");
		params.add("pickUpPkgTimeMin","00");
		if (search.isRoundtrip()) {		
			params.add("dropOffPkg","DD/MM/YY");
			params.add("dropOffPkgTimeHr","09");
			params.add("dropOffPkgTimeMin","00");
		}
				
		params.add("BAHPRESpecialOfferGroup","");
		params.add("getFlights","Get flights");
		
		return params;
	}

	public String getHeaderValue() {
		return null;
	}

	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
