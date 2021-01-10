package app.xlui.pwitter.aspect

import app.xlui.pwitter.annotation.ValidateVO
import app.xlui.pwitter.entity.common.RestResponse
import app.xlui.pwitter.entity.vo.ValidatePwitterVO
import app.xlui.pwitter.exception.PwitterException
import app.xlui.pwitter.util.logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Aspect
@Component
class ValidateVOAspect {
    val logger = logger<ValidateVOAspect>()

    @Around("execution(* *(.., @app.xlui.pwitter.annotation.ValidateVO (*), ..))")
    fun around(pjp: ProceedingJoinPoint): Any? {
        val methodParameterAnnotations = (pjp.signature as MethodSignature).method.parameterAnnotations
        for ((index, value) in methodParameterAnnotations.withIndex()) {
            val arg = pjp.args[index]
            value.forEach {
                if (it is ValidateVO && arg is ValidatePwitterVO) {
                    try {
                        arg.validate()
                        logger.info("[ValidateVOAspect] validate parameter passed, arg:$arg")
                    } catch (pe: PwitterException) {
                        logger.info("[ValidateVOAspect] validate parameter failed, arg:$arg", pe)
                        return RestResponse.buildError(pe)
                    }
                }
            }
        }
        return pjp.proceed()
    }
}