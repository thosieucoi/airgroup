package com.airgroup.controller.radar

class RadarController {

    def index = {
		session.title = "${message(code:'home.og.title')}"
		session.description = "${message(code:'home.og.description')}"
		session.url = "${message(code:'home.og.url')}"
		session.image = "${message(code:'home.og.image')}"
	}
}
