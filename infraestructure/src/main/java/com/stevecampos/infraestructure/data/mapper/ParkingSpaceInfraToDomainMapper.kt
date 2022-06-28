package com.stevecampos.infraestructure.data.mapper

import com.stevecampos.domain.aggregate.ParkingSpace
import com.stevecampos.domain.entity.Car
import com.stevecampos.domain.entity.Motorcycle
import com.stevecampos.domain.exception.VehicleNotSupportedException
import com.stevecampos.infraestructure.data.entity.CarEntity
import com.stevecampos.infraestructure.data.entity.MotorcycleEntity
import com.stevecampos.infraestructure.data.entity.ParkingSpaceEntity

class ParkingSpaceInfraToDomainMapper : Mapper<ParkingSpaceEntity, ParkingSpace> {
    override fun map(origin: ParkingSpaceEntity): ParkingSpace {
        val vehicleEntity = when (val vehicle = origin.vehicleEntity) {
            is MotorcycleEntity -> Motorcycle(
                vehicle.plate,
                vehicle.cylinderCapacity
            )
            is CarEntity -> Car(vehicle.plate)
            else -> throw VehicleNotSupportedException()
        }

        return ParkingSpace(
            vehicle = vehicleEntity,
            startTimestamp = origin.startTimeStamp
        )
    }

}
