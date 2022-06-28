package com.stevecampos.domain.entity

data class Car(val plate: String) : Vehicle {
    override fun hourCost() = 1000.0

    override fun dailyCost() = 8000.0

    override fun plate() = plate
}