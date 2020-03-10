package app.xlui.pwitter.constant

enum class CommonExceptionTypeEnum(
        val code: Int,
        val msg: String
) {
    UsernameAlreadyExist(10000, "Username already exist"),
    MissingAuthorizationHeader(10001, "Request header must contains authorization field"),
    InvalidTokenFormat(10002, "Invalid token format"),
    UsernameOrPasswordInvalid(10003, "Username or password is invalid"),
    TweetParamInvalid(10004, "Tweet param invalid"),
    TweetMediaInvalid(10005, "Tweet media should not be empty"),
    TweetIdInvalid(10006, "Tweet ID is invalid, no tweet found with this ID"),
    MissingRequiredFields(10007, "Request body missing some required fields"),
    RequestParamInvalid(10008, "Request param is invalid"),
    InvalidRequest(19999, "Invalid request"),

    UnsupportedShiroAuthorization(20000, "Shiro authorization is not supported yet"),
    FailedToGenerateJWTToken(200001, "Failed to generate JWT token"),
    InternalError(99999, "Unknown internal error, please contact the admin")
}