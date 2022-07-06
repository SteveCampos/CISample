package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Vehicle

abstract class ParkingSpaceService<V : Vehicle>(val size: Int) {

    private val parkingSpaces = mutableListOf<ParkingSpace>()

    init {
        for (i in 0 until size) {
            parkingSpaces.add(ParkingSpace(i))
        }
    }

    fun getParkingSpaces() = parkingSpaces.toList()
}