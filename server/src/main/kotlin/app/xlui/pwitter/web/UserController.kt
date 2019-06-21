package app.xlui.pwitter.web

import app.xlui.pwitter.entity.ResponseCode
import app.xlui.pwitter.entity.RestResponse
import app.xlui.pwitter.entity.User
import app.xlui.pwitter.exception.InvalidRequestException
import app.xlui.pwitter.service.UserService
import org.springframework.beans.factory.annotation.Autowired
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
        if (userService.exist(username)) return RestResponse.buildError(ResponseCode.UsernameExist)
        return RestResponse.buildSuccess("successfully register")
    }

    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun login(@RequestBody param: User): RestResponse {
        val username = param.username
        val password = param.password
        val user = userService.findByUsernameAndPassword(username, password)
                ?: throw InvalidRequestException("Username or password is invalid")
        return RestResponse.buildSuccess(user)
    }
}