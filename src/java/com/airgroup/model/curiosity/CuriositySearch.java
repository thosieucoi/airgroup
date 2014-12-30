package com.airgroup.model.curiosity;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.airgroup.datasource.LocationDatasource;
import com.airgroup.helper.DateHelper;
import com.airgroup.model.Search;

public class CuriositySearch extends Search {
	private String adultsCountString;
	private String childrenCountString;
	private String infantsCountString;
	private DateTime outboundDateWithZone;
	private boolean domestic;

	private final Map<String, Object> params = new HashMap<String, Object>();

	private static final Map<String, DateTimeZone> TIME_ZONES = LocationDatasource
		.get()
		.getTimeZones();

	public DateTime getOutboundDateWithZone() {
		return ((this.outboundDateWithZone == null) ? (this.outboundDateWithZone = DateHelper.FORMATTER
			.withZone(TIME_ZONES.get(getDepartureCode()))
			.parseDateTime(DateHelper.FORMATTER.print(getOutboundDate()))) : this.outboundDateWithZone);
	}

	public String getAdultsCountString() {
		return ((this.adultsCountString == null) ? (this.adultsCountString = Integer
			.toString(getAdultsCount())) : this.adultsCountString);
	}

	public String getChildrenCountString() {
		return ((this.childrenCountString == null) ? (this.childrenCountString = Integer
			.toString(getChildrenCount())) : this.childrenCountString);
	}

	public boolean isDomestic() {
		return domestic;
	}

	public void setDomestic(boolean domestic) {
		this.domestic = domestic;
	}

	public String getInfantsCountString() {
		return ((this.infantsCountString == null) ? (this.infantsCountString = Integer
			.toString(getInfantsCount())) : this.infantsCountString);
	}

	public void createParam(String key, Object val) {
//		if (!params.containsKey(key))
			params.put(key, val);
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public Object getParamValue(String key) {
		return params.get(key);
	}
}
