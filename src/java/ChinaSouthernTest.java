import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
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
public class ChinaSouthernTest {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("yyyy-MM-dd");
	public static void main(String[] args) throws Exception
	{
		ChinaSouthernFlight chinaSouthernFlight = new ChinaSouthernFlight();
		CuriositySearch search = new CuriositySearch();
		search.setDepartureCode("SGN");
		search.setArrivalCode("HKG");
		search.setOutboundDate(DATE_FORMATTER.parseDateTime("2013-09-12"));
		search.setInboundDate(DATE_FORMATTER.parseDateTime("2013-09-20"));
		search.setAdultsCount(1);
		search.setChildrenCount(0);
		search.setInfantsCount(0);
		System.err.println("Pre Load data");
		List<CuriosityFare> fares = chinaSouthernFlight.getFare(search); 
		System.err.println("Load data");
		for (CuriosityFare fare : fares) {
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
