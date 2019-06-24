package app.xlui.pwitter.web

import app.xlui.pwitter.annotation.CurrentUser
import app.xlui.pwitter.entity.ResponseCode
import app.xlui.pwitter.entity.RestResponse
import app.xlui.pwitter.entity.Tweet
import app.xlui.pwitter.entity.TweetMediaType
import app.xlui.pwitter.entity.User
import app.xlui.pwitter.service.TweetService
import app.xlui.pwitter.service.UserService
import app.xlui.pwitter.util.unpack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class TweetController @Autowired constructor(
        val userService: UserService,
        val tweetService: TweetService
) {
    @RequestMapping(value = ["/tweet"], method = [RequestMethod.GET])
    fun timeline(
            @CurrentUser user: User,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate
    ): RestResponse {
        /**
         * 考虑根据关注者点赞、评论、转发形成一个公式计算出关注着点赞或转发的最有价值的几条 tweet 插入用户的 timeline
         */
        val followings = userService.findFollowings(user)
        val timelineUser = mutableListOf<User>(user).apply { addAll(followings) }
        val tweets = timelineUser.flatMap { it.tweets }
                .filter {
                    val date = it.createTime.toLocalDate()
                    from <= date && date <= to
                }
                .sortedBy { it.createTime }
        return RestResponse.buildSuccess(tweets)
    }

    @RequestMapping(value = ["/tweet"], method = [RequestMethod.POST])
    fun createTweet(@CurrentUser user: User, @RequestBody param: Tweet): RestResponse {
        if (StringUtils.isEmpty(param.content) || (param.mediaType != TweetMediaType.None && StringUtils.isEmpty(param.media))) {
            return RestResponse.buildError(ResponseCode.TweetContentInvalid)
        }
        val tweet = Tweet(content = param.content, mediaType = param.mediaType, media = param.media).apply { this.user = user }
        tweetService.save(tweet)
        return RestResponse.buildSuccess("Successfully create a tweet!")
    }

    @RequestMapping(value = ["/tweet/{tweetId}"], method = arrayOf(RequestMethod.GET))
    fun viewTweet(@PathVariable("tweetId") tweetId: Long): RestResponse {
        val tweet = tweetService.findByTweetId(tweetId)
        return if (tweet.isEmpty || tweet.get().user.deleted) {
            RestResponse.buildError(ResponseCode.TweetIdInvalid)
        } else {
            RestResponse.buildSuccess(tweet.get())
        }
    }

    @RequestMapping(value = ["/tweet/{tweetId}/comment"], method = arrayOf(RequestMethod.GET))
    fun comments(@CurrentUser user: User, @PathVariable("tweetId") tweetId: Long): RestResponse {
        val tweet = unpack(tweetService.findByTweetId(tweetId))
        tweet?.let {
            return RestResponse.buildSuccess(it.comments)
        }
        return RestResponse.buildError(ResponseCode.TweetIdInvalid)
    }
}