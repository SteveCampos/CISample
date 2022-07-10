package com.stevecampos.infraestructure.vehicle.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car")
data class CarEntity(@PrimaryKey val plate: String) : VehicleEntity(plate)