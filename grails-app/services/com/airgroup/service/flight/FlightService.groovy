package com.airgroup.service.flight

import java.util.regex.Matcher
import java.util.regex.Pattern

import org.joda.time.DateTime
import org.joda.time.Hours
import org.joda.time.Minutes
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

import com.airgroup.domain.Airline
import com.airgroup.domain.Country
import com.airgroup.domain.Fee
import com.airgroup.domain.Location
import com.airgroup.domain.Rate
import com.airgroup.model.Flight
import com.airgroup.model.SimpleFare
import com.airgroup.model.curiosity.CuriosityFare
import com.airgroup.model.curiosity.CuriositySearch
import com.airgroup.model.curiosity.CuriositySegment
import com.airgroup.services.SearchFlights
import com.airgroup.services.impl.abacus.AbacusFlight
import com.airgroup.services.impl.aeroflot.AeroflotFlight
import com.airgroup.services.impl.airasia.AirAsiaFlight
import com.airgroup.services.impl.airchina.AirChinaFlight
import com.airgroup.services.impl.airfrance.AirfranceFlight
import com.airgroup.services.impl.allnipponairway.AllNipponAirwaysFlight
import com.airgroup.services.impl.americanairline.AmericanAirlinesFlight
import com.airgroup.services.impl.britishairways.BritishAirwaysFlight
import com.airgroup.services.impl.cathaypacific.CathayPacificFlight
import com.airgroup.services.impl.cebupacificair.CebupacificFlight
import com.airgroup.services.impl.chinaairlines.ChinaAirlinesFlight
import com.airgroup.services.impl.chinaeaternairlines.ChinaEaternAirlinesFlight
import com.airgroup.services.impl.chinasouthern.ChinaSouthernFlight
import com.airgroup.services.impl.deltaairlines.DeltaAirlinesFlight
import com.airgroup.services.impl.edreams.EdreamsFlight
import com.airgroup.services.impl.emirates1.EmiratesFlight
import com.airgroup.services.impl.ethiopianairlines.EthiopianAirlinesFlight
import com.airgroup.services.impl.eva.EvaFlight
import com.airgroup.services.impl.hongkongairlines.HongKongAirlinesFlight
import com.airgroup.services.impl.japan.JapanAirlineFlight
import com.airgroup.services.impl.jetstar.JetstarFlight
import com.airgroup.services.impl.kenya.KenyaAirlineFlight
import com.airgroup.services.impl.koreanair.KoreanFlight
import com.airgroup.services.impl.lufthansa.LufthansaFlight
import com.airgroup.services.impl.malaysiaairlines.MalaysiaAirlinesFlight
import com.airgroup.services.impl.philippineairlines.PhilippineAirlinesFlight
import com.airgroup.services.impl.qatarairway.QatarAirwayParser
import com.airgroup.services.impl.singaporeair.SingaporeAirlinesFlight
import com.airgroup.services.impl.southafricanairways.SouthAfricanAirwaysFlight
import com.airgroup.services.impl.thaiairways.ThaiAirwaysFlight
import com.airgroup.services.impl.transasiaairways.TransAsiaAirwaysFlight
import com.airgroup.services.impl.united.UnitedFlight
import com.airgroup.services.impl.vietjetAir.VietjetAirFlight
import com.airgroup.services.impl.vietnamairlines.VietnamAirlinesFlight

class FlightService {
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd");
	//I 1
	SearchFlights edreamFlight=new EdreamsFlight()
	SearchFlights qatarFlight=new QatarAirwayParser()
	SearchFlights vietnamairlineFlight=new VietnamAirlinesFlight()
	SearchFlights unitedFlight=new UnitedFlight()
	SearchFlights vietjetAir=new VietjetAirFlight()
	SearchFlights aeroflotFlight=new AeroflotFlight()
	SearchFlights jetstarFlight=new JetstarFlight()
	SearchFlights cebupacificFlight=new CebupacificFlight()
	SearchFlights malaysiaairlinesFlight=new MalaysiaAirlinesFlight()
	SearchFlights philippineairlinesFlight=new PhilippineAirlinesFlight()
	SearchFlights southafricanairwaysFlight=new SouthAfricanAirwaysFlight()
	//I 2
	SearchFlights britishairwaysFlight=new BritishAirwaysFlight()
	SearchFlights cathaypacificFlight=new CathayPacificFlight()
	SearchFlights airchinaAirways=new AirChinaFlight()
	SearchFlights chinaeasternairlineFlight=new ChinaEaternAirlinesFlight()
	SearchFlights evaFlight=new EvaFlight()
	SearchFlights lufthansaFlight=new LufthansaFlight()
	SearchFlights transasiaairwaysFlights=new TransAsiaAirwaysFlight()
	SearchFlights chinaairlineFlights=new ChinaAirlinesFlight()
	SearchFlights allnipponairwaysFlights=new AllNipponAirwaysFlight()
	SearchFlights chinasouthernFlights=new ChinaSouthernFlight()
	SearchFlights emiratesFlights=new EmiratesFlight()
	SearchFlights hongkongFlights=new HongKongAirlinesFlight()
	SearchFlights koreanAirFlight=new KoreanFlight()
	SearchFlights japanAirWays=new JapanAirlineFlight()
	// I3
	SearchFlights ethiopianFlight=new EthiopianAirlinesFlight()
	SearchFlights singaporeFlight=new SingaporeAirlinesFlight()
	SearchFlights thaiFlight=new ThaiAirwaysFlight()
	SearchFlights americanFlight=new AmericanAirlinesFlight()
	SearchFlights airfranceFlight=new AirfranceFlight()
	SearchFlights kenyaFlight=new KenyaAirlineFlight()
	SearchFlights deltaFlight=new DeltaAirlinesFlight()
	SearchFlights airasiaFlight = new AirAsiaFlight()

	//Abacus
	SearchFlights abacusFlight=new AbacusFlight()

	List<SimpleFare> localInboundFare=[]
	List<SimpleFare> localOutboundFare=[]


//	def getEdreamsFares(def params){
//		try{
//			CuriositySearch search=getSearchObject(params)
//			List<CuriosityFare> fares= edreamFlight.getFare(search)
//			return convertToList( fares)
//		}catch(Exception e){
//			println e.getMessage()
//			return null
//		}
//	}

	def getQatarFares(def params){
		println 'getQatarFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= qatarFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getQatarFares'
			return null
		}
	}
	def getVietnamairlinesFares(def params){
		println 'getVietnamairlinesFares'
		try{
			CuriositySearch search=getSearchObject(params)
			if(search.getDepartureCode().equals("CXR")){
				search.setDepartureCode("NHA")
			}
			if(search.getArrivalCode().equals("CXR")){
				search.setArrivalCode("NHA")
			}
			List<CuriosityFare> fares= vietnamairlineFlight.getFare(search)
			printf 'VietnamAirline FARE PRICE=' + fares.get(0).getPrice()
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getVietnamairlinesFares'
			//println e.printStackTrace()
			return null
		}
	}

	def getUnitedFares(def params){
		println 'getUnitedFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= unitedFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getUnitedFares'
			return null
		}
	}
	def getVietjetFares(def params){
		println 'getVietjetFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= vietjetAir.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getVietjetFares'
			return null
		}
	}
	def getAeroflotFares(def params){
		println 'getAeroflotFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= aeroflotFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getAeroflotFares'
			return null
		}
	}
	def getJetstarFares(def params){
		println 'getJetstarFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= jetstarFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getJetstarFares'
			return null
		}
	}
	def getCebupacificFares(def params){
		println 'getCebupacificFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= cebupacificFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getCebupacificFares'
			return null
		}
	}
	def  getMalaysiaairlinesFares(def params){
		println 'getMalaysiaairlinesFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= malaysiaairlinesFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getMalaysiaairlinesFares'
			return null
		}
	}
	def  getPhilippineairlinesFares(def params){
		println 'getPhilippineairlinesFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= philippineairlinesFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getPhilippineairlinesFares'
			return null
		}
	}
	def  getSouthafricanairwaysFares(def params){
		println 'getSouthafricanairwaysFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= southafricanairwaysFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getSouthafricanairwaysFares'
			return null
		}
	}

	//I 2
	def  getBritishairwaysFares(def params){
		println 'getBritishairwaysFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= britishairwaysFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getBritishairwaysFares'
			return null
		}
	}
	def  getCathaypacificFares(def params){
		println 'getCathaypacificFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= cathaypacificFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getCathaypacificFares'
			return null
		}
	}
	def  getTransasiaairwaysFares(def params){
		println 'getTransasiaairwaysFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= transasiaairwaysFlights.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getTransasiaairwaysFares'
			return null
		}
	}
	def  getEvaFares(def params){
		println 'getEvaFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= evaFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getEvaFares'
			return null
		}
	}
	def  getLufthansaFares(def params){
		println 'getLufthansaFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= lufthansaFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getLufthansaFares'
			return null
		}
	}
	def  getChinaeasternairlineFares(def params){
		println 'getChinaeasternairlineFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= chinaeasternairlineFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getChinaeasternairlineFares'
			return null
		}
	}
	def  getAllnipponairwaysFares(def params){
		println 'getAllnipponairwaysFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= allnipponairwaysFlights.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getAllnipponairwaysFares'
			return null
		}
	}
	def  getChinaairlineFares(def params){
		println 'getChinaairlineFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= chinaairlineFlights.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getChinaairlineFares'
			return null
		}
	}
	def  getChinasouthernFares(def params){
		println 'getChinasouthernFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= chinasouthernFlights.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getChinasouthernFares'
			return null
		}
	}
	def  getEmiratesFares(def params){
		println 'getEmiratesFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= emiratesFlights.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getEmiratesFares'
			return null
		}
	}
	def  getHongkongFares(def params){
		println 'getHongkongFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= hongkongFlights.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getHongkongFares'
			return null
		}
	}

	def  getKoreanFares(def params){
		println 'getKoreanFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= koreanAirFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getKoreanFares'
			return null
		}
	}
	def  getJapanFares(def params){
		println 'getJapanFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= japanAirWays.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getJapanFares'
			e.printStackTrace()
			return null
		}
	}
	def  getAirchinaFares(def params){
		println 'getAirchinaFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= airchinaAirways.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getAirchinaFares'
			e.printStackTrace()
			return null
		}
	}

	//I 3

	def  getEthiopiaFares(def params){
		println 'getEthiopiaFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares=ethiopianFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getEthiopiaFares'
			e.printStackTrace()
			return null
		}
	}

	def getSingaporeFares(def params){
		println 'getSingaporeFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= singaporeFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getSingaporeFares'
			e.printStackTrace()
			return null
		}
	}
	def getThaiFares(def params){
		println 'getThaiFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= thaiFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getThaiFares'
			e.printStackTrace()
			return null
		}
	}

	def getAmericanFares(def params){
		println 'getAmericanFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= americanFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getAmericanFares'
			e.printStackTrace()
			return null
		}
	}
	def getAirfranceFares(def params){
		println 'getAirfranceFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= airfranceFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getAmericanFares'
			e.printStackTrace()
			return null
		}
	}
	def getKenyaFares(def params){
		println 'getKenyaFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= kenyaFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getKenyaFares'
			e.printStackTrace()
			return null
		}
	}
	def getDeltaFares(def params){
		println 'getDeltaFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= deltaFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getDeltaFares'
			e.printStackTrace()
			return null
		}
	}

	def getAbacusFares(def params){
		println 'getAbacusFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= abacusFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getAbacusFares'
			return null
		}
	}

	def getAirAsiaFares(def params){
		println 'getAirAsiaFares'
		try{
			CuriositySearch search=getSearchObject(params)
			List<CuriosityFare> fares= airasiaFlight.getFare(search)
			return convertToList(fares, params)
		}catch(Exception e){
			println e.getMessage()+':getAirAsiaFares'
			return null
		}
	}

	CuriositySearch getSearchObject(def params){

		CuriositySearch search=new CuriositySearch()
		search.setDepartureCode(getCode(params.departureCode))
		search.setArrivalCode(getCode(params.arrivalCode))
		if(params.omonth.toInteger()!=0&&params.oday.toInteger()!=0){
			if(params.omonth.toInteger()<(new Date().getMonth()+1)){
				Calendar c=Calendar.getInstance()
				c.add(Calendar.YEAR, 1)
				search.setOutboundDate(DATE_TIME_FORMATTER.parseDateTime(c.get(Calendar.YEAR)+params.omonth+params.oday))
			}else if(params.omonth.toInteger()==(new Date().getMonth()+1)&&params.oday.toInteger()<(new Date().getDate())){
				Calendar c=Calendar.getInstance()
				c.add(Calendar.YEAR, 1)
				search.setOutboundDate(DATE_TIME_FORMATTER.parseDateTime(c.get(Calendar.YEAR)+params.omonth+params.oday))
			}else{
			search.setOutboundDate(DATE_TIME_FORMATTER.parseDateTime(Calendar.getInstance().get(Calendar.YEAR)+params.omonth+params.oday))
			}
		}
		if(params.imonth.toInteger()!=0&&params.iday.toInteger()!=0){
			if(params.imonth.toInteger()<params.omonth.toInteger() || params.imonth.toInteger()<(new Date().getMonth()+1)){
				Calendar c=Calendar.getInstance()
				c.add(Calendar.YEAR, 1)
				search.setInboundDate(DATE_TIME_FORMATTER.parseDateTime(c.get(Calendar.YEAR)+params.imonth+params.iday))
			}
			else if(params.imonth.toInteger()==(new Date().getMonth()+1)&&params.iday.toInteger()<(new Date().getDate())){
				Calendar c=Calendar.getInstance()
				c.add(Calendar.YEAR, 1)
				search.setOutboundDate(DATE_TIME_FORMATTER.parseDateTime(c.get(Calendar.YEAR)+params.omonth+params.oday))
			}else{
				search.setInboundDate(DATE_TIME_FORMATTER.parseDateTime(Calendar.getInstance().get(Calendar.YEAR)+params.imonth+params.iday))
			}
		}
		if(params.adults.toInteger() > 6){
			search.setAdultsCount(6)
		}else{
			search.setAdultsCount(params.adults.toInteger())
		}
		search.setChildrenCount(params.kids.toString().toInteger())
		search.setInfantsCount(params.infants.toString().toInteger())
		search.setDomestic(new Boolean(params.isDomestic+""))
		return search;
	}
	String getCode(String str){
		int i=str.lastIndexOf("(")
		int j=str.lastIndexOf(")")
		return str.substring(i+1, j);
	}
	private void setArrivalCity(Location location,Flight flight){
		if(location!=null){
			if(location.city_id!='null'){
				Location lc=Location.findById(location.city_id)
				if(lc!=null){
					flight.setArrivalCity(lc.name)
				}else{
					flight.setArrivalCity("Undefined")
				}
			}else{
				flight.setArrivalCity(location.getCountry_name())
			}
		}else{
			flight.setArrivalCity("Undefined")
		}
	}

	private void setDepatureCity(Location location,Flight flight){
		if(location!=null){
			if(location.city_id!='null'){
				Location lc=Location.findById(location.city_id)
				if(lc!=null){
					flight.setDepartureCity(lc.name)
				}else{
					flight.setDepartureCity("Undefined")
				}
			}
			else{
				flight.setDepartureCity(location.getCountry_name())
			}
		}else{
			flight.setDepartureCity("Undefined")
		}
	}

	List<SimpleFare> convertToList(List<CuriosityFare> fares, def params){
		//Regex to separate airline code from designatorCode
		Pattern ptt=Pattern.compile("\\d");

		DateTimeFormatter dtf=DateTimeFormat.forPattern("HH:mm")
		DateTimeFormatter dtfForDuration=DateTimeFormat.forPattern("HH\\h+mm\\m")
		DateTimeFormatter dtfForSegment=DateTimeFormat.forPattern("dd/MM/yyyy")
		DateTime dt=null;
		CuriositySearch search = getSearchObject(params)

		String country = Location.findByCode(search.getArrivalCode()).country_code
		String continental = Country.findByCode(country).continental

		List<SimpleFare> list=[]
		for (CuriosityFare fare : fares) {
			SimpleFare simpleFare = new SimpleFare();
			simpleFare.setCurrencyCode(fare.getCurrencyCode());

			simpleFare.airlineCode=fare.getAirlineCode()
			simpleFare.airlineName=(Airline.findByCode(fare.getAirlineCode())).name

			simpleFare.setBasePrice(fare.getBasePrice())
			simpleFare.setBasePricePerAdult(fare.getBasePricePerAdult())
			simpleFare.setBasePricePerChild(fare.getBasePricePerChild())
			simpleFare.setDescription(fare.getDescription())
			simpleFare.setComfortIndex(fare.getComfortIndex())
			simpleFare.setFareType(fare.getFareType())
			simpleFare.setId(fare.getId())
			simpleFare.setPriceUsd(fare.getPriceUsd())
			simpleFare.setProviderCode(fare.getProviderCode())
			List<CuriositySegment> outboundSegments= fare.getOutboundSegments()
			List<CuriositySegment> inboundSegments=fare.getInboundSegments()
			if(outboundSegments!=null){
				simpleFare.setOutboundSegmentsCount(outboundSegments.size())
				simpleFare.setOutboundDepartureDayTime(fare.getOutboundDepartureDayTime())
				simpleFare.setOutboundStopoverDuration(fare.getOutboundStopoverDuration())
				simpleFare.setOutboundArrivalCode(fare.getOutboundArrivalCode())
				simpleFare.setOutboundDepartureCode(fare.getOutboundDepartureCode())
				simpleFare.setOutboundDepartureTime(dtf.print(fare.getOutboundDepartureTime()))
				simpleFare.setOutboundArrivalTime(dtf.print(fare.getOutboundArrivalTime()))
				simpleFare.setOutboundNumberOfStopover(outboundSegments.size()-1)
				CuriositySegment firstSegment=outboundSegments.get(0)
				CuriositySegment lastSegment=outboundSegments.get(outboundSegments.size()-1)
				DateTime firstSegmentDepatureTime=firstSegment.departureTime
				DateTime lastSegmentArrivalTime=lastSegment.arrivalTime
				simpleFare.setOutboundDuration(String.format("%02d",Hours.hoursBetween(firstSegmentDepatureTime, lastSegmentArrivalTime).getHours())+'h'+" + "+String.format("%02d",Minutes.minutesBetween(firstSegmentDepatureTime, lastSegmentArrivalTime).getMinutes()%60)+"m")

				int o=0
				for (CuriositySegment segment : outboundSegments) {
					Flight flight=new Flight();
				flight.setFlightNumber(segment.getFlightNumber())
				flight.setDepartureCode(segment.getDepartureCode())
				flight.setArrivalCode(segment.getArrivalCode())
				dt=segment.getDepartureTime()
				flight.setDepartureTime(dtf.print(dt))
				flight.setDepartureDate(dtfForSegment.print(dt))

				dt=segment.getArrivalTime()
				flight.setArrivalTime(dtf.print(dt))
				flight.setArrivalDate(dtfForSegment.print(dt))

				flight.setAircraftType(segment.getAircraftType())
				flight.setDesignatorCode(segment.getDesignatorCode())

				//get first index of flight number
				Matcher matcher = ptt.matcher(segment.getDesignatorCode());
				Integer indexOfFlightNumber=0;
				indexOfFlightNumber= matcher.find() ?matcher.start():0


				flight.setOperatingAirlineCode(segment.getOperatingAirlineCode())
				flight.setOperatingFlightNumber(segment.getOperatingFlightNumber())

				if(segment.getDepartureCode()!=null){
					Location depatureAirport=Location.findWhere(code : segment.getDepartureCode(), location_type: 'airport')
//					Location depatureAirport=Location.findByCode(segment.getDepartureCode())
					flight.setDepartureAirport(depatureAirport.name)
					flight.setDepartureCity(Location.findById(depatureAirport.city_id).name)
				}else if(segment.getDepartureName()!=null){
					flight.setDepartureAirport(segment.getDepartureName())
					Location depatureAirport=Location.findByName(segment.getDepartureName())
					setDepatureCity(depatureAirport, flight)
					flight.setDepartureCode(flight.getDepartureCity().equals('Undefined')?segment.getDepartureName():flight.getDepartureCity())
				}

				if(segment.getArrivalCode()!=null){
					Location arrivalAirport=Location.findWhere(code : segment.getArrivalCode(), location_type: 'airport')
					flight.setArrivalAirport(arrivalAirport.name)
					flight.setArrivalCity(Location.findById(arrivalAirport.city_id).name)
				}else	if(segment.getArrivalName()!=null){
					flight.setArrivalAirport(segment.getArrivalName())
					Location arrivalAirport=Location.findByName(segment.getArrivalName())
					setArrivalCity(arrivalAirport, flight)
					flight.setArrivalCode(flight.getArrivalCity().equals('Undefined')?segment.getArrivalName():flight.getArrivalCity())
				}
				flight.setCabin(segment.getCabin().toString())
				flight.setCarrier(segment.getCarrier())
				if(o>0){
					int current=o-1
					simpleFare.getOutboundStopoverDurations().add(
					Hours.hoursBetween(outboundSegments.get(current).getArrivalTime(), segment.getDepartureTime()).getHours()+'hr'+" + "+
					Minutes.minutesBetween(outboundSegments.get(current).getArrivalTime(), segment.getDepartureTime()).getMinutes()%60+"min"
					)
				}
				++o
				if(indexOfFlightNumber!=0){
					flight.setAirlineCode(segment.getDesignatorCode().substring(0,indexOfFlightNumber))
					if(segment.getCarrier()==null){
						def ai=Airline.findByCode(flight.getAirlineCode())
						if(ai!=null){
							flight.setCarrier(ai.name)
						}
					}
				}
				else if(segment.getAirlineCode()!=null){
					flight.setAirlineCode(segment.getAirlineCode())
					if(segment.getCarrier()==null){
						def ai=Airline.findByCode(segment.getAirlineCode())
						if(ai!=null){
							flight.setCarrier(ai.name)
						}
					}
			}else if(segment.getAirlineCode()==null&&segment.getCarrier()!=null){

				def ai=Airline.findByName(segment.getCarrier())
				if(ai!=null){
					flight.setAirlineCode(ai.code)
				}
			}
				simpleFare.oflights.add(flight)
			}
		}
		if(inboundSegments!=null){
			simpleFare.setInboundSegmentsCount(inboundSegments.size())
			simpleFare.setInboundStopoverDuration(fare.getInboundStopoverDuration())
			simpleFare.setInboundDepartureDayTime(fare.getInboundDepartureDayTime())
			simpleFare.setInboundDepartureCode(fare.getInboundDepartureCode())
			simpleFare.setInboundArrivalCode(fare.getInboundArrivalCode())
			simpleFare.setInboundDepartureTime(dtf.print(fare.getInboundDepartureTime()))
			simpleFare.setInboundArrivalTime(dtf.print(fare.getInboundArrivalTime()))
			simpleFare.setInboundNumberOfStopover(inboundSegments.size()-1)

			CuriositySegment firstSegment=inboundSegments.get(0)
			CuriositySegment lastSegment=inboundSegments.get(inboundSegments.size()-1)
			DateTime firstSegmentDepatureTime=firstSegment.departureTime
			DateTime lastSegmentArrivalTime=lastSegment.arrivalTime
			simpleFare.setInboundDuration(String.format("%02d",Hours.hoursBetween(firstSegmentDepatureTime, lastSegmentArrivalTime).getHours())+'h'+" + "+String.format("%02d",Minutes.minutesBetween(firstSegmentDepatureTime, lastSegmentArrivalTime).getMinutes()%60)+"m")
			int i=0
			for (CuriositySegment segment : inboundSegments) {

				Flight flight=new Flight();
				flight.setFlightNumber(segment.getFlightNumber())
				flight.setDepartureCode(segment.getDepartureCode())
				flight.setArrivalCode(segment.getArrivalCode())
				dt=segment.getDepartureTime()
				flight.setDepartureTime(dtf.print(dt))
				flight.setDepartureDate(dtfForSegment.print(dt))

				dt=segment.getArrivalTime()
				flight.setArrivalTime(dtf.print(dt))
				flight.setArrivalDate(dtfForSegment.print(dt))
				flight.setAircraftType(segment.getAircraftType())
				flight.setDesignatorCode(segment.getDesignatorCode())

				Matcher matcher = ptt.matcher(segment.getDesignatorCode());
				Integer indexOfFlightNumber=0;
					indexOfFlightNumber= matcher.find() ?matcher.start():0


				flight.setOperatingAirlineCode(segment.getOperatingAirlineCode())
				flight.setOperatingFlightNumber(segment.getOperatingFlightNumber())
				if(segment.getDepartureCode()!=null){
					Location depatureAirport=Location.findByCode(segment.getDepartureCode())
					flight.setDepartureAirport(depatureAirport.name)
					flight.setDepartureCity(Location.findById(depatureAirport.city_id).name)

				}else if(segment.getDepartureName()!=null){
					flight.setDepartureAirport(segment.getDepartureName())
					Location depatureAirport=Location.findByName(segment.getDepartureName())
					setDepatureCity(depatureAirport, flight)
					flight.setDepartureCode(flight.getDepartureCity().equals('Undefined')?segment.getDepartureName():flight.getDepartureCity())
				}

				if(segment.getArrivalCode()!=null){
					Location arrivalAirport=Location.findByCode(segment.getArrivalCode())
					flight.setArrivalAirport(arrivalAirport.name)
					flight.setArrivalCity(Location.findById(arrivalAirport.city_id).name)
				}else	if(segment.getArrivalName()!=null){
					flight.setArrivalAirport(segment.getArrivalName())
					Location arrivalAirport=Location.findByName(segment.getArrivalName())
					setArrivalCity(arrivalAirport, flight)
					flight.setArrivalCode(flight.getArrivalCity().equals('Undefined')?segment.getArrivalName():flight.getArrivalCity())
				}
				flight.setCabin(segment.getCabin().toString())
				flight.setCarrier(segment.getCarrier())
				if(i>0){
					int current=i-1
					simpleFare.getInboundStopoverDurations().add(
					Hours.hoursBetween(inboundSegments.get(current).getArrivalTime(), segment.getDepartureTime()).getHours()+'hr'+" "+
					Minutes.minutesBetween(inboundSegments.get(current).getArrivalTime(), segment.getDepartureTime()).getMinutes()%60+"min"
					)
				}
				++i
				if(indexOfFlightNumber!=0){
					flight.setAirlineCode(segment.getDesignatorCode().substring(0,indexOfFlightNumber))
					if(segment.getCarrier()==null){
						def ai=Airline.findByCode(flight.getAirlineCode())
						if(ai!=null){
							flight.setCarrier(ai.name)
						}
					}
				}
				else if(segment.getAirlineCode()!=null){
					flight.setAirlineCode(segment.getAirlineCode())
					if(segment.getCarrier()==null){
						def ai=Airline.findByCode(segment.getAirlineCode())
						if(ai!=null){
							flight.setCarrier(ai.name)
						}
					}
			}else if(segment.getAirlineCode()==null&&segment.getCarrier()!=null){
				def ai=Airline.findByName(segment.getCarrier())
				if(ai!=null){
					flight.setAirlineCode(ai.code)
				}
			}
				simpleFare.iflights.add(flight);
			}
		}

		def fee
		if(continental == "AS" || continental == "EU" || continental == "AF" || continental == "SEA"){
			fee = search.isRoundtrip() ? (Fee.findByCode(continental).price * 2) : (Fee.findByCode(continental).price)
		}
		else if(continental == "NA" || continental == "SA"){
			fee = search.isRoundtrip() ? (Fee.findByCode("AM").price * 2) : (Fee.findByCode("AM").price)
		}
		else{
			fee = search.isRoundtrip() ? (Fee.findByCode("OT").price * 2) : (Fee.findByCode("OT").price)
		}

		int numsOfStop = 0
		if(inboundSegments!=null){
			if(search.isDomestic()){
				numsOfStop = simpleFare.inboundNumberOfStopover + 1
			}else{
			numsOfStop = simpleFare.outboundNumberOfStopover + 1 + simpleFare.inboundNumberOfStopover + 1
			}
		}
		else{
			numsOfStop = simpleFare.outboundNumberOfStopover + 1
		}

		if(search.isDomestic()){
			if(simpleFare.getAirlineCode().equals("VN")){
				simpleFare.setPrice((fare.getPrice() + (Fee.findByCode("VN").price * numsOfStop * search.getPassengersCount())).toFloat())
				simpleFare.setPricePerAdult((fare.getPricePerAdult() + (Fee.findByCode("VN").price * numsOfStop)).toFloat())
				simpleFare.setPricePerChild((fare.getPricePerChild() + (Fee.findByCode("VN").price * numsOfStop)).toFloat())
			}else if(simpleFare.getAirlineCode().equals("VJ")){
				simpleFare.setPrice((fare.getPrice() + (Fee.findByCode("VJ").price * numsOfStop * search.getPassengersCount())).toFloat())
				simpleFare.setPricePerAdult((fare.getPricePerAdult() + (Fee.findByCode("VJ").price * numsOfStop)).toFloat())
				simpleFare.setPricePerChild((fare.getPricePerChild() + (Fee.findByCode("VJ").price * numsOfStop)).toFloat())
			}else if(simpleFare.getAirlineCode().equals("BL")){
				simpleFare.setPrice((fare.getPrice() + (Fee.findByCode("BL").price * numsOfStop * search.getPassengersCount())).toFloat())
				simpleFare.setPricePerAdult((fare.getPricePerAdult() + (Fee.findByCode("BL").price * numsOfStop)).toFloat())
				simpleFare.setPricePerChild((fare.getPricePerChild() + (Fee.findByCode("BL").price * numsOfStop)).toFloat())
			}

		}
		else{
			if(!fare.getCurrencyCode().equals("VND")){
				String currencyCode=fare.getCurrencyCode()
				Rate rateobj=Rate.findByCode(currencyCode)
				String rate=rateobj.rate.replace(",","")
				simpleFare.setPrice((((float)fare.getPrice()*(new Float(rate))) + (fee * search.getPassengersCount())).toFloat())
				simpleFare.setPricePerAdult((((float)fare.getPricePerAdult()*(new Float(rate))) + (fee)).toFloat())
				simpleFare.setPricePerChild((((float)fare.getPricePerChild()*(new Float(rate))) + (fee)).toFloat())
				simpleFare.setCurrencyCode("VND")
			}
			else{
				simpleFare.setPrice((fare.getPrice() + (fee * search.getPassengersCount())).toFloat())
				simpleFare.setPricePerAdult((fare.getPricePerAdult() + (fee)).toFloat())
				simpleFare.setPricePerChild((fare.getPricePerChild() + (fee)).toFloat())
			}
		}

		list.add(simpleFare)
	}
	return list
}


void separateLocalInboundOutboundFares(params){
	List<SimpleFare> l1= getVietnamairlinesFares(params)
	List<SimpleFare> l2=getVietjetFares(params)
	List<SimpleFare> l3=getJetstarFares(params)
	for(SimpleFare item:l1){
		if(item.getIflights().size>0){
			this.localInboundFare.add(item)
		}
		if(item.getOflights().size>0){
			this.localOutboundFare.add(item)
		}
	}
	for(SimpleFare item:l2){
		if(item.getIflights().size>0){
			this.localInboundFare.add(item)
		}
		if(item.getOflights().size>0){
			this.localOutboundFare.add(item)
		}
	}
	for(SimpleFare item:l3){
		if(item.getIflights().size>0){
			this.localInboundFare.add(item)
		}
		if(item.getOflights().size>0){
			this.localOutboundFare.add(item)
		}
	}

}
//[precondition]: separateLocalInboundOutboundFares must be call before
SimpleFare getInboundRandom(){
	Random random=new Random()
	int i=random.nextInt(localInboundFare.size())
	return localInboundFare.get(i)
}
SimpleFare getOutboundRandom(){
	Random random=new Random()
	int i=random.nextInt(localOutboundFare.size())
	return localOutboundFare.get(i)
}

static void main(String[] args) {
	def params=[departureCode:"Ha noi (TYO)",arrivalCode:"Sai gon (HAN)",iday:"30",imonth:"10",oday:"25",omonth:"10",adults:"1",kids:"0",infants:"0"]
	FlightService s=new FlightService()
	//		List<SimpleFare> l= s.getPhilippineairlinesFares(params)
	//		List<SimpleFare> l= s.getEdreamsFares(params)
	//		List<SimpleFare> l= s.getQatarFares(params)
	//			List<SimpleFare> l= s.getVietnamairlinesFares(params)
	//				List<SimpleFare> l= s.getVietjetFares(params)
	//				List<SimpleFare> l= s.getUnitedFares(params)
	//		List<SimpleFare> l=s.getJetstarFares(params)
//	List<SimpleFare> l= s.getSingaporeFares(params)
	List<SimpleFare> l=s.getJapanFares(params)
	for (SimpleFare item : l) {

		for (Flight f : item.getIflights()) {
			println "------------Inbound-----"
			println "..............."+ item.getPrice()
			println f.airlineCode+":"+f.flightNumber+':'+f.departureCode+":"+f.arrivalCode+":"+f.departureTime+":"+f.arrivalTime+":"
		}
		for (Flight f : item.getOflights()) {
			println "------------Outbound-----"
			println "..............."+ item.getPrice()
			println f.airlineCode+":"+f.flightNumber+':'+f.departureCode+":"+f.arrivalCode+":"+f.departureTime+":"+f.arrivalTime
		}
	}

}
}
