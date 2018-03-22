package io.bootique.kotlin.demo

import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.util.SameThreadExecutor
import kotlinx.coroutines.experimental.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newSingleThreadContext

/**
 * Handler that allows to use undertow with kotlin coroutines.
 *
 * @author Ibragimov Ruslan
 * @since 1.0
 */
private val handlerContext = newSingleThreadContext("CoroutinesHandler")

class CoroutinesHandler(
    private val handler: suspend (HttpServerExchange) -> Unit
) : HttpHandler {
    override fun handleRequest(exchange: HttpServerExchange) {
        exchange.dispatch(SameThreadExecutor.INSTANCE, Runnable {
            launch(context = handlerContext, start = UNDISPATCHED) {
                handler(exchange)
            }
        })
    }
}
