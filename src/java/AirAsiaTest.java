import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.impl.airasia.AirAsiaFlight;

public class AirAsiaTest {
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("yyyyMMdd");

	public static void main(String[] args) throws Exception {
		AirAsiaFlight flight = new AirAsiaFlight();

		CuriositySearch search = new CuriositySearch();
		search.setDepartureCode("BWN");
		search.setArrivalCode("SIN");
		search.setOutboundDate(DATE_TIME_FORMATTER.parseDateTime("20140525"));
		// search.setInboundDate(DATE_TIME_FORMATTER.parseDateTime("20140430"));
		search.setAdultsCount(1);
		search.setChildrenCount(1);
		search.setInfantsCount(1);

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