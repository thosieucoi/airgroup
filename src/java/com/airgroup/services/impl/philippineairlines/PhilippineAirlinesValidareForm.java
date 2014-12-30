/**
 * 
 */
package com.airgroup.services.impl.philippineairlines;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 * 
 */
public class PhilippineAirlinesValidareForm extends FlightResponse {

	private static final String URL = "https://onlinebooking.philippineairlines.com/flypal/ValidateFormAction.do";
	protected static final String JSESSIONID = "PHILIPINES_JSESSIONID";
	private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormat.forPattern("MM");
	private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormat.forPattern("dd");

	public String getDateTimeFormatterPattern() {
		return "MM/dd/yyyy";
	}

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return URL;
	}

	@Override
	public String getHeaderValue() {
		return (String) search.getParamValue(JSESSIONID);
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		params.add("validateAction", "AirLowFareSearch");
		params.add("searchContext", "AIR_SEARCH_ADVANCED");
		params.add("tripType", search.isRoundtrip() ? "RT" : "OW");
		params.add("redemption", "false");
		params.add("outboundOption.originLocationCode", search.getDepartureCode());
		params.add("outboundOption.originLocationType", "A");
		params.add(
			"outboundOption.departureDate",
			getDateTimeFormatter().print(search.getOutboundDate()));
		params
			.add("outboundOption.departureMonth", MONTH_FORMATTER.print(search.getOutboundDate()));
		params.add("outboundOption.departureDay", DAY_FORMATTER.print(search.getOutboundDate()));
		params.add(
			"outboundOption.departureYear",
			String.valueOf(search.getOutboundDate().getYear()));
		params.add("outboundOption.departureTime", "NA");
		params.add("outboundOption.destinationLocationCode", search.getArrivalCode());
		params.add("outboundOption.destinationLocationType", "A");
		if(search.isRoundtrip()){
			params.add(
				"inboundOption.departureDate",
				getDateTimeFormatter().print(search.getInboundDate()));
			params.add("inboundOption.departureMonth", MONTH_FORMATTER.print(search.getInboundDate()));
			params.add("inboundOption.departureDay", DAY_FORMATTER.print(search.getOutboundDate()));
			params
				.add("inboundOption.departureYear", String.valueOf(search.getInboundDate().getYear()));
			params.add("inboundOption.departureTime", "NA");
			params.add("inboundOption.originLocationCode", search.getArrivalCode());
			params.add("inboundOption.originLocationType", "A");
			params.add("inboundOption.destinationLocationCode", search.getDepartureCode());
			params.add("inboundOption.destinationLocationType", "A");
		}
		params.add("multiCityOptions[0].originLocationName", "");
		params.add("multiCityOptions[0].originLocationCode", "");
		params.add("multiCityOptions[0].originLocationType", "");
		params.add("multiCityOptions[0].destinationLocationName", "");
		params.add("multiCityOptions[0].destinationLocationCode", "");
		params.add("multiCityOptions[0].destinationLocationType", "");
		params.add("multiCityOptions[0].departureDate", "");
		params.add("multiCityOptions[0].departureMonth", "");
		params.add("multiCityOptions[0].departureDay", "");
		params.add("multiCityOptions[0].departureYear", "");
		params.add("multiCityFirstLeg", "");
		params.add("multiCityOptions[0].departureTime", "NA");
		params.add("multiCityOptions[1].originLocationName", "");
		params.add("multiCityOptions[1].originLocationCode", "");
		params.add("multiCityOptions[1].originLocationType", "");
		params.add("multiCityOptions[1].destinationLocationName", "");
		params.add("multiCityOptions[1].destinationLocationCode", "");
		params.add("multiCityOptions[1].destinationLocationType", "");
		params.add("multiCityOptions[1].departureDate", "");
		params.add("multiCityOptions[1].departureMonth", "");
		params.add("multiCityOptions[1].departureDay", "");
		params.add("multiCityOptions[1].departureYear", "");
		params.add("multiCityOptions[1].departureTime", "NA");
		params.add("multiCityOptions[2].originLocationName", "");
		params.add("multiCityOptions[2].originLocationCode", "");
		params.add("multiCityOptions[2].originLocationType", "");
		params.add("multiCityOptions[2].destinationLocationName", "");
		params.add("multiCityOptions[2].destinationLocationCode", "");
		params.add("multiCityOptions[2].destinationLocationType", "");
		params.add("multiCityOptions[2].departureDate", "");
		params.add("multiCityOptions[2].departureMonth", "");
		params.add("multiCityOptions[2].departureDay", "");
		params.add("multiCityOptions[2].departureYear", "");
		params.add("multiCityOptions[2].departureTime", "NA");
		params.add("multiCityOptions[3].originLocationName", "");
		params.add("multiCityOptions[3].originLocationCode", "");
		params.add("multiCityOptions[3].originLocationType", "");
		params.add("multiCityOptions[3].destinationLocationName", "");
		params.add("multiCityOptions[3].destinationLocationCode", "");
		params.add("multiCityOptions[3].destinationLocationType", "");
		params.add("multiCityOptions[3].departureDate", "");
		params.add("multiCityOptions[3].departureMonth", "");
		params.add("multiCityOptions[3].departureDay", "");
		params.add("multiCityOptions[3].departureYear", "");
		params.add("multiCityOptions[3].departureTime", "NA");
		params.add("multiCityOptions[4].originLocationName", "");
		params.add("multiCityOptions[4].originLocationCode", "");
		params.add("multiCityOptions[4].originLocationType", "");
		params.add("multiCityOptions[4].destinationLocationName", "");
		params.add("multiCityOptions[4].destinationLocationCode", "");
		params.add("multiCityOptions[4].destinationLocationType", "");
		params.add("multiCityOptions[4].departureDate", "");
		params.add("multiCityOptions[4].departureMonth", "");
		params.add("multiCityOptions[4].departureDay", "");
		params.add("multiCityOptions[4].departureYear", "");
		params.add("multiCityOptions[4].departureTime", "NA");
		params.add("flexibleSearch", "false");
		params.add("cabinClass", "FIESTADEAL");
		params.add("directFlightsOnly", "false");
		params.add("guestTypes[0].amount", search.getAdultsCountString());
		params.add("guestTypes[0].type", "ADT");
		params.add("guestTypes[1].amount", search.getChildrenCountString());
		params.add("guestTypes[1].type", "CNN");
		params.add("guestTypes[2].amount", "0");
		params.add("guestTypes[2].type", "INF");
		params.add("showUMNRInstructions", "true");
		params.add("searchType", "FARE");
		params.add("moneyToMilesRatio", "");
		params.add("urlHome", "");
		params.add("urlLogoff", "");
		params.add("urlRedemption", "");
		params.add("vsessionid", "");
		return params;
	}
}
