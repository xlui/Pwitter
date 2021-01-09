package app.xlui.pwitter.entity.vo

import app.xlui.pwitter.constant.CommonExceptionTypeEnum
import app.xlui.pwitter.exception.PwitterException
import org.apache.commons.validator.routines.EmailValidator

data class UserVO constructor(
    val username: String,
    val password: String,
    val email: String = "",
    val nickname: String = ""
) : ValidatePwitterVO {
    override fun validate() {
        username.takeIf { it.isNotBlank() } ?: throw PwitterException(CommonExceptionTypeEnum.UsernameOrPasswordInvalid)
        password.takeIf { it.isNotBlank() } ?: throw PwitterException(CommonExceptionTypeEnum.UsernameOrPasswordInvalid)
        email.takeIf { it.isNotBlank() }
            .takeIf { EmailValidator.getInstance().isValid(it) }
            ?: throw PwitterException(CommonExceptionTypeEnum.EmailFormatInvalid)
    }
}