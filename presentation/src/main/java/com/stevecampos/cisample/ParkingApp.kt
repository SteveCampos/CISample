package com.stevecampos.cisample

import android.app.Application

class ParkingApp: Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        /*startKoin {
            androidLogger()
            androidContext(this@ParkingApp)
            modules(
                domainModule,
                infraestructureModule,
                presentationModule
            )
        }*/
    }
}