package com.stevecampos.infraestructure

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.infraestructure.data.dao.CarRegisterSpaceDao
import com.stevecampos.infraestructure.data.db.ParkingDatabase
import com.stevecampos.infraestructure.data.entity.*
import com.stevecampos.infraestructure.data.exception.RegisterSpaceNotSavedException
import com.stevecampos.infraestructure.data.repository.CarRegisterSpaceRoom
import kotlinx.coroutines.runBlocking
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
    private lateinit var carRegisterSpaceRoom: CarRegisterSpaceRoom

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ParkingDatabase::class.java
        ).build()
        val carRegisterSpaceDao = db.carRegisterSpaceDao
        carRegisterSpaceRoom = CarRegisterSpaceRoom(carRegisterSpaceDao)
    }

    @After
    fun close() {
        db.close()
    }

    @Test
    fun register_whenRegisterCar_shouldRegisteredCarsCountsBeOne() = runTest {

        //Arrange

        val carRegisterSpace = CarRegisterSpaceEntity(
            id = UUID.randomUUID().toString(),
            car = CarEntity("AAA000"),
            parkingSpaceEntity = ParkingSpaceEntity(1),
            startDate = Date(1656945638133),
            endDate = null,
            state = RegisterStateEntity.LOCKED
        ).asDomain()
        //Act
        carRegisterSpaceRoom.register(carRegisterSpace)
        //Assert
        val items = carRegisterSpaceRoom.getRegisteredSpaces(state = RegisteredState.Locked)
        Assert.assertEquals(items.size, 1)
    }

    @Test
    fun register_whenRegisterTwoCarsWithSamePlate_shouldFail() = runTest {

        //Arrange
        val car1 = CarEntity("AAA000")
        val car2 = CarEntity("AAA000")

        val carRegisterSpace1 = CarRegisterSpaceEntity(
            "car1",
            car1,
            ParkingSpaceEntity(1),
            Date(1657005294106),
            null,
            state = RegisterStateEntity.LOCKED
        ).asDomain()
        val carRegisterSpace2 = CarRegisterSpaceEntity(
            "car2",
            car2,
            ParkingSpaceEntity(2),
            Date(1657005294106),
            null,
            state = RegisterStateEntity.LOCKED
        ).asDomain()

        //Assert
        Assert.assertThrows(RegisterSpaceNotSavedException::class.java) {
            runBlocking {
                //Act
                carRegisterSpaceRoom.register(carRegisterSpace1)
                carRegisterSpaceRoom.register(carRegisterSpace2)
            }
        }
    }

    @Test
    fun register_whenRegisterTwoCarsOnSameSpace_shouldFail() = runTest {

        //Arrange
        val car1 = CarEntity("AAA000")
        val car2 = CarEntity("AAA001")

        val carRegisterSpace1 = CarRegisterSpaceEntity(
            "car1",
            car1,
            ParkingSpaceEntity(1),
            Date(1657005294106),
            null,
            state = RegisterStateEntity.LOCKED
        ).asDomain()
        val carRegisterSpace2 = CarRegisterSpaceEntity(
            "car2",
            car2,
            ParkingSpaceEntity(1),
            Date(1657005294106),
            null,
            state = RegisterStateEntity.LOCKED
        ).asDomain()

        //Assert
        Assert.assertThrows(RegisterSpaceNotSavedException::class.java) {
            runBlocking {
                //Act
                carRegisterSpaceRoom.register(carRegisterSpace1)
                carRegisterSpaceRoom.register(carRegisterSpace2)
            }
        }
    }

    @Test
    fun getRegisteredSpaces_whenNoItemsRegistered_shouldGetRegisteredSpacesBeEmtpy() = runTest {
        //Arrange

        //Act
        val items = carRegisterSpaceRoom.getRegisteredSpaces(RegisteredState.Locked)
        //Assert

        Assert.assertEquals(items.size, 0)
    }

    @Test
    fun finishRegisterSpaced_whenOneRegisterActiveFinshed_shouldRegisteredSpacesBeEmpty() =
        runTest {

            //Arrange

            val car1 = CarEntity("AAA000")
            val carRegisterSpace1 = CarRegisterSpaceEntity(
                "car1",
                car1,
                ParkingSpaceEntity(1),
                Date(1657005294106),
                null,
                state = RegisterStateEntity.LOCKED
            ).asDomain()
            carRegisterSpaceRoom.register(carRegisterSpace1)

            //Act

            carRegisterSpaceRoom.finishRegisterSpaced(carRegisterSpace1)
            //Assert

            val items = carRegisterSpaceRoom.getRegisteredSpaces(state = RegisteredState.Locked)
            Assert.assertEquals(0, items.size)
        }
}