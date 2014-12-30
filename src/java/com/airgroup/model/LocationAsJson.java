/**
 * 
 */
package com.airgroup.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.airgroup.helper.TimeZoneHelper;

import java.util.Map;
import org.joda.time.DateTimeZone;
/**
 * @author linhnd1
 *
 */
public abstract interface LocationAsJson extends AsJson
{
	  @JsonProperty("id")
	  public abstract Integer getId();

	  @JsonProperty("city_id")
	  public abstract Integer getCityId();

	  @JsonProperty("location_type")
	  public abstract LocationType getLocationType();

	  @JsonProperty("names")
	  public abstract Map<String, String> getNames();

	  @JsonProperty("name")
	  public abstract String getName();

	  @JsonProperty("code")
	  public abstract String getCode();

	  @JsonProperty("state_code")
	  public abstract String getStateCode();

	  @JsonProperty("country_name")
	  public abstract String getCountryName();

	  @JsonProperty("country_names")
	  public abstract String getCountryNames();

	  @JsonProperty("country_code")
	  public abstract String getCountryCode();

	  @JsonProperty("time_zone")
	  @JsonSerialize(using=TimeZoneHelper.Serializer.class)
	  @JsonDeserialize(using=TimeZoneHelper.Deserializer.class)
	  @JsonInclude(JsonInclude.Include.NON_EMPTY)
	  public abstract DateTimeZone getTimeZone();
}
