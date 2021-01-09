package app.xlui.pwitter.entity.db

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "pwitter_comment")
data class Comment(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val userId: Long = 0,
    val tweetId: Long = 0,
    val replyCommentId: Long = 0,
    @field:NotBlank(message = "Comment content should not be blank!")
    val content: String = "",
    val deleted: Boolean = false,

    @CreationTimestamp
    val createTime: LocalDateTime = LocalDateTime.now(),
    @UpdateTimestamp
    val updateTime: LocalDateTime = LocalDateTime.now()
)