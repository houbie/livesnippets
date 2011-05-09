package org.livesnippets.extendedvalidation

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
}
