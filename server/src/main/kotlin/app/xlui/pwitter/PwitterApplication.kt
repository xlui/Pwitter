package app.xlui.pwitter

import app.xlui.pwitter.entity.User
import app.xlui.pwitter.service.UserService
import app.xlui.pwitter.util.logger
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@Slf4j
@SpringBootApplication
class PwitterApplication @Autowired constructor(
        val userService: UserService
): CommandLineRunner {
    val logger = logger<PwitterApplication>()

    override fun run(vararg args: String?) {
        logger.info("Run database init code in command line runner.")
        val user = User(username = "xlui", password = "pass")
        userService.save(user)
        logger.info("Init success.")
    }
}

fun main(args: Array<String>) {
    runApplication<PwitterApplication>(*args)
}