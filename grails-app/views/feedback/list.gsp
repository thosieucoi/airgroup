<html>
<head>
<meta name="layout" content="responsivemasterpage" />
<g:set var="entityName"
	value="${message(code: 'feedback.label', default: 'Feedback')}" />
<title><g:message code="title.homepage" args="[entityName]" /></title>
</head>
<body>
	<section id="break-crumb" class="row title-b">
                <article class="col-md-12 col-sm-12 col-xs-12">
					<g:link controller="home" action="index"><img
					src="${resource(dir:'images/img',file:'icon-home-black.png')}"
					alt="home" />Trang chủ</g:link>
                    <span class="">&gt;</span>
                    <a href="${createLink(action:'list', controller:'feedback') }" class="current">Phản hồi</a>
                </article>
            </section>
	<section class="row">
		<g:form id="feedback-form" action="save">
			<article id="chon-chuyen-bay-1" class="col-md-8 col-sm-8 col-xs-12">
			<h1>
				<p>Khách hàng nói về chúng tôi</p>
			</h1>
				<div class="debook">
					<g:each in="${feedbackInstanceList}" status="i"
						var="feedbackInstance">

						<div class="feedback">
							<table>
								<tr>
									<td valign="top" width="65px"><a href=""><img
											src="${resource(dir:'images',file:'avatar.png')}" /></a></td>
									<td valign="top"><span class="datepost"> ${message(code: 'feedback.listform.sendDate', default: 'Sent Date')}:
											<g:formatDate formatName="date.time.format"  date="${feedbackInstance.sendDate}" />
									</span><br /> <span class="nameKH"> ${fieldValue(bean: feedbackInstance, field: "name")}
											- ${fieldValue(bean: feedbackInstance, field: "phoneNumber")} ${fieldValue(bean: feedbackInstance, field: "address")}
									</span></td>
								</tr>
								<tr>
									<td colspan="2">
										<p>
											"${fieldValue(bean: feedbackInstance, field: "content")}"
										</p>
									</td>
								</tr>
							</table>
						</div>

					</g:each>

					<div style="clear: both"></div>

					<div class="paginateButtons">
						<g:hiddenField controller="feedback" action="list" name="offset" value="${offset}"/>
						<g:hiddenField controller="feedback" action="list" name="max" value="${max}"/>
						<g:paginate next="Forward" prev="Back" max="5" maxsteps="10"
							controller="feedback" action="list"
							total="${feedbackTotal ? feedbackTotal:0}" />
					</div>
				</div>
			</article>

			<article class="col-md-4 col-sm-4 col-xs-12">
				<div style="height: 380px;" class="book hdd bg-type-red box-border-radius-5 region-type-2 box-padding-10">
					<div class="title title-b title-upper">
						<span class="title"> ${message(code: 'feedback.sendform.caption', default: 'Send your comment')}</span>
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

						</table>

						<input type="submit"
							value="${message(code: 'feedback.sendform.submitbutton.value', default: 'Send')}"
							class="send" />
					</div>

				</div>
			</article>
		</g:form>
	</section>
</body>
</html>
