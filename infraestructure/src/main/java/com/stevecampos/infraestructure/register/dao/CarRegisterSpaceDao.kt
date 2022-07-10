package com.stevecampos.infraestructure.register.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stevecampos.infraestructure.register.entity.CarRegisterSpaceEntity
import com.stevecampos.infraestructure.register.entity.RegisterStateEntity

@Dao
interface CarRegisterSpaceDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveCarRegisterSpace(carRegisterSpaceEntity: CarRegisterSpaceEntity): Long


    @Query(
        "SELECT * FROM car_register_space " +
                "WHERE state = :state"
    )
    suspend fun getAllCarRegisterSpacesWithState(state: RegisterStateEntity): List<CarRegisterSpaceEntity>


    @Query(
        "SELECT * FROM car_register_space " +
                "WHERE state =:state " +
                "AND parking_space_id =:parkingSpaceId"
    )
    suspend fun getRegisterSpaceForSpaceWithState(
        parkingSpaceId: Int,
        state: RegisterStateEntity
    ): CarRegisterSpaceEntity?

    @Query(
        "SELECT * FROM car_register_space " +
                "WHERE state =:state " +
                "AND plate =:plate"
    )
    suspend fun getRegisterSpaceForVehicleWithState(
        plate: String,
        state: RegisterStateEntity
    ): CarRegisterSpaceEntity?

    @Query(
        "UPDATE  car_register_space SET " +
                "state = 'FINISHED' " +
                "WHERE id =:registerSpaceId"
    )
    suspend fun finishRegisterSpace(registerSpaceId: String): Int
}