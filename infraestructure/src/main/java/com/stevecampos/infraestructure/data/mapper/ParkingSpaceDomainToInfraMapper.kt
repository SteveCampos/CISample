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
            carEntity = getCar(origin),
            motorcycleEntity = getMotorcycle(origin),
            startTimeStamp = origin.startTimestamp
        )
    }

    private fun isCar(origin: ParkingSpace) = origin.vehicle is Car
    private fun isMotorcycle(origin: ParkingSpace) = origin.vehicle is Motorcycle

    private fun getCar(origin: ParkingSpace): CarEntity? {
        if (isCar(origin)) {
            return CarEntity(origin.vehicle.plate())
        }
        return null
    }

    private fun getMotorcycle(origin: ParkingSpace): MotorcycleEntity? {
        if (isMotorcycle(origin)) {
            return MotorcycleEntity(
                origin.vehicle.plate(),
                (origin.vehicle as Motorcycle).cylinderCapacity
            )
        }
        return null
    }
}