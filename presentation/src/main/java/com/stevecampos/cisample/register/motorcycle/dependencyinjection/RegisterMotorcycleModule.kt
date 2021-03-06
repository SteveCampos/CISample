package com.stevecampos.cisample.register.motorcycle.dependencyinjection

import com.stevecampos.domain.register.repository.MotorcycleRegisterRepository
import com.stevecampos.domain.register.service.MotorcycleRegisterService
import com.stevecampos.infraestructure.register.dao.MotoRegisterSpaceDao
import com.stevecampos.infraestructure.register.repository.MotorcycleRegisterRoom
import com.stevecampos.infraestructure.share.ParkingDatabase
import org.koin.dsl.module

val registerMotorcycleModule = module {

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