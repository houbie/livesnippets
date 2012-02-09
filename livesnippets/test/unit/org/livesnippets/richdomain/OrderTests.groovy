package org.livesnippets.richdomain


class OrderTests extends GroovyTestCase {

    void testConstraints() {
        Order order = new Order()
        order.orderLines << new OrderLine(product: Product.Apples, quantity: 10)

        assert !order.validate()
        assert order.errors.hasFieldErrors('contactPerson')
        assert order.errors.hasFieldErrors('customer')
        assert order.customer.errors.hasFieldErrors('name')

        order = new Order()
        order.orderLines << new OrderLine(product: Product.Apples, quantity: 10)
        order.orderLines << new OrderLine(product: Product.Pears, quantity: 5)
        order.customer.name = 'Ivo Houbrechts'
        order.paymentMethod = PaymentMethod.INVOICE
        order.contactPerson = 'Joe'
        assert order.validate(groups: ['invoiceData'], excludes: ['customer.address'])

        assert !order.validate(includes: ['maxInvoiceQuantity', 'orderLines.*'])
        assert (order.errors.globalErrors.first().codes as List).contains('org.livesnippets.richdomain.Order.maxInvoiceQuantity')

        order.clearErrors()
        order.orderLines*.clearErrors()
        assert order.validate(includes: ['orderLines.quantity'])
    }

}
