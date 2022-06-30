package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.exception.NotParkingSpacesAvailableException
import com.stevecampos.domain.register.exception.UnAuthorizedException
import com.stevecampos.domain.register.repository.RegisterRepository
import com.stevecampos.domain.register.valueobject.ParkingSpaceSize
import com.stevecampos.domain.register.valueobject.isMonday
import com.stevecampos.domain.register.valueobject.isSunday
import com.stevecampos.domain.vehicle.entity.Vehicle
import java.util.*

abstract class RegisterService<V : Vehicle>(
    private val registerRepository: RegisterRepository<V>,
    private val parkingSpaceSize: ParkingSpaceSize<V>
) : IRegisterService<V> {
    override suspend fun register(v: V, parkingSpace: ParkingSpace, startDate: Date) {
        if (v.plateBeginsWithA() && (startDate.isSunday() || startDate.isMonday()))
            throw UnAuthorizedException()

        val spacesLockedForVehicleType =
            registerRepository.getRegisteredSpaces(state = RegisteredState.Locked)


        if (spacesLockedForVehicleType.size >= parkingSpaceSize.size)
            throw NotParkingSpacesAvailableException()

        val registeredSpace = RegisteredSpace(
            vehicle = v,
            parkingSpace = parkingSpace,
            startDate = startDate,
            finishDate = null
        )
        registerRepository.register(registeredSpace)
    }
}