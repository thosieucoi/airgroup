package com.airgroup.services.impl.kenya;

import java.util.ArrayList;

public class FlightInformations {
	private String flightNumber;
	private String recommendationId;
	private String row;
	private String type;
	private ArrayList<FlightInformations> arrivalFlights;
	
	public static String commendation_id;
	public static String flight_id_1;
	public static String flight_id_2;
	
	public void setCommendationId(String commendationId){
		commendation_id = commendationId;
	}
	
	public void setType(String type){
		this.type = type;
	}
	public void setFlightId1(String flight1){
		flight_id_1 = flight1;
	}
	
	public void setFlightId2(String flight2){
		flight_id_2 = flight2;
	}
	public FlightInformations(){
		arrivalFlights = new ArrayList<FlightInformations>();
	}
	public void setFlightNumber(String flightNumber){
		this.flightNumber = flightNumber;
	}
	
	public void setRecommendationId(String recommendationId){
		this.recommendationId = recommendationId;
	}
	
	public void setRow(String row){
		this.row = row;
	}
	
	public void addArrivalFlight(FlightInformations flight){
		arrivalFlights.add(flight);
	}
	
	public String getFlightNumber(){
		return flightNumber;
	}
	
	public String getRecommendationId(){
		return recommendationId;
	}
	
	public String getRow(){
		return row;
	}
	
	public String getType(){
		return type;
	}
	
	public ArrayList<FlightInformations> getArrivalFlightList(){
		return arrivalFlights;
	}
	
	public void  removeArrivalFlight(int position){
		arrivalFlights.remove(position);
	}
	
	
	public int getRecommendatinIdAsInt(){
		return Integer.parseInt(recommendationId);
	}
	
	public boolean equals(Object obj){
		if (!(obj instanceof FlightInformations))
			return false;
		else {
			FlightInformations tmp = (FlightInformations) obj;
			if (!this.row.equals(tmp.getRow()))
				return false;
			else if (arrivalFlights.size() != tmp.getArrivalFlightList().size())
				return false;
			else{
				for (int i = 0; i < arrivalFlights.size();i++){
					if (!arrivalFlights.get(i).getRow().equals(tmp.getArrivalFlightList().get(i).getRow()))
						return false;
				}
			}
			
				
			
		}
		return true;
	}
}
