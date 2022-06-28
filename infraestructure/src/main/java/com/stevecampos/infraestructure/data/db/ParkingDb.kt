package com.stevecampos.infraestructure.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stevecampos.infraestructure.data.dao.CarEntityDao
import com.stevecampos.infraestructure.data.dao.MotorcycleEntityDao
import com.stevecampos.infraestructure.data.dao.ParkingSpaceDao
import com.stevecampos.infraestructure.data.entity.CarEntity
import com.stevecampos.infraestructure.data.entity.MotorcycleEntity
import com.stevecampos.infraestructure.data.entity.ParkingSpaceEntity
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [
        CarEntity::class, MotorcycleEntity::class, ParkingSpaceEntity::class
    ], version = 1, exportSchema = false
)
abstract class ParkingDb : RoomDatabase() {
    abstract val parkingSpaceDao: ParkingSpaceDao
    abstract val motorcycleEntityDao: MotorcycleEntityDao
    abstract val carEntityDao: CarEntityDao

    companion object {

        @Volatile
        private var INSTANCE: ParkingDb? = null

        fun getDatabase(context: Context): ParkingDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ParkingDb::class.java,
                    "parking-database"
                )
                    .build()

                INSTANCE = instance

                // return instance
                instance
            }
        }
    }
}