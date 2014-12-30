/**
 * 
 */
package com.airgroup.model.curiosity;

import java.util.Date;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;

import com.airgroup.datasource.LocationDatasource;
import com.airgroup.model.Segment;

/**
 * @author linhnd1
 *
 */
public class CuriositySegment extends Segment{
	  private static final Map<String, DateTimeZone> TIME_ZONES = LocationDatasource.get().getTimeZones();
	  private DateTimeZone departureTimeZone;
	  private DateTimeZone arrivalTimeZone;

	  public void setDepartureCode(String departureCode)
	  {
	    super.setDepartureCode(departureCode);
	    this.departureTimeZone = ((DateTimeZone)TIME_ZONES.get(departureCode));
	  }

	  @Deprecated
	  public void setDepartureTime(DateTime departureTime)
	  {
	    super.setDepartureTime(departureTime);
	  }

	  public void setDepartureTimeWithoutZone(Date departureTime) {
	    super.setDepartureTime(new DateTime(departureTime).withZone(this.departureTimeZone));
	  }

	  public void setDepartureTime(DateTimeFormatter dateTimeFormatter, String departureTime) {
	    super.setDepartureTime(dateTimeFormatter.withZone(this.departureTimeZone).parseDateTime(departureTime));
	  }

	  public void setArrivalCode(String arrivalCode)
	  {
	    super.setArrivalCode(arrivalCode);
	    this.arrivalTimeZone = ((DateTimeZone)TIME_ZONES.get(arrivalCode));
	  }

	  @Deprecated
	  public void setArrivalTime(DateTime arrivalTime)
	  {
	    super.setArrivalTime(arrivalTime);
	  }

	  public void setArrivalTime(DateTimeFormatter dateTimeFormatter, String arrivalTime) {
	    super.setArrivalTime(dateTimeFormatter.withZone(this.arrivalTimeZone).parseDateTime(arrivalTime));
	  }

	  public void setArrivalTimeWithoutZone(Date arrivalTime)
	  {
	    super.setArrivalTime(new DateTime(arrivalTime).withZone(this.arrivalTimeZone));
	  }

	  public void setArrivalTimeBasedOnDepartureTime(DateTimeFormatter dateTimeFormatter, String time) {
	    if (getDepartureTime() == null) {
	      throw new UnsupportedOperationException("Please set departure date before use this method");
	    }

	    DateTime arrivalTime = dateTimeFormatter.withZone(this.arrivalTimeZone).parseDateTime(time);
	    if (arrivalTime.isBefore(getDepartureTime())) {
	      arrivalTime = arrivalTime.plusDays(1);
	    }

	    super.setArrivalTime(arrivalTime);
	  }
}
