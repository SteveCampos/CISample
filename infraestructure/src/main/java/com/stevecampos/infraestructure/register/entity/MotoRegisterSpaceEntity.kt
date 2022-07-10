package com.stevecampos.infraestructure.register.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stevecampos.infraestructure.vehicle.entity.MotorcycleEntity
import java.util.*

@Entity(tableName = "moto_register_space")
data class MotoRegisterSpaceEntity(
    @PrimaryKey
    val id: String,
    @Embedded
    val moto: MotorcycleEntity,
    @Embedded
    val parkingSpaceEntity: ParkingSpaceEntity,
    val startDate: Date,
    val endDate: Date?,
    val state: RegisterStateEntity
)