package app.xlui.pwitter.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * 读取 pwitter 开头的配置属性
 */
@Component
@ConfigurationProperties("pwitter")
class PwitterProperties {
    var init: Boolean = false
    val fake: Boolean = false
}