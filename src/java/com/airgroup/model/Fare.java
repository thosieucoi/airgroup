/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.airgroup.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;

public class Fare<S extends Segment>
{
  public static final String ID_JOINT = ":";
  private String id;
  private String searchId;
  private String tripId;
  public static final String ROUTE_JOINT = "=";
  private float price;
  private float basePrice;
  private boolean taxAndFeeExclusive;
  private float priceUsd;
  private float pricePerAdult;
  private float basePricePerAdult;
  private float pricePerChild;
  private float basePricePerChild;
  private String currencyCode;
  private List<S> outboundSegments;
  public static final String LEG_JOINT = ":";
  private String outboundDepartureCode;
  private String outboundArrivalCode;
  private int outboundSegmentsCount;
  private DateTime outboundDepartureTime;
  private int outboundDepartureDayTime;
  private DateTime outboundArrivalTime;
  private int outboundArrivalDayTime;
  private int outboundDuration;
  private int outboundStopoverDuration;
  private List<S> inboundSegments;
  private String inboundDepartureCode;
  private String inboundArrivalCode;
  private int inboundSegmentsCount;
  private DateTime inboundDepartureTime;
  private int inboundDepartureDayTime;
  private DateTime inboundArrivalTime;
  private int inboundArrivalDayTime;
  private int inboundDuration;
  private int inboundStopoverDuration;
  private String providerCode;
  private FareType fareType;
  private String description;
  private String url;
  private Map<String, Object> params;
  private DateTime createdAt;
  private float outboundComfortIndex;
  private float inboundComfortIndex;
  private float comfortIndex;
  private String airlineCode;

  public String getAirlineCode() {
	return airlineCode;
  }

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

public String getId()
  {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSearchId()
  {
    return this.searchId;
  }

  public void setSearchId(String searchId) {
    this.searchId = searchId;
  }

  public String getTripId()
  {
    return this.tripId;
  }

  public void setTripId(String tripId) {
    this.tripId = tripId;
  }

  public float getPrice()
  {
    return this.price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public boolean isTaxAndFeeExclusive()
  {
    return this.taxAndFeeExclusive;
  }

  public void setTaxAndFeeExclusive(boolean taxAndFeeExclusive) {
    this.taxAndFeeExclusive = taxAndFeeExclusive;
  }

  public float getBasePrice()
  {
    return this.basePrice;
  }

  public void setBasePrice(float basePrice) {
    this.basePrice = basePrice;
  }

  public float getPriceUsd()
  {
    return this.priceUsd;
  }

  public void setPriceUsd(float priceUsd) {
    this.priceUsd = priceUsd;
  }

  public float getPricePerAdult()
  {
    return this.pricePerAdult;
  }

  public void setPricePerAdult(float pricePerAdult) {
    this.pricePerAdult = pricePerAdult;
  }

  public float getBasePricePerAdult()
  {
    return this.basePricePerAdult;
  }

  public void setBasePricePerAdult(float basePricePerAdult) {
    this.basePricePerAdult = basePricePerAdult;
  }

  public float getPricePerChild()
  {
    return this.pricePerChild;
  }

  public void setPricePerChild(float pricePerChild) {
    this.pricePerChild = pricePerChild;
  }

  public float getBasePricePerChild()
  {
    return this.basePricePerChild;
  }

  public void setBasePricePerChild(float basePricePerChild) {
    this.basePricePerChild = basePricePerChild;
  }

  public String getCurrencyCode()
  {
    return this.currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public void clearOutboundSegments()
  {
    this.outboundSegments = null;
    this.outboundDepartureCode = null;
    this.outboundArrivalCode = null;
    this.outboundSegmentsCount = 0;
    this.outboundDepartureTime = null;
    this.outboundDepartureDayTime = 0;
    this.outboundArrivalTime = null;
    this.outboundArrivalDayTime = 0;
    this.outboundDuration = 0;
    this.outboundStopoverDuration = 0;
  }

  public List<S> getOutboundSegments()
  {
    return this.outboundSegments;
  }

  public List<S> createOutboundSegments()
  {
    return ((this.outboundSegments == null) ? (this.outboundSegments = Lists.newArrayList()) : this.outboundSegments);
  }

  public void setOutboundSegments(List<S> outboundSegments) {
    this.outboundSegments = outboundSegments;
  }

  public String getOutboundDepartureCode()
  {
    return outboundSegments.get(0).getDepartureCode();
  }

  public void setOutboundDepartureCode(String outboundDepartureCode) {
    this.outboundDepartureCode = outboundDepartureCode;
  }

  public String getOutboundArrivalCode()
  {
	  
    return outboundSegments.get(outboundSegments.size()-1).getArrivalCode();
  }

  public void setOutboundArrivalCode(String outboundArrivalCode) {
    this.outboundArrivalCode = outboundArrivalCode;
  }

  public int getOutboundSegmentsCount()
  {
    return (((this.outboundSegmentsCount == 0) && (this.outboundSegments != null)) ? (this.outboundSegmentsCount = this.outboundSegments.size()) : this.outboundSegmentsCount);
  }

  public DateTime getOutboundDepartureTime()
  {
    return ((this.outboundDepartureTime == null) ? createOutboundDepartureTime() : this.outboundDepartureTime);
  }

  public DateTime createOutboundDepartureTime() {
    return (this.outboundDepartureTime = (getOutboundSegmentsCount() < 1) ? null : ((Segment)getOutboundSegments().get(0)).getDepartureTime());
  }

  public int getOutboundDepartureDayTime()
  {
    return ((this.outboundDepartureDayTime == 0) ? createOutboundDepartureDayTime() : this.outboundDepartureDayTime);
  }

  public int createOutboundDepartureDayTime() {
    return (this.outboundDepartureDayTime = (getOutboundSegmentsCount() < 1) ? 0 : ((Segment)getOutboundSegments().get(0)).getDepartureTime().getMinuteOfDay());
  }

  public DateTime getOutboundArrivalTime()
  {
    return ((this.outboundArrivalTime == null) ? createOutboundArrivalTime() : this.outboundArrivalTime);
  }

  public DateTime createOutboundArrivalTime() {
    return (this.outboundArrivalTime = (getOutboundSegmentsCount() < 1) ? null : ((Segment)getOutboundSegments().get(getOutboundSegments().size() - 1)).getArrivalTime());
  }

  public int getOutboundArrivalDayTime()
  {
    return ((this.outboundArrivalDayTime == 0) ? createOutboundArrivalDayTime() : this.outboundArrivalDayTime);
  }

  public int createOutboundArrivalDayTime() {
    return (this.outboundArrivalDayTime = (getOutboundSegmentsCount() < 1) ? 0 : ((Segment)getOutboundSegments().get(getOutboundSegments().size() - 1)).getArrivalTime().getMinuteOfDay());
  }

  public int getOutboundDuration()
  {
    return ((this.outboundDuration == 0) ? createOutboundDuration() : this.outboundDuration);
  }

  public int createOutboundDuration() {
    return (this.outboundDuration = (getOutboundSegmentsCount() < 1) ? 0 : (int)(((Segment)getOutboundSegments().get(getOutboundSegments().size() - 1)).getArrivalTime().getMillis() - ((Segment)getOutboundSegments().get(0)).getDepartureTime().getMillis()) / 60000);
  }

  public int getOutboundStopoverDuration()
  {
    return ((this.outboundStopoverDuration == 0) ? createOutboundStopoverDuration() : this.outboundStopoverDuration);
  }

  public int createOutboundStopoverDuration() {
    this.outboundStopoverDuration = 0;
    if (getOutboundSegmentsCount() > 0) {
      for (int i = 0; i < getOutboundSegments().size() - 1; ++i) {
        this.outboundStopoverDuration += (int)(((Segment)getOutboundSegments().get(i + 1)).getDepartureTime().getMillis() - ((Segment)getOutboundSegments().get(i)).getArrivalTime().getMillis()) / 60000;
      }

    }

    return this.outboundStopoverDuration;
  }

  public void setInboundSegments(List<S> inboundSegments)
  {
    this.inboundSegments = inboundSegments;
  }

  public List<S> getInboundSegments() {
    return this.inboundSegments;
  }

  public List<S> createInboundSegments()
  {
    return ((this.inboundSegments == null) ? (this.inboundSegments = Lists.newArrayList()) : this.inboundSegments);
  }

  public void clearInboundSegments()
  {
    this.inboundSegments = null;
    this.inboundDepartureCode = null;
    this.inboundArrivalCode = null;
    this.inboundSegmentsCount = 0;
    this.inboundDepartureTime = null;
    this.inboundDepartureDayTime = 0;
    this.inboundArrivalTime = null;
    this.inboundArrivalDayTime = 0;
    this.inboundDuration = 0;
    this.inboundStopoverDuration = 0;
  }

  public String getInboundDepartureCode()
  {
    return inboundSegments.get(0).getDepartureCode();
  }

  public void setInboundDepartureCode(String inboundDepartureCode) {
    this.inboundDepartureCode = inboundDepartureCode;
  }

  public String getInboundArrivalCode()
  {
    return inboundSegments.get(inboundSegments.size()-1).getArrivalCode();
  }

  public void setInboundArrivalCode(String inboundArrivalCode) {
    this.inboundArrivalCode = inboundArrivalCode;
  }

  public int getInboundSegmentsCount()
  {
    return (((this.inboundSegmentsCount == 0) && (this.inboundSegments != null)) ? (this.inboundSegmentsCount = this.inboundSegments.size()) : this.inboundSegmentsCount);
  }

  public DateTime getInboundDepartureTime()
  {
    return ((this.inboundDepartureTime == null) ? (this.inboundDepartureTime = createInboundDepartureTime()) : this.inboundDepartureTime);
  }

  public DateTime createInboundDepartureTime() {
    return ((getInboundSegmentsCount() == 0) ? null : ((Segment)getInboundSegments().get(0)).getDepartureTime());
  }

  public int getInboundDepartureDayTime()
  {
    return ((this.inboundDepartureDayTime == 0) ? createInboundDepartureDayTime() : this.inboundDepartureDayTime);
  }

  public int createInboundDepartureDayTime() {
    return (this.inboundDepartureDayTime = (getInboundSegmentsCount() == 0) ? 0 : ((Segment)getInboundSegments().get(0)).getDepartureTime().getMinuteOfDay());
  }

  public DateTime getInboundArrivalTime()
  {
    return ((this.inboundArrivalTime == null) ? (this.inboundArrivalTime = createInboundArrivalTime()) : this.inboundArrivalTime);
  }

  public DateTime createInboundArrivalTime() {
    return ((getInboundSegmentsCount() < 1) ? null : ((Segment)getInboundSegments().get(getInboundSegments().size() - 1)).getArrivalTime());
  }

  public int getInboundArrivalDayTime()
  {
    return ((this.inboundArrivalDayTime == 0) ? createInboundArrivalDayTime() : this.inboundArrivalDayTime);
  }

  public int createInboundArrivalDayTime() {
    return (this.inboundArrivalDayTime = (getInboundSegmentsCount() < 1) ? 0 : ((Segment)getInboundSegments().get(getInboundSegments().size() - 1)).getArrivalTime().getMinuteOfDay());
  }

  public int getInboundDuration()
  {
    return ((this.inboundDuration == 0) ? createInboundDuration() : this.inboundDuration);
  }

  public int createInboundDuration() {
    return (this.inboundDuration = (getInboundSegmentsCount() < 1) ? 0 : (int)(((Segment)getInboundSegments().get(getInboundSegments().size() - 1)).getArrivalTime().getMillis() - ((Segment)getInboundSegments().get(0)).getDepartureTime().getMillis()) / 60000);
  }

  public int getInboundStopoverDuration()
  {
    return ((this.inboundStopoverDuration == 0) ? createInboundStopoverDuration() : this.inboundStopoverDuration);
  }

  public int createInboundStopoverDuration() {
    this.inboundStopoverDuration = 0;
    if (getInboundSegmentsCount() > 0) {
      for (int i = 0; i < getInboundSegments().size() - 1; ++i) {
        this.inboundStopoverDuration += (int)(((Segment)getInboundSegments().get(i + 1)).getDepartureTime().getMillis() - ((Segment)getInboundSegments().get(i)).getArrivalTime().getMillis()) / 60000;
      }

    }

    return this.inboundStopoverDuration;
  }

  public String getProviderCode()
  {
    return this.providerCode;
  }

  public void setProviderCode(String providerCode) {
    this.providerCode = providerCode;
  }

  public void setFareType(FareType fareType)
  {
    this.fareType = fareType;
  }

  public FareType getFareType() {
    return this.fareType;
  }

  public boolean isOneway() {
    return (FareType.oneway == this.fareType);
  }

  public boolean isOutbound() {
    return (FareType.outbound == this.fareType);
  }

  public boolean isInbound() {
    return (FareType.inbound == this.fareType);
  }

  public boolean isRoundtrip() {
    return (FareType.roundtrip == this.fareType);
  }

  public String getDescription()
  {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl()
  {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Map<String, Object> getParams()
  {
    return this.params;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }

  public Map<String, Object> createParams() {
    return ((this.params == null) ? (this.params = Maps.newHashMap()) : this.params);
  }

  public DateTime getCreatedAt()
  {
    return this.createdAt;
  }

  public void setCreatedAt(DateTime createdAt) {
    this.createdAt = createdAt;
  }
  public float getOutboundComfortIndex()
  {
    return this.outboundComfortIndex;
  }

  public void setOutboundComfortIndex(float outboundComfortIndex) {
    this.outboundComfortIndex = outboundComfortIndex;
  }

  public float getInboundComfortIndex()
  {
    return this.inboundComfortIndex;
  }

  public void setInboundComfortIndex(float inboundComfortIndex) {
    this.inboundComfortIndex = inboundComfortIndex;
  }

  public float getComfortIndex()
  {
    return this.comfortIndex;
  }

  public void setComfortIndex(float comfortIndex) {
    this.comfortIndex = comfortIndex;
  }

  public static class PriceComparator<F extends Fare<? extends Segment>>
    implements Comparator<F>
  {
    public int compare(F fare1, F fare2)
    {
      float result = 0.0F;
      result = fare1.getPrice() - fare2.getPrice();
      return ((result < 0.0F) ? -1 : (result > 0.0F) ? 1 : 0);
    }
  }
}