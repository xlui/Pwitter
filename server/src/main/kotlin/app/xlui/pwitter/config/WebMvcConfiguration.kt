package app.xlui.pwitter.config

import app.xlui.pwitter.annotation.CurrentUserMethodArgumentResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration @Autowired constructor(
    val currentUserResolver: CurrentUserMethodArgumentResolver,
) : WebMvcConfigurer {
    /**
     * annotation resolver
     */
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(currentUserResolver)
    }

    /**
     * Cross-Origin support
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
            .allowedHeaders("Authorization", "Content-Type")
            .maxAge(600)
            .allowCredentials(false);
    }
}