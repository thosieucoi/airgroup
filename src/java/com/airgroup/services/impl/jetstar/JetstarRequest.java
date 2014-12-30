/**
 * 
 */
package com.airgroup.services.impl.jetstar;


import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

/**
 * @author linhnd1
 *
 */
public class JetstarRequest extends FlightResponse{
	private static final String URL = "http://booknow.jetstar.com/Search.aspx";
	
	public String getDateTimeFormatterPattern() {
		return "dd/MM/yyyy";
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
		return null;
	}
	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		params.add("__EVENTTARGET","");
		params.add("__EVENTARGUMENT","");
		params.add("pageToken","");
		params.add("total_price","");
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$RadioButtonMarketStructure",search.isRoundtrip() ? "RoundTrip":"OneWay");
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketOrigin1",search.getDepartureCode());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketDestination1",search.getArrivalCode());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextboxDepartureDate1",getDateTimeFormatter().print(search.getOutboundDate()));
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextboxDestinationDate1",getDateTimeFormatter().print(search.getInboundDate()));
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListCurrency","VND");
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketOrigin2",search.getArrivalCode());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketDestination2",search.getDepartureCode());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextboxDepartureDate2",getDateTimeFormatter().print(search.getInboundDate()));
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListPassengerType_ADT",search.getAdultsCountString());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListPassengerType_CHD",search.getChildrenCountString());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListPassengerType_INFANT",search.getInfantsCountString());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$RadioButtonSearchBy","SearchStandard");
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMultiCityOrigin1","Origin");
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMultiCityDestination1","Destination");
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextboxDepartureMultiDate1","");
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMultiCityOrigin2","Origin");
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMultiCityDestination2","Destination");
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextboxDepartureMultiDate2","");
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMultiPassengerType_ADT",search.getAdultsCountString());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMultiPassengerType_CHD",search.getChildrenCountString());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMultiPassengerType_INFANT",search.getInfantsCountString());
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$numberTrips","2");
		params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$ButtonSubmit","");
		return params;
	}
}

//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListCurrency",search.getCurrencyCode());
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListFareTypes","I");
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketDay1",String.valueOf(search.getOutboundDate().getDayOfMonth()));
//if(search.isRoundtrip()){
//	params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketDay2",String.valueOf(search.getInboundDate().getDayOfMonth()));
//	params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketMonth2",getDateTimeFormatter().print(search.getInboundDate()));
//}
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketDay3","");
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketMonth1",getDateTimeFormatter().print(search.getOutboundDate()));
//
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListMarketMonth3","");
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListPassengerType_ADT",search.getAdultsCountString());
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListPassengerType_CHD",search.getChildrenCountString());
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$DropDownListPassengerType_INFANT","0");
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$RadioButtonMarketStructure",search.isRoundtrip() ? "RoundTrip":"OneWay");
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketDestination1",search.getArrivalCode());
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketDestination2","");
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketDestination3","");
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketOrigin1",search.getDepartureCode());
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketOrigin2","");
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$TextBoxMarketOrigin3","");
//params.add("ControlGroupSearchView$ButtonSubmit","");
//params.add("__VIEWSTATE","/wEPDwUBMGRkuk23y/c8bQS+tyzOftjdNwPRLxU=");
//params.add("culture","vi-VN");
//params.add("date_picker","");
//params.add("go-booking","");
//params.add("pageToken","sLkmnwXwAsY=");
//params.add("ControlGroupSearchView$AvailabilitySearchInputSearchView$fromCS","yes");