package com.stevecampos.infraestructure.data.dao

import androidx.room.*
import com.stevecampos.infraestructure.data.entity.ParkingSpaceEntity

@Dao
interface ParkingSpaceDao {
    @Query("SELECT * FROM ParkingSpaceEntity")
    suspend fun getParkingSpaceItems(): List<ParkingSpaceEntity>

    @Query("SELECT * FROM ParkingSpaceEntity WHERE vehicle_plate LIKE :vehiclePlate LIMIT 1")
    fun findByPlate(vehiclePlate: String): ParkingSpaceEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(parkingSpace: ParkingSpaceEntity)

    @Query("DELETE FROM ParkingSpaceEntity WHERE vehicle_plate = :vehiclePlate")
    suspend fun delete(vehiclePlate: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(parkingSpace: List<ParkingSpaceEntity>)
}