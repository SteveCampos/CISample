package com.stevecampos.infraestructure.register.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.stevecampos.infraestructure.vehicle.entity.CarEntity
import java.util.*

@Entity(
    tableName = "car_register_space",
    indices = [Index(value = ["plate", "parking_space_id"], unique = true)]
)
data class CarRegisterSpaceEntity(
    @PrimaryKey
    val id: String,
    @Embedded
    val car: CarEntity,
    @Embedded
    val parkingSpaceEntity: ParkingSpaceEntity,
    val startDate: Date,
    val endDate: Date?,
    val state: RegisterStateEntity
)