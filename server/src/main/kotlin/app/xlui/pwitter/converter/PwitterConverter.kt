package app.xlui.pwitter.converter

import app.xlui.pwitter.entity.db.Tweet
import app.xlui.pwitter.entity.db.User
import app.xlui.pwitter.entity.vo.TweetVO
import app.xlui.pwitter.entity.vo.UserVO
import org.springframework.data.domain.Page

object PwitterConverter {
    fun <T> convert(page: Page<T>): app.xlui.pwitter.entity.common.Page {
        return app.xlui.pwitter.entity.common.Page(page.pageable.pageNumber + 1, page.pageable.pageSize, page.totalElements.toInt())
    }

    fun convert(tweets: List<Tweet>, userMap: Map<Long, User>): List<TweetVO> {
        return tweets.map { convert(it, userMap) }
    }

    fun convert(tweet: Tweet, userMap: Map<Long, User>): TweetVO {
        return TweetVO(
            tweet.id,
            tweet.content,
            tweet.mediaType,
            tweet.media,
            tweet.deleted,
            tweet.createTime,
            tweet.updateTime,
            convert(userMap[tweet.userId])
        )
    }

    fun convert(user: User?): UserVO {
        return user?.let { convertNonNull(it) } ?: UserVO()
    }

    fun convertNull(user: User?): UserVO? {
        return user?.let { convertNonNull(it) }
    }

    fun convertNonNull(user: User): UserVO {
        return UserVO(user.id, user.username, user.password, user.email, user.nickname)
    }
}