/**
 * 
 */
package com.airgroup.model;
import java.util.Map;
import org.joda.time.DateTimeZone;

/**
 * @author linhnd1
 *
 */
public class Location {
	  private int id;
	  private int cityId;
	  private LocationType locationType;
	  private Map<String, String> names;
	  private String name;
	  private String code;
	  private String stateCode;
	  private Map<String, String> countryNames;
	  private String countryName;
	  private String countryCode;
	  private DateTimeZone timeZone;

	  public int getId()
	  {
	    return this.id;
	  }

	  public int getCityId()
	  {
	    return this.cityId;
	  }

	  public LocationType getLocationType()
	  {
	    return this.locationType;
	  }

	  public boolean isAirport() {
	    return (LocationType.airport == this.locationType);
	  }

	  public boolean isCity() {
	    return (LocationType.city == this.locationType);
	  }

	  public Map<String, String> getNames()
	  {
	    return this.names;
	  }

	  public String getName()
	  {
	    return this.name;
	  }

	  public String getName(String locale)
	  {
	    String name = null;

	    if ((this.names != null) && (locale != null)) {
	      name = (String)this.names.get(locale);
	    }

	    if (name == null) {
	      name = this.name;
	    }

	    return name;
	  }

	  public String getCode()
	  {
	    return this.code;
	  }

	  public String getStateCode()
	  {
	    return this.stateCode;
	  }

	  public Map<String, String> getCountryNames()
	  {
	    return this.countryNames;
	  }

	  public String getCountryName(String locale)
	  {
	    String countryName = null;

	    if ((this.countryNames != null) && (locale != null)) {
	      countryName = (String)this.countryNames.get(locale);
	    }

	    if (countryName == null) {
	      countryName = this.countryName;
	    }

	    return countryName;
	  }

	  public String getCountryName()
	  {
	    return this.countryName;
	  }

	  public String getCountryCode()
	  {
	    return this.countryCode;
	  }

	  public DateTimeZone getTimeZone()
	  {
	    return this.timeZone;
	  }
}
