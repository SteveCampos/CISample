package com.stevecampos.domain.repository

import com.stevecampos.domain.aggregate.ParkingSpace
import com.stevecampos.domain.entity.Vehicle

interface ParkingRepository {

    suspend fun countCars(): Int
    suspend fun countMotorcycles(): Int

    suspend fun saveParkSpace(parkingSpace: ParkingSpace): Boolean
    suspend fun getParkSpaceForVehicle(vehicle: Vehicle): ParkingSpace?
    suspend fun resetParkSpace(vehicle: Vehicle): Boolean

    suspend fun saveParkingSpaces(spaces: List<ParkingSpace>): Boolean

    suspend fun getParkingSpaces(): List<ParkingSpace>


}
