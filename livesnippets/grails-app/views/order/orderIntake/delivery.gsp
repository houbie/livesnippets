<%@ page import="org.livesnippets.extendedvalidation.Product; org.livesnippets.extendedvalidation.Customer.CreditScore; org.livesnippets.webflow.Project" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Order intake: add products</title>
	</head>
	<body>
		<div id="create-order" class="content scaffold-create" role="main">
			<h1>Order intake: add products</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
            <g:if test="${order.hasErrors()}">
                <ul class="errors" role="alert">
                    <g:each in="${order.allErrorsRecursive}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:each>
                </ul>
            </g:if>
			<g:form action="orderIntake" >
                <g:each in="${order.orderLines}" var="orderLine" status="i">
                    <fieldset class="form">
                        <div class="fieldcontain ${hasErrors(bean: orderLine.deliveryAddress, field: 'street', 'error')} required">
                            <label for="name">Delivery street<span class="required-indicator">*</span></label>
                            <g:textField name="orderLines[${i}].deliveryAddress.street" required="" value="${orderLine.deliveryAddress.street}"/>
                        </div>
                        <div class="fieldcontain ${hasErrors(bean: orderLine.deliveryAddress, field: 'city', 'error')} required">
                            <label for="name">Delivery city<span class="required-indicator">*</span></label>
                            <g:textField name="orderLines[${i}].deliveryAddress.city" required="" value="${orderLine.deliveryAddress.city}"/>
                        </div>

                    </fieldset>
                </g:each>
                <fieldset class="buttons">
					<g:submitButton name="finish" value="Finish" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
