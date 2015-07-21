import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.impl.americanairline.AmericanAirlinesFlight;
import com.airgroup.services.impl.cathaypacific.CathayPacificFlight;
import com.airgroup.services.impl.chinaairlines.ChinaAirlinesFlight;
import com.airgroup.services.impl.chinasouthern.ChinaSouthernFlight;

/**
 * 
 */

/**
 * @author linhnd1
 *
 */
public class AmericanAirlinesTest {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("yyyy-MM-dd");
	public static void main(String[] args) throws Exception
	{
		AmericanAirlinesFlight chinaSouthernFlight = new AmericanAirlinesFlight();
		CuriositySearch search = new CuriositySearch();
		search.setDepartureCode("JFK");
		search.setArrivalCode("LHR");
		search.setOutboundDate(DATE_FORMATTER.parseDateTime("2015-01-30"));
		//search.setInboundDate(DATE_FORMATTER.parseDateTime("2013-10-30"));
		search.setAdultsCount(1);
		search.setChildrenCount(0);
		search.setInfantsCount(0);
		System.err.println("Pre Load data");
		List<CuriosityFare> fares = chinaSouthernFlight.getFare(search); 
		System.err.println("Load data");
		for (CuriosityFare fare : fares) {
			System.err.println("Price " + fare.getPrice());
			System.err.println("Adult Price " + fare.getBasePricePerAdult());
			System.err.println("Children Price " + fare.getBasePricePerChild());
			System.err.println("Currency " + fare.getCurrencyCode());
			if(fare.getInboundSegments()!=null){
				for (CuriositySegment segment : fare.getOutboundSegments()) {
					System.err.println(segment.getCarrier() +
										" " +
										segment.getFlightNumber() +
										" " +
										segment.getDepartureName() +
										" " +
										segment.getArrivalName() +
										" " +
										segment.getDepartureTime() +
										" - " +
										segment.getArrivalTime());
				}
				for (CuriositySegment segment : fare.getInboundSegments()) {
					System.err.println(segment.getCarrier() +
										" " +
										segment.getFlightNumber() +
										" " +
										segment.getDepartureName() +
										" " +
										segment.getArrivalName() +
										" " +
										segment.getDepartureTime() +
										" - " +
										segment.getArrivalTime());
				}

			}
			else {
				for (CuriositySegment segment : fare.getOutboundSegments()) {
					System.err.println(segment.getCarrier() +
										" " +
										segment.getFlightNumber() +
										" " +
										segment.getDepartureName() +
										" " +
										segment.getArrivalName() +
										" " +
										segment.getDepartureTime() +
										" - " +
										segment.getArrivalTime());
				}
			}
		}
	}
}
