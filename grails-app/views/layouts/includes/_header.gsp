<!-- header -->
<div class="header">
	<h1 class="logo">
		<g:link controller="home" />
	</h1>
	<ul class="menu">
		<g:if test="${params['controller'].equals('home')}">
			<li class="active-m">
		</g:if>
		<g:else>
		<li>
		</g:else>
		<g:link controller="home">
			<span><g:message code="navigation.home" /></span>
			<em></em>
		</g:link>
		</li>
		<g:if test="${params['controller'].equals('flight')}">
			<li class="active-m">
		</g:if>
		<g:else>
			<li>
		</g:else>
		<g:link controller="flight" action="search">
			<span><g:message code="navigation.booking" /></span>
			<em></em>
		</g:link>
		</li>
		<g:if test="${params['controller'].equals('guide')}">
			<li class="active-m">
		</g:if>
		<g:else>
			<li>
		</g:else>
		<g:link controller="guide">
			<span><g:message code="navigation.guide" /></span>
			<em></em>
		</g:link>
		</li>
		<g:if test="${params['controller'].equals('tour')}">
			<li class="active-m">
		</g:if>
		<g:else>
			<li>
		</g:else>
		<g:link controller="tour">
			<span><g:message code="navigation.tour" /></span>
			<em></em>
		</g:link>
		</li>
		<g:if test="${params['controller'].equals('contactUs')}">
			<li class="active-m">
		</g:if>
		<g:else>
			<li>
		</g:else>
		<g:link controller="contactUs" action="infor">
			<span><g:message code="navigation.contact" /></span>
			<em></em>
		</g:link>
		</li>
	</ul>

</div>