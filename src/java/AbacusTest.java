/**
 * 
 */

import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.impl.abacus.AbacusFlight;

/**
 * @author linhnd1
 * 
 */
public class AbacusTest {
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("yyyyMMdd");

	public static void main(String[] args) throws Exception {
		AbacusFlight flight = new AbacusFlight();
		CuriositySearch search = new CuriositySearch();
		search.setDepartureCode("HAN");
		search.setArrivalCode("SGN");
		search.setOutboundDate(DATE_TIME_FORMATTER.parseDateTime("20140510"));
		// search.setInboundDate(DATE_TIME_FORMATTER.parseDateTime("20131130"));
		search.setAdultsCount(1);
		search.setChildrenCount(0);
		search.setInfantsCount(0);

		List<CuriosityFare> fares = flight.getFare(search);

		for (CuriosityFare fare : fares) {
			System.err.println("Price " + fare.getPrice());
			System.err.println("Adult Price " + fare.getPricePerAdult());
			System.err.println("Children Price " + fare.getPricePerChild());
			System.err.println("Currency " + fare.getCurrencyCode());
			if (fare.getInboundSegments() != null) {
				for (CuriositySegment segment : fare.getOutboundSegments()) {
					System.err.println(segment.getAirlineCode() +
										" " +
										segment.getFlightNumber() +
										" " +
										segment.getDepartureCode() +
										" " +
										segment.getArrivalCode() +
										" " +
										segment.getDepartureTime() +
										" - " +
										segment.getArrivalTime());
				}
				for (CuriositySegment segment : fare.getInboundSegments()) {
					System.err.println(segment.getAirlineCode() +
										" " +
										segment.getFlightNumber() +
										" " +
										segment.getDepartureCode() +
										" " +
										segment.getArrivalCode() +
										" " +
										segment.getDepartureTime() +
										" - " +
										segment.getArrivalTime());
				}

			} else {
				for (CuriositySegment segment : fare.getOutboundSegments()) {
					System.err.println(segment.getAirlineCode() +
										" " +
										segment.getFlightNumber() +
										" " +
										segment.getDepartureCode() +
										" " +
										segment.getArrivalCode() +
										" " +
										segment.getDepartureTime() +
										" - " +
										segment.getArrivalTime());
				}
			}
		}
	}
}
