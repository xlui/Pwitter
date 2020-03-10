package app.xlui.pwitter.entity.vo

import app.xlui.pwitter.constant.CommonExceptionTypeEnum
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
        fun buildError(type: CommonExceptionTypeEnum, msg: String = "") = RestResponse(type.code, null, if (StringUtils.isBlank(msg)) type.msg else msg)
    }
}
