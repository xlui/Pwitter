package app.xlui.pwitter.util

import app.xlui.pwitter.config.Const
import app.xlui.pwitter.exception.InternalException
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

object JWTUtils {
    val log = logger<JWTUtils>()
    private const val claim = "username"

    fun sign(username: String, secret: String): String {
        try {
            val expire = Date(System.currentTimeMillis() + Const.tokenExpireTime)
            val algorithm = Algorithm.HMAC256(secret)
            return JWT.create()
                    .withClaim(claim, username)
                    .withExpiresAt(expire)
                    .sign(algorithm)
        } catch (e: Exception) {
            throw InternalException("Catch exception while generating jwt token!", e)
        }
    }

    fun verify(token: String, username: String, secret: String): Boolean {
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            val verifier = JWT.require(algorithm)
                    .withClaim(claim, username)
                    .build()
            verifier.verify(token)
            true
        } catch (e: Exception) {
            log.info("Failed to verify token!")
            false
        }
    }

    fun getUsername(token: String): String {
        val jwt = JWT.decode(token)
        return jwt.getClaim(claim).asString()
    }
}