package com.stevecampos.cisample.di.shared

import com.stevecampos.infraestructure.share.ParkingDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
val databaseModule = module{

    single<ParkingDatabase> {
        ParkingDatabase.provideDatabase(androidApplication())
    }
}