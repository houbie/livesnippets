<%@ page namespace="t" %>
<%--/**
     * A collapsable panel example
     *
     * @attr collapsed OPTIONAL the initial state of the panel, default is false
     */--%>
<%
    boolean collapsed = Boolean.valueOf(attrs.collapsed)
%>
<div class="collapsablePanel">
    <div class="header"></div>
    <div class="body"></div>
</div>
