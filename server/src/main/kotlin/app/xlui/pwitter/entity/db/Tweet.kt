package app.xlui.pwitter.entity.db

import app.xlui.pwitter.constant.TweetMediaTypeEnum
import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "t_tweet")
data class Tweet(
        @Id
        @GeneratedValue
        val id: Long = 0,
        @field:NotBlank
        val content: String = "",
        @field:NotNull
        val mediaTypeEnum: TweetMediaTypeEnum = TweetMediaTypeEnum.None,
        val media: String = "",

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        val createTime: LocalDateTime = LocalDateTime.now()
) {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var user: User = User()

    @OneToMany(mappedBy = "tweet")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val comments: List<Comment> = listOf()
}
