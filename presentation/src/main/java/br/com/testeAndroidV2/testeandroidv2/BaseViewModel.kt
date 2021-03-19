package br.com.testeAndroidV2.testeandroidv2

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.plusAssign

abstract class BaseViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        disposables += disposable
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}