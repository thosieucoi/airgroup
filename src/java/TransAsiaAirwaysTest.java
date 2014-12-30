/**
 * 
 */


import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.impl.transasiaairways.TransAsiaAirwaysFlight;
/**
 * @author linhnd1
 *
 */
public class TransAsiaAirwaysTest {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("dd-MM-yyyy");
	public static void main(String[] args) throws Exception
	{
		TransAsiaAirwaysFlight transAsiaAirwaysFlight = new TransAsiaAirwaysFlight();
		CuriositySearch search = new CuriositySearch();
		search.setDepartureCode("MFM");
		search.setArrivalCode("TPE");
		search.setOutboundDate(DATE_FORMATTER.parseDateTime("25-10-2013"));
		search.setInboundDate(DATE_FORMATTER.parseDateTime("30-10-2013"));
		search.setAdultsCount(1);
		search.setChildrenCount(1);
		search.setInfantsCount(0);
		List<CuriosityFare> fares = transAsiaAirwaysFlight.getFare(search); 
		System.err.println("Loaded");
		for (CuriosityFare fare : fares) {
			System.err.println("Price " + fare.getPrice());
			System.err.println("Price per adult " + fare.getBasePricePerAdult());
			System.err.println("Price per child " + fare.getBasePricePerChild());
			System.err.println("Currency " + fare.getCurrencyCode());
			if(fare.getOutboundSegments()!=null && fare.getInboundSegments()!=null){
				for (CuriositySegment segment : fare.getOutboundSegments()) {
					System.err.println("Chuyen Di " +
										segment.getAirlineCode() +
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
					System.err.println("Chuyen ve " +
										segment.getAirlineCode() +
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
			}else if(fare.getInboundSegments()!=null){
				for (CuriositySegment segment : fare.getInboundSegments()) {
					System.err.println("Chuyen ve " +
										segment.getAirlineCode() +
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
			}else if (fare.getOutboundSegments()!=null) {
				for (CuriositySegment segment : fare.getOutboundSegments()) {
					System.err.println("Chuyen Di " +
										segment.getAirlineCode() +
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
