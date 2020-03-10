package app.xlui.pwitter.constant

enum class TweetMediaTypeEnum(
        val code: Int,
        val msg: String
) {
    None(0, "默认"),
    Image(1, "图片"),
    Video(2, "视频"),
    ReTweet(3, "转发");

    companion object {
        fun of(code: Int): TweetMediaTypeEnum {
            return values().first { code == it.code }
        }
    }
}