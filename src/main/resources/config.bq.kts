import io.bootique.kotlin.config.modules.BootiqueConfiguration
import io.bootique.kotlin.config.modules.config
import io.bootique.kotlin.config.undertow.httpListener
import io.bootique.kotlin.config.undertow.undertowFactory
import io.bootique.kotlin.demo.ApplicationConfiguration
import io.bootique.undertow.UndertowFactory

val application = ApplicationConfiguration(
    message = "Message from config.bq.kts"
)
val undertow = undertowFactory(
    httpListeners = listOf(
        httpListener(port = 9988)
    )
)

addConfig("application", application)
addConfig("undertow", undertow)
