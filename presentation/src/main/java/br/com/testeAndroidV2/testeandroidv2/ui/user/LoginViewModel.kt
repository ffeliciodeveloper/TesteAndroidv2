package br.com.testeAndroidV2.testeandroidv2.ui.user

import androidx.lifecycle.MutableLiveData
import br.com.testeAndroidV2.domain.ErrorResponse
import br.com.testeAndroidV2.domain.user.interactor.GetUserInteractor
import br.com.testeAndroidV2.domain.user.interactor.LoginInteractor
import br.com.testeAndroidV2.domain.user.interactor.SaveUserInteractor
import br.com.testeAndroidV2.domain.user.model.User
import br.com.testeAndroidV2.testeandroidv2.BaseViewModel
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.observers.DisposableSingleObserver

class LoginViewModel(
    private val loginInteractor: LoginInteractor,
    private val getUserInteractor: GetUserInteractor,
    private val saveUserInteractor: SaveUserInteractor,
) : BaseViewModel() {

    var loading = MutableLiveData<Boolean>()
    var error = MutableLiveData<Throwable>()
    var errorLogin = MutableLiveData<ErrorResponse>()
    var user = MutableLiveData<User>()
    var lastUserLogged = MutableLiveData<User>()
    var userSaved = MutableLiveData<Boolean>()


    fun getLastLoggedUser() = getUserPreferences()

    private fun getUserPreferences() {
        val user = getUserInteractor.execute()
        lastUserLogged.value = user
    }

    fun fetchUserByLoginAndPassword(login: String, password: String) {
        addDisposable(login(login, password))
    }

    fun saveUser(user: User) = saveUserInPreferences(user)

    private fun login(login: String, password: String): Disposable {

        return loginInteractor.execute(loginInteractor.Request(login, password)).subscribeWith(
            object : DisposableSingleObserver<Pair<User?, ErrorResponse>>() {
                override fun onStart() {
                    loading.value = true
                }

                override fun onSuccess(login: Pair<User?, ErrorResponse>) {
                    if (login.first?.name != null) {
                        user.value = login.first
                    }

                    if (!login.second.message.isNullOrBlank()) {
                        errorLogin.value = login.second

                    }
                    loading.value = false
                }

                override fun onError(throwable: Throwable) {
                    error.value = throwable
                    loading.value = false
                }

            })
    }

    private fun saveUserInPreferences(user: User) {
        return saveUserInteractor.execute(saveUserInteractor.Request(user))
            .subscribe(object :
                DisposableCompletableObserver() {
                override fun onStart() {
                    loading.value = true
                }
                override fun onComplete() {
                    userSaved.value = true
                    loading.value = false
                }

                override fun onError(e: Throwable?) {
                    error.value = e
                    userSaved.value = false
                    loading.value = false
                }
            })
    }

}