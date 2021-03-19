package br.com.testeAndroidV2.testeandroidv2

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import br.com.testeAndroidV2.testeandroidv2.di.dataModule
import br.com.testeAndroidV2.testeandroidv2.di.domainModule
import br.com.testeAndroidV2.testeandroidv2.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                presentationModule,
                domainModule,
                dataModule
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}