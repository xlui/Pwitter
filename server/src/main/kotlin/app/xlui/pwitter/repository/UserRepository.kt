package app.xlui.pwitter.repository

import app.xlui.pwitter.entity.db.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {
    fun findByIdIn(idList: List<Long>): List<User>

    fun findByUsername(username: String): User?

    fun findByUsernameAndPassword(username: String, password: String): User?

    @Query(
        "SELECT u FROM Follow AS f LEFT JOIN User AS u ON f.followingUserId = u.id " +
                "WHERE f.followerUserId = :#{#userId} AND u.deleted = false"
    )
    fun findFollowings(userId: Long): List<User>
}