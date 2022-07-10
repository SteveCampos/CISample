package com.stevecampos.infraestructure.register.anticorrupt

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Motorcycle
import com.stevecampos.infraestructure.register.entity.MotoRegisterSpaceEntity
import com.stevecampos.infraestructure.register.entity.ParkingSpaceEntity
import com.stevecampos.infraestructure.vehicle.entity.MotorcycleEntity

class MotoRegisterSpaceTranslator {

    fun translateToInfrastructure(origin: RegisteredSpace<Motorcycle>): MotoRegisterSpaceEntity {
        val registerStateTranslator = RegisterStateTranslator()
        return MotoRegisterSpaceEntity(
            id = origin.id,
            moto = MotorcycleEntity(origin.vehicle.plate, origin.vehicle.cylinderCapacity),
            parkingSpaceEntity = ParkingSpaceEntity(origin.parkingSpace.id),
            startDate = origin.startDate,
            endDate = origin.endDate,
            state = registerStateTranslator.translateToInfrastructure(origin.state)
        )
    }

    fun translateToDomain(origin: MotoRegisterSpaceEntity): RegisteredSpace<Motorcycle> {
        val registerStateTranslator = RegisterStateTranslator()
        return RegisteredSpace<Motorcycle>(
            id = origin.id,
            vehicle = Motorcycle(origin.moto.plate, origin.moto.cylinderCapacity),
            parkingSpace = ParkingSpace(origin.parkingSpaceEntity.id),
            startDate = origin.startDate,
            endDate = origin.endDate,
            state = registerStateTranslator.translateToDomain(origin.state)
        )
    }

}