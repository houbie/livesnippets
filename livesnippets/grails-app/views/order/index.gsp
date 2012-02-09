<%@ page import="org.livesnippets.webflow.Project" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Order intake</title>
	</head>
	<body>
		<div id="create-order" class="content scaffold-create" role="main">
			<h1>Order intake</h1>
			<g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:if test="${flash.newOrder}">
                <div id="show-order" class="content scaffold-show" role="main">
                    <h1>The order was successfully registered</h1>
                    <g:if test="${flash.message}">
                    </g:if>
                    <ol class="property-list order">
                        <li class="fieldcontain">
                            <span id="name-label" class="property-label">Customer</span>
                            <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${flash.newOrder.customer}" field="name"/></span>
                        </li>
                        <g:each in="${flash.newOrder.orderLines}" var="orderLine" status="i">
                            <li class="fieldcontain">
                                <span id="product-label${i}" class="property-label">Product</span>
                                <span class="property-value" aria-labelledby="product-label${i}"><g:fieldValue bean="${orderLine}" field="product"/> (<g:fieldValue bean="${orderLine}" field="quantity"/>)</span>
                            </li>
                        </g:each>
                    </ol>
                </div>
            </g:if>
            <g:form action="orderIntake" >
				<fieldset class="buttons">
					<g:submitButton name="start" value="Start order intake wizard" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
