package br.com.testeAndroidV2.domain.preferences

import br.com.testeAndroidV2.domain.user.model.User
import io.reactivex.rxjava3.core.Completable

interface PreferencesRepository {
    fun save(user: User?) : Completable
    fun getUser() : User
}