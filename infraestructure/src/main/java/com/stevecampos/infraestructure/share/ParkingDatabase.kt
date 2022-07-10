package com.stevecampos.infraestructure.share

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stevecampos.infraestructure.register.dao.CarRegisterSpaceDao
import com.stevecampos.infraestructure.register.dao.MotoRegisterSpaceDao
import com.stevecampos.infraestructure.register.entity.CarRegisterSpaceEntity
import com.stevecampos.infraestructure.register.entity.MotoRegisterSpaceEntity
import com.stevecampos.infraestructure.register.entity.ParkingSpaceEntity
import com.stevecampos.infraestructure.vehicle.entity.CarEntity
import com.stevecampos.infraestructure.vehicle.entity.MotorcycleEntity

@Database(
    entities = [
        MotoRegisterSpaceEntity::class, CarRegisterSpaceEntity::class, CarEntity::class, MotorcycleEntity::class, ParkingSpaceEntity::class
    ], version = 1, exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class ParkingDatabase : RoomDatabase() {
    abstract val carRegisterSpaceDao: CarRegisterSpaceDao
    abstract val motoRegisterSpaceDao: MotoRegisterSpaceDao

    companion object{
        fun provideDatabase(context: Context): ParkingDatabase {
            return Room.databaseBuilder(
                context,
                ParkingDatabase::class.java,
                "ParkingDatabase"
            ).build()
        }
    }

}