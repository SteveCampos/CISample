package com.stevecampos.infraestructure.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CarEntity")
data class CarEntity(@PrimaryKey val plate: String) : VehicleEntity {
    override fun plate() = plate
}