package com.airgroup.services.impl.emirates1;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class EmiratesBook extends FlightResponse {

	DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("ddMMyyyy");

	DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("dd-MMM-yyyy");

	@Override
	public String getDateTimeFormatterPattern() {
		return "dd MMM yy";
	}

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return "http://www.emirates.com/vn/English/";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params.add("__VIEWSTATE", "");
		params.add("__VIEWSTATEENCRYPTED", "");
		params.add("txtHeaderSearch", "Search");
		params.add("siteSelectorID", "0");
		params.add("Itinerary", "rbReturn");
		params.add("seldcity1", "");
		params.add("seldcity1-suggest", "Departure Airport");
		params.add("selddate1", getDateTimeFormatter().print(new DateTime()));
		params.add("selcabinclass", "0");
		params.add("selacity1", "");
		params.add("selacity1-suggest", "Arrival Airport");
		params.add("seladate1", "");
		params.add("selcabinclass1", "0");
		params.add("seladults", "1");
		params.add("selchildren", "0");
		params.add("selinfants", "0");
		params.add("resultby", "2");
		params.add("redeemforValue", "");
		params.add("txtPromoCode", "");
		params.add("j", "t");
		params.add("multiCity", "");
		params.add("chkReturn", "on");
		params.add("redeemChecked", "");
		params.add("redeemfor", "");
		params.add("flex", "0");
		params.add("interline", "0");
		params.add("LoginButtonEnabled", "1");
		params.add("signon", "");
		params.add("departDate", DATE_TIME_FORMATTER.print(new DateTime()));
		params.add(
			"ctl00$MainContent$ctl04$ctl01$ctl00$ctl07$hdnSlideOmnitureEnabledFSearch",
			"True");
		params
			.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl07$hdnSlideOmnitureTimeSpendFSearch", "");
		params.add("EnableInterlinePriceOptions", "1");
		params.add("DefaultInterlineResultsBy", "0");
		params.add("DefaultResultsBy", "2");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl07$hdnMapURL", "");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl07$Hash", "");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl07$hdnSUrl", "");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl07$hdnMapComp", "");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl07$hdnMapErr", "");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl07$hdnIbe", "");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl07$hdnWaitingText", "");
		params.add(
			"ctl00$MainContent$ctl04$ctl01$ctl00$ctl07$hdnOmnitureHomeUIFlightSearch",
			"Search and Book Flights");
		params.add("lpHotelCar", "H");
		params.add("HotelCity", "");
		params.add("lphsdate", getDateTimeFormatter().print(new DateTime().plusDays(1)));
		params.add("lphedate", "");
		params.add("selrooms", "1");
		params.add("seladults1", "1");
		params.add("children1", "0");
		params.add("ages1_1", "2");
		params.add("ages1_2", "2");
		params.add("ages1_3", "2");
		params.add("seladults2", "1");
		params.add("children2", "0");
		params.add("ages2_1", "2");
		params.add("ages2_2", "2");
		params.add("ages2_3", "2");
		params.add("lphopt1", "0");
		params.add("lphopt2", "0");
		params.add("CarCity", "");
		params.add("calPickupDate", getDateTimeFormatter().print(new DateTime()));
		params.add("selPickupTime", "12");
		params.add("selPickupMin", "00");
		params.add("calDropOffDate", "");
		params.add("selDropOffTime", "12");
		params.add("selDropOffMin", "00");
		params.add("lpcopt1", "0");
		params.add("lpcopt2", "0");
		params.add("rmdetails", "1A");
		params.add("lphcityName", "");
		params.add("lphcity", "");
		params.add("lpccityName", "");
		params.add("lpcsdate", "");
		params.add("lpcedate", "");
		params.add("lpccity", "");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl14$hdnSlideOmnitureEnabledLP", "True");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl14$hdnSlideOmnitureTimeSpendLP", "");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl14$hdnOmnitureFindHotel", "Find Hotel");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl14$hdnOmnitureFindCar", "Find Car");
		params.add("lastname2", "");
		params.add("bookref2", "");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl21$hdnSlideOmnitureEnabledMB", "True");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl21$hdnSlideOmnitureTimeSpendMB", "");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl21$hdnSlideUCTFSInterval", "300000");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl21$hdnSlideUCTFSEnable", "true");
		params.add(
			"ctl00$MainContent$ctl04$ctl01$ctl00$ctl21$hdnOmnitureHomeUIMB",
			"Manage Existing Booking");
		params.add("lastname", "");
		params.add("bookref", "");
		params.add("seldcity2", "");
		params.add("flightno", "");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl28$hdnSlideOmnitureEnabledOC", "True");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl28$hdnSlideOmnitureTimeSpendOC", "");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl28$hdnSlideUCTFSInterval", "300000");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl28$hdnSlideUCTFSEnable", "true");
		params.add(
			"ctl00$MainContent$ctl04$ctl01$ctl00$ctl28$hdnOmnitureHomeUIOLCI",
			"Online Checkin");
		params.add("FSSearchBy", "FSFlightNumber");
		params.add("txtFSFlightNumber", "");
		params.add("ddlFSDepartureAirport", "");
		params.add("ddlFSArrivalAirport", "");
		params.add("ddlFSDepartureDate", DATE_FORMATTER.print(new DateTime()));
		params.add("FSArrivalDeparture", "FSDeparture");
		params.add(
			"ctl00$MainContent$ctl04$ctl01$ctl00$ctl35$hdnSlideOmnitureEnabledFStatus",
			"True");
		params
			.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl35$hdnSlideOmnitureTimeSpendFStatus", "");
		params.add("hdnSlideUCTFSInterval", "300000");
		params.add("hdnSlideUCTFSEnable", "true");
		params.add(
			"ctl00$MainContent$ctl04$ctl01$ctl00$ctl35$hdnOmnitureHomeUIFls",
			"Check the Status of Flight");
		params.add("SFSearchBy", "rbSFFlightNumber");
		params.add("txtSFFlightNumber", "");
		params.add("ddlSFDepartureAirport", "");
		params.add("ddlSFArrivalAirport", "");
		params.add("calSFDeparture", getDateTimeFormatter().print(new DateTime()));
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl42$hdnSlideOmnitureEnabledSF", "True");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl42$hdnSlideOmnitureTimeSpendSF", "");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl42$hdnSlideUCTFSInterval", "300000");
		params.add("ctl00$MainContent$ctl04$ctl01$ctl00$ctl42$hdnSlideUCTFSEnable", "true");
		params.add(
			"ctl00$MainContent$ctl04$ctl01$ctl00$ctl42$hdnOmnitureHomeUISF",
			"See Whats on your Flight");
		params.add("Itinerary_1", search.isRoundtrip() ? "rbReturn_1" : "rbOneway_1");
		params.add("seldcity1_1", search.getDepartureCode());
		// params.add("seldcity1_1-suggest", "Hanoi (HAN)");
		params.add("selddate1_1", getDateTimeFormatter().print(search.getOutboundDate()));
		params.add("selcabinclass_1", "0");
		params.add("selacity1_1", search.getArrivalCode());
		// params.add("selacity1_1-suggest", "London Heathrow (LHR)");
		params.add(
			"seladate1_1",
			search.isRoundtrip() ? getDateTimeFormatter().print(search.getInboundDate()) : "");
		params.add("selcabinclass1_1", "0");
		params.add("seladults_1", search.getAdultsCountString());
		params.add("selchildren_1", search.getChildrenCountString());
		params.add("selinfants_1", search.getInfantsCountString());
		params.add("resultby_1", "0");
		params.add("txtPromoCode_1", "");
		params.add("redeemforValue_1", "");
		params.add(
			"ctl00$MainContent$ctl04$ctl01$ctl00$ctl07$PlanAndBook$btnPlanAndBookStartBooking",
			"Find Flights");
		params.add("chkReturn_1", search.isRoundtrip() ? "on" : "");
		params.add("departDate_1", DATE_TIME_FORMATTER.print(search.getOutboundDate()));
		params.add("redeemChecked_1", "");
		params.add("redeemfor_1", "");
		params.add("flex_1", "0");
		params.add("interline_1", "1");
		params.add("multiCity_1", "");
		params.add(
			"ctl00$MainContent$ctl04$ctl01$ctl00$ctl07$PlanAndBook$hdnOmnitureHomeUIPB",
			"Close_Plan_and_Book_Window");
		params.add("currentPanelOpen", "");
		params.add("__SEOVIEWSTATE", (String) search.getParamValue("seoState"));

		return params;
	}
}
