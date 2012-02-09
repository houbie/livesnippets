<%@ page import="org.livesnippets.richdomain.Product; org.livesnippets.webflow.Project" %>
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
                    <fieldset class="form">

                        <div class="fieldcontain ${hasErrors(bean: orderLine, field: 'product', 'error')} required">
                            <label >Product<span class="required-indicator">*</span></label>
                            <g:select name="orderLines[${i}].product" from="${Product.values()}" required="" value="${orderLine.product}"/>
                        </div>
                        <div class="fieldcontain ${hasErrors(bean: orderLine, field: 'quantity', 'error')} required">
                            <label >Quantity<span class="required-indicator">*</span></label>
                            <g:textField name="orderLines[${i}].quantity" required="" value="${orderLine.quantity}"/>
                        </div>
                    </fieldset>
                </g:each>
                <fieldset class="buttons">
					<g:submitButton name="add" value="Add product" />
					<g:submitButton name="next" value="Next" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
