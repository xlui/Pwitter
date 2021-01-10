package app.xlui.pwitter.web

import app.xlui.pwitter.annotation.CurrentUser
import app.xlui.pwitter.constant.CommonExceptionTypeEnum
import app.xlui.pwitter.constant.TweetMediaTypeEnum
import app.xlui.pwitter.entity.common.RestResponse
import app.xlui.pwitter.entity.db.Comment
import app.xlui.pwitter.entity.db.Tweet
import app.xlui.pwitter.entity.db.User
import app.xlui.pwitter.service.CommentService
import app.xlui.pwitter.service.TweetService
import app.xlui.pwitter.service.UserService
import app.xlui.pwitter.util.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.util.StringUtils
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import javax.validation.Valid

@RestController
class TweetController @Autowired constructor(
    val userService: UserService,
    val tweetService: TweetService,
    val commentService: CommentService
) {
    private val logger = logger<TweetController>()

    /**
     * Timeline, the homepage of each user.
     */
    @RequestMapping(value = ["/tweet"], method = [RequestMethod.GET])
    fun timeline(
        @CurrentUser user: User,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate?
    ): RestResponse {
        /**
         * 考虑根据关注者点赞、评论、转发形成一个公式计算出关注着点赞或转发的最有价值的几条 tweet 插入用户的 timeline
         */
        val tweetFrom = from?.atStartOfDay() ?: LocalDate.now().minusDays(7).atStartOfDay()
        val tweetTo = to?.atStartOfDay() ?: LocalDate.now().atStartOfDay()
        val followings = userService.findFollowings(user)
        val timelineUsers = mutableListOf(user).apply { addAll(followings) }
        return RestResponse.buildSuccess(tweetService.findByUsers(timelineUsers, tweetFrom, tweetTo))
    }

    /**
     * Post a new tweet.
     */
    @RequestMapping(value = ["/tweet"], method = [RequestMethod.POST])
    fun createTweet(@CurrentUser user: User, @RequestBody @Valid param: Tweet, errors: Errors): RestResponse {
        if (errors.hasErrors()) return RestResponse.buildError(CommonExceptionTypeEnum.TweetParamInvalid)
        if (param.mediaType != TweetMediaTypeEnum.None && StringUtils.isEmpty(param.media)) {
            return RestResponse.buildError(CommonExceptionTypeEnum.TweetMediaInvalid)
        }
        val tweet = Tweet(userId = user.id, content = param.content, mediaType = param.mediaType, media = param.media)
        tweetService.save(tweet)
        return RestResponse.buildSuccess("Successfully create a tweet!")
    }

    /**
     * View a tweet's details.
     */
    @RequestMapping(value = ["/tweet/{tweetId}"], method = [RequestMethod.GET])
    fun viewTweet(@PathVariable("tweetId") tweetId: Long): RestResponse {
        tweetService.findValidById(tweetId)
            ?.let { return RestResponse.buildSuccess(it) }
        return RestResponse.buildError(CommonExceptionTypeEnum.TweetIdInvalid)
    }

    /**
     * View a tweet's comments.
     */
    @RequestMapping(value = ["/tweet/{tweetId}/comment"], method = [RequestMethod.GET])
    fun comments(@CurrentUser user: User, @PathVariable("tweetId") tweetId: Long): RestResponse {
        tweetService.findValidById(tweetId)
            ?.let { commentService.findByTweet(it) }
            ?.let { return RestResponse.buildSuccess(it) }
        return RestResponse.buildError(CommonExceptionTypeEnum.TweetIdInvalid)
    }

    /**
     * Post a comment to a tweet or a comment
     */
    @RequestMapping(value = ["/tweet/{tweetId}/comment"], method = [RequestMethod.POST])
    fun createComment(
        @CurrentUser user: User,
        @PathVariable("tweetId") tweetId: Long,
        @RequestBody param: Comment
    ): RestResponse {
        tweetService.findValidById(tweetId)?.let {
            commentService.save(
                Comment(
                    userId = user.id,
                    tweetId = tweetId,
                    replyCommentId = param.replyCommentId,
                    content = param.content
                )
            )
            return RestResponse.buildSuccess("Successfully post a comment!")
        }
        return RestResponse.buildError(CommonExceptionTypeEnum.TweetIdInvalid)
    }
}