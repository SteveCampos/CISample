package com.stevecampos.infraestructure.data.anticorrupt

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.infraestructure.data.entity.*

class CarRegisterSpaceTranslator : Translator<RegisteredSpace<Car>, CarRegisterSpaceEntity> {
    override fun map(origin: RegisteredSpace<Car>): CarRegisterSpaceEntity {
        return CarRegisterSpaceEntity(
            id = origin.id,
            car = CarEntity(origin.vehicle.plate),
            parkingSpaceEntity = ParkingSpaceEntity(origin.parkingSpace.id),
            startDate = origin.startDate,
            endDate = origin.endDate,
            state = origin.state.toExternal()
        )
    }

}