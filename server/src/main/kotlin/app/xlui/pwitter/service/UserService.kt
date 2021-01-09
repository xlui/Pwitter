package app.xlui.pwitter.service

import app.xlui.pwitter.entity.db.User
import app.xlui.pwitter.repository.UserRepository
import app.xlui.pwitter.util.unpack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository
) {
    /**
     * 存入数据库
     */
    fun save(user: User): User = userRepository.save(user)

    fun save(users: Iterable<User>) = userRepository.saveAll(users)

    fun delete(user: User) = userRepository.delete(user)

    fun deleteAll() = userRepository.deleteAll()

    fun findByUsername(username: String, deleted: Boolean = false): User? = userRepository.findByUsername(username)
        ?.takeIf { deleted == it.deleted }

    fun findByUsernameAndPassword(username: String, password: String, deleted: Boolean = false) =
        userRepository.findByUsernameAndPassword(username, password)
            ?.takeIf { deleted == it.deleted }

    fun findFollowings(user: User) = userRepository.findFollowings(user)

    fun findAll() = userRepository.findAll()

    /**
     * 判断是否可以通过 username 查询到用户
     */
    fun exist(username: String): Boolean = userRepository.findByUsername(username) != null

    fun exist(user: User): Boolean = exist(user.username)

    fun enabled(userId: Long): Boolean = unpack(userRepository.findById(userId))
        ?.let { return@let it.deleted }
        ?: kotlin.run { false }
}