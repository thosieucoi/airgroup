package com.airgroup.services.impl.eva;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class EvaRequest extends FlightResponse {
	
	private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormat.forPattern("yyyy-MM");
	private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormat.forPattern("dd");
	public String getDateTimeFormatterPattern() {
		return null;
	}

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return "http://eservice.evaair.com/EVAWEB/EVA/B2C/booking-online.aspx?lang=en-us";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public FluentStringsMap getParams() {

		FluentStringsMap requestParams = new FluentStringsMap();
		Document document = Jsoup.parse(search.getParamValue("EVA_DOCUMENT").toString());
		
		String viewState = document.select("input[id=__VIEWSTATE]").val();
		String zipState = document.select("input[id=__ZIPSTATE]").val();
		String previousState = document.select("input[id=__PREVIOUSPAGE]").val();
		String eventValidation = document.select("input[id=__EVENTVALIDATION]").val();
		
		requestParams.add("__VIEWSTATE", viewState);
		requestParams.add("__ZIPSTATE", zipState);
		requestParams.add("__PREVIOUSPAGE", previousState);
		requestParams.add("__EVENTVALIDATION", eventValidation);

		requestParams.add("__EVENTTARGET", "ctl00$ContentPlaceHolder1$btn_ok");
		requestParams.add("__EVENTARGUMENT", "");
		requestParams.add("__VIEWSTATEENCRYPTED", "");
		requestParams.add("q", "Search");
		requestParams.add("ctl00$ContentPlaceHolder1$hid_Init", "");
		requestParams.add("ctl00$ContentPlaceHolder1$page_top$HiddenMailData", "");
		requestParams.add("INIT", "");
		requestParams.add("ctl00$ContentPlaceHolder1$iDep", "");
		requestParams.add("ctl00$ContentPlaceHolder1$iArr", "");
		requestParams.add("rbn_Segment", "2");
		requestParams.add("DepArea", "");
		requestParams.add("GoDep", search.getDepartureCode() + search.getDepartureCode());
		requestParams.add("ArrArea", "");
		requestParams.add("GoArr", search.getArrivalCode() + search.getArrivalCode());
		requestParams.add("GoYYYYMM", MONTH_YEAR_FORMATTER.print(search.getOutboundDate()));
		requestParams.add("GoDD", DAY_FORMATTER.print(search.getOutboundDate()));
		requestParams.add("BackYYYYMM", MONTH_YEAR_FORMATTER.print(search.getInboundDate()));
		requestParams.add("BackDD", DAY_FORMATTER.print(search.getInboundDate()));
		requestParams.add("rbn_DisplayType", "2");
		requestParams.add("cabinclass", "EY");
		requestParams.add("Adult", search.getAdultsCountString());
		requestParams.add("Child", search.getChildrenCountString());
		requestParams.add("ctl00$__EVENTTARGET", "");
		requestParams.add("ctl00$__EVENTARGUMENT", "");
		requestParams.add("ctl00$HiddenCSS", "");

		return requestParams;
	}
}
