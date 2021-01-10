package app.xlui.pwitter.entity.param

import app.xlui.pwitter.constant.CommonExceptionTypeEnum
import app.xlui.pwitter.entity.ValidatePwitterVO
import app.xlui.pwitter.exception.PwitterException
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class TweetTimelineParam constructor(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val from: LocalDate = LocalDate.now().minusDays(7),
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val to: LocalDate = LocalDate.now(),
    val pageNo: Int = 1,
    val pageSize: Int = 20,
) : ValidatePwitterVO {
    override fun validate() {
        pageNo.takeIf { it > 0 } ?: throw PwitterException(CommonExceptionTypeEnum.TweetTimelinePageSizeInvalid)
    }
}