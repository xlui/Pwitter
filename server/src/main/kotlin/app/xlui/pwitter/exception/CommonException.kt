package app.xlui.pwitter.exception

import app.xlui.pwitter.constant.CommonExceptionTypeEnum

class CommonException(
        val type: CommonExceptionTypeEnum
) : RuntimeException(type.msg)