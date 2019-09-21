package app.xlui.pwitter.entity.db

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "t_user")
data class User(
        @Id @GeneratedValue val id: Long = 0,
        @Column(unique = true, length = 32) @field:NotBlank val username: String = "",
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @Length(min = 8, max = 32) @field:NotBlank val password: String = "",
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) val salt: String = "",
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @field:NotBlank @field:Email val email: String = "",
        @Column(length = 32) val nickname: String = "",
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) val deleted: Boolean = false,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) val createTime: LocalDateTime = LocalDateTime.now(),
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) val updateTime: LocalDateTime = LocalDateTime.now()
) {
    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val tweets: List<Tweet> = listOf()
}