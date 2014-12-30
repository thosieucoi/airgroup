import grails.util.Environment

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.weceem.auth.*
import org.weceem.content.*
import org.weceem.files.*

import com.airgroup.domain.Airline
import com.airgroup.domain.Category
import com.airgroup.domain.Country
import com.airgroup.domain.Fee
import com.airgroup.domain.Ipconfig
import com.airgroup.domain.Location
import com.airgroup.domain.Rate

class BootStrap {

    def initialised = false

    //def _log = LogFactory.getLog('org.weceem.BootStrap')

    def wcmContentRepositoryService

	def locationService
	def airlineService
	def countryService
	def tourService

    def init = {servletContext ->
        def ctx = ApplicationHolder.application.mainContext

        if (!initialised && (Environment.current != Environment.TEST)) {
            if (!CMSRole.findByAuthority('ROLE_USER')) {
                assert new CMSRole(authority: 'ROLE_USER', description: 'User role').save(flush:true)
            }
            if (!CMSRole.findByAuthority('ROLE_ADMIN')) {
                assert new CMSRole(authority: 'ROLE_ADMIN', description: 'Admin role').save(flush:true)
            }
			if (!CMSRole.findByAuthority('ROLE_ACCOUNTING')) {
				assert new CMSRole(authority: 'ROLE_ACCOUNTING', description: 'Accounting Role').save(flush:true)
			}
            if (!CMSUser.findByUsername('admin')) {
                assert new CMSUser(username: 'admin',name:'admin',code:'admin',regDate: new Date(),lastAccessTime: new Date(),
                        password: '21232f297a57a5a743894a0e4a801fc3',phoneNumber:'0915273033',
                        status: new Short("1"), callCenterStatus: new Short("0"), yahoo: 'admin', skype:'admin')
                        .addToAuthorities(CMSRole.findWhere(authority: 'ROLE_ADMIN'))
//                        .addToAuthorities(CMSRole.findWhere(authority: 'ROLE_USER'))
                        .save(flush:true)
            }

			//Add default rate
			if (!Rate.findByCode('USD')) {
				assert new Rate(code:'USD',rate:'21,150.00',description:'Ä�Ã´ la Má»¹').save(flush:true)
			}
			if (!Rate.findByCode('EUR')) {
				assert new Rate(code:'EUR',rate:'28,258.76',description:'Euro').save(flush:true)
			}
			if (!Rate.findByCode('JPY')) {
				assert new Rate(code:'JPY',rate:'213.28',description:'YÃªn Nháº­t').save(flush:true)
			}
			if (!Rate.findByCode('GBP')) {
				assert new Rate(code:'GBP',rate:'33,548.08',description:'Báº£ng Anh').save(flush:true)
			}
			if (!Rate.findByCode('AUD')) {
				assert new Rate(code:'AUD',rate:'19,677.40',description:'Ä�Ã´ la Ãšc').save(flush:true)
			}
			if (!Rate.findByCode('INR')) {
				assert new Rate(code:'INR',rate:'338.09',description:'RÃºp áº¤n Ä�á»™').save(flush:true)
			}
			if (!Rate.findByCode('ETB')) {
				assert new Rate(code:'ETB',rate:'1,114.96',description:'Ethiopian birr').save(flush:true)
			}
			if (!Rate.findByCode('MYR')) {
				assert new Rate(code:'MYR',rate:'6,632.09',description:'Malaysian Ringgit').save(flush:true)
			}
			if (!Rate.findByCode('HKD')) {
				assert new Rate(code:'HKD',rate:'2,725.10',description:'HongKong Dollar ').save(flush:true)
			}
            // Only install default space stuff if there isn't one already
            println "Bootstrapping Weceem - there are ${WcmSpace.count()} spaces"
            if (WcmSpace.count() == 0) {
                println "Bootstrapping Weceem - No spaces, will install default"
                initNewInstallation(servletContext)
            }

			if(Location.count() == 0){
				println "Initialize location"
				locationService.saveLocation()
			}
			def lstLocationCode = ["HAN","PAR", "SGN", "CDG", "LHR", "JFK", "TYO", "ICN", "BKK", "KUL", "MNL", "DAD"]
			Location.executeUpdate("UPDATE Location SET level = 5 WHERE code IN (:lstLocationCodes)", [lstLocationCodes : lstLocationCode])
			locationService.updateNewLocation("SEL", "Seoul", "Korea", "KR", "airport", 5)
			locationService.updateNewLocation("LON", "London", "United Kingdom", "GB", "airport", 5)
			locationService.updateNewLocation("NYC", "New York Area Airport", "United States", "US", "airport", 5)
			locationService.updateNewLocation("BER", "Berlin", "Germany", "DE", "airport", 5)

			if(Airline.count()==0){
				airlineService.saveAirline()
			}
			if(Country.count() == 0){
				println "Initialize country"
				countryService.saveCountry()
			}

			if (Ipconfig.list().size() <  1){
				assert new Ipconfig(code:'supercode',ip:'192.168.1.1',status:'1').save(flush:true)
			}

			if(Category.list().size() < 1){
				println "Initialize tour introduction"
				tourService.initiateCategory()
			}

			//Initiate default fee
			if (!Fee.findByCode('SEA')) {
				assert new Fee(code:'SEA',price:150000.00,description:'fee.label.asean').save(flush:true)
			}
			if (!Fee.findByCode('AS')) {
				assert new Fee(code:'AS',price:200000.00,description:'fee.label.asia').save(flush:true)
			}
			if (!Fee.findByCode('EU')) {
				assert new Fee(code:'EU',price:300000.00,description:'fee.label.europe').save(flush:true)
			}
			if (!Fee.findByCode('AM')) {
				assert new Fee(code:'AM',price:350000.00,description:'fee.label.america').save(flush:true)
			}
			if (!Fee.findByCode('OT')) {
				assert new Fee(code:'OT',price:100000.00,description:'fee.label.other').save(flush:true)
			}
			if (!Fee.findByCode('VN')) {
				assert new Fee(code:'VN',price:50000.00,description:'fee.label.vietnamairline').save(flush:true)
			}
			if (!Fee.findByCode('VJ')) {
				assert new Fee(code:'VJ',price:50000.00,description:'fee.label.vietjetair').save(flush:true)
			}
			if (!Fee.findByCode('BL')) {
				assert new Fee(code:'BL',price:28000.00,description:'fee.label.jetstar').save(flush:true)
			}

            // Make sure the upload dirs exist
            def uploadsDir = wcmContentRepositoryService.uploadDir
            if (!uploadsDir.exists()) {
                uploadsDir.mkdirs()

                // Make sure the expected FCK upload dirs exist eg <basedir>/Image
                // @todo need to get this list from the FCK editor plugin but its not exposed
                def fckDirs = ['Image', 'Media']
                fckDirs.each { dir ->
                    def f = new File(uploadsDir, dir)
                    if (!f.exists()) {
                        f.mkdirs()
                    }
                }
            }

            // Stop dev-mode reloads triggering duplicate data
			def moneyCodeFile = new File('file')
			if (!moneyCodeFile.exists()) {
				moneyCodeFile.mkdirs()
			}
            initialised = true
        }
    }

    private initNewInstallation(context) {
        def spc = new WcmSpace(name: 'weceem')
        assert spc.save()

        def f = File.createTempFile("default-context-import", null)
        def res = context.getResourceAsStream('/WEB-INF/default-weceem-site.zip')
        assert res
        f.withOutputStream { os ->
            os << res
        }
        def svc = context.getAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT).getBean('importExportService')

        try {
            svc.importSpace(spc, 'simpleSpaceImporter', f)
        } catch (Throwable t) {
            println "Unable to import default space"
            t.printStackTrace()
        }

    }

    def destroy = {
    }
}
