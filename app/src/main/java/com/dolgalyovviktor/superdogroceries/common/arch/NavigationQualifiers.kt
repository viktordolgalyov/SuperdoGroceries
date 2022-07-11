package com.dolgalyovviktor.superdogroceries.common.arch

import javax.inject.Qualifier

interface Navigation {
    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class Activity

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class Flow

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class SubFlow

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class Screen
}