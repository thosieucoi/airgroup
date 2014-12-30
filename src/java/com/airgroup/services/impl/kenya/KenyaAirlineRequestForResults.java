package com.airgroup.services.impl.kenya;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class KenyaAirlineRequestForResults extends FlightResponse {
	
	private static final String URL = "https://wftc2.e-travel.com/plnext/kenyaairways/FlexPricerAvailabilityDispatcherPui.action;jsessionid=";
	private static final DateTimeFormatter TIME_FORMATTER_SPECIAL = DateTimeFormat.forPattern("yyyyMMdd");
	@Override
	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestType getType() {
		// TODO Auto-generated method stub
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return URL + search.getParamValue("KENYA_SESSION");
	}
	//https://wftc2.e-travel.com/plnext/kenyaairways/Fare.action;jsessionid=zGP7SQLD9Y8ylcRcRlPyJDVFvZPsBZJqpkMwfT7VZv02q1nJByLm!-746832317!1874343953
	@Override
	public String getHeaderValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public FluentStringsMap getParams(){
		FluentStringsMap params = new FluentStringsMap();
		params.add("B_LOCATION_1",search.getDepartureCode());
		params.add("DATE_RANGE_VALUE_2","0");
		params.add("DATE_RANGE_VALUE_1","0");
		params.add("E_LOCATION_1", search.getArrivalCode());
		
		int countPerson = 0;	// For counting number of adults and childrens in this moving
		String addedParams = "TRAVELLER_TYPE_";
		for (int i = 1; i <= search.getAdultsCount();i++){
			countPerson++;
			String newParam = addedParams + countPerson;
			params.add(newParam, "ADT");
		}
		
		// ************ Set parameter for Children ******************************** //
		if (search.getChildrenCount() > 0){
			for (int i = 1; i <= search.getChildrenCount();i++){
				countPerson++;
				String newParam = addedParams + countPerson;
				params.add(newParam, "CHD");
			}
		}
		
		
		params.add("DATE_RANGE_QUALIFIER_1","C");
		params.add("DATE_RANGE_QUALIFIER_2","C");
		
		// *********** Set parameter for infants ********************************//
		if (search.getInfantsCount() > 0){
			String paramInfant = "HAS_INFANT_";
			for(int i = 1; i <= search.getInfantsCount();i++){
				String newParam = paramInfant + i;
				params.add(newParam, "true");
			}
		}
		
		
		params.add("B_DATE_1",TIME_FORMATTER_SPECIAL.print(search.getOutboundDate()) + "0000");
		if (search.isRoundtrip())
			params.add("B_DATE_2",TIME_FORMATTER_SPECIAL.print(search.getInboundDate()) + "0000");
		else 
			params.add("B_DATE_2", "201309250000");
		params.add("SITE","BASMBASM");
		params.add("DISPLAY_TYPE","1");
		params.add("TRIP_FLOW","YES");
		if (search.isRoundtrip())
			params.add("TRIP_TYPE","R");		// If search for roundtrip
		else
			params.add("TRIP_TYPE","O");
		params.add("EXTERNAL_ID","PROD");
		params.add("OFFICE_ID","NYCKQ08BB");
		params.add("COMMERCIAL_FARE_FAMILY_1","CFFINT");
		params.add("PRICING_TYPE","O");
		params.add("B_ANY_TIME_1","TRUE");
		params.add("LANGUAGE","GB");
		params.add("ARRANGE_BY","N");
		params.add("CABIN","E");
		params.add("B_ANY_TIME_2","TRUE");
		params.add("PLTG_IS_UPSELL","true");
		params.add("PAGE_TICKET","0");
		return params;
	}
}
