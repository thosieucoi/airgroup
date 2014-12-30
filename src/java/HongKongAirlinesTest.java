/**
 * 
 */


import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.impl.hongkongairlines.HongKongAirlinesFlight;
/**
 * @author linhnd1
 *
 */
public class HongKongAirlinesTest {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("dd-MM-yyyy");
	public static void main(String[] args) throws Exception
	{
		HongKongAirlinesFlight hongKongAirlinesFlight = new HongKongAirlinesFlight();
		CuriositySearch search = new CuriositySearch();
		search.setDepartureCode("HAN");
		search.setArrivalCode("HKG");
		search.setOutboundDate(DATE_FORMATTER.parseDateTime("30-09-2013"));
		//search.setInboundDate(DATE_FORMATTER.parseDateTime("24-09-2013"));
		search.setAdultsCount(1);
		search.setChildrenCount(0);
		search.setInfantsCount(0);
		List<CuriosityFare> fares = hongKongAirlinesFlight.getFare(search); 
		System.err.println("Loaded");
		for (CuriosityFare fare : fares) {
			System.err.println("Price " + fare.getPrice());
			System.err.println("Currency " + fare.getCurrencyCode());
			// if (fare.getOutboundSegments() != null) {
			System.err.println("OUTBOUND");
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
			// } else if (fare.getInboundSegments() != null) {
			System.err.println("INBOUND");
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
				// }
			}
		}
	}
}
