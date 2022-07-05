package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.repository.CarRegisterRepository
import com.stevecampos.domain.vehicle.entity.Car
import java.time.Instant
import java.util.*

class CarRegisterRepositoryFake(private val initSize: Int) : CarRegisterRepository {
    private val registeredSpaces = mutableListOf<RegisteredSpace<Car>>()

    init {
        for (i in 1..initSize) {
            registeredSpaces.add(
                RegisteredSpace(
                    vehicle = Car("AAA$i".padEnd(6, '0')),
                    parkingSpace = ParkingSpace(i),
                    startDate = Date.from(Instant.now()),
                    endDate = null
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

    override suspend fun getActiveRegisterSpaceForSpace(parkingSpace: ParkingSpace): RegisteredSpace<Car>? {
        return registeredSpaces.firstOrNull { it.parkingSpace.id == parkingSpace.id }
    }

    override suspend fun getActiveRegisterSpaceForVehicle(vehicle: Car): RegisteredSpace<Car>? {
        return registeredSpaces.firstOrNull { it.vehicle.plate == vehicle.plate }
    }

    override suspend fun finishRegisterSpaced(
        registeredSpace: RegisteredSpace<Car>
    ) {
        val registeredSpaceIndex = registeredSpaces.indexOf(registeredSpace)
        registeredSpaces[registeredSpaceIndex] =
            registeredSpace.copy(state = RegisteredState.Finished)
    }
}