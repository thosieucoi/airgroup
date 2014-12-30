package com.airgroup.services.impl.ethiopianairlines;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class EthiopianAirlinesFlightResponseFirst extends FlightResponse {

	private static final String URL = "http://www.ethiopianairlines.com/en/booking/book.aspx";
	private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormat.forPattern("MMM");
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("MM/dd/yyyy");

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

		params.add("depYear",TIME_FORMATTER.print(search.getOutboundDate()));
		params.add("depMonth",MONTH_FORMATTER.print(search.getOutboundDate()));
		params.add("depDay",search.getOutboundDate().getDayOfMonth()+"");
		if (search.isRoundtrip()) {
			params.add("retYear",TIME_FORMATTER.print(search.getInboundDate()));
			params.add("retMonth",MONTH_FORMATTER.print(search.getInboundDate()));
			params.add("retDay",search.getInboundDate().getDayOfMonth()+"");
			params.add("journeySpan","RT");
		}else{
			params.add("journeySpan","OW");
		}
		params.add("departCity",search.getDepartureCode());
		params.add("departureTime",TIME_FORMATTER.print(search.getOutboundDate()));
		params.add("returnCity",search.getArrivalCode());
		if (search.isRoundtrip()) {
			params.add("returnTime",TIME_FORMATTER.print(search.getInboundDate()));
		}
		params.add("numAdults",search.getAdultsCountString());
		params.add("numChildren",search.getChildrenCountString());
		params.add("numInfants",search.getInfantsCountString());
		params.add("cabin","ECONOMY");
		params.add("promoCode","");
		params.add("x","54");
		params.add("y","9");
		
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
