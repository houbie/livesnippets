package org.livesnippets.webflow

import org.springframework.web.servlet.support.RequestContextUtils as RCU

import org.springframework.webflow.definition.StateDefinition
import org.springframework.webflow.engine.Flow
import org.springframework.webflow.engine.SubflowState
import org.springframework.webflow.engine.TransitionableState
import org.springframework.webflow.engine.ViewState
import org.springframework.webflow.engine.impl.FlowExecutionImpl
import org.springframework.webflow.execution.FlowExecutionKey
import org.springframework.webflow.execution.FlowSession
import org.springframework.webflow.execution.repository.FlowExecutionRepository

class WebflowTagLib {
    static namespace = "flow"

    FlowExecutionRepository flowExecutionRepository
    def messageSource

    def breadCrumbs = {attrs ->
        FlowExecutionImpl execution = flowExecution
        if (execution) {
            out << '<div class="breadCrumb">'
            writeFlowSessionBreadCrumbs(execution, execution.flowSessions[0], 0)
            out << '</div>'
            for (i in 1..execution.flowSessions.size()) {
                out << '<br/>'
            }
        }
    }

    private void writeFlowSessionBreadCrumbs(FlowExecutionImpl execution, FlowSession session, int sessionIndex) {
        List<TransitionableState> states = getBreadCrumbStates(session.definition)
        if (states) {
            def locale = RCU.getLocale(request)
            out << '<ul>'
            for (state in states) {
                def label = messageSource.getMessage(state.id, null, state.id, locale)
                out << '<li'
                if (session.state.id == state.id) {
                    out << ' class="active">'
                    out << label
                    int nextSessionIndex = sessionIndex + 1
                    if (execution.flowSessions.size() > nextSessionIndex) {
                        writeFlowSessionBreadCrumbs(execution, execution.flowSessions[nextSessionIndex], nextSessionIndex)
                    }
                } else {
                    out << '>' << label
                }
                out << '</li>'
            }
            out << '</ul>'
        }
    }

    private List<TransitionableState> getBreadCrumbStates(Flow definition) {
        List result = []
        for (stateId in definition.stateIds) {
            StateDefinition stateDefinition = definition.getState(stateId)
            if (stateDefinition instanceof ViewState || stateDefinition instanceof SubflowState) {
                result << stateDefinition
            }
        }
        return result
    }

    private FlowExecutionImpl getFlowExecution() {
        if (flowExecutionKey) {
            return flowExecutionRepository.getFlowExecution((flowExecutionKey))
        }
        return null
    }

    private FlowExecutionKey getFlowExecutionKey() {
        if (params.execution) {
            String key = (params.execution instanceof String[]) ? params.execution[0] : params.execution
            return flowExecutionRepository.parseFlowExecutionKey(key)
        }
        return null
    }

}
