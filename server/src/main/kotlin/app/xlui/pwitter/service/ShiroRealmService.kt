package app.xlui.pwitter.service

import app.xlui.pwitter.entity.JWTToken
import app.xlui.pwitter.exception.InternalException
import app.xlui.pwitter.util.JWTUtils
import app.xlui.pwitter.util.logger
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShiroRealmService @Autowired constructor(
        private val userService: UserService
) : AuthorizingRealm() {
    val log = logger<ShiroRealmService>()

    override fun supports(token: AuthenticationToken?): Boolean {
        return token is JWTToken
    }

    override fun doGetAuthorizationInfo(p0: PrincipalCollection?): AuthorizationInfo {
        // permission checking
        throw InternalException("Unsupported method")
    }

    override fun doGetAuthenticationInfo(auth: AuthenticationToken?): AuthenticationInfo {
        val token = auth?.credentials as String
        val username = JWTUtils.getUsername(token)
        val user = userService.findByUsername(username) ?: throw AuthenticationException("Invalid token!")
        if (!JWTUtils.verify(token, user.username, user.password)) {
            throw AuthenticationException("token verify failed!")
        }
        return SimpleAuthenticationInfo(token, token, "Pwitter_realm")
    }
}