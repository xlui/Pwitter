package app.xlui.pwitter.repository

import app.xlui.pwitter.entity.db.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByTweetId(tweetId: Long): List<Comment>
}