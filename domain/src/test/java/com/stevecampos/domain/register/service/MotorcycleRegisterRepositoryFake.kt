package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.repository.MotorcycleRegisterRepository
import com.stevecampos.domain.vehicle.entity.Motorcycle
import java.time.Instant
import java.util.*

class MotorcycleRegisterRepositoryFake(initSize: Int) : MotorcycleRegisterRepository {
    private val registeredSpaces = mutableListOf<RegisteredSpace<Motorcycle>>()

    init {
        for (i in 1..initSize) {
            registeredSpaces.add(
                RegisteredSpace(
                    vehicle = Motorcycle("AAA$i".padEnd(6, '0'), 400),
                    parkingSpace = ParkingSpace(i),
                    startDate = Date.from(Instant.now()),
                    endDate = null
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

    override suspend fun getActiveRegisterSpaceForSpace(parkingSpace: ParkingSpace): RegisteredSpace<Motorcycle>? {
        return registeredSpaces.firstOrNull { it.parkingSpace.id == parkingSpace.id }
    }

    override suspend fun getActiveRegisterSpaceForVehicle(vehicle: Motorcycle): RegisteredSpace<Motorcycle>? {
        return registeredSpaces.firstOrNull { it.vehicle.plate == vehicle.plate }
    }

    override suspend fun finishRegisterSpaced(registeredSpace: RegisteredSpace<Motorcycle>) {
        val registeredSpaceIndex = registeredSpaces.indexOf(registeredSpace)
        registeredSpaces[registeredSpaceIndex] = registeredSpace.copy(state = RegisteredState.Finished)
    }
}

