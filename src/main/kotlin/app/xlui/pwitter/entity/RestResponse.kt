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
    OK(0, "request successfully processed!"),

    UsernameExist(10000, "username already exist!"),
    InvalidRequest(19999, "Invalid request!"),

    InternalError(99999, "Unknown internal error, please contact the admin!")
}