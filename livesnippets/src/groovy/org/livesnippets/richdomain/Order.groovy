package org.livesnippets.richdomain

import be.ixor.grails.richdomain.validation.Validateable
import javax.annotation.Resource

@Validateable
class Order implements Serializable {
    @Resource
    CreditCardService creditCardService

    Customer customer = new Customer()
    String contactPerson
    List<OrderLine> orderLines = []
    PaymentMethod paymentMethod
    CreditCard creditCard

    String toString() {
        "Customer: $customer, contactPerson: $contactPerson, order lines: $orderLines"
    }

    static constraints = {
        invoiceData {
            customer(cascade: true)
            contactPerson(nullable: false, blank: false)
        }

        orderLines(cascade: true)
        creditCard(cascade: true)

        maxInvoiceQuantity(validator: {order ->
            if (order.paymentMethod == PaymentMethod.INVOICE && order.orderLines*.quantity.sum() > 10) {
                return "maxInvoiceQuantity"
            }
        })
    }
}

enum PaymentMethod {
    CREDIT_CARD,
    INVOICE
}