package app.xlui.pwitter

import app.xlui.pwitter.config.PwitterProperties
import app.xlui.pwitter.entity.Follow
import app.xlui.pwitter.entity.Tweet
import app.xlui.pwitter.entity.User
import app.xlui.pwitter.service.FollowService
import app.xlui.pwitter.service.TweetService
import app.xlui.pwitter.service.UserService
import app.xlui.pwitter.util.generateEncryptedPassword
import app.xlui.pwitter.util.generateSalt
import app.xlui.pwitter.util.logger
import com.github.javafaker.Faker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDateTime
import java.time.ZoneId

@SpringBootApplication
class PwitterApplication @Autowired constructor(
        val userService: UserService,
        val followService: FollowService,
        val tweetService: TweetService,
        val pwitterProperties: PwitterProperties
) : CommandLineRunner {
    val logger = logger<PwitterApplication>()

    override fun run(vararg args: String?) {
        if (pwitterProperties.init) {
            logger.info("Run database init code in CommandLineRunner")
            init()
            logger.info("Init success")
        }
    }

    private fun init() {
        val faker = Faker()

        val salt = generateSalt()
        val mainUser = User(username = "xlui", password = generateEncryptedPassword("pass", salt), salt = salt)
        val follower1 = User(username = "f1", password = generateEncryptedPassword("p1", salt), salt = salt)
        val follower2 = User(username = "f2", password = generateEncryptedPassword("p2", salt), salt = salt)
        val follower3 = User(username = "f3", password = generateEncryptedPassword("p3", salt), salt = salt)

        val follow1 = Follow(user = follower1, follower = mainUser)
        val follow2 = Follow(user = follower2, follower = mainUser)
        val follow3 = Follow(user = follower3, follower = mainUser)

        val tweet1_1 = Tweet(content = faker.lorem().sentence(), user = follower1, createTime = LocalDateTime.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()))
        val tweet1_2 = Tweet(content = faker.lorem().sentence(), user = follower1, createTime = LocalDateTime.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()))
        val tweet1_3 = Tweet(content = faker.lorem().sentence(), user = follower1, createTime = LocalDateTime.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()))
        val tweet2_1 = Tweet(content = faker.lorem().sentence(), user = follower2, createTime = LocalDateTime.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()))
        val tweet2_2 = Tweet(content = faker.lorem().sentence(), user = follower2, createTime = LocalDateTime.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()))
        val tweet3_1 = Tweet(content = faker.lorem().sentence(), user = follower3, createTime = LocalDateTime.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()))
        val tweet3_2 = Tweet(content = faker.lorem().sentence(), user = follower3, createTime = LocalDateTime.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()))
        val tweet3_3 = Tweet(content = faker.lorem().sentence(), user = follower3, createTime = LocalDateTime.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()))
        val tweet3_4 = Tweet(content = faker.lorem().sentence(), user = follower3, createTime = LocalDateTime.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()))

        userService.save(listOf(mainUser, follower1, follower2, follower3))
        followService.save(listOf(follow1, follow2, follow3))
        tweetService.save(listOf(tweet1_1, tweet1_2, tweet1_3, tweet2_1, tweet2_2, tweet3_1, tweet3_2, tweet3_3, tweet3_4))
    }
}

fun main(args: Array<String>) {
    runApplication<PwitterApplication>(*args)
}