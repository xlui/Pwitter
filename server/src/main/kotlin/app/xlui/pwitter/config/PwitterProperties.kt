package app.xlui.pwitter.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("pwitter")
class PwitterProperties {
    var init: Boolean = false
}