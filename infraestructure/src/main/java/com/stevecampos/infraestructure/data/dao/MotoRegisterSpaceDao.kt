package com.stevecampos.infraestructure.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stevecampos.infraestructure.data.entity.CarRegisterSpaceEntity
import com.stevecampos.infraestructure.data.entity.MotoRegisterSpaceEntity
import com.stevecampos.infraestructure.data.entity.RegisterStateEntity

@Dao
interface MotoRegisterSpaceDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveMotoRegisterSpace(motoRegisterSpaceEntity: MotoRegisterSpaceEntity): Long

    @Query("SELECT * FROM moto_register_space " +
            "WHERE state = :state")
    suspend fun getAllMotoRegisterSpacesWithState(state: RegisterStateEntity): List<MotoRegisterSpaceEntity>



    @Query(
        "SELECT * FROM moto_register_space " +
                "WHERE state =:state " +
                "AND parking_space_id =:parkingSpaceId"
    )
    suspend fun getRegisterSpaceForSpaceWithState(
        parkingSpaceId: Int,
        state: RegisterStateEntity
    ): MotoRegisterSpaceEntity?

    @Query(
        "SELECT * FROM moto_register_space " +
                "WHERE state =:state " +
                "AND plate =:plate"
    )
    suspend fun getRegisterSpaceForVehicleWithState(
        plate: String,
        state: RegisterStateEntity
    ): MotoRegisterSpaceEntity?

    @Query(
        "UPDATE  moto_register_space SET " +
                "state = 'FINISHED' " +
                "WHERE id =:registerSpaceId"
    )
    suspend fun finishRegisterSpace(registerSpaceId: String): Int

}