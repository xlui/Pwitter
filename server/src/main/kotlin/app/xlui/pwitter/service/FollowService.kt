package app.xlui.pwitter.service

import app.xlui.pwitter.entity.db.Follow
import app.xlui.pwitter.repository.FollowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FollowService @Autowired constructor(
        val followRepository: FollowRepository
) {
    fun save(follow: Follow) = followRepository.save(follow)

    fun save(follows: Iterable<Follow>) = followRepository.saveAll(follows)
}