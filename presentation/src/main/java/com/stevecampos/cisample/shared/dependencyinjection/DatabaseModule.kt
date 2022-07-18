package com.stevecampos.cisample.shared.dependencyinjection

import com.stevecampos.infraestructure.share.ParkingDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
val databaseModule = module{

    single<ParkingDatabase> {
        ParkingDatabase.provideDatabase(androidApplication())
    }
}