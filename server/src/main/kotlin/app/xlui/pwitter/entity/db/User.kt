package app.xlui.pwitter.entity.db

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "pwitter_user")
data class User(
    @Id
    @GeneratedValue
    val id: Long = 0,
    @Column(unique = true, length = 32)
    @field:NotBlank(message = "Username should not be blank!")
    @field:Length(min = 4, max = 32, message = "Username's length must between 4 and 32 characters!")
    val username: String = "",
    @field:NotBlank(message = "Password should not be blank!")
    @field:Length(min = 8, max = 32, message = "Password's length must between 8 and 32 characters!")
    val password: String = "",
    val salt: String = "",
    @field:NotBlank(message = "Email should not be blank!")
    @field:Email(message = "Email format invalid!")
    val email: String = "",
    @Column(length = 32)
    val nickname: String = "",
    val deleted: Boolean = false,

    @CreationTimestamp
    val createTime: LocalDateTime = LocalDateTime.now(),
    @UpdateTimestamp
    val updateTime: LocalDateTime = LocalDateTime.now()
)