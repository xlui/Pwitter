package app.xlui.pwitter.entity.common

import app.xlui.pwitter.constant.CommonExceptionTypeEnum
import app.xlui.pwitter.exception.PwitterException
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
        fun buildError(type: CommonExceptionTypeEnum, msg: String? = null) =
            RestResponse(type.code, null, StringUtils.defaultString(msg, type.msg))

        fun buildError(pw: PwitterException) =
            RestResponse(pw.type.code, null, StringUtils.defaultString(pw.msg, pw.type.msg))
    }
}
