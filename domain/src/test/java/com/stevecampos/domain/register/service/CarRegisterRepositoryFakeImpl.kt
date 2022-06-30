package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.repository.CarRegisterRepository
import com.stevecampos.domain.vehicle.entity.Car
import java.time.Instant
import java.util.*

class CarRegisterRepositoryFakeImpl(initSize: Int) : CarRegisterRepository {
    private val registeredSpaces = mutableListOf<RegisteredSpace<Car>>()

    init {
        for (i in 0..initSize) {
            registeredSpaces.add(
                RegisteredSpace(
                    vehicle = Car("AAA$i".padEnd(6, '0')),
                    parkingSpace = ParkingSpace(i),
                    startDate = Date.from(Instant.now()),
                    finishDate = null
                )
            )
        }
    }

    override suspend fun register(registeredSpace: RegisteredSpace<Car>) {
        registeredSpaces.add(registeredSpace)
    }

    override suspend fun getRegisteredSpaces(state: RegisteredState): List<RegisteredSpace<Car>> {
        return registeredSpaces.toList()
    }
}