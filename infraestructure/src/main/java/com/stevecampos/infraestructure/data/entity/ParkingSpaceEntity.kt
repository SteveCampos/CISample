package com.stevecampos.infraestructure.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ParkingSpaceEntity")
data class ParkingSpaceEntity(
    @PrimaryKey @ColumnInfo(name = "vehicle_plate") val vehiclePlate: String,
    //@Embedded val vehicleEntity: VehicleEntity,
    @Embedded val carEntity: CarEntity?,
    @Embedded val motorcycleEntity: MotorcycleEntity?,
    val startTimeStamp: Long
)