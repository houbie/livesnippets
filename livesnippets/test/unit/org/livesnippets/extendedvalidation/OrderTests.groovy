package org.livesnippets.extendedvalidation

import be.ixor.grails.extendedvalidation.ValidationHelper
import org.livesnippets.extendedvalidation.Customer.CreditScore

class OrderTests extends GroovyTestCase {

    protected void setUp() {
        super.setUp();

        ValidationHelper.addValidationMethods(Order, null)
        ValidationHelper.addValidationMethods(Customer, null)
        ValidationHelper.addValidationMethods(OrderLine, null)
        ValidationHelper.addValidationMethods(Address, null)
    }


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
        order.customer.creditScore = CreditScore.BAD
        order.contactPerson = 'Joe'
        assert order.validate(groups: ['invoiceData'], excludes: ['customer.address'])

        assert !order.validate(includes: ['creditCheck', 'orderLines.*'])
        assert (order.errors.globalErrors.first().codes as List).contains('org.livesnippets.extendedvalidation.Order.creditCheck.BAD')

        order.clearErrors()
        order.orderLines*.clearErrors()
        assert order.validate(includes: ['orderLines.quantity'])
    }

}
