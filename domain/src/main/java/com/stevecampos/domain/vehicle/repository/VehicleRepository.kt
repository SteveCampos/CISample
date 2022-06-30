package com.stevecampos.domain.vehicle.repository

import com.stevecampos.domain.vehicle.entity.Vehicle

interface VehicleRepository<T : Vehicle> {
    suspend fun getAll(): List<T>
    suspend fun findByPlate(plate: String): T?
}