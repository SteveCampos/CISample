package com.stevecampos.infraestructure.data.dao

import androidx.room.*
import com.stevecampos.infraestructure.data.entity.CarEntity

@Dao
interface CarEntityDao {
    @Query("SELECT * FROM CarEntity")
    suspend fun getCarItems(): List<CarEntity>

    @Query("SELECT * FROM CarEntity WHERE plate LIKE :vehiclePlate LIMIT 1")
    fun findByPlate(vehiclePlate: String): CarEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(Car: CarEntity): Boolean

    @Query("DELETE FROM CarEntity WHERE plate = :vehiclePlate")
    suspend fun delete(vehiclePlate: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(Car: List<CarEntity>): Boolean
}