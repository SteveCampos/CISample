package com.stevecampos.infraestructure.vehicle.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moto")
data class MotorcycleEntity(@PrimaryKey val plate: String, val cylinderCapacity: Int) : VehicleEntity(plate)