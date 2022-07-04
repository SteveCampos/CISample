package com.stevecampos.infraestructure.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parking_space")
data class ParkingSpaceEntity(
    @PrimaryKey
    @ColumnInfo(name = "parking_space_id")
    val id: Int
)