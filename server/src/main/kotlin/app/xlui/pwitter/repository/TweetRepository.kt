package app.xlui.pwitter.repository

import app.xlui.pwitter.entity.db.Tweet
import org.springframework.data.jpa.repository.JpaRepository

interface TweetRepository : JpaRepository<Tweet, Long> {
    fun findByUserIdIn(userIdList: List<Long>): List<Tweet>
}