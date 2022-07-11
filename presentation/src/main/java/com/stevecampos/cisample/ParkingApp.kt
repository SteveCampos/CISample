package com.stevecampos.cisample

import android.app.Application
import com.stevecampos.cisample.di.parkingSpaces.carParkingSpaceModule
import com.stevecampos.cisample.di.parkingSpaces.motorcycleParkingSpaceModule
import com.stevecampos.cisample.di.parkingSpaces.parkingSpaceModule
import com.stevecampos.cisample.di.register.car.registerCarModule
import com.stevecampos.cisample.di.register.motorcycle.registerMotorcycleModule
import com.stevecampos.cisample.di.shared.databaseModule
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
                databaseModule,
                parkingSpaceModule,
                carParkingSpaceModule,
                motorcycleParkingSpaceModule,
                registerCarModule,
                registerMotorcycleModule
            )
        }
    }
}