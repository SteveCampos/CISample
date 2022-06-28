package com.stevecampos.infraestructure.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stevecampos.infraestructure.data.dao.CarEntityDao
import com.stevecampos.infraestructure.data.dao.MotorcycleEntityDao
import com.stevecampos.infraestructure.data.dao.ParkingSpaceDao
import kotlinx.coroutines.CoroutineScope

@Database(entities = [ParkingSpaceDao::class], version = 1, exportSchema = false)
abstract class ParkingDb : RoomDatabase() {
    abstract fun parkingSpaceDao(): ParkingSpaceDao
    abstract fun motorcycleEntityDao(): MotorcycleEntityDao
    abstract fun carEntityDao(): CarEntityDao

    companion object {

        @Volatile
        private var INSTANCE: ParkingDb? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ParkingDb {
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