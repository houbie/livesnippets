

<%@ page import="org.livesnippets.webflow.Developer" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'developer.label', default: 'Developer')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-developer" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="create-developer" class="content scaffold-create" role="main">
            <flow:breadCrumbs/>
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${developerInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${developerInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
            %{-- change action to getDeveloper --}%
			<g:form>
				<fieldset class="form">
				
					<div class="fieldcontain ${hasErrors(bean: developerInstance, field: 'name', 'error')} required">
						<label for="name"><g:message code="developer.name.label" default="Name" /><span class="required-indicator">*</span></label>
						<g:textField name="name" required="" value="${developerInstance?.name}"/>
					</div>
				
					<div class="fieldcontain ${hasErrors(bean: developerInstance, field: 'experience', 'error')} required">
						<label for="experience"><g:message code="developer.experience.label" default="Experience" /><span class="required-indicator">*</span></label>
						<g:select name="experience" from="${org.livesnippets.webflow.Developer$Experience?.values()}" keys="${org.livesnippets.webflow.Developer$Experience.values()*.name()}" required="" value="${developerInstance?.experience?.name()}"/>
					</div>
				
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
					<g:submitButton name="cancel" class="cancel" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
