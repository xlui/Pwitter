@file:Suppress("LocalVariableName", "SpellCheckingInspection")

package app.xlui.pwitter

import app.xlui.pwitter.config.PwitterProperties
import app.xlui.pwitter.entity.db.Comment
import app.xlui.pwitter.entity.db.Follow
import app.xlui.pwitter.entity.db.Tweet
import app.xlui.pwitter.entity.db.User
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
import java.util.*

@Suppress("DuplicatedCode")
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
        userService.deleteAll()
        commentService.deleteAll()
        tweetService.deleteAll()
        followService.deleteAll()

        val faker = Faker()

        val salt = generateSalt()
        val u = User(username = "123asd", password = "dafasfafsadfdaasdsa", salt = salt, email = "asdas@mei.com")
        userService.save(u)
        val mainUser = userService.save(
            User(
                username = "xlui",
                password = generateEncryptedPassword("pass", salt),
                salt = salt,
                email = "test@example.com"
            )
        )
        val follower1 = userService.save(
            User(
                username = "follower1",
                password = generateEncryptedPassword("p1", salt),
                salt = salt,
                email = "test@example.com"
            )
        )
        val follower2 = userService.save(
            User(
                username = "follower2",
                password = generateEncryptedPassword("p2", salt),
                salt = salt,
                deleted = true,
                email = "test@example.com"
            )
        )
        val follower3 = userService.save(
            User(
                username = "follower3",
                password = generateEncryptedPassword("p3", salt),
                salt = salt,
                email = "test@example.com"
            )
        )

        val follow1 = Follow(followingUserId = mainUser.id, followerUserId = follower1.id)
        val follow2 = Follow(followingUserId = mainUser.id, followerUserId = follower2.id)
        val follow3 = Follow(followingUserId = mainUser.id, followerUserId = follower3.id)

        val from = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
        val to = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant())
        val tweetm_1 = tweetService.save(
            Tweet(
                userId = mainUser.id,
                content = faker.lorem().sentence(),
                createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())
            )
        )
        val tweetm_2 = Tweet(
            userId = mainUser.id,
            content = faker.lorem().sentence(),
            createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())
        )
        val tweet1_1 = Tweet(
            userId = follow1.id,
            content = faker.lorem().sentence(),
            createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())
        )
        val tweet1_2 = Tweet(
            userId = follow1.id,
            content = faker.lorem().sentence(),
            createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())
        )
        val tweet1_3 = Tweet(
            userId = follow1.id,
            content = faker.lorem().sentence(),
            createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())
        )
        val tweet2_1 = Tweet(
            userId = follow2.id,
            content = faker.lorem().sentence(),
            createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())
        )
        val tweet2_2 = Tweet(
            userId = follow2.id,
            content = faker.lorem().sentence(),
            createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())
        )
        val tweet3_1 = Tweet(
            userId = follow3.id,
            content = faker.lorem().sentence(),
            createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())
        )
        val tweet3_2 = Tweet(
            userId = follow3.id,
            content = faker.lorem().sentence(),
            createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())
        )
        val tweet3_3 = Tweet(
            userId = follow3.id,
            content = faker.lorem().sentence(),
            createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())
        )
        val tweet3_4 = Tweet(
            userId = follow3.id,
            content = faker.lorem().sentence(),
            createTime = LocalDateTime.ofInstant(faker.date().between(from, to).toInstant(), ZoneId.systemDefault())
        )

        val comment1 = Comment(userId = follow1.id, tweetId = tweetm_1.id, content = faker.lorem().sentence())
        val comment2 =
            commentService.save(Comment(userId = follow2.id, tweetId = tweetm_2.id, content = faker.lorem().sentence()))
        val comment3 = Comment(
            userId = follow3.id,
            tweetId = tweetm_1.id,
            replyCommentId = comment2.id,
            content = faker.lorem().sentence()
        )

        followService.save(listOf(follow1, follow2, follow3))
        tweetService.save(
            listOf(
                tweetm_2,
                tweet1_1,
                tweet1_2,
                tweet1_3,
                tweet2_1,
                tweet2_2,
                tweet3_1,
                tweet3_2,
                tweet3_3,
                tweet3_4
            )
        )
        commentService.save(listOf(comment1, comment3))
    }
}

fun main(args: Array<String>) {
    runApplication<PwitterApplication>(*args)
}