package app.xlui.pwitter.filter

import app.xlui.pwitter.entity.JWTToken
import app.xlui.pwitter.entity.ResponseCode
import app.xlui.pwitter.entity.RestResponse
import app.xlui.pwitter.util.logger
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
import org.springframework.http.HttpStatus
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTFilter : BasicHttpAuthenticationFilter() {
    val log = logger<JWTFilter>()

    override fun onAccessDenied(request: ServletRequest?, response: ServletResponse?): Boolean {
        log.info("Start token authentication")
        val authorization = getAuthzHeader(request)
        return if (StringUtils.isEmpty(authorization)) {
            buildResponse(response, RestResponse.buildError(ResponseCode.MissingAuthorizationHeader))
            log.info("Token authentication failed of empty authorization field in request")
            false
        } else {
            val token = JWTToken(authorization)
            try {
                getSubject(request, response).login(token)
                log.info("Token authentication success")
                true
            } catch (e: AuthenticationException) {
                buildResponse(response, RestResponse.buildError(ResponseCode.InvalidTokenFormat))
                log.info("Token authentication failed of invalid token")
                false
            }
        }
    }

    private fun buildResponse(response: ServletResponse?, restResponse: RestResponse) {
        response?.let {
            it.contentType = "application/json"
            it.outputStream.println(ObjectMapper().writeValueAsString(restResponse))
        }
    }
}