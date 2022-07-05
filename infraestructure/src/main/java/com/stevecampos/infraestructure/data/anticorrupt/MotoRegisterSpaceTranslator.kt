package com.stevecampos.infraestructure.data.anticorrupt

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.vehicle.entity.Motorcycle
import com.stevecampos.infraestructure.data.entity.*

class MotoRegisterSpaceTranslator :
    Translator<RegisteredSpace<Motorcycle>, MotoRegisterSpaceEntity> {
    override fun map(origin: RegisteredSpace<Motorcycle>): MotoRegisterSpaceEntity {
        return MotoRegisterSpaceEntity(
            id = origin.id,
            moto = MotorcycleEntity(origin.vehicle.plate, origin.vehicle.cylinderCapacity),
            parkingSpaceEntity = ParkingSpaceEntity(origin.parkingSpace.id),
            startDate = origin.startDate,
            endDate = origin.endDate,
            state = origin.state.toExternal()
        )
    }

}