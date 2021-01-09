package app.xlui.pwitter.web

import app.xlui.pwitter.annotation.CurrentUser
import app.xlui.pwitter.annotation.ValidateVO
import app.xlui.pwitter.constant.CommonExceptionTypeEnum
import app.xlui.pwitter.entity.common.RestResponse
import app.xlui.pwitter.entity.db.User
import app.xlui.pwitter.entity.vo.UserVO
import app.xlui.pwitter.service.UserService
import app.xlui.pwitter.util.JWTUtils
import app.xlui.pwitter.util.generateEncryptedPassword
import app.xlui.pwitter.util.generateSalt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController @Autowired constructor(
    private val userService: UserService
) {
    /**
     * Register
     */
    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    fun register(@RequestBody @ValidateVO param: UserVO, errors: Errors): RestResponse {
        if (userService.exist(param.username)) return RestResponse.buildError(CommonExceptionTypeEnum.UsernameAlreadyExist)

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
     * Login
     */
    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun login(aaa: String, @RequestBody @ValidateVO param: UserVO, ccc: String): RestResponse {
        userService.findByUsername(param.username)?.let {
            if (it.password == generateEncryptedPassword(param.password, it.salt)) {
                return RestResponse.buildSuccess(JWTUtils.sign(it.username, it.password))
            }
        }
        return RestResponse.buildError(CommonExceptionTypeEnum.UsernameOrPasswordInvalid)
    }

    @RequestMapping("/t")
    fun test(@CurrentUser user: User) = RestResponse.buildSuccess(
        """
        Successfully access API.
        Current user: $user
    """.trimIndent()
    )
}