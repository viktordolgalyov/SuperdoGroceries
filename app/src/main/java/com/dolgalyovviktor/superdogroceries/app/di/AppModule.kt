package com.dolgalyovviktor.superdogroceries.app.di

import com.dolgalyovviktor.superdogroceries.common.arch.Dispatchers
import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import timber.log.Timber
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers as KotlinDispatchers

@Module
class AppModule {

    @Provides
    @Singleton
    fun httpClient() = HttpClient(CIO) {
        engine {
            maxConnectionsCount = 1000
            endpoint {
                maxConnectionsPerRoute = 100
                pipelineMaxSize = 20
                keepAliveTime = 5000//ms
                connectTimeout = 5000//ms
                connectAttempts = 5
            }
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.d(message)
                }
            }
            level = LogLevel.ALL
        }
        install(WebSockets) {
            pingInterval = 500//ms
            followRedirects = true
        }
    }

    @Provides
    @Singleton
    fun dispatchers() = Dispatchers(
        background = KotlinDispatchers.IO,
        main = KotlinDispatchers.Main
    )
}