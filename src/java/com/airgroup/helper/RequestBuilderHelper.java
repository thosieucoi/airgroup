/**
 * 
 */
package com.airgroup.helper;

import com.ning.http.client.Cookie;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import java.util.List;
/**
 * @author linhnd1
 *
 */
public class RequestBuilderHelper {
	  public static RequestBuilder getRequestBuilderWithCoockies(Response response)
	  {
	    RequestBuilder builder = new RequestBuilder();
	    if ((response != null) && (response.getCookies() != null)) {
	      for (Cookie cookie : response.getCookies()) {
	        builder.addCookie(cookie);
	      }
	    }
	    return builder;
	  }
	
	  public static RequestBuilder getRequestBuilderWithCookies(List<Cookie> cookies)
	  {
	    RequestBuilder builder = new RequestBuilder();
	    if (cookies != null) {
	      for (Cookie cookie : cookies) {
	        builder.addCookie(cookie);
	      }
	    }
	    return builder;
	  }
}
