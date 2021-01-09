package app.xlui.pwitter.repository

import app.xlui.pwitter.entity.db.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    fun findByUsernameAndPassword(username: String, password: String): User?

    @Query(
        "SELECT u FROM Follow AS f LEFT JOIN User AS u ON f.followingUserId = u.id " +
                "WHERE f.followerUserId = :#{#user.id} AND u.deleted = false"
    )
    fun findFollowings(@Param("user") user: User): List<User>
}