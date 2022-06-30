package com.stevecampos.domain.register.aggregate

import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Vehicle
import java.util.*

data class RegisteredSpace<V : Vehicle>(
    val id: String = UUID.randomUUID().toString(),
    val vehicle: V,
    val parkingSpace: ParkingSpace,
    val startDate: Date,
    val finishDate: Date?,
    val state: RegisteredState = RegisteredState.Locked
)


sealed class RegisteredState {
    object Locked : RegisteredState()
    object Finished : RegisteredState()
}
