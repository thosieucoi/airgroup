package com.airgroup.services.impl.singaporeair;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class SingaporeAirlinesProcess extends FlightResponse {

	@Override
	public String getDateTimeFormatterPattern() {
		return "yyyy-MM-dd";
	}

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return "https://abacuswebstart.abacus.com.sg/khang-vuong/flight-search-process.aspx";
	}

	@Override
	public String getHeaderValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params.add("adult", search.getAdultsCountString());
		params.add("child", search.getChildrenCountString());
		params.add("class", "Y");
		params.add("departureDate1", getDateTimeFormatter().print(search.getOutboundDate()) +
										" 09:01:00");
		params.add("departureDate2", getDateTimeFormatter().print(search.getInboundDate()) +
										" 09:01:00");
		params.add("flightType", "2");
		params.add("from1", search.getDepartureCode());
		params.add("to1", search.getArrivalCode());

		if (search.isRoundtrip()) {
			params.add("from2", search.getArrivalCode());
			params.add("to2", search.getDepartureCode());
		}

		params.add("infant", search.getInfantsCountString());
		params.add("labour", "0");
		params.add("prefAirline", ",Singapore Airlines (SQ)");
		params.add("seaman", "0");
		params.add("seniorCitizen", "0");
		params.add("student", "0");
		params.add("tripType", search.isRoundtrip() ? "2" : "1");

		return params;
	}
}
