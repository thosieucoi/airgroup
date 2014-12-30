package com.airgroup.model

import java.util.List
import java.util.Map

import org.joda.time.DateTime

import com.airgroup.model.FareType;

class SimpleFare {
 String id
 String searchId
 String tripId
 Float price
 Float basePrice
 Boolean taxAndFeeExclusive
 Float priceUsd
 Float pricePerAdult
 Float basePricePerAdult
 Float pricePerChild
 Float basePricePerChild
 String currencyCode
 String outboundDepartureCode
 String outboundArrivalCode
 Integer outboundSegmentsCount
 String outboundDepartureTime
 Integer outboundDepartureDayTime
 String outboundArrivalTime
 Integer outboundArrivalDayTime
 String outboundDuration
 Integer outboundStopoverDuration
 String inboundDepartureCode
 String inboundArrivalCode
 Integer inboundSegmentsCount
 String inboundDepartureTime
 Integer inboundDepartureDayTime
 String inboundArrivalTime
 Integer inboundArrivalDayTime
 String inboundDuration
 Integer inboundStopoverDuration
 String providerCode
 FareType fareType
 String description
 String url
 String createdAt
 Float outboundComfortIndex
 Float inboundComfortIndex
 Float comfortIndex
 List<String> outboundStopoverDurations=[]
 List<String> inboundStopoverDurations=[]
 Integer inboundNumberOfStopover
 Integer outboundNumberOfStopover
 String airlineCode
 String airlineName
 List<Flight> iflights=[]
 List<Flight> oflights=[]	public SimpleFare(){
		
	}
	public SimpleFare(Float price, String currencyCode) {
		super()
		this.price = price
		this.currencyCode = currencyCode
	}
	
}
