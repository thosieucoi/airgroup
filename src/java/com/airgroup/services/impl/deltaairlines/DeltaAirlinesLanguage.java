package com.airgroup.services.impl.deltaairlines;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class DeltaAirlinesLanguage extends FlightResponse {

	@Override
	public String getDateTimeFormatterPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return "https://abacuswebstart.abacus.com.sg/vietnam-booking/flight-search.aspx";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params.add("__EVENTTARGET", "ctl00$ddlLanguage");
		params.add("__EVENTARGUMENT", "");
		params.add("__LASTFOCUS", "");
		params.add("__VIEWSTATE",(String)search.getParamValue("viewState"));
		params.add("__EVENTVALIDATION",(String)search.getParamValue("eventValidation"));
		params.add("ctl00$ddlLanguage", "en-US");

		return params;
	}
}
