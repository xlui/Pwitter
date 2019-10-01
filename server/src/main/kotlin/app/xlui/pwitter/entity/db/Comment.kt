package app.xlui.pwitter.entity.db

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "t_comment")
data class Comment(
        @Id
        @GeneratedValue
        val id: Long = 0,
        val replyTo: Long = 0,
        val content: String = "",
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) val deleted: Boolean = false,

        val createTime: LocalDateTime = LocalDateTime.now()
) {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.AUTO)
    var user: User = User()

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var tweet: Tweet = Tweet()
}