import org.springframework.scheduling.quartz.CronTriggerBean
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean
import org.springframework.scheduling.quartz.SchedulerFactoryBean

// Place your Spring DSL code here
beans = {

    cleanDataJob(MethodInvokingJobDetailFactoryBean) {
        targetObject = ref('projectDataCleanerService')
        targetMethod = 'resetData'
    }

    cleanDataCronTrigger(CronTriggerBean) {
        jobDetail = ref('cleanDataJob')
    }

    scheduler(SchedulerFactoryBean) {
        triggers = [ref('cleanDataCronTrigger')]
    }

}
