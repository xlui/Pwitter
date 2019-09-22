package app.xlui.pwitter.util

import app.xlui.pwitter.constant.CommonConstant
import org.slf4j.LoggerFactory
import java.security.MessageDigest
import java.util.*

inline fun <reified T> logger() = LoggerFactory.getLogger(T::class.java)!!

/**
 * Unpack a Kotlin instance from {@code java.util.Optional} class
 */
fun <T> unpack(pack: Optional<T>): T? = if (pack.isEmpty) null else pack.get()

/**
 * 生成唯一盐，算法 {@code UUID+saltMaterial+currentTimeMills}
 */
fun generateSalt() = "${UUID.randomUUID().run { toString().replace("-", "") }}${CommonConstant.saltMaterial}${System.currentTimeMillis()}"

/**
 * 通过对加盐密码求 MD5 得到加密密码
 */
fun generateEncryptedPassword(password: String, salt: String = generateSalt()) = MessageDigest.getInstance(CommonConstant.passwordEncrypt)
        .digest("$password$salt".toByteArray())
        .joinToString("") { String.format("%02x", it) }
