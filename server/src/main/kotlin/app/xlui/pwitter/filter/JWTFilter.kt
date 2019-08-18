package app.xlui.pwitter.filter

import app.xlui.pwitter.entity.JWTToken
import app.xlui.pwitter.entity.ResponseCode
import app.xlui.pwitter.entity.RestResponse
import app.xlui.pwitter.util.logger
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
import org.springframework.util.StringUtils
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class JWTFilter : BasicHttpAuthenticationFilter() {
    private val logger = logger<JWTFilter>()

    /**
     * 拦截器，对 Shiro 拦截到的方法进行 Token 校验
     */
    override fun onAccessDenied(request: ServletRequest?, response: ServletResponse?): Boolean {
        logger.info("Start token authentication")
        val authorization = getAuthzHeader(request)
        return if (StringUtils.isEmpty(authorization)) {
            buildResponse(response, RestResponse.buildError(ResponseCode.MissingAuthorizationHeader))
            logger.info("Token authentication failed of empty authorization field in request")
            false
        } else {
            val token = JWTToken(authorization)
            try {
                getSubject(request, response).login(token)
                logger.info("Token authentication success")
                true
            } catch (e: AuthenticationException) {
                buildResponse(response, RestResponse.buildError(ResponseCode.InvalidTokenFormat))
                logger.info("Token authentication failed of invalid token")
                false
            }
        }
    }

    /**
     * 构建校验失败响应
     */
    private fun buildResponse(response: ServletResponse?, restResponse: RestResponse) {
        response?.let {
            it.contentType = "application/json"
            it.outputStream.println(ObjectMapper().writeValueAsString(restResponse))
        }
    }
}