<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.airgroup.domain.Location"%>

<html>
<head>
<meta name="layout" content="responsivemasterpage" />
<title>Liên hệ để được phục vụ Vé máy bay, đại lý máy bay ahotua</title>
</head>
<body>
	<section id="break-crumb" class="row title-b">
		<article class="col-md-12 col-sm-12 col-xs-12">
			<g:link controller="home" action="index"><img
					src="${resource(dir:'images/img',file:'icon-home-black.png')}"
					alt="home" />Trang chủ</g:link>
			<span class="">&gt;</span> <a href="#" class="current">Liên Hệ</a>
		</article>
	</section>

	<section class="row">
		<article class="col-md-9 col-sm-9 col-xs-12">
			<div class="bg-type-2 region-type-2 box-padding-20">
				<div style="height: 380px; background-color: #f6c922;" class="book hdd bg-type-1 box-border-radius-5 region-type-2 box-padding-10">
					<div class="title title-b title-upper" style="text-align: left;">
						<span class="title">Liên hệ với chúng tôi</span>
					</div>
					
					<div class="line linehd">
						<table class="feedback">
							<tr>
								<td><g:if test="${flash.message}">
										<div class="message">
											<p class="validate">
												${flash.message}
											</p>
										</div>
									</g:if> <g:hasErrors bean="${feedbackInstance}">
										<div class="errors">
											<g:renderErrors bean="${feedbackInstance}" as="list" />
										</div>
									</g:hasErrors></td>
							</tr>
							<tr>
								<td
									class="value ${hasErrors(bean: feedbackInstance, field: 'name', 'errors')}"><span>
										${message(code: 'feedback.name.label', default: 'Name')}
								</span><br /> <g:textField class="control-input" name="name" maxlength="100"
										value="${feedbackInstance?.name}" /></td>
								<g:hiddenField name="feedbackTotal" value="${feedbackTotal}" />
							</tr>
							<tr>
								<td
									class="value ${hasErrors(bean: feedbackInstance, field: 'address', 'errors')}"><span>
										${message(code: 'feedback.address.label', default: 'Address')}
								</span><br /> <g:textField class="control-input" name="address" maxlength="45"
										value="${feedbackInstance?.address}" /></td>
							</tr>
							<tr>
								<td
									class="value ${hasErrors(bean: feedbackInstance, field: 'phoneNumber', 'errors')}"><span>
										${message(code: 'feedback.phoneNumber.label', default: 'Phone Number')}
								</span><br /> <g:textField class="control-input" name="phoneNumber"
										value="${feedbackInstance?.phoneNumber}" /></td>
							</tr>
							<tr>
								<td
									class="value ${hasErrors(bean: feedbackInstance, field: 'content', 'errors')}"><span>
										${message(code: 'feedback.content.label', default: 'Content')}
								</span><br /> <g:textArea class="control-input" name="content"
										value="${feedbackInstance?.content}" rows="5" cols="40"/></td>
							</tr>
							
							<tr>
								<td>
									<input type="submit"
									value="${message(code: 'feedback.sendform.submitbutton.value', default: 'Send')}"
									class="send" style="width: 100px !important; float: left;"/>
								</td>
							</tr>

						</table>
					</div>
				</div>
				<div class="space-40 hidden-xs"></div>
				<g:if test="${contact != null }">
					<wcm:find id="${contact.id}" var="node">
						<div class="debook">
							${node.content}
						</div>
					</wcm:find>
				</g:if>
			</div>
		</article>

		<g:include view="layouts/responsivewebpart/_searchFlightForm.gsp" />
	</section>
</body>
</html>