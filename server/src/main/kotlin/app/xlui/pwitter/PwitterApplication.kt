package app.xlui.pwitter

import app.xlui.pwitter.entity.User
import app.xlui.pwitter.service.UserService
import app.xlui.pwitter.util.generateEncryptedPassword
import app.xlui.pwitter.util.generateSalt
import app.xlui.pwitter.util.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PwitterApplication @Autowired constructor(
        val userService: UserService
) : CommandLineRunner {
    val logger = logger<PwitterApplication>()

    override fun run(vararg args: String?) {
        logger.info("Run database init code in command line runner")
        init()
        logger.info("Init success")
    }

    private fun init() {
        val salt = generateSalt()
        val user = User(username = "xlui", password = generateEncryptedPassword("pass", salt), salt = salt)
        userService.save(user)
    }
}

fun main(args: Array<String>) {
    runApplication<PwitterApplication>(*args)
}