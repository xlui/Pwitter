package app.xlui.pwitter.repository

import app.xlui.pwitter.entity.db.Follow
import org.springframework.data.jpa.repository.JpaRepository

interface FollowRepository : JpaRepository<Follow, Long>