package app.xlui.pwitter.entity.db

import app.xlui.pwitter.constant.TweetMediaTypeEnum
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "pwitter_tweet")
data class Tweet(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val userId: Long = 0,
    @field:NotBlank(message = "Tweet content should not be blank!")
    val content: String = "",
    @field:NotNull
    val mediaType: TweetMediaTypeEnum = TweetMediaTypeEnum.None,
    val media: String = "",
    val deleted: Boolean = false,

    @CreationTimestamp
    val createTime: LocalDateTime = LocalDateTime.now(),
    @UpdateTimestamp
    val updateTime: LocalDateTime = LocalDateTime.now()
)
