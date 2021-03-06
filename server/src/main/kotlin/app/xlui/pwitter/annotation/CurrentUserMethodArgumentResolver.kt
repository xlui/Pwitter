package app.xlui.pwitter.annotation

import app.xlui.pwitter.constant.CommonExceptionTypeEnum
import app.xlui.pwitter.converter.PwitterConverter
import app.xlui.pwitter.exception.PwitterException
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

/**
 * 注解处理类，解析 {@code CurrentUser} 注解并从 Token 中提取出用户进行注入
 */
@Component
class CurrentUserMethodArgumentResolver @Autowired constructor(
    val userService: UserService
) : HandlerMethodArgumentResolver {
    /**
     * 判断当前处理的方法是否符合注入条件
     */
    override fun supportsParameter(methodParameter: MethodParameter): Boolean {
        return methodParameter.hasParameterAnnotation(CurrentUser::class.java)
    }

    /**
     * 进行处理，从 Token 中提取出用户并注入
     */
    override fun resolveArgument(
        methodParameter: MethodParameter,
        p1: ModelAndViewContainer?,
        p2: NativeWebRequest,
        p3: WebDataBinderFactory?
    ): Any? {
        val token = SecurityUtils.getSubject().principal as String
        return PwitterConverter.convertNull(userService.findByUsername(JWTUtils.getUsername(token)))
            ?: throw PwitterException(CommonExceptionTypeEnum.InvalidTokenFormat)
    }
}