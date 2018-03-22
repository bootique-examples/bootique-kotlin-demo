import io.bootique.kotlin.config.modules.config
import io.bootique.kotlin.config.undertow.httpListener
import io.bootique.kotlin.config.undertow.undertowFactory
import io.bootique.kotlin.demo.ApplicationConfiguration

config {
    addConfig("application" to ApplicationConfiguration(
        message = "Message from config.kts"
    ))
    addConfig("undertow" to undertowFactory(httpListeners = listOf(
        httpListener(port = 9988)
    )))
}
