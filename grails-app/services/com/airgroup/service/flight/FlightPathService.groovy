package com.airgroup.service.flight

import com.airgroup.domain.FlightPath

class FlightPathService {

    static transactional = true

    def save(FlightPath flightPath) {
		List<FlightPath> lstFlightPath = FlightPath.list()
		if((lstFlightPath != null) && (lstFlightPath.toList().size() == 0)){
			flightPath.save()
		} else {
			lstFlightPath.get(0).domesticFlightPath1 = flightPath.domesticFlightPath1
			lstFlightPath.get(0).domesticFlightPath2 = flightPath.domesticFlightPath2
			lstFlightPath.get(0).domesticFlightPath3 = flightPath.domesticFlightPath3
			lstFlightPath.get(0).domesticFlightPath4 = flightPath.domesticFlightPath4
			lstFlightPath.get(0).domesticFlightPath5 = flightPath.domesticFlightPath5
			lstFlightPath.get(0).internationalFlightPath1 = flightPath.internationalFlightPath1
			lstFlightPath.get(0).internationalFlightPath2 = flightPath.internationalFlightPath2
			lstFlightPath.get(0).internationalFlightPath3 = flightPath.internationalFlightPath3
			lstFlightPath.get(0).internationalFlightPath4 = flightPath.internationalFlightPath4
			lstFlightPath.get(0).internationalFlightPath5 = flightPath.internationalFlightPath5
			lstFlightPath.get(0).save()
		}
	}
	
	def getFlightPathList() {
		List<FlightPath> lstFlightPath = FlightPath.list()
		return lstFlightPath
	}
}
