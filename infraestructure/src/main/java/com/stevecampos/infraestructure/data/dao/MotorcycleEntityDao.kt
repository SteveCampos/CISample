package com.stevecampos.infraestructure.data.dao

import androidx.room.*
import com.stevecampos.infraestructure.data.entity.MotorcycleEntity

@Dao
interface MotorcycleEntityDao {
    @Query("SELECT * FROM MotorcycleEntity")
    suspend fun getMotorcycleItems(): List<MotorcycleEntity>

    @Query("SELECT * FROM MotorcycleEntity WHERE plate LIKE :vehiclePlate LIMIT 1")
    fun findByPlate(vehiclePlate: String): MotorcycleEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(Motorcycle: MotorcycleEntity): Boolean

    @Query("DELETE FROM MotorcycleEntity WHERE plate = :vehiclePlate")
    suspend fun delete(vehiclePlate: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(Motorcycle: List<MotorcycleEntity>): Boolean
}