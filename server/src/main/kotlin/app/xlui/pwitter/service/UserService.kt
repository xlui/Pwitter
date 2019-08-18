package app.xlui.pwitter.service

import app.xlui.pwitter.entity.User
import app.xlui.pwitter.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
        private val userRepository: UserRepository
) {
    /**
     * 存入数据库
     */
    fun save(user: User) = userRepository.save(user)

    fun save(users: Iterable<User>) = userRepository.saveAll(users)

    fun delete(user: User) = userRepository.delete(user)

    fun deleteAll() = userRepository.deleteAll()

    fun findByUsername(username: String, isDeleted: Boolean = false) = userRepository.findByUsername(username)?.takeIf { isDeleted == it.deleted }

    fun findByUsernameAndPassword(username: String, password: String) = userRepository.findByUsernameAndPassword(username, password)

    fun findFollowings(user: User) = userRepository.findFollowings(user)

    /**
     * 判断是否可以通过 username 查询到用户
     */
    fun exist(username: String) = userRepository.findByUsername(username) != null

    fun exist(user: User) = exist(user.username)
}