package org.livesnippets.richdomain

import be.ixor.grails.richdomain.validation.Validateable

@Validateable
class OrderLine implements Serializable {
    Product product
    int quantity
    Address deliveryAddress= new Address()

    String toString(){
        "($product:$quantity @ $deliveryAddress)"
    }


    static constraints = {
        product(nullable: false)
        quantity(min: 1)
        deliveryAddress(cascade: true)
    }
}
