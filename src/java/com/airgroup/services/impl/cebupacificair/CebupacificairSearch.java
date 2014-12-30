/**
 * 
 */
package com.airgroup.services.impl.cebupacificair;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author hiepnt
 * 
 */
public class CebupacificairSearch extends FlightResponse {

	private final static String URL = "http://book.cebupacificair.com/Search.aspx";
	private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormat
		.forPattern("yyyy-MM");
	private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormat.forPattern("dd");


	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return "yyyy-MM-dd";
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		params.add("__EVENTTARGET","");
		params.add("__EVENTARGUMENT","");
		params.add("__VIEWSTATE","/wEPDwUBMGRkZ0I3jKvfnhKfx57f3Wjr7p2WhvU=");
		params.add("pageToken","");
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$RadioButtonMarketStructure",search.isRoundtrip() ? "RoundTrip" : "OneWay");
		params.add("ControlGroupSearchView_AvailabilitySearchInputSearchVieworiginStation1",search.getDepartureCode());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketOrigin1",search.getDepartureCode());
		params.add("ControlGroupSearchView_AvailabilitySearchInputSearchViewdestinationStation1",search.getArrivalCode());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketDestination1",search.getArrivalCode());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketDay1",DAY_FORMATTER.print(search.getOutboundDate()));
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketMonth1",MONTH_YEAR_FORMATTER.print(search.getOutboundDate()));
		params.add("date_picker",getDateTimeFormatter().print(search.getOutboundDate()));
		if(search.isRoundtrip()){
			params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketDay2",DAY_FORMATTER.print(search.getInboundDate()));
			params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketMonth2",MONTH_YEAR_FORMATTER.print(search.getInboundDate()));
			params.add("date_picker",getDateTimeFormatter().print(search.getInboundDate()));
		}
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListPassengerType_ADT",search.getAdultsCountString());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListPassengerType_CHD",search.getChildrenCountString());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListPassengerType_INFANT",search.getInfantsCountString());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$promoCodeID","");
		params.add("ControlGroupSearchView$ButtonSubmit","");
		
		return params;
	}
}


