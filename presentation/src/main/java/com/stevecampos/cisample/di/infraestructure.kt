package com.stevecampos.cisample.di

import com.stevecampos.domain.register.repository.CarRegisterRepository
import com.stevecampos.domain.register.repository.MotorcycleRegisterRepository
import com.stevecampos.domain.register.service.CarRegisterService
import com.stevecampos.domain.register.service.MotorcycleRegisterService
import com.stevecampos.infraestructure.data.dao.CarRegisterSpaceDao
import com.stevecampos.infraestructure.data.dao.MotoRegisterSpaceDao
import com.stevecampos.infraestructure.data.db.ParkingDatabase
import com.stevecampos.infraestructure.data.repository.CarRegisterRoom
import com.stevecampos.infraestructure.data.repository.MotorcycleRegisterRoom
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val infrastructureModule = module {

    single<ParkingDatabase> {
        ParkingDatabase.provideDatabase(androidApplication())
    }
    single<CarRegisterSpaceDao> {
        get<ParkingDatabase>().carRegisterSpaceDao
    }
    single<CarRegisterRepository> {
        CarRegisterRoom(get())
    }
    single {
        CarRegisterService(get<CarRegisterRepository>())
    }
    single<MotoRegisterSpaceDao> {
        get<ParkingDatabase>().motoRegisterSpaceDao
    }
    single<MotorcycleRegisterRepository> {
        MotorcycleRegisterRoom(get())
    }
    single {
        MotorcycleRegisterService(get<MotorcycleRegisterRepository>())
    }

}



