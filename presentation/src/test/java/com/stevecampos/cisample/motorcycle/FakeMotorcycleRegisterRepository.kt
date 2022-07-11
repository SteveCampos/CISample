package com.stevecampos.cisample.motorcycle

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.repository.MotorcycleRegisterRepository
import com.stevecampos.domain.vehicle.entity.Motorcycle

class FakeMotorcycleRegisterRepository : MotorcycleRegisterRepository {
    override suspend fun register(registeredSpace: RegisteredSpace<Motorcycle>) {

    }

    override suspend fun getRegisteredSpaces(state: RegisteredState): List<RegisteredSpace<Motorcycle>> {
        return listOf()
    }

    override suspend fun getActiveRegisterSpaceForSpace(parkingSpace: ParkingSpace): RegisteredSpace<Motorcycle>? {
        return null
    }

    override suspend fun getActiveRegisterSpaceForVehicle(vehicle: Motorcycle): RegisteredSpace<Motorcycle>? {
        return null
    }

    override suspend fun finishRegisterSpaced(registeredSpace: RegisteredSpace<Motorcycle>) {
    }

}
