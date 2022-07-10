package com.stevecampos.infraestructure.register.repository

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.repository.CarRegisterRepository
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.infraestructure.register.anticorrupt.CarRegisterSpaceTranslator
import com.stevecampos.infraestructure.register.anticorrupt.RegisterStateTranslator
import com.stevecampos.infraestructure.register.dao.CarRegisterSpaceDao
import com.stevecampos.infraestructure.register.entity.RegisterStateEntity
import com.stevecampos.infraestructure.register.exception.RegisterSpaceNotFinishedException
import com.stevecampos.infraestructure.register.exception.RegisterSpaceNotSavedException

class CarRegisterRoom(private val carRegisterSpaceDao: CarRegisterSpaceDao) :
    CarRegisterRepository {
    override suspend fun register(registeredSpace: RegisteredSpace<Car>) {
        val carRegisterSpaceTranslator = CarRegisterSpaceTranslator()
        val carRegisterSpaceEntity =
            carRegisterSpaceTranslator.translateToInfrastructure(registeredSpace)
        val rowsAffected = carRegisterSpaceDao.saveCarRegisterSpace(carRegisterSpaceEntity)
        if (rowsAffected.toInt() != 1) {
            throw RegisterSpaceNotSavedException()
        }
    }

    override suspend fun getRegisteredSpaces(state: RegisteredState): List<RegisteredSpace<Car>> {
        val carRegisterStateTranslator = CarRegisterSpaceTranslator()
        val registerTranslator = RegisterStateTranslator()
        val stateEntity = registerTranslator.translateToInfrastructure(state)
        return carRegisterSpaceDao.getAllCarRegisterSpacesWithState(stateEntity)
            .map { carRegisterStateTranslator.translateToDomain(it) }
    }

    override suspend fun getActiveRegisterSpaceForSpace(parkingSpace: ParkingSpace): RegisteredSpace<Car>? {
        val carRegisterStateTranslator = CarRegisterSpaceTranslator()
        val carRegisterSpace = carRegisterSpaceDao.getRegisterSpaceForSpaceWithState(
            parkingSpace.id,
            state = RegisterStateEntity.LOCKED
        ) ?: return null
        return carRegisterStateTranslator.translateToDomain(carRegisterSpace)
    }

    override suspend fun getActiveRegisterSpaceForVehicle(vehicle: Car): RegisteredSpace<Car>? {
        val carRegisterStateTranslator = CarRegisterSpaceTranslator()
        val carRegisterSpace = carRegisterSpaceDao.getRegisterSpaceForVehicleWithState(
            state = RegisterStateEntity.LOCKED,
            plate = vehicle.plate
        ) ?: return null
        return carRegisterStateTranslator.translateToDomain(carRegisterSpace)
    }

    override suspend fun finishRegisterSpaced(registeredSpace: RegisteredSpace<Car>) {
        val rowsAffected =
            carRegisterSpaceDao.finishRegisterSpace(registerSpaceId = registeredSpace.id)
        if (rowsAffected != 1) {
            throw RegisterSpaceNotFinishedException()
        }
    }
}