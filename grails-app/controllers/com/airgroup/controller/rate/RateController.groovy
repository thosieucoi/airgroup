package com.airgroup.controller.rate

import com.airgroup.domain.Rate;

class RateController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        [rateInstanceList: Rate.list(params), rateInstanceTotal: Rate.count()]
    }

    def save = {
		def currencies = []
		params.currencies.each { k, v ->
			if (v instanceof Map) {
				def currency = Rate.get(k)
				bindData(currency, v, ['_rate'])
				currencies << currency
			}
		}
		def rate
		try {
			def valid = true
			for (rateInstance in currencies) {
				if (!rateInstance.rate.matches(/[0-9.\,]+/)) {
					valid = false
					break
				}
			}
			if (valid) {
				flash.message = "rate.update.success"
				currencies.save()
			} else {
				flash.error = 'rate.validate.fail'
			}
			redirect action: list
		} catch (Exception e) {
			render(view: "list", model: [rateInstanceList: currencies])
		}
    }
}
