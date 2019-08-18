package app.xlui.pwitter.util

import org.slf4j.LoggerFactory
import java.security.MessageDigest
import java.util.Optional
import java.util.UUID

inline fun <reified T> logger() = LoggerFactory.getLogger(T::class.java)!!

/**
 * Unpack a Kotlin instance from {@code java.util.Optional} class
 */
fun <T> unpack(pack: Optional<T>): T? = if (pack.isEmpty) null else pack.get()

/**
 * 生成唯一盐
 */
fun generateSalt() = "${UUID.randomUUID().run { toString().replace("-", "") }}YttTxE*@7R8W*tJen9p6${System.currentTimeMillis()}"

/**
 * 通过对加盐密码求 MD5 得到加密密码
 */
fun generateEncryptedPassword(password: String, salt: String = generateSalt()) = MessageDigest.getInstance("MD5")
        .digest("$password$salt".toByteArray())
        .joinToString("") { String.format("%02x", it) }
