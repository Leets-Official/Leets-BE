package land.leets.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig(
    @Value("\${cors.origin.development}")
    private val developmentOrigin: String,

    @Value("\${cors.origin.production}")
    private val productionOrigin: String
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").apply {
            allowedMethods("OPTIONS", "HEAD", "GET", "POST", "PUT", "PATCH", "DELETE")
            allowCredentials(true)
            allowedOrigins(developmentOrigin, productionOrigin, "http://localhost:3000")
            maxAge(3000)
        }
    }
}
