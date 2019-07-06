package app.xlui.pwitter.exception

import app.xlui.pwitter.entity.ResponseCode
import app.xlui.pwitter.entity.RestResponse
import app.xlui.pwitter.util.logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.support.MissingServletRequestPartException

@RestControllerAdvice
class GlobalExceptionController {
    val log = logger<GlobalExceptionController>()

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [
        InvalidRequestException::class,
        MissingServletRequestPartException::class
    ])
    fun handleBadRequest(e: Exception): RestResponse {
        log.error("Bad request", e)
        return RestResponse(100001, null, e.message!!)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleValidateException(e: Exception): RestResponse {
        log.error("Missing required request body", e)
        return RestResponse.buildError(ResponseCode.MissingRequiredFields)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [InternalException::class])
    fun handlerInternalException(e: Exception): RestResponse {
        log.error("Internal exception", e)
        return RestResponse.buildError(ResponseCode.InternalError, e.message!!)
    }
}