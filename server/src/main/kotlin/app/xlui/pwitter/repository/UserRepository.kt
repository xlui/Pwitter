package app.xlui.pwitter.repository

import app.xlui.pwitter.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    fun findByUsernameAndPassword(username: String, password: String): User?
}