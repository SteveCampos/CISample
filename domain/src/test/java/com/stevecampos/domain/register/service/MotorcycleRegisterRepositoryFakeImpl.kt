package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.repository.MotorcycleRegisterRepository
import com.stevecampos.domain.vehicle.entity.Motorcycle
import java.time.Instant
import java.util.*

class MotorcycleRegisterRepositoryFakeImpl(initSize: Int) : MotorcycleRegisterRepository {
    private val registeredSpaces = mutableListOf<RegisteredSpace<Motorcycle>>()

    init {
        for (i in 0..initSize) {
            registeredSpaces.add(
                RegisteredSpace(
                    vehicle = Motorcycle("AAA$i".padEnd(6, '0'), 400),
                    parkingSpace = ParkingSpace(i),
                    startDate = Date.from(Instant.now()),
                    finishDate = null
                )
            )
        }
    }

    override suspend fun register(registeredSpace: RegisteredSpace<Motorcycle>) {
        registeredSpaces.add(registeredSpace)
    }

    override suspend fun getRegisteredSpaces(state: RegisteredState): List<RegisteredSpace<Motorcycle>> {
        return registeredSpaces.toList()
    }
}

