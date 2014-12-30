import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import com.airgroup.model.curiosity.CuriosityFare;
import com.airgroup.model.curiosity.CuriositySearch;
import com.airgroup.model.curiosity.CuriositySegment;
import com.airgroup.services.impl.kenya.KenyaAirlineFlight;


public class KenyaTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException, ParseException {
		DateTimeFormatter TIME_FORMATTER_TEST = DateTimeFormat.forPattern("dd/MM/yyyy");
		KenyaAirlineFlight flight = new KenyaAirlineFlight();
		CuriositySearch search = new CuriositySearch();
		search.setDepartureCode("FCO");
		search.setArrivalCode("LHR"); 
		search.setOutboundDate(TIME_FORMATTER_TEST.parseDateTime("25/11/2013"));
		search.setInboundDate(TIME_FORMATTER_TEST.parseDateTime("30/11/2013"));
		search.setAdultsCount(2);
		search.setChildrenCount(1);
		search.setInfantsCount(1);
		List<CuriosityFare> fares = flight.getFare(search);
		if (fares == null)
			System.err.println("KHONG CO THONG TIN VE CHUYEN BAY");
		
		else {
			for (int i = 0; i < fares.size();i++){
				CuriosityFare fare = fares.get(i);
				System.err.println("Price = " + fare.getPrice() + " " +  fare.getCurrencyCode());
				if (!search.isRoundtrip()){
					List<CuriositySegment> segments = fare.getOutboundSegments();
					for (int j = 0; j < segments.size();j++){
						System.err.println("FLIGHT NUMBER   = " +segments.get(j).getCarrier() + " " + segments.get(j).getFlightNumber());
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
						System.err.println("FLIGHT NUMBER   = " + deparSegments.get(j).getCarrier() + " " + deparSegments.get(j).getFlightNumber());
						System.err.println("DEPARTURE NAME  = " + deparSegments.get(j).getDepartureName());
						System.err.println("DEPARTURE TIME  = " + deparSegments.get(j).getDepartureTime());
						System.err.println("ARRIVAL NAME    = " + deparSegments.get(j).getArrivalName());
						System.err.println("ARRIVAL TIME    = " + deparSegments.get(j).getArrivalTime());
					}
					
					System.err.println("FOR RETURN FLIGHTS");
					List<CuriositySegment> returnSegments = fare.getInboundSegments();
					for (int j = 0; j < returnSegments.size();j++){
						System.err.println("FLIGHT NUMBER   = " +returnSegments.get(j).getCarrier() + " " + returnSegments.get(j).getFlightNumber());
						System.err.println("DEPARTURE NAME  = " + returnSegments.get(j).getDepartureName());
						System.err.println("DEPARTURE TIME  = " + returnSegments.get(j).getDepartureTime());
						System.err.println("ARRIVAL NAME    = " + returnSegments.get(j).getArrivalName());
						System.err.println("ARRIVAL TIME    = " + returnSegments.get(j).getArrivalTime());
					}
				}
			}
		}
	}
}
