/**
 * 
 */
package com.airgroup.services;

import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import com.airgroup.model.RequestType;
import com.airgroup.model.curiosity.CuriositySearch;

/**
 * @author linhnd1
 *
 */
public interface IFlightResponse {
	 RequestType getType();
	 String getURL();
	 FluentStringsMap getParams();
	 String getHeaderValue();
	 Response getResponse(CuriositySearch search);
	 String getDateTimeFormatterPattern();
}
