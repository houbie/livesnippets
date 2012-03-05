<%@ page namespace="widget" import="be.ixor.grails.gsptaglib.TagLibCodeBlock; org.livesnippets.gsptaglib.HelloWorldService" %>
<% @TagLibCodeBlock
// helloWorldService will be auto-wired
HelloWorldService helloWorldService
%>
<h2>${helloWorldService.hello()}</h2>
