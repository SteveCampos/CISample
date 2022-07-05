package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Vehicle
import java.util.*

interface IRegisterService<V : Vehicle> {
    suspend fun register(vehicle: V, parkingSpace: ParkingSpace, startDate: Date)
    suspend fun endRegisterSpace(registeredSpace: RegisteredSpace<V>, endDate: Date)
}