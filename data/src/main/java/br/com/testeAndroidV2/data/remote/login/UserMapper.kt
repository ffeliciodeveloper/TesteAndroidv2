package br.com.testeAndroidV2.data.remote.login

import br.com.testeAndroidV2.domain.ErrorResponse
import br.com.testeAndroidV2.domain.user.model.User

class UserMapper {
    fun userEntityToUser(loginResponse: LoginResponse): Pair<User, ErrorResponse> {

        val user = User(
            loginResponse.userEntity.id,
            loginResponse.userEntity.name,
            loginResponse.userEntity.bankAccount,
            loginResponse.userEntity.agency,
            loginResponse.userEntity.balance
        )

        val loginError =
            ErrorResponse(loginResponse.errorResponse.code, loginResponse.errorResponse.message)
        return Pair(user, loginError)
    }
}