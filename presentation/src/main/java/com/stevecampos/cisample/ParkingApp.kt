package com.stevecampos.cisample

import android.app.Application
/*import com.stevecampos.cisample.parkingspaces.dependencyinjection.carParkingSpaceModule
import com.stevecampos.cisample.parkingspaces.dependencyinjection.motorcycleParkingSpaceModule
import com.stevecampos.cisample.parkingspaces.dependencyinjection.parkingSpaceModule
import com.stevecampos.cisample.register.car.dependencyinjection.registerCarModule
import com.stevecampos.cisample.register.motorcycle.dependencyinjection.registerMotorcycleModule
import com.stevecampos.cisample.shared.dependencyinjection.databaseModule*/
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ParkingApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //setupKoin()
    }

    /*
    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@ParkingApp)
            modules(
                databaseModule,
                carParkingSpaceModule,
                motorcycleParkingSpaceModule,
                registerCarModule,
                registerMotorcycleModule,
                parkingSpaceModule
            )
        }
    }*/
}