package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Vehicle
import java.util.*

interface IRegisterService<V : Vehicle> {
    suspend fun register(v: V, parkingSpace: ParkingSpace, startDate: Date)
}