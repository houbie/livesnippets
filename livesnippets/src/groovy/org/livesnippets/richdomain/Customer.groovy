package org.livesnippets.richdomain

import be.ixor.grails.richdomain.validation.Validateable

@Validateable
class Customer implements Serializable {
    String name
    Address address = new Address()

    String toString() {
        "{$name @ $address}"
    }

    static constraints = {
        invoiceData {
            name(nullable: false, blank: false)
            address(cascade: true)
        }
    }
}
