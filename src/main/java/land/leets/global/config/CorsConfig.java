package land.leets.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    @Value("${cors.origin.development}")
    private String developmentOrigin;

    @Value("${cors.origin.production}")
    private String productionOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("OPTIONS", "HEAD", "GET", "POST", "PUT", "PATCH", "DELETE")
                .allowCredentials(true)
                .allowedOrigins(developmentOrigin, productionOrigin, "http://localhost:3000")
                .maxAge(3000);
    }
}
