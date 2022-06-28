package com.stevecampos.infraestructure.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "MotorcycleEntity")
data class MotorcycleEntity(
    @PrimaryKey @ColumnInfo(name = "motorcycle_plate") val plate: String,
    val cylinderCapacity: Int
) :
    VehicleEntity {
    @Ignore
    override fun plate() = plate
}