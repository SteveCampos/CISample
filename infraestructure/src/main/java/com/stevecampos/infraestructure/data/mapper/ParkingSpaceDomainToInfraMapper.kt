package com.stevecampos.infraestructure.data.mapper

import com.stevecampos.domain.aggregate.ParkingSpace
import com.stevecampos.domain.entity.Car
import com.stevecampos.domain.entity.Motorcycle
import com.stevecampos.domain.exception.VehicleNotSupportedException
import com.stevecampos.infraestructure.data.entity.CarEntity
import com.stevecampos.infraestructure.data.entity.MotorcycleEntity
import com.stevecampos.infraestructure.data.entity.ParkingSpaceEntity

class ParkingSpaceDomainToInfraMapper : Mapper<ParkingSpace, ParkingSpaceEntity> {
    override fun map(origin: ParkingSpace): ParkingSpaceEntity {

        val vehicleEntity = when (val vehicle = origin.vehicle) {
            is Motorcycle -> MotorcycleEntity(
                vehicle.plate(),
                vehicle.cylinderCapacity
            )
            is Car -> CarEntity(vehicle.plate())
            else -> throw VehicleNotSupportedException()
        }

        return ParkingSpaceEntity(
            vehiclePlate = origin.vehicle.plate(),
            vehicleEntity = vehicleEntity,
            startTimeStamp = origin.startTimestamp
        )
    }
}