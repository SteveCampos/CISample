package com.stevecampos.infraestructure.register.repository

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.repository.MotorcycleRegisterRepository
import com.stevecampos.domain.vehicle.entity.Motorcycle
import com.stevecampos.infraestructure.register.anticorrupt.MotoRegisterSpaceTranslator
import com.stevecampos.infraestructure.register.anticorrupt.RegisterStateTranslator
import com.stevecampos.infraestructure.register.dao.MotoRegisterSpaceDao
import com.stevecampos.infraestructure.register.entity.RegisterStateEntity
import com.stevecampos.infraestructure.register.exception.RegisterSpaceNotSavedException

class MotorcycleRegisterRoom(private val motoRegisterSpaceDao: MotoRegisterSpaceDao) :
    MotorcycleRegisterRepository {
    override suspend fun register(registeredSpace: RegisteredSpace<Motorcycle>) {
        val motoRegisterSpaceTranslator = MotoRegisterSpaceTranslator()
        val motoRegisterSpaceEntity =
            motoRegisterSpaceTranslator.translateToInfrastructure(registeredSpace)

        val space = motoRegisterSpaceDao.getRegisterSpaceForSpaceWithState(
            registeredSpace.parkingSpace.id,
            RegisterStateEntity.LOCKED
        )
        if (space != null)
            throw IllegalStateException()
        val vehicle = motoRegisterSpaceDao.getRegisterSpaceForVehicleWithState(
            registeredSpace.vehicle.plate,
            RegisterStateEntity.LOCKED
        )
        if (vehicle != null)
            throw IllegalStateException()

        motoRegisterSpaceDao.saveMotoRegisterSpace(motoRegisterSpaceEntity)
    }

    override suspend fun getRegisteredSpaces(state: RegisteredState): List<RegisteredSpace<Motorcycle>> {
        val motorcycleRegisterSpaceTranslator = MotoRegisterSpaceTranslator()
        val registerStateTranslator = RegisterStateTranslator()
        val stateEntity = registerStateTranslator.translateToInfrastructure(state)
        return motoRegisterSpaceDao.getAllMotoRegisterSpacesWithState(stateEntity)
            .map { motorcycleRegisterSpaceTranslator.translateToDomain(it) }
    }

    override suspend fun getActiveRegisterSpaceForSpace(parkingSpace: ParkingSpace): RegisteredSpace<Motorcycle>? {
        val motoRegisterSpaceTranslator = MotoRegisterSpaceTranslator()
        val motoRegisterSpace = motoRegisterSpaceDao.getRegisterSpaceForSpaceWithState(
            parkingSpace.id,
            state = RegisterStateEntity.LOCKED
        ) ?: return null
        return motoRegisterSpaceTranslator.translateToDomain(motoRegisterSpace)
    }

    override suspend fun getActiveRegisterSpaceForVehicle(vehicle: Motorcycle): RegisteredSpace<Motorcycle>? {
        val motoRegisterSpaceTranslator = MotoRegisterSpaceTranslator()
        val motoRegisterSpace = motoRegisterSpaceDao.getRegisterSpaceForVehicleWithState(
            state = RegisterStateEntity.LOCKED,
            plate = vehicle.plate
        ) ?: return null
        return motoRegisterSpaceTranslator.translateToDomain(motoRegisterSpace)
    }

    override suspend fun finishRegisterSpaced(registeredSpace: RegisteredSpace<Motorcycle>) {
        val rowsAffected =
            motoRegisterSpaceDao.finishRegisterSpace(registerSpaceId = registeredSpace.id)
        if (rowsAffected != 1) {
            throw RegisterSpaceNotSavedException()
        }
    }
}