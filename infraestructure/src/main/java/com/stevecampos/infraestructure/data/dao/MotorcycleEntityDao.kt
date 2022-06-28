package com.stevecampos.infraestructure.data.dao

import androidx.room.*
import com.stevecampos.infraestructure.data.entity.MotorcycleEntity

@Dao
interface MotorcycleEntityDao {
    @Query("SELECT * FROM MotorcycleEntity")
    suspend fun getMotorcycleItems(): List<MotorcycleEntity>

    @Query("SELECT * FROM MotorcycleEntity WHERE motorcycle_plate LIKE :vehiclePlate LIMIT 1")
    fun findByPlate(vehiclePlate: String): MotorcycleEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(Motorcycle: MotorcycleEntity)

    @Query("DELETE FROM MotorcycleEntity WHERE motorcycle_plate = :vehiclePlate")
    suspend fun delete(vehiclePlate: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(Motorcycle: List<MotorcycleEntity>)
}