package com.airgroup.services.impl.lufthansa;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class LufthansaFlightResponseFirst extends FlightResponse{
	private static final String URL ="http://book.lufthansa.com/pl/Lufthansa/wds/Override.action";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("yyyyMMddHHmm");
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
		
//		params.add("ALLOW_PROMO","Y");
//		params.add("ARRANGE_BY","ND");
//		params.add("BANNER","FALSE");
//		params.add("B_DATE_1",DATE_FORMATTER.print(search.getOutboundDate()));
//		params.add("B_DATE_2",DATE_FORMATTER.print(search.getInboundDate()));
//		params.add("B_LOCATION_1",search.getDepartureCode());
//		params.add("CABIN","E");
//		params.add("CABIN_PRIORITY_1","EB");
//		params.add("CABIN_PRIORITY_2","BFE");
//		params.add("CABIN_PRIORITY_3","FBE");
//		params.add("COOKIE_USER","TRUE");
//		params.add("DIRECT_NON_STOP","FALSE");
//		params.add("EMBEDDED_TRANSACTION","AirAvailability");
//		params.add("E_LOCATION_1",search.getArrivalCode());
//		for (int i = 1; i <= search.getInfantsCount(); i++) {
//			params.add("HAS_INFANT_"+i,"TRUE");
//		}
//		params.add("HAS_INFANT_1","TRUE");
//		params.add("INCLUDE_TAXES","TRUE");
//		params.add("LANGUAGE","GB");
//		params.add("PORTAL_SESSION","nxhmfA9kojSLdg2EHJEWsCu");
//		params.add("POS","us");
//		params.add("SECURE","FALSE");
//		params.add("SEVEN_DAY_SEARCH","TRUE");
//		params.add("SITE","5AHB5AHB");
//		params.add("SKIN","LH");
//		params.add("SO_GL","<?xml version='1.0' encoding='iso-8859-1'?><SO_GL><GLOBAL_LIST mode='global'><NAME>SITE_INSURANCE_PRODUCTS</NAME><LIST_ELEMENT><CODE>EGB</CODE><LIST_VALUE>LIRP</LIST_VALUE><LIST_VALUE>FAKE</LIST_VALUE><LIST_VALUE>N</LIST_VALUE><LIST_VALUE>N</LIST_VALUE><LIST_VALUE>Y</LIST_VALUE><LIST_VALUE>N</LIST_VALUE><LIST_VALUE>1</LIST_VALUE></LIST_ELEMENT></GLOBAL_LIST><GLOBAL_LIST><NAME>SITE_SERVICE_FEE</NAME><LIST_ELEMENT><CODE>0</CODE><LIST_VALUE>1</LIST_VALUE><LIST_VALUE>1</LIST_VALUE><LIST_VALUE>27</LIST_VALUE><LIST_VALUE>GBP</LIST_VALUE><LIST_VALUE> </LIST_VALUE><LIST_VALUE> </LIST_VALUE><LIST_VALUE>3</LIST_VALUE></LIST_ELEMENT></GLOBAL_LIST><GLOBAL_LIST><NAME>SITE_QUEUE_DEFINITION_LIST</NAME><LIST_ELEMENT><CODE>AAAAAAA</CODE><LIST_VALUE>CAN</LIST_VALUE><LIST_VALUE>LONLH08AB</LIST_VALUE><LIST_VALUE>8</LIST_VALUE><LIST_VALUE>1</LIST_VALUE><LIST_VALUE></LIST_VALUE></LIST_ELEMENT></GLOBAL_LIST><GLOBAL_LIST mode='partial'><NAME>SITE_LIST_OB_FEE_MOP_APPLICATION_RULE</NAME><LIST_ELEMENT><CODE>PPAL</CODE><LIST_VALUE>FCA</LIST_VALUE><LIST_VALUE>Y</LIST_VALUE><LIST_VALUE>N</LIST_VALUE></LIST_ELEMENT></GLOBAL_LIST><GLOBAL_LIST><NAME>SITE_LIST_OB_FEE_CODE_TO_EXEMPT</NAME><LIST_ELEMENT><CODE>FCA</CODE><LIST_VALUE>FCA</LIST_VALUE></LIST_ELEMENT></GLOBAL_LIST></SO_GL>");
//		params.add("SO_SITE_AA_MIN_DUR_ON_HOLD","D02");
//		params.add("SO_SITE_ADDR_DELIVERY_FMT","ADDR:%M,%Y,%A,%B,%C,%Z,%N");
//		params.add("SO_SITE_ALLOW_CCS_ON_FARE","FALSE");
//		params.add("SO_SITE_ALLOW_CITP_COMMANDS","TRUE");
//		params.add("SO_SITE_ALLOW_ON_HOLD_CANCEL","TRUE");
//		params.add("SO_SITE_ALLOW_PNR_REPRICE","FALSE");
//		params.add("SO_SITE_ALLOW_THRD_BK_PLESS","TRUE");
//		params.add("SO_SITE_ALLOW_TSC_ON_FARE","TRUE");
//		params.add("SO_SITE_BOOL_ISSUE_ETKT","TRUE");
//		params.add("SO_SITE_BOOL_RK_ETKT_FAIL","TRUE");
//		params.add("SO_SITE_CHK_BILLING_ADDRESS","TRUE");
//		params.add("SO_SITE_COUNTRY_OF_RESIDENCE","US");
//		params.add("SO_SITE_CTRY_OF_COMMENCEMENT","GB");
//		params.add("SO_SITE_DELIVERY_AS_BILLING","FALSE");
//		params.add("SO_SITE_DISABLE_PREBOOKING","FALSE");
//		params.add("SO_SITE_DISP_OBFEES_DETAIL","TRUE");
//		params.add("SO_SITE_ETIX_ALLOW","TRUE");
//		params.add("SO_SITE_ETIX_MIN_TIME","4");
//		params.add("SO_SITE_ETIX_PLUS_TBM_ALLOW","FALSE");
//		params.add("SO_SITE_ETIX_QUEUE_CATEGORY","88");
//		params.add("SO_SITE_ETIX_QUEUE_ID","60");
//		params.add("SO_SITE_ETIX_QUEUE_OFFICE_ID","LONLH08AB");
//		params.add("SO_SITE_ETKT_Q_AND_CAT","60C88");
//		params.add("SO_SITE_ETKT_Q_OFFICE_ID","LONLH08AB");
//		params.add("SO_SITE_FAKE_CANCEL","TRUE");
//		params.add("SO_SITE_FP_AGT_NUMBER","73001533");
//		params.add("SO_SITE_FP_WITHHOLD_TAXES","FALSE");
//		params.add("SO_SITE_LH_FRONTEND_URL","www.lufthansa.com");
//		params.add("SO_SITE_LH_OSI_GLOBAL_PREFIX","IFW");
//		params.add("SO_SITE_MANUAL_ETKT_CMD","TTP");
//		params.add("SO_SITE_MOP_DD","FALSE");
//		params.add("SO_SITE_MOP_EXT_WITH_PRECALL","TRUE");
//		params.add("SO_SITE_MOP_PPAL","TRUE");
//		params.add("SO_SITE_OB_FEES_ENABLED","TRUE");
//		params.add("SO_SITE_OFFICE_ID","LONLH08AA");
//		params.add("SO_SITE_OH_FULLF_BUFFER","D01");
//		params.add("SO_SITE_OH_STATUS_RK_TAG","<OH_SF_AMOUNT><OH_SF_CURRENCY>/");
//		params.add("SO_SITE_OH_USE_AIRLTD_BUF","FALSE");
//		params.add("SO_SITE_OH_USE_BKDATE_BUF","TRUE");
//		params.add("SO_SITE_ONLINE_INDICATOR","TRUE");
//		params.add("SO_SITE_PAY_LATER_GUARANTEE","TRUE");
//		params.add("SO_SITE_PENDING_TIME_LIMIT","D02");
//		params.add("SO_SITE_PENDING_TKT_TYPE","AUTO_QUEUE");
//		params.add("SO_SITE_PENDING_TL_IS_LTD","N");
//		params.add("SO_SITE_PREFERRED_CARRIER","000000");
//		params.add("SO_SITE_QUEUE_SUCCESS_ETKT","FALSE");
//		params.add("SO_SITE_STATUS_RK_TAG","STATUS:ON HOLD/FEE");
//		params.add("SO_SITE_TBM_ALLOW","FALSE");
//		params.add("SO_SITE_TOD_ALLOW","FALSE");
//		params.add("SO_SITE_USE_CREDIT_CARD_FEE","FALSE");
//		params.add("SO_SITE_USE_MIN_DUR_BD_DD","FALSE");
//		params.add("SO_SITE_USE_OH_FULLF_BUFFER","TRUE");
//		params.add("SO_SITE_USE_PENDING_TRIPS","TRUE");
//		params.add("SO_SITE_USE_TKT_SERVICE_FEE","FALSE");
//		int j =0;
//		for (int i = 1; i <= search.getAdultsCount(); i++) {
//			params.add("TRAVELLER_TYPE_"+i,"ADT");
//			j=i;
//		}
//		for (int i = 1; i <= search.getChildrenCount(); i++) {
//			params.add("TRAVELLER_TYPE_"+(i+j),"CHD");
//		}
//
//		if (search.isRoundtrip()) {
//			params.add("TRIP_TYPE","R");
//		}else{
//			params.add("TRIP_TYPE","O");
//		}
//		params.add("URL_FLOWCONTROL","/online/portal/lh/us/booking?l=en&cid=1000390&action=CallbackHandler&command=callback&view=flight");
//		params.add("WDS_3DS_DATE_ACTIVATION","72");
//		params.add("WDS_ALLOWED_CC_TYPES","VI:CA:DC:AX:JC:TP");
//		params.add("WDS_ALLOW_TPB","TRUE");
//		params.add("WDS_APPLY_CCS_FOR_PAYPAL","TRUE");
//		params.add("WDS_DISPLAY_BANNERS","TRUE");
//		params.add("WDS_DISPLAY_MM_CC_INFO","FALSE");
//		params.add("WDS_FOP_SEQUENCE","SE");
//		params.add("WDS_OFFER_RAILFLY","TRUE");
//		params.add("WDS_ONHOLD_EXEMPT","UA:TG:LX:OS:SK:EY:TK:A3:AC:AI:BD:CA:EN:ET:FM:JJ:JK:JP:KF:KM:LG:LO:MS:NH:NZ:OU:QR:SA:SK:SN:SQ:TA:US:VO:XQ:XG:QF:PR:AV:PU:CX:CA:KA:MU:CI:CM:OZ:AS:CO:JP:AM:4U:DE:VF:9W:BL:3K:JQ:MI:AR:H2:PZ:R7:S3:S2:4X:4Z:ZH:PG:BA:MH:BR:GU:UT:UG:AT:JZ:TP:QI:JU:YM:GF:QR");
//		params.add("WDS_USER_NL_PARAM","SHOW");
//		params.add("WDS_WR_AREA","NYC");
//		params.add("WDS_WR_BFT","IK");
//		params.add("WDS_WR_CHANNEL","LHCOM");
//		params.add("WDS_WR_DCSEXT_SCENARIO","6");
//		params.add("WDS_WR_DCSID","dcsi8dl8n100000kbqfxzervo_7i6g");
//		params.add("WDS_WR_LANG","en");
//		params.add("WDS_WR_MARKET","US");
//		params.add("WDS_WR_SCENARIO","scenario6");
		
		params.add("ALLOW_PROMO","Y");
		params.add("ARRANGE_BY","ND");
		params.add("BANNER","FALSE");
		params.add("B_DATE_1",DATE_FORMATTER.print(search.getOutboundDate()));
		params.add("B_DATE_2",DATE_FORMATTER.print(search.getInboundDate()));
		params.add("B_LOCATION_1",search.getDepartureCode());
		params.add("CABIN","E");
		params.add("CABIN_PRIORITY_1","ERB");
		params.add("CABIN_PRIORITY_2","BFER");
		params.add("CABIN_PRIORITY_3","FBER");
		params.add("COOKIE_USER","TRUE");
		params.add("DIRECT_NON_STOP","FALSE");
		params.add("EMBEDDED_TRANSACTION","AirAvailability");
		params.add("E_LOCATION_1",search.getArrivalCode());
		if (search.getAdultsCount()>0) {
			for (int i = 1; i <= search.getInfantsCount(); i++) {
				params.add("HAS_INFANT_"+i,"TRUE");
			}
		}else{
			params.add("HAS_INFANT_1","FALSE");
		}
		params.add("INCLUDE_TAXES","TRUE");
		params.add("LANGUAGE","GB");
		params.add("PORTAL_SESSION","8eU2uHnWFqkpWl_8Klpq7iL");
		params.add("POS","us");
		params.add("SECURE","FALSE");
		params.add("SEVEN_DAY_SEARCH","TRUE");
		params.add("SITE","5AHB5AHB");
		params.add("SKIN","LH");
		params.add("SO_GL","<?xml version='1.0' encoding='iso-8859-1'?><SO_GL><GLOBAL_LIST mode='partial'><NAME>SITE_SITE_FARE_COMMANDS_AND_OPTIONS</NAME><LIST_ELEMENT><CODE>4</CODE><LIST_VALUE>1</LIST_VALUE><LIST_VALUE>2</LIST_VALUE><LIST_VALUE>-2</LIST_VALUE><LIST_VALUE>195585</LIST_VALUE><LIST_VALUE>2</LIST_VALUE><LIST_VALUE></LIST_VALUE><LIST_VALUE></LIST_VALUE><LIST_VALUE></LIST_VALUE><LIST_VALUE></LIST_VALUE><LIST_VALUE></LIST_VALUE><LIST_VALUE>TRUE</LIST_VALUE></LIST_ELEMENT><LIST_ELEMENT><CODE>5</CODE><LIST_VALUE>1</LIST_VALUE><LIST_VALUE>2</LIST_VALUE><LIST_VALUE>-2</LIST_VALUE><LIST_VALUE>195585</LIST_VALUE><LIST_VALUE>2</LIST_VALUE><LIST_VALUE></LIST_VALUE><LIST_VALUE></LIST_VALUE><LIST_VALUE></LIST_VALUE><LIST_VALUE></LIST_VALUE><LIST_VALUE></LIST_VALUE><LIST_VALUE>TRUE</LIST_VALUE></LIST_ELEMENT></GLOBAL_LIST><GLOBAL_LIST><NAME>SITE_LIST_OB_FEE_CODE_TO_EXEMPT</NAME><LIST_ELEMENT><CODE>FCA</CODE><LIST_VALUE>FCA</LIST_VALUE></LIST_ELEMENT><LIST_ELEMENT><CODE>T01</CODE><LIST_VALUE>T01</LIST_VALUE></LIST_ELEMENT></GLOBAL_LIST></SO_GL>");
		params.add("SO_SITE_ADDR_DELIVERY_FMT","ADDR: %M,%Y,%A,%B%P,%Z %C,%N");
		params.add("SO_SITE_ALLOW_CCS_ON_FARE","FALSE");
		params.add("SO_SITE_ALLOW_CITP_COMMANDS","TRUE");
		params.add("SO_SITE_ALLOW_THRD_BK_PLESS","TRUE");
		params.add("SO_SITE_ALLOW_TSC_ON_FARE","TRUE");
		params.add("SO_SITE_BOOL_ISSUE_ETKT","TRUE");
		params.add("SO_SITE_BOOL_RK_ETKT_FAIL","FALSE");
		params.add("SO_SITE_CHK_BILLING_ADDRESS","TRUE");
		params.add("SO_SITE_COUNTRY_OF_RESIDENCE","US");
		params.add("SO_SITE_CTRY_OF_COMMENCEMENT","VN");
		params.add("SO_SITE_DELIVERY_AS_BILLING","FALSE");
		params.add("SO_SITE_DISABLE_PREBOOKING","FALSE");
		params.add("SO_SITE_DISP_OBFEES_DETAIL","FALSE");
		params.add("SO_SITE_ETIX_ALLOW","TRUE");
		params.add("SO_SITE_ETIX_MIN_TIME","4");
		params.add("SO_SITE_ETIX_PLUS_TBM_ALLOW","FALSE");
		params.add("SO_SITE_ETIX_QUEUE_CATEGORY","88");
		params.add("SO_SITE_ETIX_QUEUE_ID","60");
		params.add("SO_SITE_ETIX_QUEUE_OFFICE_ID","SGNLH08AB");
		params.add("SO_SITE_ETKT_Q_AND_CAT","60C88");
		params.add("SO_SITE_ETKT_Q_OFFICE_ID","SGNLH08AB");
		params.add("SO_SITE_FP_AGT_NUMBER","73001533");
		params.add("SO_SITE_FP_WITHHOLD_TAXES","FALSE");
		params.add("SO_SITE_IS_OH_PARAM_DRIVEN","FALSE");
		params.add("SO_SITE_LH_FRONTEND_URL","www.lufthansa.com");
		params.add("SO_SITE_LH_OSI_GLOBAL_PREFIX","IFW");
		params.add("SO_SITE_MANUAL_ETKT_CMD","TTP/ET");
		params.add("SO_SITE_MOP_DD","FALSE");
		params.add("SO_SITE_MOP_EXT_WITH_PRECALL","FALSE");
		params.add("SO_SITE_MOP_OFFLINE","FALSE");
		params.add("SO_SITE_MOP_PPAL","TRUE");
		params.add("SO_SITE_OB_FEES_ENABLED","TRUE");
		params.add("SO_SITE_OFFICE_ID","SGNLH08AA");
		params.add("SO_SITE_ONLINE_INDICATOR","TRUE");
		params.add("SO_SITE_PREFERRED_CARRIER","000000");
		params.add("SO_SITE_QUEUE_SUCCESS_ETKT","FALSE");
		params.add("SO_SITE_TBM_ALLOW","FALSE");
		params.add("SO_SITE_TOD_ALLOW","FALSE");
		params.add("SO_SITE_USE_CREDIT_CARD_FEE","FALSE");
		params.add("SO_SITE_USE_TKT_SERVICE_FEE","FALSE");
		int j =0;
		for (int i = 1; i <= search.getAdultsCount(); i++) {
			params.add("TRAVELLER_TYPE_"+i,"ADT");
			j=i;
		}
		for (int i = 1; i <= search.getChildrenCount(); i++) {
			params.add("TRAVELLER_TYPE_"+(i+j),"CHD");
		}

		if (search.isRoundtrip()) {
			params.add("TRIP_TYPE","R");
		}else{
			params.add("TRIP_TYPE","O");
		}
//		params.add("TRAVELLER_TYPE_1","ADT");
//		params.add("TRIP_TYPE","R");
		params.add("URL_FLOWCONTROL","/online/portal/lh/us/booking?l=en&cid=1000390&action=CallbackHandler&command=callback&view=flight");
		params.add("WDS_3DS_DATE_ACTIVATION","72");
		params.add("WDS_ALLOWED_CC_TYPES","VI:CA:DC:AX:JC:TP");
		params.add("WDS_ALLOW_TPB","TRUE");
		params.add("WDS_DISPLAY_BANNERS","TRUE");
		params.add("WDS_DISPLAY_MM_CC_INFO","FALSE");
		params.add("WDS_FOP_SEQUENCE","SE");
		params.add("WDS_OFFER_RAILFLY","TRUE");
		params.add("WDS_USER_NL_PARAM","SHOW");
		params.add("WDS_WR_AREA","NYC");
		params.add("WDS_WR_BFT","IK");
		params.add("WDS_WR_CHANNEL","LHCOM");
		params.add("WDS_WR_DCSEXT_SCENARIO","6");
		params.add("WDS_WR_DCSID","dcsi8dl8n100000kbqfxzervo_7i6g");
		params.add("WDS_WR_LANG","en");
		params.add("WDS_WR_MARKET","US");
		params.add("WDS_WR_SCENARIO","scenario6");
		return params;
	}

	public String getHeaderValue() {
		return null;
	}

	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
