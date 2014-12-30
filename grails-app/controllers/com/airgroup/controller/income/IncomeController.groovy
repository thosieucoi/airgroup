/**
 *
 */
package com.airgroup.controller.income

import com.airgroup.domain.Country
import com.airgroup.domain.Fee
import com.airgroup.domain.Location
import com.airgroup.domain.Order
import com.airgroup.domain.OrderDetail
import com.airgroup.util.Utils

/**
 * @author linhnd1
 *
 */
class IncomeController {
	def index = { redirect(action: "totalIncome") }

	def totalIncome = {

		def cal = Calendar.instance
		def year = cal.get(Calendar.YEAR)
		def month = Utils.numberNormalize(cal.get(Calendar.MONTH) + 1 + "")
		def day = Utils.numberNormalize(cal.get(Calendar.DATE) + "")
		def dateTimeFormat = new java.text.SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		def fromDate = dateTimeFormat.parse("01/" + month + "/" + year + " 00:00:00")
		def toDate = dateTimeFormat.parse(day + "/" + month + "/" + year + " 23:59:59")

		if(params.fromDate != null){
			fromDate = dateTimeFormat.parse(params.fromDate + " 00:00:00");
			println fromDate
		}
		if(params.toDate != null){
			toDate = dateTimeFormat.parse(params.toDate + " 23:59:59");
			println toDate
		}

		def domOrder = Order.createCriteria()

		def domesticOrder = domOrder.list{
			and{
				eq("status", (Short) 2)
				eq("isDomestic", (Short) 0)
				between("orderTime", fromDate, toDate)
			}
		}

		def domesticIncome = 0
		def domesticProfit = 0

		domesticOrder.each { domestic ->
			def obJourney = OrderDetail.countByOrderAndTripType(domestic, 1)
			def obCode = OrderDetail.findByOrderAndTripType(domestic, 1).airline

			def ibProfit = 0
			if(OrderDetail.countByOrderAndTripType(domestic, 2) > 0){
				def ibJourney = OrderDetail.countByOrderAndTripType(domestic, 2)
				def ibCode = OrderDetail.findByOrderAndTripType(domestic, 2).airline
				ibProfit = (domestic.adultNumber + domestic.kidNumber) * ibJourney * (Fee.findByCode(ibCode).price)
			}

			def income = domestic.price
			def obProfit = (domestic.adultNumber + domestic.kidNumber) * obJourney * (Fee.findByCode(obCode).price)

			//			println(OrderDetail.countByOrder(domestic, 2))

			domesticIncome = domesticIncome + income
			domesticProfit = domesticProfit + obProfit + ibProfit
		}

		def intOrder = Order.createCriteria()

		def internationalOrder = intOrder.list{
			and{
				eq("status", (Short) 2)
				eq("isDomestic", (Short) 1)
				between("orderTime", fromDate, toDate)
			}
		}

		def internationalIncome = 0
		def internationalProfit = 0

		internationalOrder.each { international ->
			def income = international.price
			def arrivalCode = OrderDetail.findAllByOrderAndTripType(international, 1,[sort: "order", order:"desc"])[0].arrival
			def countryCode = Location.findByCode(arrivalCode).country_code
			String continental = Country.findByCode(countryCode).continental

			boolean roundTrip = (OrderDetail.findAllByOrderAndTripType(international, 2).size() > 0)

			def fee
			if(continental == "AS" || continental == "EU" || continental == "AF" || continental == "SEA"){
				fee = roundTrip ? (Fee.findByCode(continental).price * 2) : (Fee.findByCode(continental).price)
			}
			else if(continental == "NA" || continental == "SA"){
				fee = roundTrip ? (Fee.findByCode("AM").price * 2) : (Fee.findByCode("AM").price)
			}
			else{
				fee = roundTrip ? (Fee.findByCode("OT").price * 2) : (Fee.findByCode("OT").price)
			}

			def profit = (international.adultNumber + international.kidNumber) * (fee)

			internationalIncome = internationalIncome + income
			internationalProfit = internationalProfit + profit
		}

		[domesticIncome:domesticIncome, domesticProfit:domesticProfit, internationalIncome:internationalIncome, internationalProfit:internationalProfit]
	}
}



