package com.stevecampos.domain.register.repository

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Vehicle

interface RegisterRepository<V : Vehicle> {
    suspend fun register(registeredSpace: RegisteredSpace<V>)
    suspend fun getRegisteredSpaces(state: RegisteredState): List<RegisteredSpace<V>>

    suspend fun getActiveRegisterSpaceForSpace(parkingSpace: ParkingSpace): RegisteredSpace<V>?
    suspend fun getActiveRegisterSpaceForVehicle(vehicle: V): RegisteredSpace<V>?
    suspend fun finishRegisterSpaced(registeredSpace: RegisteredSpace<V>)
}