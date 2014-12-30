package com.airgroup.services.impl.allnipponairway;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.FluentStringsMap;
import com.airgroup.model.RequestType;
import com.airgroup.services.FlightResponse;

public class AllNipponAirwaysFlightResponseSecond extends FlightResponse{
	private static final String URL ="https://aswbe-i.ana.co.jp/p_per/sky_ip_per_jp/preReSearchRoundtripResult.do";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("M");
	@Override
	public RequestType getType() {
		return RequestType.POST;
	}

	@Override
	public String getURL() {
		String newURL = (String) search.getParamValue("newurl");
		return newURL;
	}

	@Override
	public FluentStringsMap getParams() {
		FluentStringsMap params = new FluentStringsMap();
		
		if (search.isRoundtrip()) {
			params.add("intSrch00","nom");
		}else{
			params.add("intSrch00","add");
		}
		params.add("depArea","VN");
		params.add("depApoCode",search.getDepartureCode());
		params.add("depApo",search.getDepartureCode());
		params.add("arrArea","1");
		params.add("arrApoCode",search.getArrivalCode());
		params.add("arrApo",search.getArrivalCode());
		params.add("wayToMonth",DATE_FORMATTER.print(search.getOutboundDate()));
		params.add("wayToDay",search.getOutboundDate().getDayOfMonth()+"");
		if (search.isRoundtrip()) {
			params.add("wayBackMonth",DATE_FORMATTER.print(search.getInboundDate()));
			params.add("wayBackDay",search.getInboundDate().getDayOfMonth()+"");
		}
		params.add("intSrch","nom");
		params.add("adultCount",search.getAdultsCountString());
		params.add("childCount",search.getChildrenCountString());
		params.add("babyCount",search.getInfantsCountString());
		params.add("seatKind","Y");
		params.add("searchOption","1");
		if (search.isRoundtrip()) {
			params.add("btnSubmit:mapping=fare_ref_search22.x","92");
			params.add("btnSubmit:mapping=fare_ref_search22.y","16");
		}else{
			params.add("btnSubmit:mapping=fare_ref_search22.x","96");
			params.add("btnSubmit:mapping=fare_ref_search22.y","23");
		}
		
		return params;
	}

	public String getHeaderValue() {
		return null;
	}

	public String getDateTimeFormatterPattern() {
		return "yyyy-MMM-dd";
	}

}
