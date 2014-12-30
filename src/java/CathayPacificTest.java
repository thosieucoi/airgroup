import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.impl.cathaypacific.CathayPacificFlight;
import com.airgroup.services.impl.chinaairlines.ChinaAirlinesFlight;

/**
 * 
 */

/**
 * @author linhnd1
 *
 */
public class CathayPacificTest {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("yyyy-MM-dd");
	public static void main(String[] args) throws Exception
	{
		CathayPacificFlight cathayFlight = new CathayPacificFlight();
		CuriositySearch search = new CuriositySearch();
		search.setDepartureCode("SGN");
		search.setArrivalCode("HKG");
		search.setOutboundDate(DATE_FORMATTER.parseDateTime("2013-09-12"));
		search.setInboundDate(DATE_FORMATTER.parseDateTime("2013-09-20"));
		search.setAdultsCount(1);
		search.setChildrenCount(1);
		search.setInfantsCount(0);
		System.err.println("Pre Load data");
		List<CuriosityFare> fares = cathayFlight.getFare(search); 
		System.err.println("Load data");
		for (CuriosityFare fare : fares) {
			System.err.println("Price " + fare.getPrice());
			System.err.println("Adult Price " + fare.getBasePricePerAdult());
			System.err.println("Children Price " + fare.getBasePricePerChild());
			System.err.println("Currency " + fare.getCurrencyCode());
			if(fare.getInboundSegments()!=null){
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
			else if(fare.getOutboundSegments()!=null){
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
