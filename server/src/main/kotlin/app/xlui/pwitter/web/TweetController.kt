package app.xlui.pwitter.web

import app.xlui.pwitter.annotation.CurrentUser
import app.xlui.pwitter.annotation.ValidateVO
import app.xlui.pwitter.constant.CommonExceptionTypeEnum
import app.xlui.pwitter.constant.TweetMediaTypeEnum
import app.xlui.pwitter.converter.PwitterConverter
import app.xlui.pwitter.entity.common.RestResponse
import app.xlui.pwitter.entity.db.Comment
import app.xlui.pwitter.entity.db.Tweet
import app.xlui.pwitter.entity.db.User
import app.xlui.pwitter.entity.param.TweetTimelineParam
import app.xlui.pwitter.entity.resp.TweetTimelineResp
import app.xlui.pwitter.entity.vo.UserVO
import app.xlui.pwitter.service.CommentService
import app.xlui.pwitter.service.TweetService
import app.xlui.pwitter.service.UserService
import app.xlui.pwitter.util.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.StringUtils
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors
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
    fun timeline(@CurrentUser user: UserVO, @RequestBody @ValidateVO param: TweetTimelineParam): RestResponse {
        /**
         * 考虑根据关注者点赞、评论、转发形成一个公式计算出关注着点赞或转发的最有价值的几条 tweet 插入用户的 timeline
         */
        val followings = userService.findFollowings(user.id)
        val timelineUsers = mutableListOf(user.id).apply { addAll(followings.map { it.id }) }
        val page = tweetService.findByUsers(timelineUsers, param.from.atStartOfDay(), param.to.atStartOfDay(), param.pageNo, param.pageSize)
        val tweets = page.get().collect(Collectors.toList())
        val userMap = userService.findMapByIdList(tweets.map { it.userId })
        return RestResponse.buildSuccess(TweetTimelineResp(PwitterConverter.convert(tweets, userMap), PwitterConverter.convert(page)))
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