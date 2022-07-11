package com.dolgalyovviktor.superdogroceries.data.remote

import com.dolgalyovviktor.superdogroceries.BuildConfig
import com.dolgalyovviktor.superdogroceries.data.remote.model.SuperdoProduct
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber

class ProductRemoteSource(
    private val client: HttpClient
) {

    fun observeProducts(): Flow<SuperdoProduct> = callbackFlow {
        client.webSocket(BuildConfig.API_URL) {
            incoming.consumeEach {
                if (it !is Frame.Text) return@consumeEach
                val data = kotlin.runCatching {
                    Json.decodeFromString<SuperdoProduct>(it.readText())
                }
                data.exceptionOrNull()?.run(Timber::e)
                data.getOrNull()?.run(this@callbackFlow::trySend)
            }
        }
        awaitClose { Timber.i("Connection closed") }
    }
}