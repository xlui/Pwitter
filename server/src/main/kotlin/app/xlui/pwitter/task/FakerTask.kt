package app.xlui.pwitter.task

import app.xlui.pwitter.config.PwitterProperties
import app.xlui.pwitter.constant.CommonExceptionTypeEnum
import app.xlui.pwitter.entity.common.RestResponse
import app.xlui.pwitter.entity.vo.UserVO
import app.xlui.pwitter.exception.PwitterException
import app.xlui.pwitter.service.CommentService
import app.xlui.pwitter.service.TweetService
import app.xlui.pwitter.service.UserService
import app.xlui.pwitter.util.logger
import app.xlui.pwitter.web.UserController
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.javafaker.Faker
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.Random

@Component
class FakerTask @Autowired constructor(
    private val gson: Gson,
    private val pwitterProperties: PwitterProperties,
    private val userService: UserService,
    private val tweetService: TweetService,
    private val commentService: CommentService,
    private val userController: UserController
) {
    private val logger = logger<FakerTask>()
    private val objectMapper = ObjectMapper()
    private val faker = Faker()
    private val httpClient = HttpClient.newHttpClient()
    private val fakerCount = 2

    @Scheduled(fixedRate = 3_000)
    fun active() {
        if (pwitterProperties.fake) {
            val random = Random()
            try {
                when (random.nextInt(fakerCount)) {
                    0 -> postTweet()
                    1 -> commentTweet()
                }
            } catch (e: Exception) {
                logger.error("FakerTask exec failed, ", e)
            }
        }
    }

    fun likeTweet() {
        logger.info("开始点赞tweet")
        val token = login()
        val tweet = tweetService.findAll().shuffled().first()
    }

    fun commentTweet() {
        logger.info("开始发送comment")
        val token = login()
        val tweet = tweetService.findAll().shuffled().first()
        val commentIds = commentService.findByTweet(tweet).map { it.id }
        val replyTo = mutableListOf(0L).apply { addAll(commentIds) }.shuffled().first()
        val comment = if (replyTo != 0L) """{"replyTo":"$replyTo", "content":"${faker.lorem().sentence()}"}"""
        else """{"content":"${faker.lorem().sentence()}"}"""
        val commentResp = httpClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tweet/${tweet.id}/comment"))
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(comment))
                .build(),
            HttpResponse.BodyHandlers.ofString()
        )
        logger.info("发送的comment：$comment")
        logger.info("发送的结果：${commentResp.body()}")
    }

    fun postTweet() {
        val token = login()
        val tweet = """{"id":0,"content":"${faker.lorem().sentence()}","mediaType":"None","media":""}"""
        val tweetResp = httpClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tweet"))
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(tweet))
                .build(),
            HttpResponse.BodyHandlers.ofString()
        )
        val restResp = gson.fromJson<RestResponse>(tweetResp.body(), RestResponse::class.java)
        logger.info("发送的Tweet：${objectMapper.writeValueAsString(tweet)}")
        logger.info("发送结果：$restResp")
    }

    fun login(): String {
        val passwordMap = mapOf(
            "xlui" to "pass",
            "follower1" to "p1",
            "follower2" to "p2",
            "follower3" to "p3"
        )
        val randomUser = userService.findAll().filter { !it.deleted }.shuffled().first()
        val loginResp = userController.login(
            UserVO(
                username = randomUser.username,
                password = passwordMap[randomUser.username] ?: error("未取到 ${randomUser.username} 的密码！")
            )
        )
        return if (loginResp.code == 0) {
            loginResp.data!! as String
        } else {
            logger.error("使用 $randomUser 登录失败！")
            throw PwitterException(CommonExceptionTypeEnum.InnerLoginFailed)
        }
    }
}
