package com.stevecampos.infraestructure

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stevecampos.infraestructure.data.dao.CarRegisterSpaceDao
import com.stevecampos.infraestructure.data.db.ParkingDatabase
import com.stevecampos.infraestructure.data.entity.CarEntity
import com.stevecampos.infraestructure.data.entity.CarRegisterSpaceEntity
import com.stevecampos.infraestructure.data.entity.ParkingSpaceEntity
import com.stevecampos.infraestructure.data.entity.RegisterStateEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class CarRegisterSpaceRoomTest {

    private lateinit var db: ParkingDatabase
    private lateinit var carRegisterSpaceDao: CarRegisterSpaceDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ParkingDatabase::class.java
        ).build()
        carRegisterSpaceDao = db.carRegisterSpaceDao
    }
    @After
    fun close(){
        db.close()
    }

    @Test
    fun register_whenRegisterCar_shouldRegisteredCarsCountsBeOne() = runTest {

        //Act

        val carRegisterSpace = CarRegisterSpaceEntity(
            id = UUID.randomUUID().toString(),
            car = CarEntity("AAA000"),
            parkingSpaceEntity = ParkingSpaceEntity(1),
            startDate = Date(1656945638133),
            endDate = null,
            state = RegisterStateEntity.LOCKED
        )
        //Arrange
        carRegisterSpaceDao.saveCarRegisterSpace(carRegisterSpace)
        //Assert

        val items = carRegisterSpaceDao.getAllCarRegisterSpacesWithState(RegisterStateEntity.LOCKED)
        Assert.assertEquals(items.size, 1)
    }

    @Test
    fun getRegisteredSpaces_whenNoItemsRegistered_shouldGetRegisteredSpacesBeEmtpy() = runTest {

        //Act

        //Arrange
        val items = carRegisterSpaceDao.getAllCarRegisterSpacesWithState(RegisterStateEntity.LOCKED)
        //Assert

        Assert.assertEquals(items.size, 0)
    }
}