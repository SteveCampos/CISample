package com.stevecampos.infraestructure.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stevecampos.infraestructure.data.entity.CarRegisterSpaceEntity
import com.stevecampos.infraestructure.data.entity.RegisterStateEntity

@Dao
interface CarRegisterSpaceDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveCarRegisterSpace(carRegisterSpaceEntity: CarRegisterSpaceEntity)

    @Query("SELECT * FROM car_register_space " +
            "WHERE state = :state")
    suspend fun getAllCarRegisterSpacesWithState(state: RegisterStateEntity): List<CarRegisterSpaceEntity>
}