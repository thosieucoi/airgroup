package com.airgroup.controller.flight_path

import com.airgroup.controller.location.LocationController;
import com.airgroup.domain.CheapFlight;
import com.airgroup.domain.FlightPath;
import com.airgroup.domain.Location;
import grails.converters.JSON

class FlightPathController {
	
	def flightPathService

	def index = {
		redirect(action:"showForm")
	}
	
	def listFlightPath = {
		def flightPath = FlightPath.findAll()
		List<String> list_flight_path = new ArrayList<String>()
		if(flightPath != null){
			//Add "0" at the beginning if it is domestic path, "1" if it is international path
			for(FlightPath fp : flightPath){
				list_flight_path.add("0;" + fp.domesticFlightPath1 + ";" + getDestinationName(fp.domesticFlightPath1) + ";" + getCheapestFlightPrice(fp.domesticFlightPath1))
				list_flight_path.add("0;" + fp.domesticFlightPath2 + ";" + getDestinationName(fp.domesticFlightPath2) + ";" + getCheapestFlightPrice(fp.domesticFlightPath2))
				list_flight_path.add("0;" + fp.domesticFlightPath3 + ";" + getDestinationName(fp.domesticFlightPath3) + ";" + getCheapestFlightPrice(fp.domesticFlightPath3))
				list_flight_path.add("0;" + fp.domesticFlightPath4 + ";" + getDestinationName(fp.domesticFlightPath4) + ";" + getCheapestFlightPrice(fp.domesticFlightPath4))
				list_flight_path.add("0;" + fp.domesticFlightPath5 + ";" + getDestinationName(fp.domesticFlightPath5) + ";" + getCheapestFlightPrice(fp.domesticFlightPath5))
				list_flight_path.add("1;" + fp.internationalFlightPath1 + ";" + getDestinationName(fp.internationalFlightPath1) + ";" + getCheapestFlightPrice(fp.internationalFlightPath1))
				list_flight_path.add("1;" + fp.internationalFlightPath2 + ";" + getDestinationName(fp.internationalFlightPath2) + ";" + getCheapestFlightPrice(fp.internationalFlightPath2))
				list_flight_path.add("1;" + fp.internationalFlightPath3 + ";" + getDestinationName(fp.internationalFlightPath3) + ";" + getCheapestFlightPrice(fp.internationalFlightPath3))
				list_flight_path.add("1;" + fp.internationalFlightPath4 + ";" + getDestinationName(fp.internationalFlightPath4) + ";" + getCheapestFlightPrice(fp.internationalFlightPath4))
				list_flight_path.add("1;" + fp.internationalFlightPath5 + ";" + getDestinationName(fp.internationalFlightPath5) + ";" + getCheapestFlightPrice(fp.internationalFlightPath5))
			}
		}
		render list_flight_path as JSON
	}
	
	def showForm = {
		List<FlightPath> lstFlightPath = flightPathService.getFlightPathList()
		if(lstFlightPath != null && !lstFlightPath.isEmpty()){
			int destinationIndex = lstFlightPath.get(0).domesticFlightPath1.indexOf(";")
			int departureIndex = lstFlightPath.get(0).domesticFlightPath1.indexOf(";") + 1
			int endIndex = lstFlightPath.get(0).domesticFlightPath1.length()
			render(view:"/flightPath/flightPathManagement", model:[lstFlightPath:lstFlightPath, destinationIndex:destinationIndex, departureIndex:departureIndex, endIndex:endIndex])
		} else {
			render(view:"/flightPath/flightPathManagement")
		}
	}
	
	def save = {
		FlightPath flightPath = new FlightPath()
		if(params != null){
			flightPath.domesticFlightPath1 = params.domesticDestination1 + ";" + params.domesticDeparture1
			flightPath.domesticFlightPath2 = params.domesticDestination2 + ";" + params.domesticDeparture2
			flightPath.domesticFlightPath3 = params.domesticDestination3 + ";" + params.domesticDeparture3
			flightPath.domesticFlightPath4 = params.domesticDestination4 + ";" + params.domesticDeparture4
			flightPath.domesticFlightPath5 = params.domesticDestination5 + ";" + params.domesticDeparture5
			flightPath.internationalFlightPath1 = params.internationalDestination1 + ";" + params.internationalDeparture1
			flightPath.internationalFlightPath2 = params.internationalDestination2 + ";" + params.internationalDeparture2
			flightPath.internationalFlightPath3 = params.internationalDestination3 + ";" + params.internationalDeparture3
			flightPath.internationalFlightPath4 = params.internationalDestination4 + ";" + params.internationalDeparture4
			flightPath.internationalFlightPath5 = params.internationalDestination5 + ";" + params.internationalDeparture5
			flightPathService.save(flightPath)
		}
		redirect(action:"showForm")
	}
	
	def getDestinationName(String path){
		if(path.length() > 6){
			String[] destinationArray = path.split(";")
			String destinationCode = destinationArray[1]
			Location location = Location.findByCode(destinationCode)
			if(location != null){
				return location.name
			}
		}
		return ""
	}
	
	def getCheapestFlightPrice(String path){
		String departure = ""
		String destination = ""
		if(path.length() > 6){
			String[] pathArray = path.split(";")
			departure = pathArray[0]
			destination = pathArray[1]
			def cheapFlight = CheapFlight.executeQuery("SELECT DISTINCT min(price) FROM CheapFlight c WHERE c.departure = ? AND c.arrival = ?", [departure, destination])
			if(cheapFlight != null){
				return cheapFlight[0]
			}
		}
		return (double)-1
	}
}