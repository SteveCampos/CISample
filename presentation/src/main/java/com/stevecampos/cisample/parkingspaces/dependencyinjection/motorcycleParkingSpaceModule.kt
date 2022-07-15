package com.stevecampos.cisample.parkingspaces.dependencyinjection

import com.stevecampos.domain.register.service.MotorcycleParkingSpaceService
import com.stevecampos.domain.register.service.ParkingSpaceService
import com.stevecampos.domain.vehicle.entity.Motorcycle
import org.koin.dsl.module

val motorcycleParkingSpaceModule = module{
    single<ParkingSpaceService<Motorcycle>> {
        MotorcycleParkingSpaceService()
    }
}