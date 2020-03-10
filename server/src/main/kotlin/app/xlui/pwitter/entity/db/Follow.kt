package app.xlui.pwitter.entity.db

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "t_follow")
data class Follow(
        @Id @GeneratedValue val id: Long = 0,
        @ManyToOne val user: User = User(),
        @ManyToOne val follower: User = User(),

        val createTime: LocalDateTime = LocalDateTime.now()
)