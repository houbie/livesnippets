<%@ page import="org.livesnippets.richdomain.PaymentMethod; org.livesnippets.richdomain.Product; org.livesnippets.webflow.Project" %>
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
			<g:form >
                <g:each in="${order.orderLines}" var="orderLine" status="i">
                    <h3>Delivery address for ${orderLine.quantity} ${orderLine.product}</h3>
                    <fieldset class="form">
                        <div class="fieldcontain ${hasErrors(bean: orderLine.deliveryAddress, field: 'street', 'error')} required">
                            <label f>Delivery street<span class="required-indicator">*</span></label>
                            <g:textField name="orderLines[${i}].deliveryAddress.street" required="" value="${orderLine.deliveryAddress.street}"/>
                        </div>
                        <div class="fieldcontain ${hasErrors(bean: orderLine.deliveryAddress, field: 'city', 'error')} required">
                            <label >Delivery city<span class="required-indicator">*</span></label>
                            <g:textField name="orderLines[${i}].deliveryAddress.city" required="" value="${orderLine.deliveryAddress.city}"/>
                        </div>

                    </fieldset>
                </g:each>
                <fieldset class="buttons">
					<g:submitButton name="next" value="${order.paymentMethod == PaymentMethod.CREDIT_CARD ? "Next" : "Finish"}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
