package app.xlui.pwitter.exception

import app.xlui.pwitter.constant.CommonExceptionType

class CommonException(
        val type: CommonExceptionType
) : RuntimeException(type.msg)