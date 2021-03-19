package br.com.testeAndroidV2.testeandroidv2.di

import android.content.Context
import br.com.testeAndroidV2.data.local.PreferencesRepositoryImpl
import br.com.testeAndroidV2.data.remote.api.TesteAndroidV2Service
import br.com.testeAndroidV2.data.remote.api.TesteAndroidv2ImplService
import br.com.testeAndroidV2.data.remote.login.LoginRepositoryImpl
import br.com.testeAndroidV2.data.remote.login.UserMapper
import br.com.testeAndroidV2.data.remote.statements.StatementMapper
import br.com.testeAndroidV2.data.remote.statements.StatementsRepositoryImpl
import br.com.testeAndroidV2.domain.Schedulers
import br.com.testeAndroidV2.domain.preferences.PreferencesRepository
import br.com.testeAndroidV2.domain.statements.interactor.StatementsInteractor
import br.com.testeAndroidV2.domain.statements.repository.StatementsRepository
import br.com.testeAndroidV2.domain.user.interactor.GetUserInteractor
import br.com.testeAndroidV2.domain.user.interactor.LoginInteractor
import br.com.testeAndroidV2.domain.user.repository.LoginRepository
import br.com.testeAndroidV2.domain.user.interactor.SaveUserInteractor
import br.com.testeAndroidV2.testeandroidv2.scheduler.AppScheduler
import br.com.testeAndroidV2.testeandroidv2.ui.statements.StatementsViewModel
import br.com.testeAndroidV2.testeandroidv2.ui.user.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { StatementsViewModel(get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
}
val domainModule = module {
    single<Schedulers> { AppScheduler() }
    single { LoginInteractor(get(), get()) }
    single { StatementsInteractor(get(), get()) }
    single { GetUserInteractor(get()) }
    single { SaveUserInteractor(get(), get()) }
}
val dataModule = module {
    factory<TesteAndroidV2Service> { TesteAndroidv2ImplService() }
    single { UserMapper() }
    single { StatementMapper() }
    single { androidContext().getSharedPreferences("preferences-teste-android-v2", Context.MODE_PRIVATE) }
    single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }
    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
    single<StatementsRepository> { StatementsRepositoryImpl(get(), get()) }
}