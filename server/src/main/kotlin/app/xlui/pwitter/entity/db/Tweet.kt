package app.xlui.pwitter.entity.db

import app.xlui.pwitter.constant.TweetMediaType
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.*

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

    @OneToMany(mappedBy = "tweet")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val comments: List<Comment> = listOf()
}
