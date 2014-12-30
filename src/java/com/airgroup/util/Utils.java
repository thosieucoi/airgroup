/**
 * 
 */
package com.airgroup.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;

/**
 * @author linhnd1
 * 
 */
public final class Utils {
	private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());
	private static final String DATE_FORMAT = "yyyyMMdd HH:mm";

	public static final void printLogg(String message) {
		LOGGER.info(message);
	}

	public static final float floatFromString(String floatString) {
		assert floatString != null;
		float f=0;
		try{
		 f= Float.parseFloat(floatString.replaceAll(",", ""));
		}catch(Exception e){
			System.out.println(e.getMessage()+""+floatString);
		}
		return f;
	}
	public static List<CuriosityFare> DOMESTIC_OBFARES = new ArrayList<CuriosityFare>();
	public static List<CuriosityFare> DOMESTIC_IBFARES = new ArrayList<CuriosityFare>();
	public static List<CuriosityFare> INTERNATIONAL_FARES = new ArrayList<CuriosityFare>();
	
	public static CuriositySearch changeCuriositySearch(CuriositySearch search){
		String departureCode = search.getDepartureCode();
		String arriveCode = search.getArrivalCode();
		search.setDepartureCode(arriveCode);
		search.setArrivalCode(departureCode);
		return search;
	}
	public static final String removeQuote(String qouteString) {
		if (qouteString == null) {
			return null;
		}
		int beginIndex = qouteString.startsWith("\"") ? 1 : 0;
		int endIndex = qouteString.endsWith("\"") ? qouteString.length() - 1 : qouteString.length();
		return qouteString.substring(beginIndex, endIndex);
	}

	public static final String parseDate(String date, String dateFormat) {
		DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern(DATE_FORMAT);
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			date = TIME_FORMATTER.print(sdf.parse(date).getTime()).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static final String parseDateWithFormat(String date, String fromFormat, String toFormat) {
		DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern(toFormat);
		SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
		try {
			date = TIME_FORMATTER.print(sdf.parse(date).getTime()).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static String numberNormalize(String str) {
		try {
			if (Integer.parseInt(str) < 10) {
				str = "0" + str;
			}
			return str;
		} catch (NumberFormatException ex) {
			return "00";
		}
	}
}
