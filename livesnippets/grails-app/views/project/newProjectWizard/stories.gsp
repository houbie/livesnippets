<%@ page import="org.livesnippets.webflow.Project" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}"/>
    <title>New project wizard: Stories</title>
</head>
<body>
<div id="create-project" class="content scaffold-create" role="main">
    <h1>New project wizard: Stories</h1>
    <div id="editStories">
        <g:render template="newProjectWizard/editStories"/>
    </div>
</div>
<div>
    <g:link controller="project" action="newProjectWizard" event="finish">Finish</g:link>
</div>
</body>
</html>
