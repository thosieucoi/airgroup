import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.impl.cebupacificair.CebupacificFlight;

/**
 * 
 */

/**
 * @author hiepnt
 * 
 */
public class CebuPacificAirTest {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
		.forPattern("dd-MMM-yyyy");

	public static void main(String[] args) throws Exception {
		CebupacificFlight cebuFlight = new CebupacificFlight();
		CuriositySearch search = new CuriositySearch();
		search.setDepartureCode("KUL");
		search.setArrivalCode("MNL");
		search.setOutboundDate(DATE_FORMATTER.parseDateTime("05-SEP-2013"));
		search.setInboundDate(DATE_FORMATTER.parseDateTime("10-SEP-2013"));
		search.setAdultsCount(1);
		search.setChildrenCount(0);
		search.setInfantsCount(0);
		List<CuriosityFare> fares = cebuFlight.getFare(search);
		for (CuriosityFare fare : fares) {
			System.err.println("Price " + fare.getPrice());
			System.err.println("Currency " + fare.getCurrencyCode());
			if (fare.getOutboundSegments() != null) {
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
			} else if (fare.getInboundSegments() != null) {
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
			}
		}
	}
}
