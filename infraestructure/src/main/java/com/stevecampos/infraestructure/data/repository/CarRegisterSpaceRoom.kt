package com.stevecampos.infraestructure.data.repository

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.repository.CarRegisterRepository
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.infraestructure.data.anticorrupt.CarRegisterSpaceTranslator
import com.stevecampos.infraestructure.data.dao.CarRegisterSpaceDao
import com.stevecampos.infraestructure.data.entity.RegisterStateEntity
import com.stevecampos.infraestructure.data.entity.asDomain
import com.stevecampos.infraestructure.data.entity.toExternal
import com.stevecampos.infraestructure.data.exception.RegisterSpaceNotFinishedException
import com.stevecampos.infraestructure.data.exception.RegisterSpaceNotSavedException

class CarRegisterSpaceRoom(private val carRegisterSpaceDao: CarRegisterSpaceDao) :
    CarRegisterRepository {
    override suspend fun register(registeredSpace: RegisteredSpace<Car>) {
        val carRegisterSpaceTranslator = CarRegisterSpaceTranslator()
        val carRegisterSpaceEntity = carRegisterSpaceTranslator.map(registeredSpace)
        val rowsAffected = carRegisterSpaceDao.saveCarRegisterSpace(carRegisterSpaceEntity)
        if (rowsAffected.toInt() != 1){
            throw RegisterSpaceNotSavedException()
        }
    }

    override suspend fun getRegisteredSpaces(state: RegisteredState): List<RegisteredSpace<Car>> {
        val stateEntity = state.toExternal()
        return carRegisterSpaceDao.getAllCarRegisterSpacesWithState(stateEntity)
            .map { it.asDomain() }
    }

    override suspend fun getActiveRegisterSpaceForSpace(parkingSpace: ParkingSpace): RegisteredSpace<Car>? {
        return carRegisterSpaceDao.getRegisterSpaceForSpaceWithState(
            parkingSpace.id,
            state = RegisterStateEntity.LOCKED
        )?.asDomain()
    }

    override suspend fun getActiveRegisterSpaceForVehicle(vehicle: Car): RegisteredSpace<Car>? {
        return carRegisterSpaceDao.getRegisterSpaceForVehicleWithState(
            state = RegisterStateEntity.LOCKED,
            plate = vehicle.plate
        )?.asDomain()
    }

    override suspend fun finishRegisterSpaced(registeredSpace: RegisteredSpace<Car>) {
        val rowsAffected = carRegisterSpaceDao.finishRegisterSpace(registerSpaceId = registeredSpace.id)
        if (rowsAffected != 1){
            throw RegisterSpaceNotFinishedException()
        }
    }
}