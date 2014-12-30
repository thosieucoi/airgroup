/**
 * 
 */
package com.airgroup.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Cookie;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.airgroup.helper.RequestBuilderHelper;
import com.airgroup.model.RequestType;
import com.airgroup.model.curiosity.CuriositySearch;

/**
 * @author linhnd1
 * 
 */
public abstract class FlightResponse implements IFlightResponse {

	protected FluentStringsMap params;
	protected CuriositySearch search = null;
	protected Response response = null;

	@Override
	abstract public RequestType getType();

	@Override
	abstract public String getURL();

	@Override
	public FluentStringsMap getParams() {
		if (params == null)
			params = new FluentStringsMap();
		return params;
	}

	@Override
	abstract public String getHeaderValue();

	@Override
	public Response getResponse(CuriositySearch newSearch) {
		search = newSearch;
		RequestBuilder requestBuilder = null;

		requestBuilder = new RequestBuilder(getType().toString()).setUrl(getURL());

		if (getParams() != null) {
			if (RequestType.GET.equals(getType())) {
				requestBuilder.setQueryParameters(getParams());
			} else if (RequestType.POST.equals(getType())) {
				requestBuilder.setParameters(getParams());
			} else
				throw new UnsupportedOperationException();
		}

		if (getHeaderValue() != null)
			requestBuilder.setHeader("Cookie", getHeaderValue());
		// requestBuilder.setFollowRedirects(false);
		AsyncHttpClient async=new AsyncHttpClient();
		try {
			response = async.executeRequest(requestBuilder.build()).get();
		} catch (Exception e){
			e.printStackTrace();
			async.close();
		}finally{
			async.close();
		}

		return response;
	}

	public Response getResponseWithCookies(CuriositySearch newSearch, List<Cookie> cookieResponse) {
		search = newSearch;
		RequestBuilder requestBuilder = null;

		if (cookieResponse.size() > 0) {
			requestBuilder = RequestBuilderHelper
				.getRequestBuilderWithCookies(cookieResponse)
				.setMethod(getType().toString())
				.setUrl(getURL());
		} else {
			requestBuilder = new RequestBuilder(getType().toString()).setUrl(getURL());
		}

		if (getParams() != null) {
			if (RequestType.GET.equals(getType())) {
				requestBuilder.setQueryParameters(getParams());
			} else if (RequestType.POST.equals(getType())) {
				requestBuilder.setParameters(getParams());
			} else
				throw new UnsupportedOperationException();
		}

		if (getHeader() != null) {
			Map<String, String> map = getHeader();
			for (String key : map.keySet())
				requestBuilder.setHeader(key, map.get(key));
		}
		AsyncHttpClient async=new AsyncHttpClient();
		try {
			response = async.executeRequest(requestBuilder.build()).get();
		} catch (Exception e){
			e.printStackTrace();
			async.close();
		}finally{
			async.close();
		}

		return response;
	}

	public DateTimeFormatter getDateTimeFormatter() {
		return DateTimeFormat.forPattern(getDateTimeFormatterPattern());
	}

	public Response getResponse() {
		return response;
	}

	public Map<String, String> getHeader() {
		Map<String, String> map = new HashMap<String, String>();
		if (getHeaderValue() != null)
			map.put("Cookie", getHeaderValue());
		return map;
	}
}
