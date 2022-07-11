package com.dolgalyovviktor.superdogroceries.common.arch

import javax.inject.Qualifier
import javax.inject.Scope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppContext

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FlowScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SubFlowScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SubScreenScope