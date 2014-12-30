package com.airgroup.services.impl.airasia;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class AirAsiaRequest extends FlightResponse {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("dd");

	private static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormat
		.forPattern("yyyy-MM");

	@Override
	public String getDateTimeFormatterPattern() {
		return "MM/dd/yyyy";
	}

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return "http://booking.airasia.com/Search.aspx";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params.add("__EVENTTARGET", "");
		params.add("__EVENTARGUMENT", "");
		params.add("__VIEWSTATE", (String) search.getParamValue("viewState"));
		params.add("pageToken", "");
		params.add("MemberLoginSearchView$HFTimeZone", "420");
		params.add(
			"ControlGroupSearchView$AvailabilitySearchInputSearchView$RadioButtonMarketStructure",
			search.isRoundtrip() ? "RoundTrip" : "OneWay");
		params.add(
			"ControlGroupSearchView_AvailabilitySearchInputSearchVieworiginStation1",
			search.getDepartureCode());
		params.add(
			"ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketOrigin1",
			search.getDepartureCode());
		params.add(
			"ControlGroupSearchView_AvailabilitySearchInputSearchViewdestinationStation1",
			search.getArrivalCode());
		params.add(
			"ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketDestination1",
			search.getArrivalCode());
		params.add(
			"ControlGroupSearchView$MultiCurrencyConversionViewSearchView$DropDownListCurrency",
			"5028008745");
		params.add("date_picker", getDateTimeFormatter().print(search.getOutboundDate()));
		params.add("date_picker", "");
		params.add(
			"ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketDay1",
			DATE_FORMATTER.print(search.getOutboundDate()));
		params.add(
			"ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketMonth1",
			YEAR_MONTH_FORMATTER.print(search.getOutboundDate()));
		if (search.isRoundtrip()) {
			params.add("date_picker", getDateTimeFormatter().print(search.getInboundDate()));
			params.add("date_picker", "");
			params.add(
				"ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketDay2",
				DATE_FORMATTER.print(search.getInboundDate()));
			params
				.add(
					"ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketMonth2",
					YEAR_MONTH_FORMATTER.print(search.getInboundDate()));
		}
		params
			.add(
				"ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListPassengerType_ADT",
				search.getAdultsCountString());
		params
			.add(
				"ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListPassengerType_CHD",
				search.getChildrenCountString());
		params
			.add(
				"ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListPassengerType_INFANT",
				search.getInfantsCountString());
		params.add(
			"ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListSearchBy",
			"columnView");
		params.add("ControlGroupSearchView$ButtonSubmit", "Search");

		return params;
	}

}
