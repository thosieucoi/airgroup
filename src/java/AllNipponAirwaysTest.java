/**
 * 
 */


import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.impl.allnipponairway.AllNipponAirwaysFlight;

public class AllNipponAirwaysTest {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("dd-MM-yyyy");
	public static void main(String[] args) throws Exception
	{
		AllNipponAirwaysFlight allNipponAirwaysFlight = new AllNipponAirwaysFlight();
		CuriositySearch search = new CuriositySearch();
		search.setDepartureCode("HAN");
		search.setArrivalCode("TYO");
		search.setOutboundDate(DATE_FORMATTER.parseDateTime("30-01-2015"));
		//search.setInboundDate(DATE_FORMATTER.parseDateTime("30-10-2013"));
		search.setAdultsCount(1);
		search.setChildrenCount(1);
		search.setInfantsCount(0);
		List<CuriosityFare> fares = allNipponAirwaysFlight.getFare(search); 
		System.err.print("Loaded  "+fares.size());
		for (CuriosityFare fare : fares) {
			System.err.println("Price " + fare.getPrice());
			System.err.println("Currency " + fare.getCurrencyCode());
			System.err.println("Base AdultPrice " + fare.getBasePricePerAdult());
			System.err.println("Base ChildPrice " + fare.getBasePricePerChild());
			if(fare.getOutboundSegments()!=null&&fare.getInboundSegments()!=null){
				for (CuriositySegment segment : fare.getOutboundSegments()) {
					System.err.println("Chuyen Di " +
										segment.getAirlineCode() +
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
					System.err.println("Chuyen ve " +
										segment.getAirlineCode() +
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
			}else if(fare.getInboundSegments()!=null){
				for (CuriositySegment segment : fare.getInboundSegments()) {
					System.err.println("Chuyen ve " +
										segment.getAirlineCode() +
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
			}else if (fare.getOutboundSegments()!= null)
			{
				for (CuriositySegment segment : fare.getOutboundSegments()) {
					System.err.println("Chuyen Di " +
										segment.getAirlineCode() +
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
