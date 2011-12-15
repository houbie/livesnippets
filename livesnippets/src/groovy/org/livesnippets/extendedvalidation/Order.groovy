package org.livesnippets.extendedvalidation

import be.ixor.grails.richdomain.validation.Validateable
import org.livesnippets.extendedvalidation.Customer.CreditScore

@Validateable
class Order implements Serializable {
    Customer customer = new Customer()
    String contactPerson
    List<OrderLine> orderLines = []

    String toString(){
        "Customer: $customer, contactPerson: $contactPerson, order lines: $orderLines"
    }

    static constraints = {
        invoiceData {
            customer(cascade: true)
            contactPerson(nullable: false, blank: false)
        }

        orderLines(cascade: true)

        creditCheck(validator: {order ->
            if (order.customer.creditScore == CreditScore.UNKNOWN && order.orderLines.size() > 10) {
                return "order.creditCheck.UNKNOWN"
            }
            if (order.customer.creditScore == CreditScore.BAD && order.orderLines.size() > 1) {
                return "order.creditCheck.BAD"
            }
        })
    }
}
