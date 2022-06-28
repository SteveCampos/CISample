package com.stevecampos.cisample.di

import android.app.Application
import com.stevecampos.domain.repository.ParkingRepository
import com.stevecampos.infraestructure.data.dao.ParkingSpaceDao
import com.stevecampos.infraestructure.data.db.ParkingDb
import com.stevecampos.infraestructure.data.mapper.ParkingSpaceDomainToInfraMapper
import com.stevecampos.infraestructure.data.mapper.ParkingSpaceInfraToDomainMapper
import com.stevecampos.infraestructure.data.repository.LocalRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val infraestructureModule = module {

    single {
        provideDataBase(androidApplication())
    }

    single {
        ParkingSpaceDomainToInfraMapper()
    }
    single {
        ParkingSpaceInfraToDomainMapper()
    }
    single<ParkingRepository> {
        LocalRepositoryImpl(
            get(),
            get(),
            get()
        )
    }

}

fun provideDataBase(application: Application): ParkingDb {
    return ParkingDb.getDatabase(application)
}

//fun provideDao(parkingDb: ParkingDb): ParkingSpaceDao = parkingDb.parkingSpaceDao

