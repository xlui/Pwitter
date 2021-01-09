package app.xlui.pwitter.exception

import app.xlui.pwitter.constant.CommonExceptionTypeEnum

class PwitterException(
    val type: CommonExceptionTypeEnum,
    val msg: String
) : RuntimeException(msg) {
    constructor(type: CommonExceptionTypeEnum) : this(type, type.msg)

    constructor() : this(CommonExceptionTypeEnum.InternalError)
}