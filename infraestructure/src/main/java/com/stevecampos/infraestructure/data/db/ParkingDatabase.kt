package com.stevecampos.infraestructure.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stevecampos.infraestructure.data.dao.CarRegisterSpaceDao
import com.stevecampos.infraestructure.data.dao.MotoRegisterSpaceDao
import com.stevecampos.infraestructure.data.entity.*

@Database(
    entities = [
        MotoRegisterSpaceEntity::class, CarRegisterSpaceEntity::class, CarEntity::class, MotorcycleEntity::class, ParkingSpaceEntity::class
    ], version = 1, exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class ParkingDatabase : RoomDatabase() {
    abstract val carRegisterSpaceDao: CarRegisterSpaceDao
    abstract val motoRegisterSpaceDao: MotoRegisterSpaceDao
}