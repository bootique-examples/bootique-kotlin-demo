package io.bootique.kotlin.demo

import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.util.SameThreadExecutor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

/**
 * Handler that allows to use undertow with kotlin coroutines.
 *
 * @author Ibragimov Ruslan
 * @since 1.0
 */
private val handlerContext = Executors.newFixedThreadPool(1).asCoroutineDispatcher()

class CoroutinesHandler(
    private val handler: suspend (HttpServerExchange) -> Unit
) : HttpHandler {
    override fun handleRequest(exchange: HttpServerExchange) {
        exchange.dispatch(SameThreadExecutor.INSTANCE, Runnable {
            GlobalScope.launch(context = handlerContext) {
                handler(exchange)
            }
        })
    }
}
