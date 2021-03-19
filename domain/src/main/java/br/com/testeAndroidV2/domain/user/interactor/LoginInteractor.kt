package br.com.testeAndroidV2.domain.user.interactor

import br.com.testeAndroidV2.domain.ErrorResponse
import br.com.testeAndroidV2.domain.InteractorSingle
import br.com.testeAndroidV2.domain.Schedulers
import br.com.testeAndroidV2.domain.user.repository.LoginRepository
import br.com.testeAndroidV2.domain.user.model.User
import io.reactivex.rxjava3.core.Single

class LoginInteractor(private val loginRepository: LoginRepository, schedulers: Schedulers) :
    InteractorSingle<Pair<User, ErrorResponse>, LoginInteractor.Request>(schedulers) {


    override fun create(request: Request): Single<Pair<User, ErrorResponse>> {
        return loginRepository.fetchUserByLoginAndPassword(request.getLogin(), request.getPassword())
    }

    inner class Request(private val login: String, private val password: String) :
        InteractorSingle.Request() {
        fun getLogin() = login
        fun getPassword() = password
    }
}