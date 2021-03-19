package br.com.testeAndroidV2.domain.user.repository

import br.com.testeAndroidV2.domain.ErrorResponse
import br.com.testeAndroidV2.domain.user.model.User
import io.reactivex.rxjava3.core.Single

interface LoginRepository {
    fun fetchUserByLoginAndPassword(login: String, password: String) : Single<Pair<User, ErrorResponse>>
}