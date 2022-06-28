package com.stevecampos.infraestructure.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "CarEntity")
data class CarEntity(@PrimaryKey @ColumnInfo(name = "car_plate") val plate: String) : VehicleEntity {
    @Ignore
    override fun plate() = plate
}