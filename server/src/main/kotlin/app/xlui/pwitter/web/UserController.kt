package app.xlui.pwitter.web

import app.xlui.pwitter.constant.CommonExceptionType
import app.xlui.pwitter.entity.db.User
import app.xlui.pwitter.entity.vo.RestResponse
import app.xlui.pwitter.service.UserService
import app.xlui.pwitter.util.JWTUtils
import app.xlui.pwitter.util.generateEncryptedPassword
import app.xlui.pwitter.util.generateSalt
import app.xlui.pwitter.util.logger
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class UserController @Autowired constructor(
        private val userService: UserService
) {
    private val logger = logger<UserController>()

    /**
     * 注册
     */
    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    fun register(@RequestBody @Valid param: User): RestResponse {
        if (userService.exist(param.username)) return RestResponse.buildError(CommonExceptionType.UsernameAlreadyExist)

        val salt = generateSalt()
        val user = User(
                username = param.username,
                password = generateEncryptedPassword(param.password, salt),
                salt = salt,
                email = param.email,
                nickname = if (param.nickname.isEmpty()) param.username else param.nickname
        )
        userService.save(user)

        return RestResponse.buildSuccess("Successfully register!")
    }

    /**
     * 登录
     */
    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun login(@RequestBody param: User): RestResponse {
        if (StringUtils.isBlank(param.username) || StringUtils.isBlank(param.password)) {
            return RestResponse.buildError(CommonExceptionType.UsernameOrPasswordInvalid)
        }

        val user = userService.findByUsername(param.username)
        user?.let {
            val pass = generateEncryptedPassword(param.password, it.salt)
            if (pass == it.password) {
                return RestResponse.buildSuccess(JWTUtils.sign(it.username, it.password))
            }
        }
        return RestResponse.buildError(CommonExceptionType.UsernameOrPasswordInvalid)
    }

    @RequestMapping("/t")
    fun test(@RequestBody username: Map<String, String>) = RestResponse.buildSuccess("Successfully access API. $username")
}