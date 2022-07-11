package com.stevecampos.cisample.di.parkingSpaces

import com.stevecampos.domain.register.service.CarParkingSpaceService
import com.stevecampos.domain.register.service.ParkingSpaceService
import com.stevecampos.domain.vehicle.entity.Car
import org.koin.dsl.module

val carParkingSpaceModule = module{
    single<ParkingSpaceService<Car>> {
        CarParkingSpaceService()
    }
}