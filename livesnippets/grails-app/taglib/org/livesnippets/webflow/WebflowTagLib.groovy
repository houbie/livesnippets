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

    /**
     * Creates a link that triggers a webflow event that can be invoked via ajax.
     *
     * @attr event Webflow _eventId parameter
     * @attr update Either a map containing the elements to update for 'success' or 'failure' states, or a string with the element to update in which cause failure events would be ignored
     * @attr before The javascript function to call before the remote function call
     * @attr after The javascript function to call after the remote function call
     * @attr asynchronous Whether to do the call asynchronously or not (defaults to true)
     * @attr method The method to use the execute the call (defaults to "post")
     * @attr id The id to use in the link
     * @attr params A map containing URL query parameters
     */
    def remoteLink = {attrs, body ->
        attrs.controller = controllerName
        attrs.action = actionName
        if (attrs.params == null) {
            atts.params = [:]
        }
        attrs.params.ajaxSource = true
        if (!request['flowExecutionKey']) {
            request['flowExecutionKey'] = params.execution
        }
        out << g.remoteLink(attrs, body)
    }

    /**
     * A form which uses the javascript provider to serialize its parameters and submit via an asynchronous ajax call in a webflow.
     *
     * @attr event Webflow _eventId parameter
     * @attr name REQUIRED The form name
     * @attr update Either a map containing the elements to update for 'success' or 'failure' states, or a string with the element to update in which cause failure events would be ignored
     * @attr before The javascript function to call before the remote function call
     * @attr after The javascript function to call after the remote function call
     * @attr asynchronous Whether to do the call asynchronously or not (defaults to true)
     * @attr method The method to use the execute the call (defaults to "post")
     */
    def formRemote = {attrs, body ->
        def url = [:]
        url.controller = controllerName
        url.action = actionName
        url.params = attrs.params ?: [:]
        url.params.ajaxSource = true
        if (!request['flowExecutionKey']) {
            request['flowExecutionKey'] = params.execution
        }
        if (attrs.event) {
            url.params._eventId = attrs.remove('event')
        }
        attrs.url = url
        out << g.formRemote(attrs, body)
    }

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
