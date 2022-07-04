package com.stevecampos.infraestructure.data.repository

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.repository.CarRegisterRepository
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.infraestructure.data.anticorrupt.CarRegisterSpaceTranslator
import com.stevecampos.infraestructure.data.dao.CarRegisterSpaceDao
import com.stevecampos.infraestructure.data.entity.asDomain
import com.stevecampos.infraestructure.data.entity.toExternal

class CarRegisterSpaceRoom(private val carRegisterSpaceDao: CarRegisterSpaceDao) :
    CarRegisterRepository {
    override suspend fun register(registeredSpace: RegisteredSpace<Car>) {
        val carRegisterSpaceTranslator = CarRegisterSpaceTranslator()
        val carRegisterSpaceEntity = carRegisterSpaceTranslator.map(registeredSpace)
        carRegisterSpaceDao.saveCarRegisterSpace(carRegisterSpaceEntity)
    }

    override suspend fun getRegisteredSpaces(state: RegisteredState): List<RegisteredSpace<Car>> {
        val stateEntity = state.toExternal()
        return carRegisterSpaceDao.getAllCarRegisterSpacesWithState(stateEntity)
            .map { it.asDomain() }
    }
}