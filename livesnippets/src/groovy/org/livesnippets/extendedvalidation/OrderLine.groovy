package org.livesnippets.extendedvalidation

import be.ixor.grails.extendedvalidation.Validateable

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
