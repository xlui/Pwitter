package app.xlui.pwitter.web

import app.xlui.pwitter.annotation.CurrentUser
import app.xlui.pwitter.entity.RestResponse
import app.xlui.pwitter.entity.User
import app.xlui.pwitter.service.TweetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class TweetController @Autowired constructor(
        val tweetService: TweetService
) {
    @RequestMapping(value = ["/tweet"], method = [RequestMethod.GET])
    fun fetchTweets(@CurrentUser user: User): RestResponse {

        return RestResponse.buildSuccess("ok")
    }

    @RequestMapping(value = ["/tweet"], method = [RequestMethod.POST])
    fun submitTweet(): RestResponse {
        return RestResponse.buildSuccess("ok")
    }
}