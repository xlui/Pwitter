package app.xlui.pwitter

import app.xlui.pwitter.config.PwitterProperties
import app.xlui.pwitter.entity.Comment
import app.xlui.pwitter.entity.Follow
import app.xlui.pwitter.entity.Tweet
import app.xlui.pwitter.entity.User
import app.xlui.pwitter.service.CommentService
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
        val commentService: CommentService,
        val pwitterProperties: PwitterProperties
) : CommandLineRunner {
    private val logger = logger<PwitterApplication>()

    override fun run(vararg args: String?) {
        if (pwitterProperties.init) {
            logger.info("Run database init code in CommandLineRunner...")
            init()
            logger.info("Init success!")
        }
    }

    /**
     * 初始化测试数据
     */
    private fun init() {
        val faker = Faker()

        val salt = generateSalt()
        val mainUser = User(username = "xlui", password = generateEncryptedPassword("pass", salt), salt = salt, email = "test@example.com")
        val follower1 = User(username = "f1", password = generateEncryptedPassword("p1", salt), salt = salt, email = "test@example.com")
        val follower2 = User(username = "f2", password = generateEncryptedPassword("p2", salt), salt = salt, deleted = true, email = "test@example.com")
        val follower3 = User(username = "f3", password = generateEncryptedPassword("p3", salt), salt = salt, email = "test@example.com")

        val follow1 = Follow(user = follower1, follower = mainUser)
        val follow2 = Follow(user = follower2, follower = mainUser)
        val follow3 = Follow(user = follower3, follower = mainUser)

        val from = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
        val to = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant())
        val tweet1 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = mainUser }
        val tweet2 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = mainUser }
        val tweet1_1 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower1 }
        val tweet1_2 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower1 }
        val tweet1_3 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower1 }
        val tweet2_1 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower2 }
        val tweet2_2 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower2 }
        val tweet3_1 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower3 }
        val tweet3_2 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower3 }
        val tweet3_3 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower3 }
        val tweet3_4 = Tweet(content = faker.lorem().sentence(), createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())).apply { user = follower3 }

        val comment1 = Comment(content = faker.lorem().sentence()).apply { user = follower1; tweet = tweet1 }
        val comment2 = Comment(content = faker.lorem().sentence()).apply { user = follower2; tweet = tweet1 }
        val comment3 = Comment(content = faker.lorem().sentence(), replyTo = 2).apply { user = follower3; tweet = tweet1 }

        userService.save(listOf(mainUser, follower1, follower2, follower3))
        followService.save(listOf(follow1, follow2, follow3))
        tweetService.save(listOf(tweet1, tweet2, tweet1_1, tweet1_2, tweet1_3, tweet2_1, tweet2_2, tweet3_1, tweet3_2, tweet3_3, tweet3_4))
        commentService.save(listOf(comment1, comment2, comment3))
    }
}

fun main(args: Array<String>) {
    runApplication<PwitterApplication>(*args)
}