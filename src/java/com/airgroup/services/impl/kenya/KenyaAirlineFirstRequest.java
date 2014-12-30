package com.airgroup.services.impl.kenya;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class KenyaAirlineFirstRequest extends FlightResponse {
	private static final String URL = "http://www.kenya-airways.com/kqr.aspx?";
	//"prc=book&TRIP_TYPE=R&B_LOCATION_1=FCO&E_LOCATION_1=MAD&CABIN=E&EXTERNAL_ID=PROD&SO_SITE_SHOW_MENU=FALSE&SO_SITE_EXTERNAL_ID_LIMIT=9&SO_LANG_DISABLE_X_XSS_PROTEC=TRUE&D_Day=25&D_Month=201309&R_Day=30&R_Month=201309&B_DATE_1=201309250000&B_ANY_TIME_1=TRUE&B_DATE_2=201309300000&B_ANY_TIME_2=TRUE&LANGUAGE=GB&SITE=BASMBASM&TRIP_FLOW=YES&DATE_RANGE_QUALIFIER_1=C&DATE_RANGE_QUALIFIER_2=C&TRAVELLER_TYPE_1=ADT&SO_SITE_OFFICE_ID=NYCKQ08BB&SO_SITE_ALLOW_LSA_INDICATOR=TRUE&SO_SITE_IS_INSURANCE_ENABLED=FALSE&SO_SITE_BOOL_ISSUE_ETKT=FALSE&SO_SITE_SIGNIN_METHOD=NOT_REQUIRED&SO_SITE_ALLOW_PROFILELESS=True&SO_SITE_EXT_PSPURL=https://book.kenya-airways.com/kqs/allsree.php/0e3dae5e-6d98-4091-bed9-066bc43ae617";
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMM");
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
		params.add("prc","book");
		if (search.isRoundtrip())
			params.add("TRIP_TYPE","R");
		else 
			params.add("TRIP_TYPE","O");
		
		params.add("B_LOCATION_1",search.getDepartureCode());
		params.add("E_LOCATION_1",search.getArrivalCode());
		params.add("CABIN","E");
		params.add("EXTERNAL_ID","PROD");
		params.add("SO_SITE_SHOW_MENU","FALSE");
		params.add("SO_SITE_EXTERNAL_ID_LIMIT","9");
		params.add("SO_LANG_DISABLE_X_XSS_PROTEC","TRUE");
		params.add("D_Day", "" + search.getOutboundDate().getDayOfMonth());
		params.add("D_Month", TIME_FORMATTER.print(search.getOutboundDate()));
		if (search.isRoundtrip()){
			params.add("R_Day","" + search.getInboundDate().getDayOfMonth());
			params.add("R_Month", TIME_FORMATTER.print(search.getInboundDate()));
		}
		else {
			params.add("R_Day","25");
			params.add("R_Month","201309");
			
		}
		params.add("B_DATE_1",TIME_FORMATTER_SPECIAL.print(search.getOutboundDate()) + "0000");
		params.add("B_ANY_TIME_1","TRUE");
		if (search.isRoundtrip())
			params.add("B_DATE_2", TIME_FORMATTER_SPECIAL.print(search.getInboundDate()) + "0000");
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
				params.add(newParam, "TRUE");
			}
		}
		
		params.add("SO_SITE_OFFICE_ID","NYCKQ08BB");
		params.add("SO_SITE_ALLOW_LSA_INDICATOR","TRUE");
		params.add("SO_SITE_IS_INSURANCE_ENABLED","FALSE");
		params.add("SO_SITE_BOOL_ISSUE_ETKT","FALSE");
		params.add("SO_SITE_SIGNIN_METHOD","NOT_REQUIRED");
		params.add("SO_SITE_ALLOW_PROFILELESS","True");
		if (search.isRoundtrip())
			params.add("SO_SITE_EXT_PSPURL","https://book.kenya-airways.com/kqs/allsree.php/0e3dae5e-6d98-4091-bed9-066bc43ae617");
		else params.add("SO_SITE_EXT_PSPURL", "https://book.kenya-airways.com/kqs/allsree.php/c269ba30-cf48-4495-9b2e-9aac26eb4d71");
		//SO_SITE_EXT_PSPURL	https://book.kenya-airways.com/kqs/allsree.php/c269ba30-cf48-4495-9b2e-9aac26eb4d71
		return params;
	}
}
