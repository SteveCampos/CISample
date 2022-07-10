package com.stevecampos.infraestructure.register.anticorrupt

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.infraestructure.register.entity.CarRegisterSpaceEntity
import com.stevecampos.infraestructure.register.entity.ParkingSpaceEntity
import com.stevecampos.infraestructure.register.entity.asDomain
import com.stevecampos.infraestructure.register.entity.toExternal
import com.stevecampos.infraestructure.vehicle.entity.CarEntity

class CarRegisterSpaceTranslator {

    fun translateToInfrastructure(carRegisterSpace: RegisteredSpace<Car>): CarRegisterSpaceEntity {
        return CarRegisterSpaceEntity(
            id = carRegisterSpace.id,
            car = CarEntity(carRegisterSpace.vehicle.plate),
            parkingSpaceEntity = ParkingSpaceEntity(carRegisterSpace.parkingSpace.id),
            startDate = carRegisterSpace.startDate,
            endDate = carRegisterSpace.endDate,
            state = carRegisterSpace.state.toExternal()
        )
    }

    fun translateToDomain(carRegisterSpace: CarRegisterSpaceEntity): RegisteredSpace<Car> {
        return RegisteredSpace<Car>(
            id = carRegisterSpace.id,
            vehicle = Car(carRegisterSpace.car.plate),
            parkingSpace = ParkingSpace(carRegisterSpace.parkingSpaceEntity.id),
            startDate = carRegisterSpace.startDate,
            endDate = carRegisterSpace.endDate,
            state = carRegisterSpace.state.asDomain()
        )
    }

}