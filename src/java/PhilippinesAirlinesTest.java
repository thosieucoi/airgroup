import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.impl.philippineairlines.PhilippineAirlinesFlight;

public class PhilippinesAirlinesTest {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
		.forPattern("dd-MMM-yyyy");

	public static void main(String[] args) throws Exception {
		PhilippineAirlinesFlight philippinesFlight = new PhilippineAirlinesFlight();
		CuriositySearch search = new CuriositySearch();
		search.setDepartureCode("MNL");
		search.setArrivalCode("HKG");
		search.setOutboundDate(DATE_FORMATTER.parseDateTime("10-OCT-2013"));
		search.setInboundDate(DATE_FORMATTER.parseDateTime("20-OCT-2013"));
		search.setAdultsCount(1);
		search.setChildrenCount(0);
		search.setInfantsCount(0);
		List<CuriosityFare> fares = philippinesFlight.getFare(search);
		System.err.println("Load");
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
