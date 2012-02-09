package org.livesnippets.richdomain

class CreditCardService {
    static transactional = false

    boolean validateCard(String cardNumber, int expirationMonth, int expirationYear) {
        //in reality this would make a webservice call..
        return new Date() < Date.parse('M-y', "$expirationMonth-$expirationYear")
    }
}
