package com.airgroup.model;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

import com.airgroup.builders.NoDefaultReflectionToStringBuilder;
import com.airgroup.datasource.LocationDatasource;


public class Search {
	private Cabin cabin;
	private int adultsCount;
	private int childrenCount;
	private int infantsCount;
	private String currencyCode;
	private String locale;
	private String countrySiteCode;
	private String userCountryCode;
	private Set<String> providerCodes;
	private Set<String> noProviderCodes;
	private String departureCode;
	private String departureName;
	private boolean departureCity;
	private String departureStateCode;
	private String departureCountryCode;
	private String departureCountryName;
	private String arrivalCode;
	private String arrivalName;
	private boolean arrivalCity;
	private String arrivalStateCode;
	private String arrivalCountryCode;
	private String arrivalCountryName;
	private DateTime outboundDate;
	private DateTime inboundDate;
	private static LocationDatasource LOCATION_DATASOURCE = LocationDatasource.get();

	public Cabin getCabin() {
		return this.cabin;
	}

	public void setCabin(Cabin cabin) {
		this.cabin = cabin;
	}

	public int getAdultsCount() {
		return this.adultsCount;
	}

	public void setAdultsCount(int adultsCount) {
		this.adultsCount = adultsCount;
	}
	
	public int getInfantsCount() {
		return this.infantsCount;
	}

	public void setInfantsCount(int infantsCount) {
		this.infantsCount = infantsCount;
	}

	public int getChildrenCount() {
		return this.childrenCount;
	}

	public void setChildrenCount(int childrenCount) {
		this.childrenCount = childrenCount;
	}

	public boolean hasChildren() {
		return (this.childrenCount > 0);
	}

	public int getPassengersCount() {
		return (this.childrenCount + this.adultsCount);
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getLocale() {
		return this.locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getCountrySiteCode() {
		return this.countrySiteCode;
	}

	public void setCountrySiteCode(String countrySiteCode) {
		this.countrySiteCode = countrySiteCode;
	}

	public String getUserCountryCode() {
		return this.userCountryCode;
	}

	public void setUserCountryCode(String userCountryCode) {
		this.userCountryCode = userCountryCode;
	}

	public Set<String> getProviderCodes() {
		return this.providerCodes;
	}

	public void setProviderCodes(Set<String> providerCodes) {
		this.providerCodes = providerCodes;
	}

	public Set<String> getNoProviderCodes() {
		return this.noProviderCodes;
	}

	public void setNoProviderCodes(Set<String> noProviderCodes) {
		this.noProviderCodes = noProviderCodes;
	}

	public ToStringBuilder inspect() {
		return new NoDefaultReflectionToStringBuilder(this);
	}

	public String toString() {
		return inspect().toString();
	}
	
	public String getDepartureCode()
	  {
	    return this.departureCode;
	  }

	  public void setDepartureCode(String departureCode) {
	    this.departureCode = departureCode;
	  }

	  public String getDepartureName()
	  {
	    return this.departureName;
	  }

	  public void setDepartureName(String departureName) {
	    this.departureName = departureName;
	  }

	  public Search setDepartureNameIfBlank(String locale) {
	    if (StringUtils.isBlank(getDepartureName())) {
	      setDepartureName(LOCATION_DATASOURCE.getLocationName(getDepartureCode(), locale));
	    }
	    return this;
	  }

	  public boolean isDepartureCity()
	  {
	    return this.departureCity;
	  }

	  public boolean isDepartureAirport() {
	    return (!(this.departureCity));
	  }

	  public void setDepartureCity(boolean departureCity) {
	    this.departureCity = departureCity;
	  }

	  public String getDepartureStateCode()
	  {
	    return this.departureStateCode;
	  }

	  public void setDepartureStateCode(String departureStateCode) {
	    this.departureStateCode = departureStateCode;
	  }

	  public Search setDepartureStateCodeIfBlank() {
	    if (StringUtils.isBlank(this.departureStateCode)) {
	      setDepartureStateCode(LOCATION_DATASOURCE.getLocationStateCode(this.departureCode));
	    }
	    return this;
	  }

	  public String getDepartureCountryCode()
	  {
	    return this.departureCountryCode;
	  }

	  public void setDepartureCountryCode(String departureCountryCode) {
	    this.departureCountryCode = departureCountryCode;
	  }

	  public Search setDepartureCountryCodeIfBlank()
	  {
	    if (StringUtils.isBlank(this.departureCountryCode)) {
	      setDepartureCountryCode(LOCATION_DATASOURCE.getLocationCountryCode(this.departureCode));
	    }
	    return this;
	  }

	  public String getDepartureCountryName()
	  {
	    return this.departureCountryName;
	  }

	  public void setDepartureCountryName(String departureCountryName) {
	    this.departureCountryName = departureCountryName;
	  }

	  public Search setDepartureCountryNameIfBlank(String locale) {
	    if (StringUtils.isBlank(this.departureCountryName)) {
	      setDepartureCountryName(LOCATION_DATASOURCE.getLocationCountryName(this.departureCode, locale));
	    }

	    return this;
	  }

	  public String getArrivalCode()
	  {
	    return this.arrivalCode;
	  }

	  public void setArrivalCode(String arrivalCode) {
	    this.arrivalCode = arrivalCode;
	  }

	  public String getArrivalName()
	  {
	    return this.arrivalName;
	  }

	  public void setArrivalName(String arrivalName) {
	    this.arrivalName = arrivalName;
	  }

	  public Search setArrivalNameIfBlank(String locale) {
	    if (StringUtils.isBlank(getArrivalName())) {
	      setArrivalName(LOCATION_DATASOURCE.getLocationName(this.arrivalCode, locale));
	    }
	    return this;
	  }

	  public boolean isArrivalCity()
	  {
	    return this.arrivalCity;
	  }

	  public boolean isArrivalAirport() {
	    return (!(this.arrivalCity));
	  }

	  public void setArrivalCity(boolean arrivalCity) {
	    this.arrivalCity = arrivalCity;
	  }

	  public String getArrivalStateCode()
	  {
	    return this.arrivalStateCode;
	  }

	  public void setArrivalStateCode(String arrivalStateCode) {
	    this.arrivalStateCode = arrivalStateCode;
	  }

	  public Search setArrivalStateCodeIfBlank() {
	    if (StringUtils.isBlank(this.arrivalStateCode)) {
	      setArrivalStateCode(LOCATION_DATASOURCE.getLocationStateCode(this.arrivalCode));
	    }
	    return this;
	  }

	  public String getArrivalCountryCode()
	  {
	    return this.arrivalCountryCode;
	  }

	  public void setArrivalCountryCode(String arrivalCountryCode) {
	    this.arrivalCountryCode = arrivalCountryCode;
	  }

	  public Search setArrivalCountryCodeIfBlank()
	  {
	    if (StringUtils.isBlank(this.arrivalCountryCode)) {
	      setArrivalCountryCode(LOCATION_DATASOURCE.getLocationCountryCode(this.arrivalCode));
	    }
	    return this;
	  }

	  public String getArrivalCountryName()
	  {
	    return this.arrivalCountryName;
	  }

	  public void setArrivalCountryName(String arrivalCountryName) {
	    this.arrivalCountryName = arrivalCountryName;
	  }

	  public Search setArrivalCountryNameIfBlank(String locale) {
	    if (StringUtils.isBlank(this.arrivalCountryName)) {
	      setArrivalCountryName(LOCATION_DATASOURCE.getLocationCountryName(this.arrivalCode, locale));
	    }
	    return this;
	  }

	  public DateTime getOutboundDate()
	  {
	    return this.outboundDate;
	  }

	  public void setOutboundDate(DateTime outboundDate) {
	    this.outboundDate = outboundDate;
	  }

	  public DateTime getInboundDate()
	  {
	    return this.inboundDate;
	  }

	  public void setInboundDate(DateTime inboundDate) {
	    this.inboundDate = inboundDate;
	  }

	  public boolean isRoundtrip() {
	    return (this.inboundDate != null);
	  }

	  public boolean isOneway() {
	    return (this.inboundDate == null);
	  }
}