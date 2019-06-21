package app.xlui.pwitter.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "t_tweet")
class Tweet(
        @Id @GeneratedValue val id: Long = 0,
        val type: TweetType = TweetType.CONTENT,
        val content: String = "",

        @ManyToOne val user: User = User(),

        val createTime: LocalDateTime = LocalDateTime.now()
)

enum class TweetType {
    CONTENT,
    IMAGE,
    CONTENT_IMAGE,
    VIDEO
}