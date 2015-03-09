
package com.airgroup.utils

import com.vsii.airgroup.domain.Order;

import java.text.DecimalFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;

import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

import org.codehaus.groovy.grails.commons.ApplicationHolder

import com.airgroup.domain.Airline;
import com.airgroup.domain.Customer;
import com.airgroup.domain.Location;
import com.airgroup.domain.Order;
import com.airgroup.domain.OrderDetail;
import com.airgroup.domain.Passenger;
import com.airgroup.services.IFlightResponse;

import freemarker.core.Configurable;
import freemarker.template.Configuration;
import freemarker.template.Template;

import org.joda.time.DateTime;
import org.joda.time.Hours
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

class Email {
	//	simpleMessage.setContent(text, "text/html; charset=utf-8");
	def subject
	def message
	def toAddress
	def fromAddress
	def bccAddress
	def host="smtp.gmail.com"
	def port='465'
	def username="service.ahotua@gmail.com"
	def password="ahotua2015"
	Properties mprops
	Session session
	MimeMessage msg
	def send(String subject,
	String from,
	String to,
	String customerName,
	String orderCode,
	String orderStatus,
	String totalPrice,
	String fareType,
	String passengers,
	String flightDetails,
	String paymentType,
	String paymentTypeDetails,
	String inboundFromLocation,
	String inboundDuration,
	String outboundFromLocation,
	String outboundDuration,
	List outboundFlights,
	List inboundFlights,
	def outbound,
	def inbound){
		Boolean hasInbound=true
		if(inboundFlights==null){
			hasInbound=false
		}else if(inboundFlights.size()==0){
			hasInbound=false
		}else{
			hasInbound=true
		}
		DecimalFormat df = new DecimalFormat("#,###");
		mprops=new Properties()
		mprops.put("mail.smtp.host",host)
		mprops.put("mail.smtp.port",port)
		//		mprops.put("mail.smtp.starttls.enable","true")
		mprops.put("mail.smtp.debug", "true");
		mprops.put("mail.smtp.auth", "true")
		mprops.put("mail.smtp.socketFactory.port", port)
		mprops.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
		//		mprops.put("mail.smtp.socketFactory.fallback", "false")
		def auth=new SMTPAuthenticator()

		session=Session.getInstance(mprops, auth)
		session.setDebug(false)
		msg=new MimeMessage(session)
		def filePath="resources/Mail.ftl"
		//Freemaker
		Configuration cfg=new Configuration()
		cfg.setDefaultEncoding("UTF-8")
		cfg.setClassForTemplateLoading(this.getClass(), "/")
		Template tpl=cfg.getTemplate(filePath)
		Map<String, String> map=new HashMap<String,String>()
		map.put("customerName",customerName)
		map.put("orderCode", orderCode)
		map.put("orderStatus", orderStatus)
		map.put("totalPrice", df.format(new Double(totalPrice))+" VND")
		map.put("fareType", fareType)
		map.put("passengers",passengers)
		map.put("flightDetails", flightDetails)
		map.put("outboundFromLocation", outboundFromLocation)
		map.put("outboundDuration",outboundDuration)
		map.put("outboundFlights",outboundFlights)
		map.put("inboundFromLocation",inboundFromLocation)
		map.put("inboundDuration",inboundDuration)
		map.put("inboundFlights",inboundFlights)
	    map.put('hasInbound',hasInbound)
		map.put("paymentType", paymentType)
		map.put("paymentTypeDetails",paymentTypeDetails)
		map.put("outbound",outbound)
		map.put("inbound", inbound)
		Writer out=new StringWriter()
		tpl.process(map, out)
		//end
		toAddress=new InternetAddress(to)
		fromAddress=new InternetAddress("service@ahotua.vn")
		bccAddress=new InternetAddress("booker@ahotua.vn")
		msg.setFrom(fromAddress)
		msg.setSubject(subject,"UTF-8")
		msg.setContent(out.toString(), "text/html; charset=utf-8")
		msg.setRecipient(Message.RecipientType.TO,toAddress)
		msg.setRecipient(Message.RecipientType.BCC,bccAddress)
		//		Transport transporter=session.getTransport("smtps")
		//		transporter.connect(host,port,username, password)
		Transport.send(msg)
	}
	def sendEmailAfterConfirm(
	String subject,
	String from,
	String to,
	String reservationCode,
	String odepartureDate,
	List passengers,
	String oairlineName,
	String oflightNumber,
	String odepartureCityCode,
	String oarrivalCityCode,
	String odepartureCityName,
	String oarrivalCityName,
	String oaircraft,
	String oduration,
	Integer ostops,
	String odepartureTime,
	String oarrivalTime,
	Boolean hasInbound,
	String iairlineName,
	String idepartureDate,
	String iflightNumber,
	String idepartureCityCode,
	String iarrivalCityCode,
	String idepartureCityName,
	String iarrivalCityName,
	String iaircraft,
	String iduration,
	String idepartureTime,
	String iarrivalTime,
	Integer istops){
		mprops=new Properties()
		mprops.put("mail.smtp.host",host)
		mprops.put("mail.smtp.port",port)
		//		mprops.put("mail.smtp.starttls.enable","true")
		mprops.put("mail.smtp.debug", "true");
		mprops.put("mail.smtp.auth", "true")
		mprops.put("mail.smtp.socketFactory.port", port)
		mprops.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
		//		mprops.put("mail.smtp.socketFactory.fallback", "false")
		def auth=new SMTPAuthenticator()

		session=Session.getInstance(mprops, auth)
		session.setDebug(false)
		msg=new MimeMessage(session)
		def filePath="resources/Mail2.ftl"
		//Freemaker
		Configuration cfg=new Configuration()
		cfg.setDefaultEncoding("UTF-8")
		cfg.setClassForTemplateLoading(this.getClass(), "/")
		Template tpl=cfg.getTemplate(filePath)
		Map<String, String> map=new HashMap<String,String>()
		map.put("reservationCode",reservationCode)
		map.put("odepartureDate",odepartureDate)
		map.put("oairlineName",oairlineName)
		map.put("oaircraft", oaircraft)
		map.put("odepartureCityCode", odepartureCityCode)
		map.put("oarrivalCityCode", oarrivalCityCode)
		map.put("odepartureCityName", odepartureCityName)
		map.put("oarrivalCityName",oarrivalCityName)
		map.put("oduration",oduration)
		map.put("oflightNumber",oflightNumber)
		map.put("ostops",ostops)
		map.put("odepartureTime", odepartureTime)
		map.put("oarrivalTime",oarrivalTime)
		map.put("hasInbound",hasInbound)
		map.put("passengers",passengers)
		map.put("iairlineName", iairlineName)
		map.put("idepartureDate",idepartureDate)
		map.put("iaircraft", iaircraft)
		map.put("idepartureCityCode", idepartureCityCode)
		map.put("iarrivalCityCode", iarrivalCityCode)
		map.put("iarrivalCityName",iarrivalCityName)
		map.put("idepartureCityName",idepartureCityName)
		map.put("iduration",iduration)
		map.put("istops",istops)
		map.put("iflightNumber",iflightNumber)
		map.put("idepartureTime", idepartureTime)
		map.put("iarrivalTime",iarrivalTime)
		Writer out=new StringWriter()
		tpl.process(map, out)
		//end
		toAddress=new InternetAddress(to)
		fromAddress=new InternetAddress(from)
		bccAddress=new InternetAddress("booker@ahotua.vn")
		msg.setFrom(fromAddress)
		msg.setSubject(subject,"UTF-8")
		msg.setContent(out.toString(), "text/html; charset=utf-8")
		msg.setRecipient(Message.RecipientType.TO,toAddress)
		msg.setRecipient(Message.RecipientType.BCC,bccAddress)
		//		Transport transporter=session.getTransport("smtps")
		//		transporter.connect(host,port,username, password)
		Transport.send(msg)

	}
	def sendConfirmMail(String title,Long customerId, String customerName, Long orderId, String email){
		DateTimeFormatter dtf1=DateTimeFormat.forPattern("E dd/M")
		DateTimeFormatter dtf2=DateTimeFormat.forPattern("HH:mm")
		Customer customer=Customer.findById(customerId)
		Order order=Order.findById(orderId)
		List<OrderDetail> oorderDetails=OrderDetail.findAllByOrderAndTripType(order,1)
		Airline oairline=Airline.findByCode(oorderDetails.get(0).airline)
		Integer ostops=oorderDetails.size()-1
		DateTime d=new DateTime()
		String oduration=Hours.hoursBetween(new DateTime(oorderDetails.get(0).outboundDate.getTime()),new DateTime(oorderDetails.last().inboundDate.getTime())).getHours()+'hr'+" + "+
				Minutes.minutesBetween(new DateTime(oorderDetails.get(0).outboundDate.getTime()),new DateTime(oorderDetails.last().inboundDate.getTime())).getMinutes()%60+"min"
		Airline iairline=null
		Integer istops=null
		String iduration=null
		List<OrderDetail> iorderDetails=OrderDetail.findAllByOrderAndTripType(order,2)
		if(iorderDetails.size()>0){
			iairline=Airline.findByCode(iorderDetails.get(0).airline)
			istops=iorderDetails.size()-1
			iduration=Hours.hoursBetween(new DateTime(iorderDetails.get(0).outboundDate.getTime()),new DateTime(iorderDetails.last().inboundDate.getTime())).getHours()+'hr'+" + "+
					Minutes.minutesBetween(new DateTime(iorderDetails.get(0).outboundDate.getTime()),new DateTime(iorderDetails.last().inboundDate.getTime())).getMinutes()%60+"min"
		}
		List<Passenger> passengers=Passenger.findAllByOrder(order)
		if(iorderDetails.size()==0){
			String odepartureCityName=Location.findByCode(oorderDetails.get(0).departure).name
			String oarrivalCityName= Location.findByCode(oorderDetails.get(0).arrival).name
			String oflightNumber=oorderDetails.get(0).airline+oorderDetails.get(0).flightNumber
			sendEmailAfterConfirm(title,
					"service@ahotua.vn",
					email,
					order.reservationCode,
					 dtf1.print(oorderDetails.get(0).outboundDate.getTime()),
					passengers,
					oairline.name,
					oflightNumber,
					oorderDetails.get(0).departure,
					oorderDetails.last().arrival,
					odepartureCityName,
					oarrivalCityName,
					"aircraft",
					oduration ,
					ostops,
					dtf2.print(oorderDetails.get(0).outboundDate.getTime()),
					dtf2.print(oorderDetails.last().inboundDate.getTime()),
					false,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null)
		}else {
			String odepartureCityName=Location.findByCode(oorderDetails.get(0).departure).name
			String oarrivalCityName= Location.findByCode(oorderDetails.get(0).arrival).name
			String oflightNumber=oorderDetails.get(0).airline+oorderDetails.get(0).flightNumber
			
			String idepartureCityName=Location.findByCode(iorderDetails.get(0).departure).name
			String iarrivalCityName= Location.findByCode(iorderDetails.get(0).arrival).name
			String iflightNumber=iorderDetails.get(0).airline+iorderDetails.get(0).flightNumber
			passengers.each {
				println it.name+":"+it.seatNumber
				
			}
			sendEmailAfterConfirm(
				title,
				"service@ahotua.vn",
				email,
				order.reservationCode,
				dtf1.print(oorderDetails.get(0).outboundDate.getTime()),
				passengers,
				oairline.name,
				oflightNumber,
				oorderDetails.get(0).departure,
				oorderDetails.last().arrival,
				odepartureCityName,
				oarrivalCityName,
				"aircraft",
				oduration ,
				ostops,
				dtf2.print(oorderDetails.get(0).outboundDate.getTime()),
				dtf2.print(oorderDetails.last().inboundDate.getTime()),
				true,
				iairline.name,
				dtf1.print(iorderDetails.get(0).outboundDate.getTime()),
				iflightNumber,
				iorderDetails.get(0).departure,
				iorderDetails.last().arrival,
				idepartureCityName,
				iarrivalCityName,
				"aircraft",
				iduration,
				dtf2.print(iorderDetails.get(0).outboundDate.getTime()),
				dtf2.print(iorderDetails.last().inboundDate.getTime()),
				istops
				)
		}
	}
	private class SMTPAuthenticator extends javax.mail.Authenticator{
		public javax.mail.PasswordAuthentication getPasswordAuthentication()
		{
			return new javax.mail.PasswordAuthentication(username,password);
		}
	}
}
