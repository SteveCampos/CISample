package com.stevecampos.infraestructure

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stevecampos.infraestructure.data.dao.MotoRegisterSpaceDao
import com.stevecampos.infraestructure.data.db.ParkingDatabase
import com.stevecampos.infraestructure.data.entity.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class MotorcycleRegisterSpaceRoomTest {

    private lateinit var db: ParkingDatabase
    private lateinit var motoRegisterSpaceDao: MotoRegisterSpaceDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ParkingDatabase::class.java
        ).build()
        motoRegisterSpaceDao = db.motoRegisterSpaceDao
    }
    @After
    fun close(){
        db.close()
    }

    @Test
    fun register_whenRegisterMoto_shouldRegisteredMotosCountsBeOne() = runTest {

        //Act

        val motoRegisterSpace = MotoRegisterSpaceEntity(
            id = UUID.randomUUID().toString(),
            moto = MotorcycleEntity("AAA000", 500),
            parkingSpaceEntity = ParkingSpaceEntity(1),
            startDate = Date(1656945638133),
            endDate = null,
            state = RegisterStateEntity.LOCKED
        )
        //Arrange
        motoRegisterSpaceDao.saveMotoRegisterSpace(motoRegisterSpace)
        //Assert

        val items = motoRegisterSpaceDao.getAllMotoRegisterSpacesWithState(RegisterStateEntity.LOCKED)
        Assert.assertEquals(items.size, 1)
    }

    @Test
    fun getRegisteredSpaces_whenNoItemsRegistered_shouldGetRegisteredSpacesBeEmtpy() = runTest {

        //Act

        //Arrange
        val items = motoRegisterSpaceDao.getAllMotoRegisterSpacesWithState(RegisterStateEntity.LOCKED)
        //Assert

        Assert.assertEquals(items.size, 0)
    }
}