package com.airgroup.services.impl.ethiopianairlines;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class EthiopianAirlinesFlightResponseSecond extends FlightResponse {

	private static final String URL = "https://wl-prod.sabresonicweb.com/SSW2010/ETET/webqtrip.html";
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

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
		
		params.add("alternativeLandingPage","true");
		params.add("origin",search.getDepartureCode());
		params.add("departureDate",TIME_FORMATTER.print(search.getOutboundDate()));
		params.add("destination",search.getArrivalCode());
		if (search.isRoundtrip()) {
			params.add("returnDate",TIME_FORMATTER.print(search.getInboundDate()));
			params.add("journeySpan","RT");
		}else{
			params.add("journeySpan","OW");
		}
		params.add("cabin","ECONOMY");
		params.add("numAdults",search.getAdultsCountString());
		params.add("numChildren",search.getChildrenCountString());
		params.add("numInfants",search.getInfantsCountString());

		return params;
	}

	@Override
	public String getHeaderValue() {
		return (String) search.getParamValue("jid");
	}

	@Override
	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
