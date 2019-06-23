package app.xlui.pwitter.annotation

import app.xlui.pwitter.entity.User
import app.xlui.pwitter.exception.InvalidRequestException
import app.xlui.pwitter.service.UserService
import app.xlui.pwitter.util.JWTUtils
import org.apache.shiro.SecurityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class CurrentUserMethodArgumentResolver @Autowired constructor(
        val userService: UserService
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(methodParameter: MethodParameter): Boolean {
        return methodParameter.parameterType.isAssignableFrom(User::class.java) && methodParameter.hasParameterAnnotation(CurrentUser::class.java)
    }

    override fun resolveArgument(methodParameter: MethodParameter, p1: ModelAndViewContainer?, p2: NativeWebRequest, p3: WebDataBinderFactory?): Any? {
        val token = SecurityUtils.getSubject().principal as String
        return userService.findByUsername(JWTUtils.getUsername(token))
                ?: throw InvalidRequestException("Invalid token format!")
    }
}