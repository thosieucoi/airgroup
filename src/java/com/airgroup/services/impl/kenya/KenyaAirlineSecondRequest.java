package com.airgroup.services.impl.kenya;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class KenyaAirlineSecondRequest extends FlightResponse {
	private static final String URL = "https://wftc2.e-travel.com/kenyaairways/Override.action";
	//private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMM");
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
		return URL;
	}

	@Override
	public String getHeaderValue() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public FluentStringsMap getParams(){
		FluentStringsMap params = new FluentStringsMap();
		if (search.isRoundtrip())
			params.add("TRIP_TYPE","R");
		else 
			params.add("TRIP_TYPE","O");
		params.add("B_LOCATION_1", search.getDepartureCode());
		params.add("E_LOCATION_1", search.getArrivalCode());
		params.add("CABIN","E");
		params.add("EXTERNAL_ID","PROD");
		params.add("SO_SITE_SHOW_MENU","FALSE");
		params.add("SO_SITE_EXTERNAL_ID_LIMIT","9");
		params.add("SO_LANG_DISABLE_X_XSS_PROTEC","TRUE");
		params.add("B_DATE_1",TIME_FORMATTER_SPECIAL.print(search.getOutboundDate()) + "0000");
		params.add("B_ANY_TIME_1","TRUE");
		if (search.isRoundtrip())
			params.add("B_DATE_2",TIME_FORMATTER_SPECIAL.print(search.getInboundDate()) + "0000");
		else 
			params.add("B_DATE_2","201309250000");
		params.add("B_ANY_TIME_2","TRUE");
		params.add("LANGUAGE","GB");
		params.add("SITE","BASMBASM");
		params.add("TRIP_FLOW","YES");
		params.add("DATE_RANGE_QUALIFIER_1","C");
		params.add("DATE_RANGE_QUALIFIER_2","C");
		
		//************** Set Parameters for Adults *********************************//
				int countPerson = 0;	// For counting number of persons in for this moving
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
				
				// *********** Set parameter for infants ********************************//
				if (search.getInfantsCount() > 0){
					//int countInfant = 0;
					String paramInfant = "HAS_INFANT_";
					for (int i = 1; i <= search.getInfantsCount();i++){
						String newParam = paramInfant + i;
						params.add(newParam, "true");
					}
				}
		
		params.add("SO_SITE_OFFICE_ID","NYCKQ08BB");
		params.add("SO_SITE_ALLOW_LSA_INDICATOR","TRUE");
		params.add("SO_SITE_IS_INSURANCE_ENABLED","FALSE");
		params.add("SO_SITE_BOOL_ISSUE_ETKT","FALSE");
		params.add("SO_SITE_SIGNIN_METHOD","NOT_REQUIRED");
		params.add("SO_SITE_ALLOW_PROFILELESS","True");
		if (search.isRoundtrip())
			params.add("SO_SITE_EXT_PSPURL","https://book.kenya-airways.com/kqs/allsree.php/b79e537d-3bcd-4628-9e2d-42518807a7d5");
		else 
			params.add("SO_SITE_EXT_PSPURL","https://book.kenya-airways.com/kqs/allsree.php/c269ba30-cf48-4495-9b2e-9aac26eb4d71");
		
		params.add("DATE_RANGE_VALUE_1","3");
		params.add("DATE_RANGE_VALUE_2","3");
		params.add("DISPLAY_TYPE","1");
		params.add("PRICING_TYPE","O");
		params.add("ARRANGE_BY","N");
		params.add("COMMERCIAL_FARE_FAMILY_1","CFFINT");
		params.add("SO_SITE_MOP_CREDIT_CARD","FALSE");
		params.add("SO_SITE_SIGNIN_METHOD","NOT_REQUIRED");
		params.add("SO_SITE_ALLOW_PROFILELESS","TRUE");
		params.add("SO_SITE_ET_CODE_SHARE","00");
		params.add("SO_SITE_ET_AIRLINE_CODE","00");
		params.add("SO_SITE_OFFICE_ID","NYCKQ08BB");
		if (search.isRoundtrip())
			params.add("SO_SITE_EXT_PSPURL","https://book.kenya-airways.com/kqs/allsree.php/96174f87-6d52-4947-9209-13e4420f8a66");
		else 
			params.add("SO_SITE_EXT_PSPURL", "https://book.kenya-airways.com/kqs/allsree.php/6dbe94b7-59aa-4715-b661-3d43a80cd25f");
		params.add("SO_SITE_ALLOW_MKP_DISCNT","False");
		params.add("SO_SITE_USE_SITE_FEE","");
		params.add("SO_SITE_FEE_TYPE","");
		params.add("SO_SITE_FEE_PERCENTAGE","");
		params.add("SO_SITE_FEE_AMOUNT","");
		params.add("SO_SITE_FEE_CURRENCY","");
		params.add("EMBEDDED_TRANSACTION","FlexPricerAvailability");
		//params.add("SO_GL/",/"<?xml version=/"1.0/" encoding=/"iso-8859-1/"?><SO_GL><GLOBAL_LIST mode=/"partial/"><NAME>SITE_SERVICE_FEE</NAME><LIST_ELEMENT><CODE>0</CODE><LIST_VALUE>0</LIST_VALUE> <LIST_VALUE>1</LIST_VALUE> <LIST_VALUE>10</LIST_VALUE> <LIST_VALUE>USD</LIST_VALUE> </LIST_ELEMENT></GLOBAL_LIST></SO_GL>");
		return params;
	}
}
