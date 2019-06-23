package app.xlui.pwitter.entity

import com.fasterxml.jackson.annotation.JsonProperty
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
        val type: TweetType = TweetType.CONTENT,
        val content: String = "",

        val createTime: LocalDateTime = LocalDateTime.now()
) {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var user: User = User()
}

enum class TweetType {
    CONTENT,
    IMAGE,
    CONTENT_IMAGE,
    VIDEO
}