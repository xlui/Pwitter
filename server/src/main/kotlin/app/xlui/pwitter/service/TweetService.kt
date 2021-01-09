package app.xlui.pwitter.service

import app.xlui.pwitter.entity.db.Tweet
import app.xlui.pwitter.entity.db.User
import app.xlui.pwitter.repository.TweetRepository
import app.xlui.pwitter.util.unpack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TweetService @Autowired constructor(
    val tweetRepository: TweetRepository,
    val userService: UserService
) {
    fun save(tweet: Tweet) = tweetRepository.save(tweet)

    fun save(tweets: Iterable<Tweet>): List<Tweet> = tweetRepository.saveAll(tweets)

    fun delete(tweet: Tweet) = tweetRepository.delete(tweet)

    fun deleteAll() = tweetRepository.deleteAll()

    fun findAll(): List<Tweet> = tweetRepository.findAll()

    fun findById(tweetId: Long) = tweetRepository.findById(tweetId)

    fun findValidById(id: Long): Tweet? = unpack(findById(id))
        ?.takeIf { !it.deleted }
        ?.takeIf { userService.enabled(it.userId) }

    fun findByUsers(users: List<User>): List<Tweet> = tweetRepository.findByUserIdIn(users.map { it.id })
}