package com.stevecampos.domain.entity

data class Motorcycle(val plate: String, val cylinderCapacity: Int) : Vehicle {
    override fun hourCost() = 500.0

    override fun dailyCost(): Double {
        val extraChargeForCc: Double = if (cylinderCapacity >= 500) 2000.0 else 0.0
        return 4000 + extraChargeForCc
    }

    override fun plate() = plate
}