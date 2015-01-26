package com.airgroup.controller.feedback

import org.weceem.auth.CMSUser;

import com.airgroup.domain.Feedback;

/**
 * <p>
 * Control requests which relate to User Feedback
 * This controller use only for public page.
 * </p>
 * @author nghiaTT
 * @version 1.0
 */
class FeedbackController {
	def feedbackService
	
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	/**
	 *<p>
	 *Return list of feedback to front end
	 *</p>
	 */
	def index = {
		listFeedback(params)
	}
	
	def list = {
		listFeedback(params)
	}

	/**
	 *<p>
	 *Return list of feedback to front end
	 *</p> 
	 */
	def listFeedback(def params) {
		params.status = (short)1
		feedbackService.list(params)
		
	}

	/**
	 *<p>
	 *Initial an instance of feedback object
	 *Provide object template for create page
	 *</p>
	 *@author nghiaTT 
	 */
	def create = {
		def feedbackInstance = new Feedback()
		feedbackInstance.properties = params
		return [feedbackInstance: feedbackInstance]
	}
	/**
	 * <p>
	 * Save feedback to database
	 * if Object was saved, success page will be redirected to list feedback page
	 * </p>
	 * 
	 * @author nghiaTT
	 */
	def save = {
		def feedbackInstance = new Feedback(params)
		feedbackInstance.sendDate = new Date()
		feedbackInstance.status = 0
		def offset = params.int('offset') ?: 0
		def max = params.int('max') ?: 5
		def feedbackInstanceList = index(params).feedbackInstanceList
		def feedbackTotal = feedbackInstanceList.totalCount
		
		String phoneReg = /\d{6,15}/
		String nameReg = "^[\\p{L}0-9 .'-]+"
		if (!feedbackInstance.name) {
			flash.message = "${message(code: 'feedback.error.name.blank')}"
			render(view: "index", model:[feedbackInstance: feedbackInstance, feedbackInstanceList: feedbackInstanceList,
										 feedbackTotal: feedbackTotal, offset: offset, max: max])
			return
		}  else if (!feedbackInstance.name.matches(nameReg)) {
			flash.message = "${message(code: 'feedback.error.name.special.characters')}"
			render(view: "index", model:[feedbackInstance: feedbackInstance, feedbackInstanceList: feedbackInstanceList,
										 feedbackTotal: feedbackTotal, offset: offset, max: max])
			return
		}  else if (feedbackInstance.name.length() > 100) {
			flash.message = "${message(code: 'feedback.error.name.max.length')}"
			render(view: "index", model:[feedbackInstance: feedbackInstance, feedbackInstanceList: feedbackInstanceList,
										 feedbackTotal: feedbackTotal, offset: offset, max: max])
			return
		}
		if (!feedbackInstance.address) {
			flash.message = "${message(code: 'feedback.error.address.blank')}"
			render(view: "index", model:[feedbackInstance: feedbackInstance, feedbackInstanceList: feedbackInstanceList,
										 feedbackTotal: feedbackTotal, offset: offset, max: max])
			return
		} else if (feedbackInstance.address.length() > 100) {
			flash.message = "${message(code: 'feedback.error.address.max.length')}"
			render(view: "index", model:[feedbackInstance: feedbackInstance, feedbackInstanceList: feedbackInstanceList,
										 feedbackTotal: feedbackTotal, offset: offset, max: max])
			return
		}
		if (feedbackInstance.phoneNumber?.length() > 0 && !feedbackInstance.phoneNumber.matches(phoneReg)) {
			flash.message = "${message(code: 'not.phoneNumber')}"
			render(view: "index", model:[feedbackInstance: feedbackInstance, feedbackInstanceList: feedbackInstanceList,
										 feedbackTotal: feedbackTotal, offset: offset, max: max])
			return
		}
		if (!feedbackInstance.content) {
			flash.message = "${message(code: 'feedback.error.content.blank')}"
			render(view: "index", model:[feedbackInstance: feedbackInstance, feedbackInstanceList: feedbackInstanceList,
										 feedbackTotal: feedbackTotal, offset: offset, max: max])
			return
		} else if (feedbackInstance.content.length() > 500) {
			flash.message = "${message(code: 'feedback.error.content.max.length')}"
			render(view: "index", model:[feedbackInstance: feedbackInstance, feedbackInstanceList: feedbackInstanceList,
										 feedbackTotal: feedbackTotal, offset: offset, max: max])
			return
		}

		if (feedbackService.save(params)?.id) {
			flash.message = "${message(code: 'feedback.send.sucess','Success!')}"
			redirect(action: "index")
		}
	}
		
}
