package app.xlui.pwitter.repository

import app.xlui.pwitter.entity.Tweet
import org.springframework.data.jpa.repository.JpaRepository

interface TweetRepository : JpaRepository<Tweet, Long> {
}