<%@ page import="org.livesnippets.webflow.Project" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Select team members</title>
	</head>
	<body>
		<a href="#list-project" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="list-project" class="content scaffold-list" role="main">
            <flow:breadCrumbs/>
			<h1>Select team members</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
                        <th><g:message code="developer.name" default="Name"/></th>
                        <th><g:message code="developer.experience" default="Experience"/></th>
                        <th/>
					</tr>
				</thead>
				<tbody>
                <g:each in="${projectInstance.team}" status="i" var="developer">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${fieldValue(bean: developer, field: "name")}</td>
                        <td>${fieldValue(bean: developer, field: "experience")}</td>
                        <td><g:link event="remove" params="${[name: developer.name]}">Remove</g:link></td>
                    </tr>
                </g:each>
				</tbody>
			</table>
            <fieldset class="buttons">
                <g:form>
                    <g:submitButton name="add" value="Add developer"/>
                    <g:submitButton name="next" value="Next"/>
                </g:form>
            </fieldset>
		</div>
	</body>
</html>
