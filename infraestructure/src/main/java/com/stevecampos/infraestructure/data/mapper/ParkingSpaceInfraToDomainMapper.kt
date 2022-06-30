/*package com.stevecampos.infraestructure.data.mapper

import com.stevecampos.domain.aggregate.ParkingSpace
import com.stevecampos.domain.entity.Car
import com.stevecampos.domain.entity.Motorcycle
import com.stevecampos.domain.exception.VehicleNotSupportedException
import com.stevecampos.infraestructure.data.entity.ParkingSpaceEntity

class ParkingSpaceInfraToDomainMapper : Mapper<ParkingSpaceEntity, ParkingSpace> {
    override fun map(origin: ParkingSpaceEntity): ParkingSpace {

        val vehicleEntity = if (origin.carEntity != null) {
            Car(origin.carEntity.plate)
        } else if (origin.motorcycleEntity != null) {
            Motorcycle(
                origin.motorcycleEntity.plate,
                origin.motorcycleEntity.cylinderCapacity
            )
        } else {
            throw VehicleNotSupportedException()
        }

        return ParkingSpace(
            vehicle = vehicleEntity,
            startTimestamp = origin.startTimeStamp
        )
    }

}
*/