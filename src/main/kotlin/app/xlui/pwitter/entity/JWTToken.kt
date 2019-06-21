package app.xlui.pwitter.entity

import org.apache.shiro.authc.AuthenticationToken

class JWTToken(private val token: String) : AuthenticationToken {
    override fun getCredentials() = token

    override fun getPrincipal() = token
}