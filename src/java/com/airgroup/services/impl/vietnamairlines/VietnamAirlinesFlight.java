/**
 * 
 */
package com.airgroup.services.impl.vietnamairlines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ning.http.client.Cookie;
import com.ning.http.client.Response;
import com.airgroup.model.Cabin;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.SearchFlights;
import com.airgroup.util.Const;
import com.airgroup.util.Utils;

/**
 * @author linhnd1
 * 
 */
public class VietnamAirlinesFlight extends SearchFlights {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("yyyyMMdd HH:mm");
	public static final float vnAirlineCharge = 50000;
	public static final float adultDomestic = 60000;
	public static final float chilDomestic = 30000;

	@Override
	protected Response getResponse(CuriositySearch search) throws InterruptedException,
			ExecutionException, IOException {
		VietnamAirlinesFlightRetrieveCookie retrieveCookie = new VietnamAirlinesFlightRetrieveCookie();
		List<Cookie> cookies = retrieveCookie.getResponse(search).getCookies();
		search.createParam("Cookies", cookies);
		VietnamAirlinesFlightResponseData responseData = new VietnamAirlinesFlightResponseData();
		return responseData.getResponseWithCookies(search, cookies);
	}

	@Override
	public List<CuriosityFare> getFare(CuriositySearch search) throws IOException,
			InterruptedException, ExecutionException {
		Response getContent = getResponse(search);
		String jSonContent = StringUtils.substringBetween(
				getContent.getResponseBody(),
			"var templateData =",
			"debugTemplateData =");
		ObjectMapper mapper = new ObjectMapper();
		JsonParser jp = mapper.getFactory().createJsonParser(jSonContent);
		JsonNode map = jp.readValueAsTree();
		List<CuriosityFare> fares = Lists.newArrayList();
		List<CuriosityFare> obFares = Lists.newArrayList();
		List<CuriosityFare> ibFares = Lists.newArrayList();
		JsonNode outboundJson = map.get("rootElement").findPath("outbounds");
		if (search.isDomestic() && search.isRoundtrip()) {
			for (JsonNode segments : outboundJson) {
				List<CuriositySegment> outboundSegments = parseSegments(
					segments.findPath("segments"),
					search.getCabin());
				List<String> listPrice = new ArrayList<String>();
				listPrice = getPrice(segments.path("basketsRef"), search);
				float price = Utils.floatFromString(listPrice.get(0));
				String currency = listPrice.get(1);
				float childrenPrice = Utils.floatFromString(listPrice.get(2));
				CuriosityFare outboundFare = CuriosityFare.newFromTrip(search);
				outboundFare.setOutboundSegments(outboundSegments);
				outboundFare.setPrice(price);
				outboundFare.setAirlineCode("VN");
				outboundFare.setBasePricePerAdult(Utils.floatFromString(listPrice.get(7)));
				outboundFare.setBasePricePerChild(Utils.floatFromString(listPrice.get(5)));
				outboundFare.setCurrencyCode(currency);
				outboundFare.setDescription(listPrice.get(3));
				outboundFare.setBasePrice(Utils.floatFromString(listPrice.get(4)));
				outboundFare.setPricePerChild(childrenPrice);
				outboundFare.setPricePerAdult(Utils.floatFromString(listPrice.get(6)));
				obFares.add(outboundFare);
			}
			if (search.isRoundtrip()) {
				JsonNode inboundJson = map.get("rootElement").findPath("inbounds");
				for (JsonNode segments : inboundJson) {
					List<CuriositySegment> inboundSegments = parseSegments(
						segments.findPath("segments"),
						search.getCabin());
					List<String> listPrice = new ArrayList<String>();
					listPrice = getPrice(segments.path("basketsRef"), search);
					float price = Utils.floatFromString(listPrice.get(0));
					String currency = listPrice.get(1);
					float childrenPrice = Utils.floatFromString(listPrice.get(2));
					CuriosityFare inboundFare = CuriosityFare.newFromTrip(search);
					inboundFare.setInboundSegments(inboundSegments);
					inboundFare.setPrice(price);
					inboundFare.setBasePricePerAdult(Utils.floatFromString(listPrice.get(7)));
					inboundFare.setBasePricePerChild(Utils.floatFromString(listPrice.get(5)));
					inboundFare.setCurrencyCode(currency);
					inboundFare.setAirlineCode("VN");
					inboundFare.setDescription(listPrice.get(3));
					inboundFare.setBasePrice(Utils.floatFromString(listPrice.get(4)));
					inboundFare.setPricePerChild(childrenPrice);
					inboundFare.setPricePerAdult(Utils.floatFromString(listPrice.get(6)));
					ibFares.add(inboundFare);
				}
			}
			List<CuriosityFare> outboundFares = Lists.newArrayList();
			List<CuriosityFare> inboundFares = Lists.newArrayList();
			for (CuriosityFare obFare : obFares) {
				obFare.setPrice(obFare.getPrice());
				obFare.setPricePerAdult(obFare.getBasePricePerAdult());
				obFare.setPricePerChild(obFare.getBasePricePerChild());
				outboundFares.add(obFare);
				fares.add(obFare);
			}

			for (CuriosityFare ibFare : ibFares) {
				ibFare.setPrice(ibFare.getPrice());
				ibFare.setPricePerAdult(ibFare.getBasePricePerAdult());
				ibFare.setPricePerChild(ibFare.getBasePricePerChild());
				inboundFares.add(ibFare);
				fares.add(ibFare);
			}
//			for (CuriosityFare obFare : outboundFares) {
//				for (CuriosityFare ibFare : inboundFares) {
//					CuriosityFare fare = CuriosityFare.newFromTrip(search);
//					fare.setOutboundSegments(obFare.getOutboundSegments());
//					fare.setInboundSegments(ibFare.getInboundSegments());
//					fare.setCurrencyCode(Const.VND);
//					fare.setPrice(ibFare.getPrice() + obFare.getPrice());
//					float totalPrice = fare.getPrice();
//					fare.setPricePerAdult(totalPrice / search.getPassengersCount());
//					fare.setPricePerChild(totalPrice / search.getPassengersCount());
//					fare.setAirlineCode("VN");
//					fares.add(fare);
//				}
//			}
		}

		else {
			for (JsonNode segments : outboundJson) {
				List<CuriositySegment> outboundSegments = parseSegments(
					segments.findPath("segments"),
					search.getCabin());
				CuriosityFare outboundFare = CuriosityFare.newFromTrip(search);
				outboundFare.setOutboundSegments(outboundSegments);
				outboundFare.setAirlineCode("VN");
				outboundFare.setCurrencyCode("VND");
				obFares.add(outboundFare);
			}
			if (search.isRoundtrip()) {
				JsonNode inboundJson = map.get("rootElement").findPath("inbounds");
				for (JsonNode segments : inboundJson) {
					List<CuriositySegment> inboundSegments = parseSegments(
						segments.findPath("segments"),
						search.getCabin());
					CuriosityFare inboundFare = CuriosityFare.newFromTrip(search);
					inboundFare.setInboundSegments(inboundSegments);
					inboundFare.setCurrencyCode("VND");
					inboundFare.setAirlineCode("VN");

					ibFares.add(inboundFare);
				}
			}
			int count = 0;
			List<List<Float>> getAllPrice = new ArrayList<List<Float>>();
			getAllPrice = getInternationalPrice(getContent, search);
			for (CuriosityFare obFare : obFares) {
				if (search.isRoundtrip()) {
					for (CuriosityFare ibFare : ibFares) {
						CuriosityFare fare = CuriosityFare.newFromTrip(search);
						fare.setOutboundSegments(obFare.getOutboundSegments());
						fare.setInboundSegments(ibFare.getInboundSegments());
						fare.setCurrencyCode(obFare.getCurrencyCode());
						fare.setPrice(getAllPrice.get(count).get(0));
						fare.setPricePerAdult(getAllPrice.get(count).get(2));
						fare.setPricePerChild(getAllPrice.get(count).get(1));
						fare.setAirlineCode("VN");
						fares.add(fare);
						count ++;
					}
				} else {
					obFare.setPrice(getAllPrice.get(count).get(0));
					obFare.setPricePerAdult(getAllPrice.get(count).get(2));
					obFare.setPricePerChild(getAllPrice.get(count).get(1));
					fares.add(obFare);
					count ++;
				}
			}
		}
		return fares;
	}

	private List<String> getPrice(JsonNode node, CuriositySearch search) {
		List<String> priceList = Lists.newArrayList();
		String currency = "";
		float adultBasePrice = 0;
		float serviceTax = 60000;
		float totalTax = 0;
		float childrenPrice = 0;
		float adultPrice = 0;
		float infantPrice = 0;
		float totalPrice = 0;
		float oneAdult = 0;
		float oneChildren = 0;
		float oneInfant = 0;
		String fareType = "";
		
		if (node.path("SS").size() > 0) {
			fareType = "SS";
			currency = Utils.removeQuote(node
				.path("SS")
				.findPath("pricesPerCurrency")
				.findPath("code")
				.toString());
			adultBasePrice = Utils.floatFromString(Utils.removeQuote(node
				.path("SS")
				.findPath("moneyElements")
				.path(0)
				.path("moneyTO")
				.path("amount")
				.toString()));
			oneAdult = adultBasePrice + adultBasePrice/10 + serviceTax;
			oneChildren =  adultBasePrice * 3 / 4 + (adultBasePrice * 3 / 40)+ serviceTax/2;
			oneInfant = adultPrice/10;
			adultPrice = adultBasePrice *search.getAdultsCount();
			childrenPrice = adultBasePrice * 3 / 4 * search.getChildrenCount();
			infantPrice = adultPrice/10 * search.getInfantsCount();
			totalTax = adultPrice/10 + childrenPrice/10 + infantPrice/10 +
					serviceTax * search.getAdultsCount() +
					serviceTax/2*search.getChildrenCount();
			totalPrice = adultPrice + childrenPrice + infantPrice + totalTax;
		}
		else if (node.path("ES").size() > 0) {
			if (!fareType.equals("SS"))
				fareType = "ES";
			currency = Utils.removeQuote(node
				.path("ES")
				.findPath("pricesPerCurrency")
				.findPath("code")
				.toString());
			adultBasePrice = Utils.floatFromString(Utils.removeQuote(node
				.path("ES")
				.findPath("moneyElements")
				.path(0)
				.path("moneyTO")
				.path("amount")
				.toString()));
			oneAdult = adultBasePrice + adultBasePrice/10 + serviceTax;
			oneChildren =  adultBasePrice * 3 / 4 + (adultBasePrice * 3 / 40)+ serviceTax/2;
			oneInfant = adultPrice/10;
			adultPrice = adultBasePrice *search.getAdultsCount();
			childrenPrice = adultBasePrice * 3 / 4 * search.getChildrenCount();
			infantPrice = adultPrice/10 * search.getInfantsCount();
			totalTax = adultPrice/10 + childrenPrice/10 + infantPrice/10 +
					serviceTax * search.getAdultsCount() +
					serviceTax/2*search.getChildrenCount();
			totalPrice = adultPrice + childrenPrice + infantPrice + totalTax;
		} else if (node.path("SF").size() > 0) {
			if (!fareType.equals("SS"))
				fareType = "SF";
			currency = Utils.removeQuote(node
				.path("SF")
				.findPath("pricesPerCurrency")
				.findPath("code")
				.toString());
			adultBasePrice = Utils.floatFromString(Utils.removeQuote(node
				.path("SF")
				.findPath("moneyElements")
				.path(0)
				.path("moneyTO")
				.path("amount")
				.toString()));
			oneAdult = adultBasePrice + adultBasePrice/10 + serviceTax;
			oneChildren =  adultBasePrice * 3 / 4 + (adultBasePrice * 3 / 40)+ serviceTax/2;
			oneInfant = adultPrice/10;
			adultPrice = adultBasePrice *search.getAdultsCount();
			childrenPrice = adultBasePrice * 3 / 4 * search.getChildrenCount();
			infantPrice = adultPrice/10 * search.getInfantsCount();
			totalTax = adultPrice/10 + childrenPrice/10 + infantPrice/10 +
					serviceTax * search.getAdultsCount() +
					serviceTax/2*search.getChildrenCount();
			totalPrice = adultPrice + childrenPrice + infantPrice + totalTax;

		} else if (node.path("EE").size() > 0) {
			if (!fareType.equals("SS"))
				fareType = "EE";
			currency = Utils.removeQuote(node
				.path("EE")
				.findPath("pricesPerCurrency")
				.findPath("code")
				.toString());
			adultBasePrice = Utils.floatFromString(Utils.removeQuote(node
				.path("EE")
				.findPath("moneyElements")
				.path(0)
				.path("moneyTO")
				.path("amount")
				.toString()));
			oneAdult = adultBasePrice + adultBasePrice/10 + serviceTax;
			oneChildren =  adultBasePrice * 3 / 4 + (adultBasePrice * 3 / 40)+ serviceTax/2;
			oneInfant = adultPrice/10;
			adultPrice = adultBasePrice *search.getAdultsCount();
			childrenPrice = adultBasePrice * 3 / 4 * search.getChildrenCount();
			infantPrice = adultPrice/10 * search.getInfantsCount();
			totalTax = adultPrice/10 + childrenPrice/10 + infantPrice/10 +
					serviceTax * search.getAdultsCount() +
					serviceTax/2*search.getChildrenCount();
			totalPrice = adultPrice + childrenPrice + infantPrice + totalTax;
		}  
		else if (node.path("EF").size() > 0) {
			if (!fareType.equals("SS"))
				fareType = "EF";
			currency = Utils.removeQuote(node
				.path("EF")
				.findPath("pricesPerCurrency")
				.findPath("code")
				.toString());
			adultBasePrice = Utils.floatFromString(Utils.removeQuote(node
				.path("EF")
				.findPath("moneyElements")
				.path(0)
				.path("moneyTO")
				.path("amount")
				.toString()));
			oneAdult = adultBasePrice + adultBasePrice/10 + serviceTax;
			oneChildren =  adultBasePrice * 3 / 4 + (adultBasePrice * 3 / 40)+ serviceTax/2;
			oneInfant = adultPrice/10;
			adultPrice = adultBasePrice *search.getAdultsCount();
			childrenPrice = adultBasePrice * 3 / 4 * search.getChildrenCount();
			infantPrice = adultPrice/10 * search.getInfantsCount();
			totalTax = adultPrice/10 + childrenPrice/10 + infantPrice/10 +
					serviceTax * search.getAdultsCount() +
					serviceTax/2*search.getChildrenCount();
			totalPrice = adultPrice + childrenPrice + infantPrice + totalTax;

		} else if (node.path("BF").size() > 0) {
			if (!fareType.equals("SS"))
				fareType = "BF";
			currency = Utils.removeQuote(node
				.path("BF")
				.findPath("pricesPerCurrency")
				.findPath("code")
				.toString());
			adultBasePrice = Utils.floatFromString(Utils.removeQuote(node
				.path("BF")
				.findPath("moneyElements")
				.path(0)
				.path("moneyTO")
				.path("amount")
				.toString()));
			oneAdult = adultBasePrice + adultBasePrice/10 + serviceTax;
			oneChildren =  adultBasePrice * 3 / 4 + (adultBasePrice * 3 / 40)+ serviceTax/2;
			oneInfant = adultPrice/10;
			adultPrice = adultBasePrice *search.getAdultsCount();
			childrenPrice = adultBasePrice * 3 / 4 * search.getChildrenCount();
			infantPrice = adultPrice/10 * search.getInfantsCount();
			totalTax = adultPrice/10 + childrenPrice/10 + infantPrice/10 +
					serviceTax * search.getAdultsCount() +
					serviceTax/2*search.getChildrenCount();
			totalPrice = adultPrice + childrenPrice + infantPrice + totalTax;

		}
		priceList.add(String.valueOf(totalPrice));
		priceList.add(currency);
		priceList.add(String.valueOf(childrenPrice));
		priceList.add(fareType);
		priceList.add(String.valueOf(adultPrice + childrenPrice + infantPrice));
		priceList.add(String.valueOf(oneChildren));
		priceList.add(String.valueOf(adultPrice));
		priceList.add(String.valueOf(oneAdult));
		return priceList;
	}

	private List<CuriositySegment> parseSegments(JsonNode segmentNode, Cabin cabin) {
		List<CuriositySegment> segments = Lists.newArrayList();
		for (JsonNode segment : segmentNode) {
			CuriositySegment fareSegment = new CuriositySegment();
			fareSegment.setAirlineCode(StringUtils.substringBetween(
				segment.findValue("airlineCodes").toString(),
				"[\"",
				"\"]"));
			fareSegment.setOperatingAirlineCode(StringUtils.substringBetween(
				segment.findValue("operatingCarrier").toString(),
				"[\"",
				"\"]"));
			fareSegment.setFlightNumber(StringUtils.substringBetween(
				segment.findValue("flightNumber").toString(),
				"[",
				"]"));
			fareSegment.setAircraftType(StringUtils.substringBetween(
				segment.findValue("aircraftType").toString(),
				"[\"",
				"\"]"));
			fareSegment.setDepartureCode(StringUtils.substringBetween(
				segment.findValue("departureCode").toString(),
				"\"",
				"\""));
			fareSegment.setArrivalCode(StringUtils.substringBetween(segment
				.findValue("arrivalCode")
				.toString(), "\"", "\""));
			fareSegment.setDepartureTime(DATE_TIME_FORMATTER, Utils.parseDate(
				StringUtils.substringBetween(
					segment.findValue("departureDate").toString(),
					"\"",
					"\""),
				"yyyy/MM/dd HH:mm:ss"));
			fareSegment.setArrivalTime(DATE_TIME_FORMATTER, Utils.parseDate(
				StringUtils.substringBetween(
					segment.findValue("arrivalDate").toString(),
					"\"",
					"\""),
				"yyyy/MM/dd HH:mm:ss"));

			DateTime newArrTime = fareSegment.getArrivalTime().plusDays(1);
			if (fareSegment.getArrivalTime().isBefore(fareSegment.getDepartureTime())) {
				fareSegment.setArrivalTime(
					DATE_TIME_FORMATTER,
					DATE_TIME_FORMATTER.print(newArrTime));
			}

			fareSegment.setCabin(cabin);
			segments.add(fareSegment);
		}
		if (segments.size() >= 2) {
			for (int i = 0; i <= segments.size() - 2; ++i) {
				CuriositySegment segment = segments.get(i);
				CuriositySegment nextSegment = segments.get(i + 1);
				if (segment.getArrivalTime().isAfter(nextSegment.getDepartureTime())) {
					nextSegment.setDepartureTime(
						DATE_TIME_FORMATTER,
						DATE_TIME_FORMATTER.print(nextSegment.getDepartureTime().plusDays(1)));
					nextSegment.setArrivalTime(
						DATE_TIME_FORMATTER,
						DATE_TIME_FORMATTER.print(nextSegment.getArrivalTime().plusDays(1)));
				}
			}
		}
		return segments;
	}

	private List<List<Float>> getInternationalPrice(Response content, CuriositySearch search)
			throws IOException {
		List<List<Float>> getAllPrice = new ArrayList<List<Float>>();
		Document document = Jsoup.parse(content.getResponseBody());
		Elements obData = document
				.select("div[id=dtcontainer-both] > table > tbody > tr");
		if(search.isRoundtrip()){
			obData = document
				.select("div[id=outbounds] > div[id=dtcontainer-outbounds] > table > tbody > tr");
		}
		for (Element obElement : obData) {
			Elements obPriceData = obElement.select("td");
			int obIndex = obPriceData.size() - 1;
			while(obPriceData.get(obIndex).text().contains("Sold out")){
				obIndex = obIndex - 1;
			}
			String obId = "";
			
			if(search.isRoundtrip()){
				Elements ibData = document
					.select("div[id=inbounds] > div[id=dtcontainer-inbounds] > table > tbody > tr");
				for (Element ibElement : ibData) {
					Elements ibPriceData = ibElement.select("td");
					int ibIndex = ibPriceData.size() - 1;
					while(ibPriceData.get(ibIndex).text().contains("Sold out")){
						ibIndex = ibIndex - 1;
					}
					if (obPriceData.get(obIndex).attr("fare-family-key").contains("SS")) {
						if (!ibPriceData.get(ibIndex).attr("fare-family-key").contains("SS")) {
							obIndex = obIndex - 1;
						}
					} else {
						if (ibPriceData.get(ibIndex).attr("fare-family-key").contains("SS")) {
							ibIndex = ibIndex - 1;
						}
					}
					obId = StringUtils.substringsBetween(
							obPriceData.get(obIndex).toString(),
							"id=\"flight_outbounds_",
							"\"")[0];
					String ibId = StringUtils.substringsBetween(
							ibPriceData.get(ibIndex).toString(),
							"id=\"flight_inbounds_",
							"\"")[0];
					search.createParam("VNObId", obId);
					search.createParam("VNIbId", ibId);
					getAllPrice.add(getPriceList(content,search));
					obIndex = obPriceData.size() - 1;
				}
			}
			else{
				obId = StringUtils.substringsBetween(
						obPriceData.get(obIndex).toString(),
						"id=\"flight_both_",
						"\"")[0];
				search.createParam("VNObId", obId);
				getAllPrice.add(getPriceList(content,search));
			}
		}
		return getAllPrice;
	}
	
	private List<Float> getPriceList(Response content, CuriositySearch search) throws JsonParseException, IOException{
		List<Float> priceList = new ArrayList<Float>();
		search.createParam("response", content);
		@SuppressWarnings("unchecked")
		List<Cookie> cookie = (List<Cookie>) search.getParamValue("Cookies");
		VietnamAirlinesPreGetPrice getPrePrice = new VietnamAirlinesPreGetPrice();
		Response getPrice = getPrePrice.getResponseWithCookies(search, cookie);
		ObjectMapper mapper = new ObjectMapper();
		JsonParser jp = mapper.getFactory().createJsonParser(getPrice.getResponseBody());
		JsonNode map = jp.readValueAsTree();
		float totalPrice = Utils.floatFromString(Utils.removeQuote(map
			.findPath("total")
			.path("priceAlternatives")
			.path(0)
			.findPath("amount")
			.toString()));
		float pricePerAdult = Utils.floatFromString(Utils.removeQuote(map
			.findPath("total")
			.path("moneyElements")
			.path(0)
			.findPath("amount")
			.toString()));
		float pricePerChildren = Utils.floatFromString(Utils.removeQuote(map
			.findPath("total")
			.path("moneyElements")
			.path(search.getAdultsCount())
			.findPath("amount")
			.toString()));
		float totalTax = 0;
		for (int i = 0; i < map.findPath("total").path("moneyElements").size(); i++) {
			if (map
				.findPath("total")
				.path("moneyElements")
				.path(i)
				.path("type")
				.toString()
				.contains("TAX")) {
				totalTax += Utils.floatFromString(Utils.removeQuote(map
					.findPath("total")
					.path("moneyElements")
					.path(i)
					.findPath("amount")
					.toString()));
			}
		}
		priceList.add(totalPrice - vnAirlineCharge*(search.getAdultsCount() + search.getChildrenCount() + search.getInfantsCount()));
		priceList.add(pricePerChildren + pricePerChildren / 10 + chilDomestic);
		priceList.add(pricePerAdult + pricePerAdult/10 + adultDomestic);
		return priceList;
	}
	
	public float vnAirlineTax(float price){
		return price/10 + 60000;
	}
}
