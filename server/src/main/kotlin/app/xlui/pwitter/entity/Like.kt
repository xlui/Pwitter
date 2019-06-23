package app.xlui.pwitter.entity

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "t_like")
data class Like(
        @Id @GeneratedValue val id: Long = 0,

        val createTime: LocalDateTime = LocalDateTime.now()
) {
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var user: User = User()
    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var tweet: Tweet = Tweet()
}