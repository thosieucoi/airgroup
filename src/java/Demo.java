import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Cookie;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.airgroup.helper.RequestBuilderHelper;


public class Demo {
	private static final String COOKIE_URL = "http://www.hongkongairlines.com/en_HK/booking/flight";
	
	private static final String URL = "http://book.hongkongairlines.com/hxet/reservation/AVQuery.do";
	
	private static final String INF = "http://book.hongkongairlines.com/hxet/reservation/reservation/BrandQuery.do";
	
	public static void main(String args[]) throws IllegalArgumentException, InterruptedException, ExecutionException, IOException{
		AsyncHttpClient client = new AsyncHttpClient();
		
		Response resp = client.prepareGet(COOKIE_URL).execute().get();
		
		Response response = client.preparePost(URL).setParameters(getParams1()).execute().get();
		
		Document document = Jsoup.parse(response.getResponseBody());
		
		String random = document.select("input[id=random]").val();
		String code = document.select("input[name=brandrdo]").first().val();
		
		System.err.println(resp.getCookies());
		
		List<Cookie> cookies = resp.getCookies();
		
		RequestBuilder requestBuilder = RequestBuilderHelper.getRequestBuilderWithCookies(cookies).setMethod("GET").setUrl(INF).setQueryParameters(getParams2(code, random));
		
		Response res = client.executeRequest(requestBuilder.build()).get();
		System.err.println(res.getResponseBody());
	}
	
	public static FluentStringsMap getParams1() {
		FluentStringsMap params = new FluentStringsMap();

		params.add("sureDate", "1");
		params.add("cabinType", "ECONOMY");
		params.add("adultCount", "1");
		params.add("childCount", "0");
		params.add("takeoffDate", "2013-08-21");
		params.add("orgcity", "HAN");
		params.add("dstcity", "HKG");
		params.add("tripType", "OW");
		params.add("email", "");
		params.add("language", "US");

		return params;
	}
	
	public static FluentStringsMap getParams2(String code, String random) {
		FluentStringsMap params = new FluentStringsMap();

		params.add("farefamilyname", code);
		params.add("brandseq","1_0");
		params.add("random", random);

		return params;
	}
}
