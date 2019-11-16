package app.xlui.pwitter.config

import com.google.gson.Gson
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableConfigurationProperties
@EnableScheduling
class PwitterConfiguration {
    @Bean
    fun gson() = Gson()

    @Bean
    fun registerOpenEntityManagerInViewFilterBean(): FilterRegistrationBean<OpenEntityManagerInViewFilter> {
        val registrationBean = FilterRegistrationBean<OpenEntityManagerInViewFilter>();
        val filter = OpenEntityManagerInViewFilter();
        registrationBean.filter = filter
        registrationBean.order = 5
        return registrationBean
    }
}