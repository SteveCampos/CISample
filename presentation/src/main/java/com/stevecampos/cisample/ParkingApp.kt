package com.stevecampos.cisample

import android.app.Application
import com.stevecampos.cisample.di.domainModule
import com.stevecampos.cisample.di.infrastructureModule
import com.stevecampos.cisample.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ParkingApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@ParkingApp)
            modules(
                domainModule,
                infrastructureModule,
                presentationModule
            )
        }
    }
}