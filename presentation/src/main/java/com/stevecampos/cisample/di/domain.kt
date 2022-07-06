package com.stevecampos.cisample.di


import com.stevecampos.domain.register.service.CarParkingSpaceService
import com.stevecampos.domain.register.service.MotorcycleParkingSpaceService
import org.koin.dsl.module

val domainModule = module {

    single<CarParkingSpaceService> {
        CarParkingSpaceService()
    }
    single<MotorcycleParkingSpaceService> {
        MotorcycleParkingSpaceService()
    }
}