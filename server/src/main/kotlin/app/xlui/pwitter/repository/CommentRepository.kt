package app.xlui.pwitter.repository

import app.xlui.pwitter.entity.db.Comment
import app.xlui.pwitter.entity.db.Tweet
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByTweet(tweet: Tweet): List<Comment>
}