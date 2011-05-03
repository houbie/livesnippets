<%@ page import="org.livesnippets.extendedvalidation.Customer.CreditScore; org.livesnippets.webflow.Project" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Order intake: invoice data</title>
	</head>
	<body>
		<div id="create-order" class="content scaffold-create" role="main">
			<h1>Order intake: invoice data</h1>
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
				<fieldset class="form">

					<div class="fieldcontain ${hasErrors(bean: order.customer, field: 'name', 'error')} required">
						<label for="name">Customer name<span class="required-indicator">*</span></label>
						<g:textField name="customer.name" required="" value="${order?.customer?.name}"/>
					</div>
					<div class="fieldcontain ${hasErrors(bean: order.customer.address, field: 'street', 'error')} required">
						<label for="name">Customer street<span class="required-indicator">*</span></label>
						<g:textField name="customer.address.street" required="" value="${order?.customer?.address?.street}"/>
					</div>
					<div class="fieldcontain ${hasErrors(bean: order.customer.address, field: 'city', 'error')} required">
						<label for="name">Customer city<span class="required-indicator">*</span></label>
						<g:textField name="customer.address.city" required="" value="${order?.customer?.address?.city}"/>
					</div>
                    <div class="fieldcontain ${hasErrors(bean: order.customer, field: 'creditScore', 'error')} required">
                        <label for="experience">Customer credit score<span class="required-indicator">*</span></label>
                        <g:select name="customer.creditScore" from="${CreditScore.values()}" required="" value="${order?.customer?.creditScore}"/>
                    </div>
                    <div class="fieldcontain ${hasErrors(bean: order, field: 'contactPerson', 'error')} required">
                        <label for="name">Contact person<span class="required-indicator">*</span></label>
                        <g:textField name="contactPerson" required="" value="${order?.contactPerson}"/>
                    </div>

				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="next" value="Next" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
