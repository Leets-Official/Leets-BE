package land.leets.global.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient {
        val httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
            .doOnConnected { connection ->
                connection.addHandlerLast(ReadTimeoutHandler(10))
                    .addHandlerLast(WriteTimeoutHandler(10))
            }
            .responseTimeout(Duration.ofSeconds(1))

        val connector = ReactorClientHttpConnector(httpClient)
        return WebClient.builder().clientConnector(connector).build()
    }
}
