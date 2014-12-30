package com.airgroup.controller.tips

import com.airgroup.domain.Tips
import java.awt.TexturePaintContext.Int;
import java.text.DateFormat
import java.text.SimpleDateFormat

class TipsController {
	
	static allowedMethod = [list:"POST"]
	
	def tipsService

	def index = {
		redirect(action:'list')
	}
	
	def list = {
		String startDateAjax
		String endDateAjax
		String []startDateSplit
		int startYear
		int startMonth
		int startDate
		String []endDateSplit
		int endYear
		int endMonth
		int endDate
		Date startDateSearch
		Date endDateSearch
		if(request.xhr){
			startDateAjax = params.startDate
			endDateAjax = params.endDate
			
			//Get the start date
			startDateSplit = startDateAjax.split("/")
			startYear = Integer.valueOf(startDateSplit[2]) - 1900
			startMonth = Integer.valueOf(startDateSplit[1]) - 1
			startDate = Integer.valueOf(startDateSplit[0])
			
			//Get the end date
			endDateSplit = endDateAjax.split("/")
			endYear = Integer.valueOf(endDateSplit[2]) - 1900
			endMonth = Integer.valueOf(endDateSplit[1]) - 1
			endDate = Integer.valueOf(endDateSplit[0])
			
			startDateSearch = new Date(startYear, startMonth, startDate)
			endDateSearch = new Date(endYear, endMonth, endDate)
		} else {
			endDateSearch = new Date()
			int currentMonth = endDateSearch.getMonth()
			int currentYear = endDateSearch.getYear()
			startDateSearch = new Date(currentYear, currentMonth, 1)
		}
		
		tipsService.getTipsList(params, startDateSearch, endDateSearch)
	}
	
	def saveLastTips = {
		long lastDomesticBookedTips = Integer.valueOf(params.tipsOfDomesticBook)
		long lastDomesticProcessTips = Integer.valueOf(params.tipsOfDomesticProcess)
		long lastInternationalBookedTips = Integer.valueOf(params.tipsOfInternationalBook)
		long lastInternationalProcessTips = Integer.valueOf(params.tipsOfInternationalProcess)
		if(request.xhr){
			Tips tips = new Tips()
			tips.lastDomesticBookedTips = lastDomesticBookedTips
			tips.lastDomesticProcessTips = lastDomesticProcessTips
			tips.lastInternationalBookedTips = lastInternationalBookedTips
			tips.lastInternationalProcessTips = lastInternationalProcessTips
			tipsService.saveLastTips(tips)
		}
	}
}