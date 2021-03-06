package app.xlui.pwitter.entity.db

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "pwitter_follow")
data class Follow(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val followingUserId: Long = 0,
    val followerUserId: Long = 0,
    val deleted: Boolean = false,

    @CreationTimestamp
    val createTime: LocalDateTime = LocalDateTime.now(),
    @UpdateTimestamp
    val updateTime: LocalDateTime = LocalDateTime.now()
)