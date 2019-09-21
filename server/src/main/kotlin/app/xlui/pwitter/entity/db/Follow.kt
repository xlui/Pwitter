package app.xlui.pwitter.entity.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "t_follow")
data class Follow(
        @Id @GeneratedValue val id: Long = 0,
        @ManyToOne val user: User = User(),
        @ManyToOne val follower: User = User()
)