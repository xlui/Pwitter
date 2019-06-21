package app.xlui.pwitter.config

import app.xlui.pwitter.filter.JWTFilter
import app.xlui.pwitter.service.ShiroRealmService
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator
import org.apache.shiro.mgt.DefaultSubjectDAO
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.Filter

@Configuration
class ShiroConfiguration {
    @Bean
    fun securityManager(shiroRealmService: ShiroRealmService): SecurityManager {
        val manager = DefaultWebSecurityManager()

        manager.setRealm(shiroRealmService)

        /**
         * Disable shiro's session
         */
        val subject = DefaultSubjectDAO()
        val sessionStorageEvaluator = DefaultSessionStorageEvaluator()
        sessionStorageEvaluator.isSessionStorageEnabled = false
        subject.sessionStorageEvaluator = sessionStorageEvaluator
        manager.subjectDAO = subject

        return manager
    }

    @Bean
    fun shiroFilterFactoryBean(manager: SecurityManager): ShiroFilterFactoryBean {
        val bean = ShiroFilterFactoryBean()
        bean.securityManager = manager

        val filterMap: Map<String, Filter> = mutableMapOf("jwt" to JWTFilter())
        bean.filters = filterMap

        val filterRuleMap = mapOf(
                "/register" to "anon",
                "/login" to "anon",
                "/forget" to "anon",
                "/**" to "jwt"
        )
        bean.filterChainDefinitionMap = filterRuleMap

        return bean
    }
}