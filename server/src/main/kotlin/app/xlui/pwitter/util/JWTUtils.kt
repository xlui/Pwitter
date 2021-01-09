package app.xlui.pwitter.util

import app.xlui.pwitter.config.Const
import app.xlui.pwitter.constant.CommonExceptionTypeEnum
import app.xlui.pwitter.exception.PwitterException
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JWTUtils {
    private val logger = logger<JWTUtils>()
    private const val claim = "username"

    /**
     * 生成 JWT Token
     */
    fun sign(username: String, secret: String): String {
        try {
            val expire = Date(System.currentTimeMillis() + Const.tokenExpireTime)
            val algorithm = Algorithm.HMAC256(secret)
            return JWT.create()
                .withClaim(claim, username)
                .withExpiresAt(expire)
                .sign(algorithm)
        } catch (e: Exception) {
            throw PwitterException(CommonExceptionTypeEnum.FailedToGenerateJWTToken)
        }
    }

    /**
     * 校验 Token
     */
    fun verify(token: String, username: String, secret: String): Boolean {
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            val verifier = JWT.require(algorithm)
                .withClaim(claim, username)
                .build()
            verifier.verify(token)
            true
        } catch (e: Exception) {
            logger.info("Failed to verify token!")
            false
        }
    }

    /**
     * 从 Token 获取附带的用户名
     */
    fun getUsername(token: String): String {
        val jwt = JWT.decode(token)
        return jwt.getClaim(claim).asString()
    }
}