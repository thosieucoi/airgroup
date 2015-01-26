<div class="container">
	<div id="adminlogo" class="span-14">
		<img src="${resource(dir: 'images', file:'weceem-logo.png')}" />
	</div>

	<div id="iconbar" class="span-10 last prepend-top"
		style="text-align: right">
		<g:form controller="order" action="search" method="get" class="search">
			<input type="text" name="info" value="${searchParam}" />
			<g:submitButton name="search" value="Tìm kiếm" />
		</g:form>
		<div style="margin-top: 5px;">
			<span id="recall-timelimit"> <span id="user-timelimit">
					Có <g:link controller="orderEmployee" action="list" id="timeLimit">
						<span id="numberOfTimeLimit"></span> limit
	    		</g:link>
			</span> <span id="user-recall"> Có <g:link controller="orderEmployee"
						action="list" id="recall">
						<span id="numberOfRecall"></span> recall
	    		</g:link>
			</span>
			</span> <span title="Current User"><g:message
					code="admin.user.current"
					args="${[wcm.loggedInUserName().encodeAsHTML(), wcm.loggedInUserEmail().encodeAsHTML()]}" />
			</span>&nbsp|&nbsp
			<g:link url="${wcm.userLogOutUrl().encodeAsHTML()}">
				<g:message code="navigation.admin.user.logout" />
			</g:link>
			&nbsp|&nbsp
			<g:link controller="CMSUser" action="changePassword">Đổi mật khẩu</g:link>
		</div>
	</div>

	<div id="navigation" class="span-24 last">
		<ul id="customadmin" class="navigation">
			<sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_USER, ROLE_ACCOUNTING">
				<li class="mainlist"><a href="javascript:void(0)"><span><g:message
								code="navigation.admin.employee" /></span><em></em></a>
					<div class="sublist">
						<ul>
							<li><sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_USER">
									<g:link controller="CMSUser">
										<span><g:message code="navigation.admin.employeeList" /></span>
										<em></em>
									</g:link>
								</sec:ifAnyGranted> <sec:ifAnyGranted roles="ROLE_ADMIN">
									<g:link controller="CMSUser" action="listEmpCall">
										<span><g:message code="navigation.admin.employeeCall" /></span>
										<em></em>
									</g:link>
								</sec:ifAnyGranted> <sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_ACCOUNTING">
									<g:link controller="tips" action="list">
										<span><g:message code="navigation.admin.tips" /></span>
										<em></em>
									</g:link>
								</sec:ifAnyGranted></li>
						</ul>
					</div></li>
			</sec:ifAnyGranted>

			<li class="mainlist"><sec:access url="/order/list">
					<g:link controller="order">
						<span><g:message code="navigation.admin.order" /></span>
						<em></em>
					</g:link>
				</sec:access></li>
			<sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_USER">
				<li class="mainlist"><a href="javascript:void(0)"><span><g:message
								code="navigation.admin.customer" /></span><em></em></a>
					<div class="sublist">
						<ul>
							<sec:access url="/CMSFeedback/list">
								<li><g:link controller="CMSFeedback">
										<span><g:message code="navigation.admin.feedback" /></span>
										<em></em>
									</g:link> <g:link controller="customerInfo" action="info">
										<span><g:message
												code="navigation.admin.customer.information" /></span>
										<em></em>
									</g:link>
									<g:link controller="customerEmail" action="listBackEnd">
										<span><g:message code="navigation.admin.customerEmail" /></span>
										<em></em>
									</g:link> </li>
							</sec:access>
						</ul>
					</div></li>
			</sec:ifAnyGranted>

			<sec:ifAnyGranted roles="ROLE_ADMIN">
				<li class="mainlist"><a href="javascript:void(0)"><span><g:message
								code="navigation.admin.content" /></span><em></em></a>
					<div class="sublist">
						<ul>
							<!--<sec:access url="/CMSFeedback/list">-->
							<li><g:link controller="contactUs">
									<span><g:message code="navigation.admin.contactus" /></span>
									<em></em>
								</g:link> <g:link controller="policy">
									<span><g:message code="navigation.admin.policy" /></span>
									<em></em>
								</g:link> <g:link controller="different">
									<span><g:message code="navigation.admin.different" /></span>
									<em></em>
								</g:link> <g:link controller="advert">
									<span><g:message code="navigation.admin.advert" /></span>
									<em></em>
								</g:link> <g:link controller="news" action="list">
									<span><g:message code="navigation.admin.news" /></span>
									<em></em>
								</g:link></li>
								<g:link controller="flightPath" action="showForm">
									<span><g:message code="flight.path.label.header" /></span>
									<em></em>
								</g:link></li>
							<!--</sec:access>-->
						</ul>
					</div></li>
			</sec:ifAnyGranted>

			<sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_USER">
				<li><g:link controller="orderEmployee">
						<span><g:message code="navigation.admin.recall" /></span>
						<em></em>
					</g:link></li>
			</sec:ifAnyGranted>

			<sec:ifAnyGranted roles="ROLE_ADMIN">
				<li><g:link controller="fee">
						<span><g:message code="navigation.admin.fee" /></span>
						<em></em>
					</g:link></li>
			</sec:ifAnyGranted>
			<sec:ifAnyGranted roles="ROLE_ADMIN">
				<li class="mainlist"><a href="javascript:void(0)"><span><g:message
								code="navigation.admin.tour" /></span><em></em></a>
					<div class="sublist">
						<ul>
							<li><g:link controller="information" action="listBackEnd">
									<span><g:message code="navigation.admin.list.tour" /></span>
									<em></em>
								</g:link> <g:link controller="tourIntro">
									<span><g:message code="navigation.admin.tour.intro" /></span>
									<em></em>
								</g:link> <g:link controller="link" action="listBackEnd">
									<span><g:message code="navigation.admin.list.link" /></span>
									<em></em>
								</g:link></li>
						</ul>
					</div></li>
			</sec:ifAnyGranted>
			<sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_USER, ROLE_ACCOUNTING">
				<li><g:link controller="rate">
						<span><g:message code="navigation.admin.exchange.rate" /></span>
						<em></em>
					</g:link></li>
			</sec:ifAnyGranted>

			<sec:ifAnyGranted roles="ROLE_ADMIN">
			<li><g:link controller="moneycode">
					<span><g:message code="navigation.admin.moneycodemgr"
							default="Quản lý mã tiền" /></span>
					<em></em>
				</g:link></li>
			</sec:ifAnyGranted>
			<sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_ACCOUNTING">
				<li><g:link controller="income">
						<span><g:message code="navigation.admin.totalincome" /></span>
						<em></em>
					</g:link></li>
			</sec:ifAnyGranted>
		</ul>
	</div>


</div>

