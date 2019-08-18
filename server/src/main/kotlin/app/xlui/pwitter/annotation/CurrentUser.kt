package app.xlui.pwitter.annotation

/**
 * 当前用户，通过在 Controller 上标注该注解，Spring 会通过 Token 解析出当前用户实体并进行注入
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class CurrentUser