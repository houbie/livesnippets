package org.livesnippets.richdomain

import org.springframework.web.context.request.RequestContextHolder

class OrderController {
    def index = {
    }

    def orderIntakeFlow = {
        onStart {
            flow.order = new Order()
        }

        invoiceData {
            on("next") {
                bindData(flow.order, params)
                if (!flow.order.validate(groups: ["invoiceData"])) {
                    error()
                } else if (flow.order.paymentMethod == PaymentMethod.CREDIT_CARD) {
                    flow.order.creditCard = new CreditCard()
                }
            }.to("products")
        }

        products {
            on("add") {
                bindData(flow.order, params)
                flow.order.orderLines << new OrderLine()
            }.to("products")
            on("next") {
                bindData(flow.order, params)
                if (!flow.order.validate(includes: ["orderLines"], excludes: ["orderLines.deliveryAddress"])) {
                    error()
                }
            }.to("delivery")
        }

        delivery {
            on("next") {
                bindData(flow.order, params)
                def validationIncludes = flow.order.paymentMethod == PaymentMethod.CREDIT_CARD ? ["orderLines"] : null
                if (!flow.order.validate(includes: validationIncludes)) {
                    error()
                } else {
                    RequestContextHolder.currentRequestAttributes().flashScope.newOrder = flow.order
                }
            }.to(selectCreditCardOrEnd)
        }

        creditCard {
            on("finish") {
                bindData(flow.order, params)
                if (!flow.order.validate()) {
                    error()
                } else {
                    RequestContextHolder.currentRequestAttributes().flashScope.newOrder = flow.order
                }
            }.to("end")
        }

        end {
            redirect(action: "index")
        }
    }

    private def selectCreditCardOrEnd = {
        flow.order.paymentMethod == PaymentMethod.CREDIT_CARD ? "creditCard" : "end"
    }
}
