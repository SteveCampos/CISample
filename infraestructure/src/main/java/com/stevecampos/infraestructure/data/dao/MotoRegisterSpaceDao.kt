package com.stevecampos.infraestructure.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stevecampos.infraestructure.data.entity.MotoRegisterSpaceEntity
import com.stevecampos.infraestructure.data.entity.RegisterStateEntity

@Dao
interface MotoRegisterSpaceDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveMotoRegisterSpace(motoRegisterSpaceEntity: MotoRegisterSpaceEntity)

    @Query("SELECT * FROM moto_register_space " +
            "WHERE state = :state")
    suspend fun getAllMotoRegisterSpacesWithState(state: RegisterStateEntity): List<MotoRegisterSpaceEntity>

}