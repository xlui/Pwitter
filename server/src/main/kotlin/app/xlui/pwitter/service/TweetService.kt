package app.xlui.pwitter.service

import app.xlui.pwitter.entity.db.Tweet
import app.xlui.pwitter.repository.TweetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TweetService @Autowired constructor(
        val tweetRepository: TweetRepository
) {
    fun save(tweet: Tweet) = tweetRepository.save(tweet)

    fun save(tweets: Iterable<Tweet>) = tweetRepository.saveAll(tweets)

    fun delete(tweet: Tweet) = tweetRepository.delete(tweet)

    fun deleteAll() = tweetRepository.deleteAll()

    fun findByTweetId(tweetId: Long) = tweetRepository.findById(tweetId)
}