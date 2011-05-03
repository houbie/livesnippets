package org.livesnippets.extendedvalidation

import be.ixor.grails.extendedvalidation.Validateable

@Validateable
class Address implements Serializable {
    String street
    String city

    String toString(){
        "street: $street, city: $city"
    }


    static constraints = {
        invoiceData {
            street(nullable: false, blank: false)
            city(nullable: false, blank: false)
        }
    }
}
