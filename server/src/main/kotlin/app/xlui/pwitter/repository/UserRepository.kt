package app.xlui.pwitter.repository

import app.xlui.pwitter.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    fun findByUsernameAndPassword(username: String, password: String): User?

    @Query("SELECT u FROM User AS u " +
            "INNER JOIN Follow AS f " +
            "ON u.id = f.user.id " +
            "WHERE f.follower.id = :#{#user.id} " +
            "AND u.isDeleted = false")
    fun findFollowings(@Param("user") user: User): List<User>
}