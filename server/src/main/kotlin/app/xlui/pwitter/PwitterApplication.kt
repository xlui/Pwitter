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
import java.util.Date

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
        val from = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
        val to = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant())

        val salt = generateSalt()
        val mainUser = User(username = "xlui", password = generateEncryptedPassword("pass", salt), salt = salt)
        val follower1 = User(username = "f1", password = generateEncryptedPassword("p1", salt), salt = salt)
        val follower2 = User(username = "f2", password = generateEncryptedPassword("p2", salt), salt = salt, isDeleted = true)
        val follower3 = User(username = "f3", password = generateEncryptedPassword("p3", salt), salt = salt)

        val follow1 = Follow(user = follower1, follower = mainUser)
        val follow2 = Follow(user = follower2, follower = mainUser)
        val follow3 = Follow(user = follower3, follower = mainUser)

        val tweet1_1 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower1 }
        val tweet1_2 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower1 }
        val tweet1_3 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower1 }
        val tweet2_1 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower2 }
        val tweet2_2 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower2 }
        val tweet3_1 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower3 }
        val tweet3_2 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower3 }
        val tweet3_3 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower3 }
        val tweet3_4 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower3 }

        userService.save(listOf(mainUser, follower1, follower2, follower3))
        followService.save(listOf(follow1, follow2, follow3))
        tweetService.save(listOf(tweet1_1, tweet1_2, tweet1_3, tweet2_1, tweet2_2, tweet3_1, tweet3_2, tweet3_3, tweet3_4))
    }
}

fun main(args: Array<String>) {
    runApplication<PwitterApplication>(*args)
}