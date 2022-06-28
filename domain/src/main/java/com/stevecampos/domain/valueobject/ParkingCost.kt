package com.stevecampos.domain.valueobject

interface ParkingCost {
    fun hourCost(): Double
    fun dailyCost(): Double
}