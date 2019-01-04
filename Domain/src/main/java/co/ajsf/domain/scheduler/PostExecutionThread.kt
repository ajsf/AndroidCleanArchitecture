package co.ajsf.domain.scheduler

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}