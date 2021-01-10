package app.xlui.pwitter.entity.vo

import app.xlui.pwitter.constant.TweetMediaTypeEnum
import app.xlui.pwitter.entity.ValidatePwitterVO
import java.time.LocalDateTime

data class TweetVO constructor(
    val id: Long = 0,
    val content: String = "",
    val mediaType: TweetMediaTypeEnum = TweetMediaTypeEnum.None,
    val media: String = "",
    val deleted: Boolean = false,
    val createTime: LocalDateTime = LocalDateTime.now(),
    val updateTime: LocalDateTime = LocalDateTime.now(),

    val user: UserVO = UserVO()
) : ValidatePwitterVO {
    override fun validate() {
        TODO("Not yet implemented")
    }
}