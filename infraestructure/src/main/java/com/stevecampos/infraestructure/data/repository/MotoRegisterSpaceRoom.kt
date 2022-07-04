package com.stevecampos.infraestructure.data.repository

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.repository.MotorcycleRegisterRepository
import com.stevecampos.domain.vehicle.entity.Motorcycle
import com.stevecampos.infraestructure.data.anticorrupt.MotoRegisterSpaceTranslator
import com.stevecampos.infraestructure.data.dao.MotoRegisterSpaceDao
import com.stevecampos.infraestructure.data.entity.asDomain
import com.stevecampos.infraestructure.data.entity.toExternal

class MotoRegisterSpaceRoom(private val motoRegisterSpaceDao: MotoRegisterSpaceDao) :
    MotorcycleRegisterRepository {
    override suspend fun register(registeredSpace: RegisteredSpace<Motorcycle>) {
        val motoRegisterSpaceTranslator = MotoRegisterSpaceTranslator()
        val motoRegisterSpaceEntity = motoRegisterSpaceTranslator.map(registeredSpace)
        motoRegisterSpaceDao.saveMotoRegisterSpace(motoRegisterSpaceEntity)
    }

    override suspend fun getRegisteredSpaces(state: RegisteredState): List<RegisteredSpace<Motorcycle>> {
        val stateEntity = state.toExternal()
        return motoRegisterSpaceDao.getAllMotoRegisterSpacesWithState(stateEntity)
            .map { it.asDomain() }
    }
}