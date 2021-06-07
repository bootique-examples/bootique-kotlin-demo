package io.bootique.kotlin.demo

import io.bootique.config.ConfigurationFactory
import io.bootique.di.Provides
import io.bootique.kotlin.config.modules.KotlinConfigModuleProvider
import io.bootique.kotlin.core.KotlinBQModuleProvider
import io.bootique.kotlin.core.KotlinBootique
import io.bootique.kotlin.di.KotlinBinder
import io.bootique.kotlin.di.KotlinModule
import io.bootique.kotlin.extra.config
import io.bootique.undertow.UndertowModule
import io.bootique.undertow.UndertowModuleProvider
import io.bootique.undertow.handlers.RootHandler
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.server.RoutingHandler
import io.undertow.util.Headers
import kotlinx.coroutines.delay
import java.lang.System.currentTimeMillis
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Entry point of application.
 */
fun main(args: Array<String>) {
    KotlinBootique(args)
            .moduleProvider(ApplicationModuleProvider())
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
        binder.bind(HttpHandler::class.java, RootHandler::class.java).toProvider(RootHandlerProvider::class.java).inSingletonScope()
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
