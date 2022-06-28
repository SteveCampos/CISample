package com.stevecampos.domain.service

import com.stevecampos.domain.aggregate.ParkingSpace
import com.stevecampos.domain.entity.Car
import com.stevecampos.domain.entity.Motorcycle
import com.stevecampos.domain.entity.Vehicle
import com.stevecampos.domain.exception.*
import com.stevecampos.domain.repository.ParkingRepository
import com.stevecampos.domain.valueobject.ParkCalculator

class ParkingService(
    private val carsParkingCapacity: Int = 20,
    private val motorcyclesParkingCapacity: Int = 10,
    private val parkingCalculator: ParkCalculator,
    private val parkingRepository: ParkingRepository
) {

    suspend fun addVehicleToParking(vehicle: Vehicle) {
        val isAvailableSpace: Boolean = when (vehicle) {
            is Motorcycle -> isMotorcycleAvailableSpace()
            is Car -> isCarAvailableSpace()
            else -> throw VehicleNotSupportedException()
        }

        if (!isAvailableSpace)
            throw NoAvailableSpaceException()

        val added = parkingRepository.saveParkSpace(
            ParkingSpace(
                vehicle,
                0
            )
        )
        if (!added)
            throw FailedToSaveVehicleException()
    }

    suspend fun getParkingSpaceForVehicle(vehicle: Vehicle): ParkingSpace {
        return parkingRepository.getParkSpaceForVehicle(vehicle)
            ?: throw VehicleNotFoundException()
    }

    suspend fun getParkingCostForVehicle(vehicle: Vehicle, days: Int, hours: Int): Double {
        val vehicleParkingInfo = parkingRepository.getParkSpaceForVehicle(vehicle)
            ?: throw VehicleNotFoundException()
        return parkingCalculator.calculateParkingCost(vehicleParkingInfo.vehicle, days, hours)
    }

    suspend fun getParkingSpaces() = parkingRepository.getParkingSpaces()

    suspend fun resetParkingSpace(vehicle: Vehicle) {
        val removed = parkingRepository.resetParkSpace(vehicle)
        if (!removed)
            throw FailedToRemoveException()
    }

    suspend fun saveParkingSpaces(spaces: List<ParkingSpace>) {
        val added = parkingRepository.saveParkingSpaces(spaces)
        if (!added)
            throw FailedToSaveVehicleException()
    }


    private suspend fun isMotorcycleAvailableSpace(): Boolean {
        return parkingRepository.countMotorcycles() > motorcyclesParkingCapacity
    }

    private suspend fun isCarAvailableSpace(): Boolean {
        return parkingRepository.countCars() > carsParkingCapacity
    }

}