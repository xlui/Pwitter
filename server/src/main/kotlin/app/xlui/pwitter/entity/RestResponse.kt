package app.xlui.pwitter.entity

import org.apache.commons.lang3.StringUtils

data class RestResponse(
        val code: Int,
        val data: Any?,
        val error: String
) {
    companion object Builder {
        /**
         * 构建成功响应
         */
        fun buildSuccess(data: Any?, msg: String = "") = RestResponse(0, data, msg)

        /**
         * 构建错误响应
         */
        fun buildError(code: ResponseCode, msg: String = "") = RestResponse(code.value, null, if (StringUtils.isBlank(msg)) code.description else msg)
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
    TweetContentInvalid(10004, "Tweet content or media should not be empty!"),
    TweetIdInvalid(10005, "Tweet ID is invalid, no tweet found with this ID"),
    MissingRequiredFields(10006, "Request body missing some required fields"),
    InvalidRequest(19999, "Invalid request!"),

    InternalError(99999, "Unknown internal error, please contact the admin!")
}