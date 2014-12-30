package com.airgroup.services.impl.united;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;
import com.airgroup.util.Const;

public class UnitedRequest extends FlightResponse {

	private static final String ONEWAY_URL = "http://www.united.com/web/en-US/apps/booking/flight/searchOW.aspx?CS=N";

	private static final String ROUNDTRIP_URL = "http://www.united.com/web/en-US/apps/booking/flight/searchRT.aspx?CS=N";

	protected static final String HDNSERVER = "hdnServer";
	protected static final String HDNSID = "hdnSid";
	protected static final String HDNCLIENT = "hdnClient";
	protected static final String HDNTIMING = "hdnTiming";

	@Override
	public String getDateTimeFormatterPattern() {
		return "M/d/yyyy";
	}

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		if (search.isOneway()) {
			return ONEWAY_URL;
		} else {
			return ROUNDTRIP_URL;
		}
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
		params.add("__LASTFOCUS", "");
		params.add("__VIEWSTATE", (String) search.getParamValue(Const.VIEWSTATE));
		params.add("ctl00$CustomerHeader$ddlCountries", "US");
		params.add("ctl00$CustomerHeader$rdlang", "en-us");
		params.add("ctl00$CustomerHeader$chkSave", "on");
		params.add("ctl00$CustomerHeader$countryText", "");
		params.add("ctl00$CustomerHeader$langText", "");
		params.add(
			"ctl00$ContentInfo$SearchForm$TripTypes",
			search.isRoundtrip() ? "rdoRT" : "rdoOW");

		// Outbound
		params.add(
			"ctl00$ContentInfo$SearchForm$Airports1$Origin$txtOrigin",
			search.getDepartureCode());
		params.add("ctl00$ContentInfo$SearchForm$Airports1$Origin$TBWE01_ClientState", "");
		params.add(
			"ctl00$ContentInfo$SearchForm$Airports1$Destination$txtDestination",
			search.getArrivalCode());
		params.add("ctl00$ContentInfo$SearchForm$Airports1$Destination$TBWE01_ClientState", "");
		params.add("ctl00$ContentInfo$SearchForm$Airports1$NearbyAirOrigin$cboMiles", "150");
		params.add("ctl00$ContentInfo$SearchForm$Airports1$NearbyAirDestination$cboMiles", "150");
		params.add("ctl00$ContentInfo$SearchForm$DateTimeCabin1$DateFlex", "rdoDateSpecific");
		params.add(
			"ctl00$ContentInfo$SearchForm$DateTimeCabin1$Depdate$txtDptDate",
			getDateTimeFormatter().print(search.getOutboundDate()));
		params.add("ctl00$ContentInfo$SearchForm$DateTimeCabin1$Deptime$cboDptTime", " ");
		params.add(
			"ctl00$ContentInfo$SearchForm$DateTimeCabin1$Depdate1$txtDptDate",
			getDateTimeFormatter().print(search.getOutboundDate()));
		params.add("ctl00$ContentInfo$SearchForm$DateTimeCabin1$Cabins$cboCabin", "Coach");

		// Inbound
		if (search.isRoundtrip()) {
			params.add(
				"ctl00$ContentInfo$SearchForm$DateTimeCabin1$LengthOfStay$cboLengthOfStay",
				"0");
			params.add(
				"ctl00$ContentInfo$SearchForm$ReturndateTimeCabin$Retdate$txtRetDate",
				getDateTimeFormatter().print(search.getInboundDate()));
			params.add("ctl00$ContentInfo$SearchForm$ReturndateTimeCabin$Rettime$cboDptTime", " ");
			params.add("ctl00$ContentInfo$SearchForm$ReturndateTimeCabin$Cabins$cboCabin", "Coach");
		}

		params.add(
			"ctl00$ContentInfo$SearchForm$paxSelection$Adults$cboAdults",
			search.getAdultsCountString());
		params.add("ctl00$ContentInfo$SearchForm$paxSelection$Childtwelve$cboChildTwelve", "0");
		params.add("ctl00$ContentInfo$SearchForm$paxSelection$Infantseat$cboInfant", "0");
		params.add("ctl00$ContentInfo$SearchForm$paxSelection$Seniors65$cboSenior65", "0");
		params.add(
			"ctl00$ContentInfo$SearchForm$paxSelection$Childfive$cboChildFive",
			search.getChildrenCountString());
		params.add(
			"ctl00$ContentInfo$SearchForm$paxSelection$Infantlap$cboInfantLap",
			search.getInfantsCountString());
		params.add("ctl00$ContentInfo$SearchForm$paxSelection$Childtwo$cboChildTwo", "0");
		params.add("ctl00$ContentInfo$SearchForm$fareTypes$FareTypes", "rdoFareType1");
		params.add("ctl00$ContentInfo$SearchForm$fareTypes$Classofservicecode1$txtcosCode", "");
		params.add("ctl00$ContentInfo$SearchForm$Ecdoffercode1$txtPromoCode", "");
		params.add("ctl00$ContentInfo$SearchForm$Poscountry$cboPOSCountry", "US");
		params.add("ctl00$ContentInfo$SearchForm$searchBy$SearchBy", "rdosearchby1");
		params.add("ctl00$ContentInfo$SearchForm$Numofflts$cboNumOfFlts", "0");
		params.add("ctl00$ContentInfo$SearchForm$searchbutton", "Search");
		params.add("hiddenInputToUpdateATBuffer_CommonToolkitScripts", "1");

		params.add("hdnServer", (String) search.getParamValue(HDNSERVER));
		params.add("hdnSid", (String) search.getParamValue(HDNSID));
		params.add("hdnClient", (String) search.getParamValue(HDNCLIENT));
		params.add("hdnTiming", (String) search.getParamValue(HDNTIMING));

		params.add("hdnLangCode", "en-US");
		params.add("hdnPOS", "US");
		params.add("hdnInactive", "false");
		params.add("hdnAccountNumber", "");
		params.add("hdnAccountNumberE", "");
		params.add("hdnAccountStatus", "");

		return params;
	}

}
