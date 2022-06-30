package com.stevecampos.domain.register.repository

import com.stevecampos.domain.register.entity.ParkingSpace

interface ParkingSpaceRepository {
    suspend fun getAll(): List<ParkingSpace>
    suspend fun findById(id: Int): ParkingSpace?
}