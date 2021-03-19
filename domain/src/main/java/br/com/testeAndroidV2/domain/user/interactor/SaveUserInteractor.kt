package br.com.testeAndroidV2.domain.user.interactor

import br.com.testeAndroidV2.domain.InteractorCompletable
import br.com.testeAndroidV2.domain.Schedulers
import br.com.testeAndroidV2.domain.preferences.PreferencesRepository
import br.com.testeAndroidV2.domain.user.model.User
import io.reactivex.rxjava3.core.Completable

class SaveUserInteractor(private val preferencesRepository: PreferencesRepository, schedulers: Schedulers) :
    InteractorCompletable<SaveUserInteractor.Request>(schedulers) {
    override fun create(request: Request): Completable {
        return preferencesRepository.save(request.getUser())
    }

    inner class Request(private val user: User) : InteractorCompletable.Request() {
        fun getUser() = user
    }

}