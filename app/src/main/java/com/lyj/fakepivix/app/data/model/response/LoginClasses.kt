package com.lyj.fakepivix.app.data.model.response

/**
 * @author greensun
 *
 * @date 2019/4/9
 *
 * @desc
 */


data class LoginResp(
        val response: LoginData = LoginData()
)
data class LoginError(
        val has_error: Boolean = true,
        val errors: Errors = Errors()
)

data class Errors(
        val system: SystemError = SystemError()
)

data class SystemError(
        val code: Int = -1,
        val message: String = ""
)

data class LoginData(
        // 需要放在头部的tokenBearer JbMfFwZxLlC7dLz15qVHCg50yni0wySy-wxdKyZHCYM
        val access_token: String = "",
        val device_token: String = "",
        val expires_in: Int = 0,
        val refresh_token: String = "",
        val scope: String = "",
        val token_type: String = "",
        val user: User = User()
)

