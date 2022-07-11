package com.stevecampos.cisample.di.register.car

import com.stevecampos.domain.register.repository.CarRegisterRepository
import com.stevecampos.domain.register.service.CarRegisterService
import com.stevecampos.domain.register.service.RegisterService
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.infraestructure.register.dao.CarRegisterSpaceDao
import com.stevecampos.infraestructure.register.repository.CarRegisterRoom
import com.stevecampos.infraestructure.share.ParkingDatabase
import org.koin.dsl.module

 val registerCarModule = module {

    single<CarRegisterSpaceDao> {
        get<ParkingDatabase>().carRegisterSpaceDao
    }
    single<CarRegisterRepository> {
        CarRegisterRoom(get())
    }
    single {
        CarRegisterService(get<CarRegisterRepository>())
    }
}