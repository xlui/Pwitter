package app.xlui.pwitter.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "t_comment")
data class Comment(
        @Id @GeneratedValue val id: Long = 0,
        val replyTo: Long = 0,
        val isDeleted: Boolean = false,

        @ManyToOne val user: User = User(),
        @OneToOne val tweet: Tweet = Tweet(),

        val createTime: LocalDateTime = LocalDateTime.now()
)