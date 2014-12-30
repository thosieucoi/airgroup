import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.airgroup.services.impl.japan.*;
import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;


public class JapanTest {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
			.forPattern("dd-MM-yyyy");

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
	
		CuriositySearch search = new CuriositySearch();
		
		search.setDepartureCode("TYO");
		search.setArrivalCode("HAN");
		search.setAdultsCount(2);
		search.setChildrenCount(1);
		search.setInfantsCount(1);
		search.setOutboundDate(DATE_FORMATTER.parseDateTime("25-10-2013"));
		search.setInboundDate(DATE_FORMATTER.parseDateTime("30-10-2013"));
		
		
		JapanAirlineFlight flight = new JapanAirlineFlight();
		List<CuriosityFare> fares = flight.getFare(search);
		if (fares == null || fares.size() == 0){
			System.out.println("KHONG CO DU LIEU CHO CHUYEN BAY");
			return;
		}
			
		for (int i = 0; i < fares.size();i++){
			CuriosityFare fare = fares.get(i);
			System.err.println("Price = " + fare.getPrice() + " " +  fare.getCurrencyCode());
			if (search.isRoundtrip()== false){
				List<CuriositySegment> segments = fare.getOutboundSegments();
				for (int j = 0; j < segments.size();j++){
					System.err.println("FLIGHT NUMBER   = " + segments.get(j).getAirlineCode() + " "+segments.get(j).getFlightNumber());
					System.err.println("DEPARTURE NAME  = " + segments.get(j).getDepartureName());
					System.err.println("DEPARTURE TIME  = " + segments.get(j).getDepartureTime());
					System.err.println("ARRIVAL NAME    = " + segments.get(j).getArrivalName());
					System.err.println("ARRIVAL TIME    = " + segments.get(j).getArrivalTime());
				}
			}
			else {
				System.err.println("FOR DEPARTURE FLIGHTS ");
				List<CuriositySegment> deparSegments = fare.getOutboundSegments();
				for (int j = 0; j < deparSegments.size();j++){
					System.err.println("FLIGHT NUMBER   = " +deparSegments.get(j).getAirlineCode() + " " + deparSegments.get(j).getFlightNumber());
					System.err.println("DEPARTURE NAME  = " + deparSegments.get(j).getDepartureName());
					System.err.println("DEPARTURE TIME  = " + deparSegments.get(j).getDepartureTime());
					System.err.println("ARRIVAL NAME    = " + deparSegments.get(j).getArrivalName());
					System.err.println("ARRIVAL TIME    = " + deparSegments.get(j).getArrivalTime());
				}
				
				System.err.println("FOR RETURN FLIGHTS");
				List<CuriositySegment> returnSegments = fare.getInboundSegments();
				for (int k = 0; k < returnSegments.size();k++){
					System.err.println("FLIGHT NUMBER   = " + returnSegments.get(k).getAirlineCode() + " " + returnSegments.get(k).getFlightNumber());
					System.err.println("DEPARTURE NAME  = " + returnSegments.get(k).getDepartureName());
					System.err.println("DEPARTURE TIME  = " + returnSegments.get(k).getDepartureTime());
					System.err.println("ARRIVAL NAME    = " + returnSegments.get(k).getArrivalName());
					System.err.println("ARRIVAL TIME    = " + returnSegments.get(k).getArrivalTime());
				}
			}
		}
			
	}
}
