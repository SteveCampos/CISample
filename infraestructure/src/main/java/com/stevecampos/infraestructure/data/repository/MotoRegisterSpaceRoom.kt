package com.stevecampos.infraestructure.data.repository

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.repository.MotorcycleRegisterRepository
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.domain.vehicle.entity.Motorcycle
import com.stevecampos.infraestructure.data.anticorrupt.MotoRegisterSpaceTranslator
import com.stevecampos.infraestructure.data.dao.MotoRegisterSpaceDao
import com.stevecampos.infraestructure.data.entity.RegisterStateEntity
import com.stevecampos.infraestructure.data.entity.asDomain
import com.stevecampos.infraestructure.data.entity.toExternal
import com.stevecampos.infraestructure.data.exception.RegisterSpaceNotSavedException

class MotoRegisterSpaceRoom(private val motoRegisterSpaceDao: MotoRegisterSpaceDao) :
    MotorcycleRegisterRepository {
    override suspend fun register(registeredSpace: RegisteredSpace<Motorcycle>) {
        val motoRegisterSpaceTranslator = MotoRegisterSpaceTranslator()
        val motoRegisterSpaceEntity = motoRegisterSpaceTranslator.map(registeredSpace)
        val rowsAffected = motoRegisterSpaceDao.saveMotoRegisterSpace(motoRegisterSpaceEntity)
        if (rowsAffected.toInt() != 1) {
            throw RegisterSpaceNotSavedException()
        }
    }

    override suspend fun getRegisteredSpaces(state: RegisteredState): List<RegisteredSpace<Motorcycle>> {
        val stateEntity = state.toExternal()
        return motoRegisterSpaceDao.getAllMotoRegisterSpacesWithState(stateEntity)
            .map { it.asDomain() }
    }

    override suspend fun getActiveRegisterSpaceForSpace(parkingSpace: ParkingSpace): RegisteredSpace<Motorcycle>? {
        return motoRegisterSpaceDao.getRegisterSpaceForSpaceWithState(
            parkingSpace.id,
            state = RegisterStateEntity.LOCKED
        )?.asDomain()
    }

    override suspend fun getActiveRegisterSpaceForVehicle(vehicle: Motorcycle): RegisteredSpace<Motorcycle>? {
        return motoRegisterSpaceDao.getRegisterSpaceForVehicleWithState(
            state = RegisterStateEntity.LOCKED,
            plate = vehicle.plate
        )?.asDomain()
    }

    override suspend fun finishRegisterSpaced(registeredSpace: RegisteredSpace<Motorcycle>) {
        val rowsAffected =
            motoRegisterSpaceDao.finishRegisterSpace(registerSpaceId = registeredSpace.id)
        if (rowsAffected != 1) {
            throw RegisterSpaceNotSavedException()
        }
    }
}