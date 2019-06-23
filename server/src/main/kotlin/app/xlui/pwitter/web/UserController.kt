package app.xlui.pwitter.web

import app.xlui.pwitter.entity.ResponseCode
import app.xlui.pwitter.entity.RestResponse
import app.xlui.pwitter.entity.User
import app.xlui.pwitter.exception.InvalidRequestException
import app.xlui.pwitter.service.UserService
import app.xlui.pwitter.util.JWTUtils
import app.xlui.pwitter.util.generateEncryptedPassword
import app.xlui.pwitter.util.generateSalt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController @Autowired constructor(
        private val userService: UserService
) {
    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    fun register(@RequestBody param: User): RestResponse {
        val username = param.username
        val password = param.password
        if (userService.exist(username)) return RestResponse.buildError(ResponseCode.UsernameAlreadyExist)

        val salt = generateSalt()
        val user = User(username = username, password = generateEncryptedPassword(password, salt), salt = salt)
        userService.save(user)

        return RestResponse.buildSuccess("Successfully register!")
    }

    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun login(@RequestBody param: User): RestResponse {
        if (StringUtils.isEmpty(param.username) || StringUtils.isEmpty(param.password)) {
            throw InvalidRequestException("Username or password is invalid")
        }

        val user = userService.findByUsername(param.username)
        user?.let {
            val pass = generateEncryptedPassword(param.password, it.salt)
            if (pass == it.password) {
                return RestResponse.buildSuccess(JWTUtils.sign(it.username, it.password))
            }
        }
        throw InvalidRequestException("Username or password is invalid")
    }

    @RequestMapping("/t")
    fun test() = RestResponse.buildSuccess("Successfully access API.")
}