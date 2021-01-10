package app.xlui.pwitter.repository

import app.xlui.pwitter.entity.db.Tweet
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface TweetRepository : JpaRepository<Tweet, Long> {
    fun findByUserIdIn(userIdList: List<Long>): List<Tweet>

    fun findByUserIdInAndCreateTimeBetweenOrderByCreateTimeDesc(
        userIdList: List<Long>,
        from: LocalDateTime,
        to: LocalDateTime,
        pageable: Pageable
    ): Page<Tweet>
}