package com.stevecampos.cisample.car

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.repository.RegisterRepository
import com.stevecampos.domain.vehicle.entity.Car

class FakeCarRegisterRepository : RegisterRepository<Car> {
    override suspend fun register(registeredSpace: RegisteredSpace<Car>) {
    }

    override suspend fun getRegisteredSpaces(state: RegisteredState): List<RegisteredSpace<Car>> {
        return listOf()
    }

    override suspend fun getActiveRegisterSpaceForSpace(parkingSpace: ParkingSpace): RegisteredSpace<Car>? {
        return null
    }

    override suspend fun getActiveRegisterSpaceForVehicle(vehicle: Car): RegisteredSpace<Car>? {
        return null
    }

    override suspend fun finishRegisterSpaced(registeredSpace: RegisteredSpace<Car>) {
    }
}