package app.xlui.pwitter.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "t_tweet")
data class Tweet(
        @Id @GeneratedValue val id: Long = 0,
        val content: String = "",
        val mediaType: TweetMediaType = TweetMediaType.None,
        val media: String = "",

        val createTime: LocalDateTime = LocalDateTime.now()
) {
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User = User()
}

enum class TweetMediaType {
    None,
    Image,
    Video,
    ReTweet
}