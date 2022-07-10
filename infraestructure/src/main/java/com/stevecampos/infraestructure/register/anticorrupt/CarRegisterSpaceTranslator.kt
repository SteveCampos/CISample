package com.stevecampos.infraestructure.register.anticorrupt

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.infraestructure.register.entity.CarRegisterSpaceEntity
import com.stevecampos.infraestructure.register.entity.ParkingSpaceEntity
import com.stevecampos.infraestructure.vehicle.entity.CarEntity

class CarRegisterSpaceTranslator {

    fun translateToInfrastructure(carRegisterSpace: RegisteredSpace<Car>): CarRegisterSpaceEntity {
        val registerStateTranslator = RegisterStateTranslator()
        return CarRegisterSpaceEntity(
            id = carRegisterSpace.id,
            car = CarEntity(carRegisterSpace.vehicle.plate),
            parkingSpaceEntity = ParkingSpaceEntity(carRegisterSpace.parkingSpace.id),
            startDate = carRegisterSpace.startDate,
            endDate = carRegisterSpace.endDate,
            state = registerStateTranslator.translateToInfrastructure(carRegisterSpace.state)
        )
    }

    fun translateToDomain(carRegisterSpace: CarRegisterSpaceEntity): RegisteredSpace<Car> {
        val registerStateTranslator = RegisterStateTranslator()
        return RegisteredSpace<Car>(
            id = carRegisterSpace.id,
            vehicle = Car(carRegisterSpace.car.plate),
            parkingSpace = ParkingSpace(carRegisterSpace.parkingSpaceEntity.id),
            startDate = carRegisterSpace.startDate,
            endDate = carRegisterSpace.endDate,
            state = registerStateTranslator.translateToDomain(carRegisterSpace.state)
        )
    }

}