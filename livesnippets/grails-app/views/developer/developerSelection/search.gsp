<%@ page import="org.livesnippets.webflow.Developer" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${g.message(code: 'developer.label', default: 'Developer')}" />
		<title>${title}</title>
	</head>
	<body>
		<a href="#list-developer" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
                %{-- change action to event --}%
				<li><g:link class="create" event="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-developer" class="content scaffold-list" role="main">
            <flow:breadCrumbs/>
            %{-- variable title from flow input --}%
			<h1>${title}</h1>
            %{-- don't use flash.message !! --}%
			<g:if test="${message}">
			<div class="message" role="status">${message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${g.message(code: 'developer.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="experience" title="${g.message(code: 'developer.experience.label', default: 'Experience')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${developerInstanceList}" status="i" var="developerInstance">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                        %{-- make controller  explicit and changed action='show' to 'getDeveloper; added event='select'; --}%
						<td><g:link event="select" id="${developerInstance.id}">${fieldValue(bean: developerInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: developerInstance, field: "experience")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<fieldset class="pagination">
				<g:paginate total="${developerInstanceTotal}" />
			</fieldset>
            <g:link event="cancel">Cancel</g:link>
		</div>
	</body>
</html>
