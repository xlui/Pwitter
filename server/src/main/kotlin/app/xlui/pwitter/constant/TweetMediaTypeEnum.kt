package app.xlui.pwitter.constant

enum class TweetMediaTypeEnum(
    val code: Int,
    val msg: String
) {
    None(0, "Default, Text only"),
    Image(1, "Image"),
    Video(2, "Video"),
    ReTweet(3, "Retweet");

    companion object {
        fun of(code: Int): TweetMediaTypeEnum {
            return values().first { code == it.code }
        }
    }
}