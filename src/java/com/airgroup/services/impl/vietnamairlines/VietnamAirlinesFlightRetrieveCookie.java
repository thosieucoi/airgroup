/**
 * 
 */
package com.airgroup.services.impl.vietnamairlines;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 *
 */
public class VietnamAirlinesFlightRetrieveCookie extends FlightResponse{
	private static final String RETRIEVE_COOKIE_URL = "https://wl-prod.sabresonicweb.com/SSW2010/B3QE/webqtrip.html";
	public String getDateTimeFormatterPattern() {
		return "yyyy-MM-dd";
	}

	@Override
	public RequestType getType() {
		return RequestType.GET;
	}

	@Override
	public String getURL() {
		return RETRIEVE_COOKIE_URL;
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params.add("searchType", "NORMAL");
		params.add("lang", "en");
		params.add("currency", "VND");
		params.add("journeySpan", search.isRoundtrip() ? "RT" : "OW");
		params.add("origin", search.getDepartureCode());
		params.add("destination", search.getArrivalCode());
		params.add("promoCode", "");
		params.add("departureDate", getDateTimeFormatter().print(search.getOutboundDate()));
		if (search.isRoundtrip()) {
			params.add("returnDate", getDateTimeFormatter().print(search.getInboundDate()));
		}
		params.add("alternativeLandingPage", "true");
		params.add("numAdults", search.getAdultsCountString());
		params.add("numChildren", search.getChildrenCountString());
		params.add("numInfants", search.getInfantsCountString());
		return params;
	}
}
