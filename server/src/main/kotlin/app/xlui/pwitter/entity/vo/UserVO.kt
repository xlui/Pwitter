package app.xlui.pwitter.entity.vo

import app.xlui.pwitter.constant.CommonExceptionTypeEnum
import app.xlui.pwitter.entity.ValidatePwitterVO
import app.xlui.pwitter.exception.PwitterException
import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.validator.routines.EmailValidator

class UserVO constructor(
    val id: Long = 0,
    val username: String = "",
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val password: String = "",
    val email: String = "",
    val nickname: String = ""
) : ValidatePwitterVO {
    override fun validate() {
        username.takeIf { it.isNotBlank() } ?: throw PwitterException(CommonExceptionTypeEnum.UsernameOrPasswordInvalid)
        password.takeIf { it.isNotBlank() } ?: throw PwitterException(CommonExceptionTypeEnum.UsernameOrPasswordInvalid)
        email.takeIf { it.isNotBlank() }
            ?.takeIf { e ->
                EmailValidator.getInstance().takeIf { it.isValid(e) }
                    ?.run { return@run true }
                    ?: throw PwitterException()
            }
    }

    override fun toString(): String {
        return "UserVO(id=$id, username='$username', email='$email', nickname='$nickname')"
    }
}