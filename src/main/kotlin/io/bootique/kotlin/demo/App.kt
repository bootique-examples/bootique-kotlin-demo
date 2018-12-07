package io.bootique.kotlin.demo

import com.google.inject.Inject
import com.google.inject.Provider
import com.google.inject.Provides
import com.google.inject.Singleton
import io.bootique.config.ConfigurationFactory
import io.bootique.kotlin.config.modules.KotlinConfigModuleProvider
import io.bootique.kotlin.core.KotlinBQModuleProvider
import io.bootique.kotlin.core.KotlinBootique
import io.bootique.kotlin.extra.config
import io.bootique.kotlin.guice.KotlinBinder
import io.bootique.kotlin.guice.KotlinModule
import io.bootique.undertow.UndertowModule
import io.bootique.undertow.UndertowModuleProvider
import io.bootique.undertow.handlers.RootHandler
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.server.RoutingHandler
import io.undertow.util.Headers
import kotlinx.coroutines.delay
import java.lang.System.currentTimeMillis

/**
 * Entry point of application.
 */
fun main(args: Array<String>) {
    KotlinBootique(args)
        .args("--server", "--config=classpath:config.kts")
        .module(ApplicationModuleProvider())
        .exec()
        .exit()
}

/**
 * Define provider for Application module, and define modules dependencies explicitly, in contrast with [KotlinBootique.autoLoadModules].
 */
class ApplicationModuleProvider : KotlinBQModuleProvider {
    override val module = ApplicationModule()
    override val overrides = listOf(UndertowModule::class)
    override val dependencies = listOf(
        UndertowModuleProvider(),
        KotlinConfigModuleProvider()
    )
}

/**
 * Wire interfaces with implementations in certain scope.
 */
class ApplicationModule : KotlinModule {
    override fun configure(binder: KotlinBinder) {
        binder.bind(ApplicationService::class).to(DefaultApplicationService::class).asSingleton()
        binder.bind(HttpHandler::class).annotatedWith(RootHandler::class).toProvider(RootHandlerProvider::class).asSingleton()
    }

    @Provides
    @Singleton
    fun configuration(configurationFactory: ConfigurationFactory): ApplicationConfiguration {
        return configurationFactory.config("application")
    }
}

/**
 * Define class with application config.
 */
data class ApplicationConfiguration(
    val message: String = ""
)

/**
 * Define dummy service and their default implementation [DefaultApplicationService].
 */
interface ApplicationService {
    fun message(): String
}

class DefaultApplicationService @Inject constructor(
    private val configuration: ApplicationConfiguration
) : ApplicationService {
    override fun message() = configuration.message
}

class RootHandlerProvider @Inject constructor(
    val messageHandler: MessageHandler
) : Provider<HttpHandler> {
    override fun get(): HttpHandler {
        return RoutingHandler()
            .get("/", CoroutinesHandler { messageHandler.get(it) })
    }
}

class MessageHandler @Inject constructor(
    private val applicationService: ApplicationService
) {
    suspend fun get(exchange: HttpServerExchange) {
        val start = currentTimeMillis()
        delay(1000L)
        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "text/html")
        exchange.responseSender.send("""${applicationService.message()} <br/> Total time: ${currentTimeMillis() - start}ms""")
    }
}
