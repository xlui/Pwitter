package app.xlui.pwitter.filter

import app.xlui.pwitter.constant.CommonExceptionTypeEnum
import app.xlui.pwitter.entity.common.JWTToken
import app.xlui.pwitter.entity.common.RestResponse
import app.xlui.pwitter.util.logger
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
import org.springframework.util.StringUtils
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JWTFilter : BasicHttpAuthenticationFilter() {
    private val logger = logger<JWTFilter>()

    /**
     * Fitler, for token validate
     */
    override fun onAccessDenied(request: ServletRequest?, response: ServletResponse?): Boolean {
        // skip OPTIONS
        if (skip(request)) return true
        logger.info("Start token authentication")
        val authorization = getAuthzHeader(request)
        logger.info("Token: $authorization")
        return if (StringUtils.isEmpty(authorization)) {
            buildResponse(response, RestResponse.buildError(CommonExceptionTypeEnum.MissingAuthorizationHeader))
            logger.info("Token authentication failed of empty authorization field in request")
            false
        } else {
            val token = JWTToken(authorization)
            try {
                getSubject(request, response).login(token)
                logger.info("Token authentication success")
                true
            } catch (e: AuthenticationException) {
                buildResponse(response, RestResponse.buildError(CommonExceptionTypeEnum.InvalidTokenFormat))
                logger.info("Token authentication failed of invalid token")
                false
            }
        }
    }

    /**
     * Skip OPTIONS request
     */
    private fun skip(request: ServletRequest?): Boolean {
        val req = request as HttpServletRequest
        return req.method == "OPTIONS"
    }

    /**
     * Build token validate failed response
     */
    private fun buildResponse(response: ServletResponse?, restResponse: RestResponse) {
        response?.let {
            it.contentType = "application/json"
            it.outputStream.println(ObjectMapper().writeValueAsString(restResponse))
        }
    }
}