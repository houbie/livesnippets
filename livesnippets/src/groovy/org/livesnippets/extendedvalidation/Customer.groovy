package org.livesnippets.extendedvalidation

import be.ixor.grails.richdomain.validation.Validateable

@Validateable
class Customer implements Serializable {
    enum CreditScore {
        BAD, UNKNOWN, GOOD
    }

    String name
    Address address = new Address()
    CreditScore creditScore = CreditScore.UNKNOWN

    String toString(){
        "{$name @ $address}"
    }

    static constraints = {
        invoiceData {
            name(nullable: false, blank: false)
            address(cascade: true)
            creditScore(nullable: false)
        }
    }
}
