package app.xlui.pwitter.constant

enum class CommonExceptionTypeEnum(
    val code: Int,
    val msg: String
) {
    // 10 - Account related
    UsernameAlreadyExist(10000, "Username already exist"),
    MissingAuthorizationHeader(10001, "Request header must contains authorization field"),
    UsernameOrPasswordInvalid(10002, "Username or password is invalid"),
    EmailFormatInvalid(10003, "Email format is invalid"),
    InnerLoginFailed(10004, "Inner login failed"),

    // 11 - Token related
    InvalidTokenFormat(11000, "Invalid token format"),
    UnsupportedShiroAuthorization(11001, "Shiro authorization is not supported yet"),
    FailedToGenerateJWTToken(11002, "Failed to generate JWT token"),

    // 12 - Tween related
    TweetParamInvalid(12000, "Tweet param invalid"),
    TweetMediaInvalid(12001, "Tweet media should not be empty"),
    TweetIdInvalid(12002, "Tweet ID is invalid, no tweet found with this ID"),

    // 99 - Common Exception
    RequestParamInvalid(99000, "Request param is invalid"),
    InvalidRequest(99001, "Invalid request"),
    InternalError(99999, "Unknown internal error, please contact the admin")
}