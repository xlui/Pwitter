package app.xlui.pwitter.entity.db

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "pwitter_like")
data class Like(
    @Id
    @GeneratedValue
    val id: Long,
    val userId: Long,
    val tweetId: Long,
    val deleted: Boolean = false,

    @CreationTimestamp
    val createTime: LocalDateTime,
    @UpdateTimestamp
    val updateTime: LocalDateTime
)