package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.exception.NotParkingSpacesAvailableException
import com.stevecampos.domain.register.exception.SpaceLockedException
import com.stevecampos.domain.register.exception.UnAuthorizedException
import com.stevecampos.domain.register.exception.VehicleAlreadyOnParkingSpaceException
import com.stevecampos.domain.register.repository.RegisterRepository
import com.stevecampos.domain.register.valueobject.isMonday
import com.stevecampos.domain.register.valueobject.isSunday
import com.stevecampos.domain.vehicle.entity.Vehicle
import java.util.*

abstract class RegisterService<V : Vehicle>(
    private val registerRepository: RegisterRepository<V>,
    private val parkingSpaceService: ParkingSpaceService<V>
) {
    suspend fun register(vehicle: V, parkingSpace: ParkingSpace, startDate: Date) {
        if (vehicle.plateBeginsWithA() && (startDate.isSunday() || startDate.isMonday()))
            throw UnAuthorizedException()

        val spacesLockedForVehicleType =
            registerRepository.getRegisteredSpaces(state = RegisteredState.Locked)


        if (spacesLockedForVehicleType.size >= parkingSpaceService.size)
            throw NotParkingSpacesAvailableException()

        if (registerRepository.getActiveRegisterSpaceForSpace(parkingSpace) != null) {
            throw SpaceLockedException()
        }
        if (registerRepository.getActiveRegisterSpaceForVehicle(vehicle) != null) {
            throw VehicleAlreadyOnParkingSpaceException()
        }

        val registeredSpace = RegisteredSpace(
            vehicle = vehicle,
            parkingSpace = parkingSpace,
            startDate = startDate,
            endDate = null
        )
        registerRepository.register(registeredSpace)
    }

    suspend fun endRegisterSpace(registeredSpace: RegisteredSpace<V>, endDate: Date) {
        val space = registeredSpace.copy(endDate = endDate, state = RegisteredState.Finished)
        registerRepository.finishRegisterSpaced(space)
    }
}