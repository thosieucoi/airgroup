// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = false // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*',
	 '/plugins/*','/uploads/','/Image/','/img/']
// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'
grails.web.disable.multipart=true
// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

navigation.'weceem.app.admin' = [
	controller:'CMSUser',
	action: 'list',
	title: 'users'
]

weceem.profile.url = [controller:'userProfile', action:'edit']
weceem.logout.url = [controller:'logout']
weceem.content.prefix = ''
weceem.admin.prefix = 'admin'
weceem.tools.prefix="cms/tools"
weceem.space.templates = [
	default: "classpath:/org/weceem/resources/default-space-template.zip",
	basic:"classpath:/org/weceem/resources/basic-space-template.zip"]

weceem.springsecurity.details.mapper = { ->
	[   // Stuff required by weceem spring sec
		username: username,
		name: name,
		password: password,
		phoneNumber: phoneNumber,
		code: code,
		regDate: regDate,
		lastAccessTime: lastAccessTime,
		status: status,
		authorities: authorities,
		// optional stuff we add
		id: id
	]
}

grails.validateable.packages=['org.weceem']

// Configure Spring Security
grails.plugins.springsecurity.rejectIfNoRule = true
grails {
	plugins {
		springsecurity {
			active = true
			//Force redirect to default custom page after lo
			successHandler.defaultTargetUrl  = '/order'
			successHandler.alwaysUseDefault = true
			//Redirect to login page after logout
			logout.afterLogoutUrl = '/adminAuthentication'
			//registerLoggerListener = true
			password.algorithm = "MD5"
			//use Base64 text ( true or false )
			password.encodeHashAsBase64 = false
			adh.errorPage = "/adminAuthentication/denied"

			// Turn of user caching, acegi plugin requires diskstore cache by default, which we don't want.
			cacheUsers = false

			/** login user domain class name and fields */
			userLookup.userDomainClassName = "org.weceem.auth.CMSUser"
			userLookup.userNamePropertyName = "username"
			userLookup.passwordPropertyName = "password"
			userLookup.statusPropertyName = "status"
			userLookup.authoritiesPropertyName = "authorities"

			/**
			* Authority domain class authority field name
			* authorityFieldInList
			*/
			authority.className = "org.weceem.auth.CMSRole"
			authority.nameField = "authority"

			securityConfigType = 'InterceptUrlMap'
			interceptUrlMap = [
				'/moneycode/index': ['ROLE_ADMIN','ROLE_ACCOUNTING','ROLE_USER'],
				'/moneycode/list': ['ROLE_ADMIN','ROLE_ACCOUNTING','ROLE_USER'],
			   '/moneycode/create':        ['ROLE_ADMIN','ROLE_ACCOUNTING','IS_AUTHENTICATED_FULLY'],
			  '/moneycode/edit/*':         ['ROLE_ACCOUNTING','ROLE_ADMIN','IS_AUTHENTICATED_FULLY'],
				'/moneycode/delete/*':     ['ROLE_ADMIN','ROLE_ACCOUNTING','IS_AUTHENTICATED_FULLY'],
				'/moneycode/fileExist*':   ['ROLE_ADMIN','ROLE_ACCOUNTING','IS_AUTHENTICATED_FULLY'],
			   '/CMSUSer/create':          	 ['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'],
			   '/CMSUSer/edit/*':          	 ['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'],
			   '/CMSUSer/delete/*':          ['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'],
			   '/CMSUSer/delete':          	 ['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'],
			   '/CMSUSer/update':          	 ['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'],
			   '/CMSUSer/save':          	 ['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'],
			   '/CMSUSer/index/**':          ['IS_AUTHENTICATED_FULLY'],
			   '/CMSUSer/list/**':           ['IS_AUTHENTICATED_FULLY'],
			   '/CMSUSer/listEmpCall':       ['IS_AUTHENTICATED_FULLY'],
			   '/CMSUSer/show/**':           ['IS_AUTHENTICATED_FULLY'],
			   '/CMSUSer/changePassword':    ['IS_AUTHENTICATED_FULLY'],
			   '/CMSUSer/editpass':          ['IS_AUTHENTICATED_FULLY'],
			   '/customerInfo/':   		 	['IS_AUTHENTICATED_FULLY'],
			   '/order/**':             	 ['IS_AUTHENTICATED_REMEMBERED'],
			   '/CMSFeedback/**':       	 ['IS_AUTHENTICATED_REMEMBERED'],
			   '/rate/index':        		 ['IS_AUTHENTICATED_REMEMBERED'],
		       '/rate/list':        	     ['IS_AUTHENTICATED_REMEMBERED'],
		       '/rate/save':        	     ['IS_AUTHENTICATED_REMEMBERED'],
			   '/ipconfig/**':    	    	 ['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'],
			   '/fee/**':    	  			 ['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'],
			   '/payment/**':    	    	 ['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'],
			   '/admin/**':             	 ['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'],
			   '/userProfile/**':       	 ['IS_AUTHENTICATED_REMEMBERED'],
			   '/ck/**':                	 ['IS_AUTHENTICATED_REMEMBERED'],
			   '/**':                    	 ['IS_AUTHENTICATED_ANONYMOUSLY'],
			   '/adminAuthentication/**':             	 ['IS_AUTHENTICATED_ANONYMOUSLY'],
			   '/logout/**':            	 ['IS_AUTHENTICATED_ANONYMOUSLY'],
			   '/home/**':              	 ['IS_AUTHENTICATED_ANONYMOUSLY'],
			   '/tour/**':              	 ['IS_AUTHENTICATED_ANONYMOUSLY'],
			   '/tourIntro/**':  			 ['IS_AUTHENTICATED_FULLY'],
			   '/flight/**':            	 ['IS_AUTHENTICATED_ANONYMOUSLY'],
			   '/passenger/**':         	 ['IS_AUTHENTICATED_ANONYMOUSLY'],
			   '/feedback/**':          	 ['IS_AUTHENTICATED_ANONYMOUSLY'],
			   '/contactUs/**':				 ['IS_AUTHENTICATED_ANONYMOUSLY'],
			   '/location/**':				 ['IS_AUTHENTICATED_ANONYMOUSLY'],
			   '/uploads/**':				 ['IS_AUTHENTICATED_ANONYMOUSLY'],
			   '/guide/**':					 ['IS_AUTHENTICATED_ANONYMOUSLY'],
			   '/policy/**':  				 ['IS_AUTHENTICATED_FULLY'],
			   '/different/**':  			 ['IS_AUTHENTICATED_FULLY'],
			   '/orderEmployee/**':			 ['ROLE_ADMIN'],
			   '/advert/**':				 ['IS_AUTHENTICATED_FULLY'],
			   '/tips/**':          		 ['ROLE_ADMIN'],
			   '/income/totalIncome':   	 ['ROLE_ADMIN'],
			   '/income/**':         	 	 ['ROLE_ADMIN'],
			   '/income/**':   ['ROLE_ACCOUNTING'],
			   '/rate/**':     ['ROLE_ACCOUNTING'],
			   '/tips/list':   ['ROLE_ACCOUNTING'],
			   '/order/list':  ['ROLE_ACCOUNTING'],
			   '/news/list':  ['ROLE_ADMIN'],
			   '/news/index':  ['IS_AUTHENTICATED_ANONYMOUSLY'],
			   '/login/index':  ['IS_AUTHENTICATED_ANONYMOUSLY'],
			]

			/** AJAX request header */
			ajaxHeader = "X-Requested-With"

			/** use basicProcessingFilter */
			useBasicAuth = false
			/** use switchUserProcessingFilter */
			useSwitchUserFilter = false
		}
	}
}

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"
    }
    development {
		weceem.upload.dir = 'file:/var/www/weceem.org/uploads/'

        grails.serverURL = "http://localhost:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
}
