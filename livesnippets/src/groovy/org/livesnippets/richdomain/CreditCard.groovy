package org.livesnippets.richdomain

import be.ixor.grails.richdomain.RichDomain
import javax.annotation.Resource

@RichDomain
class CreditCard implements Serializable {
    @Resource
    transient CreditCardService creditCardService

    String number
    int expirationMonth
    int expirationYear
    
    static constraints = {
        number(creditCard: true)
        expirationMonth(min: 1, max: 12)
        expirationYear(min: 2012, max: 2099)

        onlineValidation(validator: {CreditCard creditCard ->
            if (!creditCard.creditCardService.validateCard(creditCard.number, creditCard.expirationMonth, creditCard.expirationYear)) {
                return "onlineValidation.error"
            }
        })
    }    
}
