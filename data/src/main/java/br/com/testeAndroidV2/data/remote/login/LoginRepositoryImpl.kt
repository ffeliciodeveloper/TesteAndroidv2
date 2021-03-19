package br.com.testeAndroidV2.data.remote.login

import br.com.testeAndroidV2.data.remote.api.TesteAndroidV2Service
import br.com.testeAndroidV2.domain.ErrorResponse
import br.com.testeAndroidV2.domain.user.repository.LoginRepository
import br.com.testeAndroidV2.domain.user.model.User
import io.reactivex.rxjava3.core.Single

class LoginRepositoryImpl(
    private val testeAndroidV2Service: TesteAndroidV2Service,
    private val userMapper: UserMapper,
) : LoginRepository {
    override fun fetchUserByLoginAndPassword(login: String, password: String): Single<Pair<User, ErrorResponse>> {
        return testeAndroidV2Service.fetchUserByLoginAndPassword(login, password)
            .map { loginResponse ->
                userMapper.userEntityToUser(loginResponse)
            }
    }
}