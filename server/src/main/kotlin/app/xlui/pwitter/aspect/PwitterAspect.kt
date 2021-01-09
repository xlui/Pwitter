package app.xlui.pwitter.aspect;

import app.xlui.pwitter.util.logger
import com.google.gson.Gson
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Aspect
@Component
class PwitterAspect @Autowired constructor(
    private val gson: Gson
) {
    val logger = logger<PwitterAspect>()

    @Pointcut(
        "execution(* app.xlui.pwitter.web..*.*(..)) ||" +
                "execution(* app.xlui.pwitter.service..*.*(..))"
    )
    fun pointcut() {
    }

    @Around("pointcut()")
    fun around(pjp: ProceedingJoinPoint): Any? {
        val signature = pjp.signature
        val args = pjp.args
        val start = System.currentTimeMillis()
        try {
            val res = pjp.proceed()
            logger.info(
                "[PwitterAspect] Interface:${signature.declaringType.canonicalName}.${signature.name}, " +
                        "Args: ${gson.toJson(args)}, " +
                        "Result: ${res}, " +
                        "Start: ${start}, " +
                        "Cost: ${System.currentTimeMillis() - start}ms"
            )
            return res
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            return null
        }
    }
}
