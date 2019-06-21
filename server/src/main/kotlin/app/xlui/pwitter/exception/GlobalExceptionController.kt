package app.xlui.pwitter.exception

import app.xlui.pwitter.entity.ResponseCode
import app.xlui.pwitter.entity.RestResponse
import org.apache.shiro.ShiroException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [InvalidRequestException::class])
    fun handleBadRequest(e: Exception) = RestResponse(100001, null, e.message!!)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [InternalException::class])
    fun handlerInternalException(e: Exception) = RestResponse.buildError(ResponseCode.InternalError, e.message!!)
}