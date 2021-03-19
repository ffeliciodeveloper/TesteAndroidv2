package br.com.testeAndroidV2.domain.user.interactor

import br.com.testeAndroidV2.domain.preferences.PreferencesRepository

class GetUserInteractor(private val preferencesRepository: PreferencesRepository) {
    fun execute() = preferencesRepository.getUser()
}