import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.impl.edreams.EdreamsFlight;

public class EdreamsTest {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat
		.forPattern("yyyyMMdd");

	public static void main(String args[]) throws InterruptedException, ExecutionException,
			IOException {
		EdreamsFlight flight = new EdreamsFlight();
		CuriositySearch search = new CuriositySearch();
		search.setDepartureCode("HAN");
		search.setArrivalCode("SGN");
		search.setOutboundDate(DATE_TIME_FORMATTER.parseDateTime("20130919"));
		search.setInboundDate(DATE_TIME_FORMATTER.parseDateTime("20131020"));
		search.setAdultsCount(1);
		search.setChildrenCount(0);
		search.setInfantsCount(0);
		// Response response = flight.getResponse(search);

		List<CuriosityFare> fares = flight.getFare(search);

		for (CuriosityFare fare : fares) {
			System.err.println("Price " + fare.getPrice());
			if (fare.getOutboundSegments() != null) {
				for (CuriositySegment segment : fare.getOutboundSegments()) {
					System.err.println(segment.getAirlineCode() +
										segment.getFlightNumber() +
										" " +
										segment.getDepartureCode() +
										" " +
										segment.getArrivalCode() +
										" " +
										segment.getDepartureTime() +
										" - " +
										segment.getArrivalTime() +
										" " +
										segment.getCarrier());
				}
				System.err.println("");
			} else if (fare.getInboundSegments() != null) {
				for (CuriositySegment segment : fare.getInboundSegments()) {
					System.err.println(segment.getAirlineCode() +
										segment.getFlightNumber() +
										" " +
										segment.getDepartureCode() +
										" " +
										segment.getArrivalCode() +
										" " +
										segment.getDepartureTime() +
										" - " +
										segment.getArrivalTime() +
										" " +
										segment.getCarrier());
				}
				System.err.println("");
			}

		}
	}
}
