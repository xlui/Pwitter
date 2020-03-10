package app.xlui.pwitter.exception

import app.xlui.pwitter.constant.CommonExceptionTypeEnum
import app.xlui.pwitter.entity.vo.RestResponse
import app.xlui.pwitter.util.logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 全局异常处理
 */
@RestControllerAdvice
class GlobalExceptionController {
    private val logger = logger<GlobalExceptionController>()

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [
        MethodArgumentNotValidException::class
    ])
    fun handleBadRequest(e: Exception): RestResponse {
        logger.error("Bad request", e)
        return RestResponse.buildError(CommonExceptionTypeEnum.RequestParamInvalid)
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = [CommonException::class])
    fun handlerInternalException(e: CommonException): RestResponse {
        logger.error("Common exception:", e)
        return RestResponse.buildError(e.type)
    }
}