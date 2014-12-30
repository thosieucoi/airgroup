package com.airgroup.services.impl.chinasouthern;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class ChinaSouthernHome extends FlightResponse {

	@Override
	public String getDateTimeFormatterPattern() {
		return "yyyy-MM-dd";
	}

	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		return "http://tw.csair.com/php/GetSendParamOR.php";
	}

	@Override
	public String getHeaderValue() {
		return null;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();

		params.add("InterOrDom", "1");
		params.add("AreaSite", "tw");
		params.add("lang", "en");
		params.add("ENC", "");
		// params.add("segtype_1", "2");
		params.add("segtype", search.isRoundtrip() ? "2" : "1");
		// params.add("fromcity","Hanoi");
		params.add("B_LOCATION_1", search.getDepartureCode());
		// params.add("tocity","GuangZhou");
		params.add("E_LOCATION_1", search.getArrivalCode());
		params.add("DEPARTDATE", getDateTimeFormatter().print(search.getOutboundDate()));
		if (search.isRoundtrip()) {
			params.add("RETURNDATE", getDateTimeFormatter().print(search.getInboundDate()));
		} else {
			params
				.add("RETURNDATE", getDateTimeFormatter().print(search.getOutboundDate().plus(1)));
		}
		params.add("ADULTS", search.getAdultsCountString());
		params.add("CHILDS", search.getChildrenCountString());
		params.add("INFANTS", search.getInfantsCountString());
		params.add("CABIN", "E");

		return params;
	}
}
