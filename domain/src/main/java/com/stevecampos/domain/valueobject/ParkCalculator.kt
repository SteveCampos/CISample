package com.stevecampos.domain.valueobject

import com.stevecampos.domain.entity.Vehicle

interface ParkCalculator {
    fun calculateParkingCost(vehicle: Vehicle, days: Int, hours: Int): Double
}
