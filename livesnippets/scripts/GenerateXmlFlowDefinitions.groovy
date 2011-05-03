import grails.util.GrailsWebUtil
import groovy.xml.MarkupBuilder
import java.lang.reflect.Field
import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.grails.webflow.engine.builder.ClosureExpression
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry
import org.springframework.webflow.engine.*

includeTargets << grailsScript("_GrailsBootstrap")


target('default': "Generate XML flow definitions") {
    depends(bootstrap)

    ant.mkdir(dir: "${projectWorkDir}/webflowdefinitions")

    FlowDefinitionRegistry flowRegistry = appCtx.getBean('flowRegistry')

    for (flowId in flowRegistry.flowDefinitionIds) {
        File file = getOutputFile(flowId)
        GrailsWebUtil.bindMockWebRequest(appCtx)
        def flow = flowRegistry.getFlowDefinition(flowId)
        writeFlowXmlDefinition(flow, file.newPrintWriter())
    }
}

File getOutputFile(String flowId) {
    def controllerClass = grailsApp.getArtefactByLogicalPropertyName('Controller', flowId[0..<flowId.indexOf('/')]).fullName
    def pkg = ""
    def pos = controllerClass.lastIndexOf('.')
    if (pos != -1) {
        // Package name with trailing '.'
        pkg = controllerClass[0..pos]
    }

    return new File("${basedir}/grails-app/controllers/${pkg.replace('.' as char, '/' as char)}${flowId.replace('/', '-')}-flow.xml")
}

void writeFlowXmlDefinition(flow, writer) {
    def builder = new MarkupBuilder(writer)
    builder.flow(xmlns: "http://www.springframework.org/schema/webflow", 'xmlns:xsi': "http://www.w3.org/2001/XMLSchema-instance",
            'xsi:schemaLocation': "http://www.springframework.org/schema/webflow \n http://www.springframework.org/schema/webflow/spring-webflow-2.0.xsd") {
        for (id in flow.stateIds) {
            def state = flow.getState(id)
            this."build${state.class.simpleName}"(state, builder)
        }
    }
}


buildViewState = {ViewState state, delegate ->
    setDelegate(delegate)
    'view-state'(id: state.id) {
        for (transition in state.transitions) {
            buildTransition(transition, delegate)
        }
    }
}

buildActionState = {ActionState state, delegate ->
    setDelegate(delegate)
    'action-state'(id: state.id) {
        for (transition in state.transitions) {
            buildTransition(transition, delegate)
        }
    }
}

buildSubflowState = {SubflowState state, delegate ->
    setDelegate(delegate)
    'subflow-state'(id: state.id) {
        for (transition in state.transitions) {
            buildTransition(transition, delegate)
        }
    }
}

buildEndState = {EndState state, delegate ->
    setDelegate(delegate)
    'end-state'(id: state.id)
}

buildTransition = {Transition trans, delegate ->
    setDelegate(delegate)

    def targetStateIdExpression = trans.targetStateResolver.targetStateIdExpression
    if (targetStateIdExpression instanceof ClosureExpression) {
        def controller = getController(targetStateIdExpression.closure)
        for (state in findSelectStates(controller, targetStateIdExpression.closure)) {
            transition(on: trans.matchingCriteria, to: state)
        }
    } else {
        transition(on: trans.matchingCriteria, to: trans.targetStateId)
    }
}

Collection findSelectStates(def controller, def closure) {
    Field field = controller.class.declaredFields.find {
        it.accessible = true
        it.get(controller)?.class == closure.class
    }

    if (field?.name?.startsWith('select')) {
        return field.name.substring(6).split('Or').collect {StringUtils.uncapitalize(it)}
    } else {
        return []
    }
}

def getController(Closure closure) {
    def owner = closure.owner
    while (owner instanceof Closure) {
        owner = owner.owner
    }
    return owner
}
