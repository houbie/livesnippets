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
            <g:form >
				<fieldset class="form">

					<div class="fieldcontain ${hasErrors(bean: order.creditCard, field: 'number', 'error')} required">
						<label >Creditcard number (valid number: 5114039417258712)<span class="required-indicator">*</span></label>
						<g:textField name="creditCard.number" required="" value="${order?.creditCard?.number}"/>
					</div>
					<div class="fieldcontain ${hasErrors(bean: order.creditCard, field: 'expirationMonth', 'error')} required">
						<label >Expiration date<span class="required-indicator">*</span></label>
						<g:select name="creditCard.expirationMonth" required="" value="${order?.creditCard?.expirationMonth}" from="${1..12}" />
						<g:select name="creditCard.expirationYear" required="" value="${order?.creditCard?.expirationYear}" from="${2012..2020}"/>
					</div>

				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="finish" value="Finish" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
