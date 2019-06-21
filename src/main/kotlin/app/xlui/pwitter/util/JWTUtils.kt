package app.xlui.pwitter.util

import app.xlui.pwitter.config.Const
import app.xlui.pwitter.exception.InternalException
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

object JWTUtils {
    val claim = "username"

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
        try {
            val algorithm = Algorithm.HMAC256(secret)
            val verifier = JWT.require(algorithm)
                    .withClaim(claim, username)
                    .build()
            val jwt = verifier.verify(token)
            return true
        } catch (e: Exception) {
            throw InternalException("Catch exception while verifying jwt token!", e)
        }
    }

    fun getUsername(token: String): String {
        val jwt = JWT.decode(token)
        return jwt.getClaim(claim).asString()
    }
}