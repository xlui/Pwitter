package app.xlui.pwitter.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "t_user")
class User(
        @Id @GeneratedValue val id: Long = 0,
        val username: String = "",
        val password: String = "",
        val salt: String = "",
        val nickname: String = "",
        val isDeleted: Boolean = false,

        val createTime: LocalDateTime = LocalDateTime.now(),
        val updateTime: LocalDateTime = LocalDateTime.now()
)