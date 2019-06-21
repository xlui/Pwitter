package app.xlui.pwitter.service

import app.xlui.pwitter.entity.User
import app.xlui.pwitter.exception.InternalException
import app.xlui.pwitter.exception.InvalidRequestException
import app.xlui.pwitter.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
        private val userRepository: UserRepository
) {
    fun save(user: User) {
        userRepository.save(user)
    }

    fun delete(user: User) {
        userRepository.delete(user)
    }

    fun findByUsername(username: String) = userRepository.findByUsername(username)

    fun findByUsernameAndPassword(username: String, password: String) = userRepository.findByUsernameAndPassword(username, password)

    fun exist(username: String) = userRepository.findByUsername(username) != null

    fun exist(user: User) = exist(user.username)
}