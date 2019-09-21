package app.xlui.pwitter.service

import app.xlui.pwitter.constant.CommonExceptionType
import app.xlui.pwitter.entity.vo.JWTToken
import app.xlui.pwitter.exception.CommonException
import app.xlui.pwitter.util.JWTUtils
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
    override fun supports(token: AuthenticationToken?): Boolean {
        return token is JWTToken
    }

    /**
     * 当前不支持权限管理
     */
    override fun doGetAuthorizationInfo(p0: PrincipalCollection?): AuthorizationInfo {
        // permission checking
        throw CommonException(CommonExceptionType.UnsupportedShiroAuthorization)
    }

    /**
     * 身份认证
     */
    override fun doGetAuthenticationInfo(auth: AuthenticationToken?): AuthenticationInfo {
        val token = auth?.credentials as String
        val username = JWTUtils.getUsername(token)
        val user = userService.findByUsername(username) ?: throw AuthenticationException("Invalid token!")
        if (!JWTUtils.verify(token, user.username, user.password)) {
            throw AuthenticationException("Token verify failed!")
        }
        return SimpleAuthenticationInfo(token, token, "Pwitter_realm")
    }
}