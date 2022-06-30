package com.stevecampos.domain.register.repository

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.vehicle.entity.Vehicle

interface RegisterRepository<V : Vehicle> {
    suspend fun register(registeredSpace: RegisteredSpace<V>)
    suspend fun getRegisteredSpaces(state: RegisteredState): List<RegisteredSpace<V>>
}