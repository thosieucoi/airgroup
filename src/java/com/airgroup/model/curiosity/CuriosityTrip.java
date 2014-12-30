/**
 * 
 */
package com.airgroup.model.curiosity;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.airgroup.datasource.LocationDatasource;
import com.airgroup.helper.DateHelper;
import com.airgroup.model.Trip;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;

/**
 * @author linhnd1
 *
 */
public class CuriosityTrip extends Trip{
	  private String cacheKey;
	  private static final Map<String, DateTimeZone> TIME_ZONES = LocationDatasource.get().getTimeZones();
	  private DateTime outboundDateWithZone;
	  private Period cachePeriod;
	  private final AtomicInteger cacheFaresCount;
	  private Collection<CuriosityFare> cacheFares;
	  private Map<String, Object> params;
	  private CuriosityOnewayOnlyTrip onewayOnlyTrip;

	  public CuriosityTrip()
	  {
	    this.cacheFaresCount = new AtomicInteger();
	  }

	  public static CuriosityTrip newOutboundTrip(CuriosityOnewayOnlyTrip trip)
	  {
	    CuriosityTrip outboundTrip = new CuriosityTrip();

	    outboundTrip.setDepartureCode(trip.getDepartureCode());
	    outboundTrip.setDepartureCountryCode(trip.getDepartureCountryCode());
	    outboundTrip.setDepartureCountryName(trip.getDepartureCountryName());

	    outboundTrip.setArrivalCode(trip.getArrivalCode());
	    outboundTrip.setArrivalCountryCode(trip.getArrivalCountryCode());
	    outboundTrip.setArrivalCountryName(trip.getArrivalCountryName());

	    outboundTrip.setOutboundDate(trip.getOutboundDate());

	//    outboundTrip.createId();

	    outboundTrip.setOnewayOnlyTrip(trip);
	    trip.setOutboundTrip(outboundTrip);

	    return outboundTrip;
	  }

	  public static CuriosityTrip newInboundTrip(CuriosityOnewayOnlyTrip trip)
	  {
	    CuriosityTrip inboundTrip = new CuriosityTrip();

	    inboundTrip.setDepartureCode(trip.getArrivalCode());
	    inboundTrip.setDepartureCountryCode(trip.getArrivalCountryCode());
	    inboundTrip.setDepartureCountryName(trip.getArrivalCountryName());

	    inboundTrip.setArrivalCode(trip.getDepartureCode());
	    inboundTrip.setArrivalCountryCode(trip.getDepartureCountryCode());
	    inboundTrip.setArrivalCountryName(trip.getDepartureCountryName());

	    inboundTrip.setOutboundDate(trip.getInboundDate());

	  //  inboundTrip.createId();

	    inboundTrip.setOnewayOnlyTrip(trip);
	    trip.setInboundTrip(inboundTrip);

	    return inboundTrip;
	  }

	  public String getCacheKey()
	  {
	    return this.cacheKey;
	  }

	  public void setCacheKey(String cacheKey) {
	    this.cacheKey = cacheKey;
	  }

	  public DateTime getOutboundDateWithZone()
	  {
	    return ((this.outboundDateWithZone == null) ? (this.outboundDateWithZone = DateHelper.FORMATTER.withZone((DateTimeZone)TIME_ZONES.get(getDepartureCode())).parseDateTime(DateHelper.FORMATTER.print(getOutboundDate()))) : this.outboundDateWithZone);
	  }

	  public Period getCachePeriod()
	  {
	    return this.cachePeriod;
	  }

	  public void setCachePeriod(Period cachePeriod) {
	    this.cachePeriod = cachePeriod;
	  }

	  public AtomicInteger getCacheFaresCount()
	  {
	    return this.cacheFaresCount;
	  }

	  public Collection<CuriosityFare> getCacheFares()
	  {
	    return this.cacheFares;
	  }

	  public Collection<CuriosityFare> createCacheFares()
	  {
	    return ((this.cacheFares == null) ? (this.cacheFares = Queues.newConcurrentLinkedQueue()) : this.cacheFares);
	  }

	  public Map<String, Object> getParams()
	  {
	    return this.params;
	  }

	  public Map<String, Object> createParams() {
	    return ((this.params == null) ? (this.params = Maps.newHashMap()) : this.params);
	  }

	  public boolean isOnewayOnlyInbound()
	  {
	    return (this == this.onewayOnlyTrip.getInboundTrip());
	  }

	  public CuriosityOnewayOnlyTrip getOnewayOnlyTrip()
	  {
	    return this.onewayOnlyTrip;
	  }

	  public boolean isFromOnewayOnlyTrip() {
	    return (this.onewayOnlyTrip != null);
	  }

	  public void setOnewayOnlyTrip(CuriosityOnewayOnlyTrip onewayOnlyTrip)
	  {
	    this.onewayOnlyTrip = onewayOnlyTrip;
	  }

	  public void setArrivalCode(String arrivalCode)
	  {
	    super.setArrivalCode(arrivalCode);
	    super.setArrivalCountryCodeIfBlank();
	  }

	  public void setDepartureCode(String departureCode)
	  {
	    super.setDepartureCode(departureCode);
	    super.setDepartureCountryCodeIfBlank();
	  }
}
