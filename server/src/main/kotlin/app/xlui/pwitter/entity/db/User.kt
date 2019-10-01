package app.xlui.pwitter.entity.db

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "t_user")
data class User(
        @Id
        @GeneratedValue
        @JsonProperty(access = JsonProperty.Access.AUTO)
        val id: Long = 0,
        @Column(unique = true, length = 32)
        @field:NotBlank(message = "Username should not be blank!")
        @JsonProperty(access = JsonProperty.Access.AUTO)
        val username: String = "",
        @field:Length(min = 8, max = 32, message = "Password length must between 8 and 32 characters!")
        @field:NotBlank(message = "Password should not be blank!")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        val password: String = "",
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        val salt: String = "",
        @field:NotBlank(message = "Email should not be blank!")
        @field:Email(message = "Email format invalid!")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        val email: String = "",
        @Column(length = 32)
        @JsonProperty(access = JsonProperty.Access.AUTO)
        val nickname: String = "",
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        val deleted: Boolean = false,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        val createTime: LocalDateTime = LocalDateTime.now(),
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        val updateTime: LocalDateTime = LocalDateTime.now()
) {
    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val tweets: List<Tweet> = listOf()
}