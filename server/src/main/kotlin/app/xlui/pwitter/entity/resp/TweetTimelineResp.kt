package app.xlui.pwitter.entity.resp

import app.xlui.pwitter.entity.common.Page
import app.xlui.pwitter.entity.vo.TweetVO

data class TweetTimelineResp constructor(
    val tweets: List<TweetVO>,
    val page: Page,
)
