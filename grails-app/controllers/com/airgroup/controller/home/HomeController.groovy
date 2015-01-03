package com.airgroup.controller.home

import grails.converters.JSON

import java.text.DecimalFormat

import org.hibernate.criterion.CriteriaSpecification
import org.joda.time.DateTime
import org.joda.time.Hours
import org.joda.time.Minutes
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.weceem.auth.CMSUser
import org.weceem.content.WcmContent

import com.google.common.collect.Lists
import com.airgroup.domain.Advert
import com.airgroup.domain.CustomerEmail
import com.airgroup.domain.Feedback
import com.airgroup.domain.NewsContent
import com.airgroup.domain.Order
import com.airgroup.domain.OrderDetail

class HomeController {

	def index = {
		def lastFiveFeedback = Feedback.findAll("from Feedback where status=1 order by id desc",[max:5])

		def advert = Advert.findAll("from Advert where status=0 order by activeTime desc")

		def policy = WcmContent.findByTitle('Policy')

		def different = WcmContent.findByTitle('Different')
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)

		String linkDetails

		DateTimeFormatter dateFormat = DateTimeFormat.forPattern("d")

		DateTimeFormatter monthFormat = DateTimeFormat.forPattern("MM")

		DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S")

		def lastThreeNewsKM = NewsContent.findAllByActiveAndCategory("Active","${message(code:'navigation.admin.promotion')}",[max:3, offset:0, sort:"createdOn", order:"desc"])
		def lastThreeNews = NewsContent.findAllByActiveAndCategory("Active","${message(code:'navigation.admin.news')}",[max:3, offset: 0, sort:"createdOn", order:"desc"])
		
		render view: "index", model: [lastFiveFeedback: lastFiveFeedback, policy:policy, different:different, advert:advert ,
					users: users, lastThreeNews:lastThreeNews, lastThreeNewsKM: lastThreeNewsKM]
	}
	
	def listUsers={
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)
		List<CMSUser> list_user= new ArrayList<CMSUser>()
		for(CMSUser user : users){
			CMSUser newUser = new CMSUser()
			newUser.name = user.name
			newUser.yahoo = user.yahoo
			newUser.skype = user.skype
			newUser.phoneNumber = user.phoneNumber
			list_user.add(newUser)
		}
		render list_user as JSON
	}

	def contact = { render view: "/contacts/index" }

	def slideImage = {
		def advert = Advert.get(params.id)
		response.outputStream << advert.slidePic // write the image to the outputstream
		response.outputStream.flush()
	}
	def showAdvertPage = {
		def advert = Advert.get(params.advertId)
		response.outputStream << advert.pagePic // write the image to the outputstream
		response.outputStream.flush()
	}
	def searchOrder={
		if(params.orderCode!=null&&!params.orderCode.toString().equals("")&&params.phoneNumber!=null&&!params.phoneNumber.toString().equals("")){
			def c = Order.createCriteria()
			def results = c.listDistinct{
				createAlias ('customer', 'customer', CriteriaSpecification.LEFT_JOIN)
				and{
					eq('id', params.orderCode.toLong())
					eq('customer.phoneNumber', params.phoneNumber.toString())
				}
			}

			if(results.size()>0){
				def orderDetails=((Order)results.get(0)).orderDetails;
				List inboundItem=new ArrayList<OrderDetail>();
				List outboundItem=new ArrayList<OrderDetail>();
				for(OrderDetail item in orderDetails){
					if(item.tripType==2){
						inboundItem.add(item)
					}else{
						outboundItem.add(item)
					}
				}
				DecimalFormat df = new DecimalFormat("#,###");
				def price=df.format(new Double(results.get(0).price))+" VND";
				DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

				def outboundOverStopDuration=new ArrayList<String>();
				def inboundOverStopDuration=new ArrayList<String>();
				for(int i=0;i<outboundItem.size();i++){
					if(i>0){
						def previousArrivalTime=outboundItem.get(i-1).inboundDate.toString();
						previousArrivalTime=previousArrivalTime.substring(0,previousArrivalTime.length()-2);
						def nextDepartureTime=outboundItem.get(i).outboundDate.toString();
						nextDepartureTime=nextDepartureTime.substring(0,nextDepartureTime.length()-2);
						DateTime dt1=new DateTime(formatter.parseDateTime(previousArrivalTime));
						DateTime dt2=new DateTime(formatter.parseDateTime(nextDepartureTime));
						outboundOverStopDuration.add(String.format("%02d",Hours.hoursBetween(dt1, dt2).getHours())+'h'+" + "+String.format("%02d",Minutes.minutesBetween(dt1, dt2).getMinutes()%60)+"m");
					}
				}
				def firstSegment0=outboundItem.get(0)
				def lastSegment0=outboundItem.get(outboundItem.size()-1)
				String d1=firstSegment0.outboundDate.toString();
				d1=d1.substring(0,d1.length()-2);
				String d2=lastSegment0.inboundDate.toString();
				d2=d2.substring(0,d2.length()-2);
				DateTime firstSegmentDepatureTime0=new DateTime(formatter.parseDateTime(d1));
				DateTime lastSegmentArrivalTime0=new DateTime(formatter.parseDateTime(d2));
				def outboundDuration=String.format("%02d",Hours.hoursBetween(firstSegmentDepatureTime0, lastSegmentArrivalTime0).getHours())+'h'+" + "+String.format("%02d",Minutes.minutesBetween(firstSegmentDepatureTime0, lastSegmentArrivalTime0).getMinutes()%60)+"m";
				def inboundDuration="";
				if(inboundItem.size()>0){
					for(int i=0;i<inboundItem.size();i++){
						if(i>0){
							def previousArrivalTime=inboundItem.get(i-1).inboundDate.toString();
							previousArrivalTime=previousArrivalTime.substring(0,previousArrivalTime.length()-2);
							def nextDepartureTime=inboundItem.get(i).outboundDate.toString();
							nextDepartureTime=nextDepartureTime.substring(0,nextDepartureTime.length()-2);
							DateTime dt3=new DateTime(formatter.parseDateTime(previousArrivalTime));
							DateTime dt4=new DateTime(formatter.parseDateTime(nextDepartureTime));
							inboundOverStopDuration.add(String.format("%02d",Hours.hoursBetween(dt3, dt4).getHours())+'h'+" + "+String.format("%02d",Minutes.minutesBetween(dt3, dt4).getMinutes()%60)+"m");
						}
					}

					def firstSegment=inboundItem.get(0)
					def lastSegment=inboundItem.get(inboundItem.size()-1)
					String d3=firstSegment.outboundDate.toString();
					d3=d3.substring(0,d3.length()-2)
					String d4=lastSegment.inboundDate.toString()
					d4=d4.substring(0,d4.length()-2)
					DateTime firstSegmentDepatureTime=new DateTime(formatter.parseDateTime(d3));
					DateTime lastSegmentArrivalTime=new DateTime(formatter.parseDateTime(d4));
					inboundDuration=String.format("%02d",Hours.hoursBetween(firstSegmentDepatureTime, lastSegmentArrivalTime).getHours())+'h'+" + "+String.format("%02d",Minutes.minutesBetween(firstSegmentDepatureTime, lastSegmentArrivalTime).getMinutes()%60)+"m";
				}
				[order:results,inboundItem:inboundItem,outboundItem:outboundItem,price:price,inboundDuration:inboundDuration,outboundDuration:outboundDuration,outboundOverStopDuration:outboundOverStopDuration,inboundOverStopDuration:inboundOverStopDuration];
			}else{
				[message:"KhÃ´ng tÃ¬m tháº¥y hÃ³a Ä‘Æ¡n"]
			}
		}
	}

	def registerEmail = {
		def customerEmail = new CustomerEmail(params)
		if (customerEmail.save(flush: true)) {
			flash.message = "Ä�Äƒng kÃ½ nháº­n email thÃ nh cÃ´ng"
			redirect(action: "index")
		}
		else {
			flash.error = "Email nÃ y Ä‘Ã£ tá»“n táº¡i"
			redirect(action: "index")
		}
	}
}
