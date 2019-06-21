package app.xlui.pwitter.filter

import app.xlui.pwitter.entity.JWTToken
import app.xlui.pwitter.entity.ResponseCode
import app.xlui.pwitter.entity.RestResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
import org.springframework.http.HttpStatus
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTFilter : BasicHttpAuthenticationFilter() {
    /**
     * Add support for cross origin
     */
    override fun preHandle(request: ServletRequest?, response: ServletResponse?): Boolean {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        httpResponse.setHeader("Access-control-Allow-Origin", httpRequest.getHeader("Origin"))
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE")
        httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"))
        // Cross origin will send a OPTION request fist, we will return a OK response
        if (httpRequest.method == RequestMethod.OPTIONS.name) {
            httpResponse.status = HttpStatus.OK.value()
            return false
        }
        return super.preHandle(request, response)
    }

    override fun onAccessDenied(request: ServletRequest?, response: ServletResponse?): Boolean {
        val authorization = getAuthzHeader(request)
        return if (StringUtils.isEmpty(authorization)) {
            response?.contentType = "application/json"
            response?.outputStream?.println(
                    ObjectMapper().writeValueAsString(
                            RestResponse.buildError(ResponseCode.InvalidRequest, "Request header must contain authorization field!")
                    )
            )
            false
        } else {
            val token = JWTToken(authorization)
            getSubject(request, response).login(token)
            true
        }
    }
}