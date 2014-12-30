/**
 * 
 */
package com.airgroup.datasource;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.airgroup.model.Location;
import com.airgroup.model.LocationAsJson;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeZone;
/**
 * @author linhnd1
 *
 */
public class LocationDatasource extends Datasource
{
	  private static LocationDatasource instance = new LocationDatasource();
	  private final List<Location> locations;
	  private final Map<Integer, Location> cities;
	  private final Map<Integer, Location> airports;
	  private final Map<String, Set<String>> cityCodes;
	  private final Map<String, DateTimeZone> timeZones;
	  private final Map<String, Location> locationCodes;
	  private final Map<String, String> CACHED_LOCATION_CODE;

	  public LocationDatasource()
	  {
	    this.objectMapper.addMixInAnnotations(Location.class, LocationAsJson.class);

	    this.locations = Lists.newArrayList();

	    this.cities = Maps.newConcurrentMap();

	    this.airports = Maps.newConcurrentMap();

	    this.cityCodes = Maps.newConcurrentMap();

	    this.timeZones = Maps.newConcurrentMap();

	    this.locationCodes = Maps.newConcurrentMap();

	    this.CACHED_LOCATION_CODE = Maps.newConcurrentMap();
	  }

	  public static LocationDatasource get()
	  {
	    return instance;
	  }

	  @SuppressWarnings({ "unchecked", "rawtypes" })
	public LocationDatasource setLocations(File file)
	    throws Exception
	  {
	    List<Location> locations = null;

	    if (!(file.exists()))
	    {
	      String path = file.getPath().replaceAll("\\\\", "/");

	      URL url = new URL("jar:" + path);
	      InputStream stream = url.openStream();

	      locations = (List)this.objectMapper.readValue(stream, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Location.class));

	      stream.close();
	    } else {
	      locations = (List)this.objectMapper.readValue(file, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Location.class));
	    }

	    this.logger.info("Setting locations from [file={},locations_count={}]", file.getAbsolutePath(), Integer.valueOf(locations.size()));

	    for (Location location : locations) {
	      addLocation(location);
	    }

	    this.logger.info("Set locations");

	    return this;
	  }

	  public LocationDatasource addLocation(Location location)
	  {
	    this.locations.add(location);

	    if (location.isCity()) {
	      this.cities.put(Integer.valueOf(location.getId()), location);
	    }

	    if (location.isAirport()) {
	      this.airports.put(Integer.valueOf(location.getId()), location);
	    }

	    if (StringUtils.isNotBlank(location.getCode())) {
	      this.locationCodes.put(location.getCode(), location);

	      if (location.getTimeZone() != null) {
	        this.timeZones.put(location.getCode(), location.getTimeZone());
	      }
	    }

	    return this;
	  }

	  @SuppressWarnings("unchecked")
	public LocationDatasource setUp()
	  {
	    if (this.logger.isDebugEnabled()) {
	      this.logger.debug("Setting up locations");
	    }

	    for (Location airport : this.airports.values()) {
	      Location city = (Location)this.cities.get(Integer.valueOf(airport.getCityId()));

	      if (city != null) {
	        @SuppressWarnings("rawtypes")
			Set airportCodes = (Set)this.cityCodes.get(city.getCode());
	        if (airportCodes == null) {
	          airportCodes = Sets.newHashSet();
	          this.cityCodes.put(city.getCode(), airportCodes);
	        }
	        airportCodes.add(airport.getCode());
	      }
	    }

	    if (this.logger.isDebugEnabled()) {
	      this.logger.debug("Set up locations");
	    }

	    return this;
	  }

	  public List<Location> getLocations()
	  {
	    return this.locations;
	  }

	  public Map<Integer, Location> getCities()
	  {
	    return this.cities;
	  }

	  public Map<Integer, Location> getAirports()
	  {
	    return this.airports;
	  }

	  public Map<String, Set<String>> getCityCodes()
	  {
	    return this.cityCodes;
	  }

	  public Map<String, DateTimeZone> getTimeZones()
	  {
	    return this.timeZones;
	  }

	  public Map<String, Location> getLocationCodes()
	  {
	    return this.locationCodes;
	  }

	  @SuppressWarnings("rawtypes")
	public boolean isCityCode(String cityCode)
	  {
	    if (cityCode == null) {
	      return false;
	    }
	    Set airportCodes = (Set)this.cityCodes.get(cityCode);
	    return ((airportCodes != null) && (!(airportCodes.isEmpty())));
	  }

	  @SuppressWarnings("rawtypes")
	public boolean equals(String locationCode1, String locationCode2)
	  {
	    if ((locationCode1 == null) || (locationCode2 == null)) {
	      return false;
	    }

	    if (StringUtils.equals(locationCode1, locationCode2)) {
	      return true;
	    }

	    Set airportCodes = (Set)this.cityCodes.get(locationCode2);
	    if (airportCodes != null) {
	      return airportCodes.contains(locationCode1);
	    }

	    airportCodes = (Set)this.cityCodes.get(locationCode1);
	    if (airportCodes != null) {
	      return airportCodes.contains(locationCode2);
	    }

	    return false;
	  }

	  public String getCityCodeForAirportCode(String airportCode)
	  {
	    Location location = (Location)this.locationCodes.get(airportCode);

	    if (location == null) {
	      return null;
	    }

	    if (location.isCity()) {
	      return location.getCode();
	    }

	    Location city = (Location)this.cities.get(Integer.valueOf(location.getCityId()));

	    if (city == null) {
	      return null;
	    }

	    return city.getCode();
	  }

	  public String getLocationName(String locationCode, String locale)
	  {
	    String locationName = null;
	    Location location = (Location)this.locationCodes.get(locationCode);

	    if (location != null) {
	      locationName = location.getName(locale);
	    }

	    return locationName;
	  }

	  public String getLocationCode(String locationName)
	  {
	    if ((locationName != null) && (this.CACHED_LOCATION_CODE.containsKey(locationName))) {
	      return ((String)this.CACHED_LOCATION_CODE.get(locationName));
	    }

	    String name = StringUtils.trim(locationName);
	    for (Location location : this.locations) {
	      if (StringUtils.equalsIgnoreCase(name, location.getName())) {
	        this.CACHED_LOCATION_CODE.put(locationName, location.getCode());
	        return location.getCode();
	      }
	    }

	    return null;
	  }

	  public String getLocationStateCode(String locationCode) {
	    String stateCode = null;
	    Location location = (Location)this.locationCodes.get(locationCode);

	    if (location != null) {
	      stateCode = location.getStateCode();
	    }

	    return stateCode;
	  }

	  public String getLocationCountryCode(String locationCode) {
	    String countryCode = null;
	    Location location = (Location)this.locationCodes.get(locationCode);

	    if (location != null) {
	      countryCode = location.getCountryCode();
	    }

	    return countryCode;
	  }

	  public String getLocationCountryName(String locationCode, String locale) {
	    String countryName = null;
	    Location location = (Location)this.locationCodes.get(locationCode);

	    if (location != null) {
	      countryName = location.getCountryName(locale);
	    }

	    return countryName;
	  }
}
