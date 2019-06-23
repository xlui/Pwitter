package app.xlui.pwitter.entity

import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Email

@Entity
@Table(name = "t_user")
data class User(
        @Id @GeneratedValue val id: Long = 0,
        @Column(unique = true, length = 32) val username: String = "",
        @Length(min = 8, max = 32) val password: String = "",
        val salt: String = "",
        @Email val email: String = "",
        val nickname: String = "",
        val isDeleted: Boolean = false,

        val createTime: LocalDateTime = LocalDateTime.now(),
        val updateTime: LocalDateTime = LocalDateTime.now()
)