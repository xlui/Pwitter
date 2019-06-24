package app.xlui.pwitter.entity

data class RestResponse(
        val code: Int,
        val data: Any?,
        val error: String
) {
    companion object Builder {
        fun buildSuccess(data: Any?) = RestResponse(0, data, "")
        fun buildError(code: ResponseCode) = RestResponse(code.value, null, code.description)
        fun buildError(code: ResponseCode, msg: String) = RestResponse(code.value, null, msg)
    }
}

enum class ResponseCode(
        val value: Int,
        val description: String
) {
    OK(0, "Request successfully processed!"),

    UsernameAlreadyExist(10000, "Username already exist!"),
    MissingAuthorizationHeader(10001, "Request header must contain authorization field!"),
    InvalidTokenFormat(10002, "Invalid token!"),
    UsernameOrPasswordInvalid(10003, "Username or password is invalid!"),
    InvalidRequest(19999, "Invalid request!"),

    InternalError(99999, "Unknown internal error, please contact the admin!")
}