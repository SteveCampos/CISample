package com.stevecampos.infraestructure

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.infraestructure.register.anticorrupt.CarRegisterSpaceTranslator
import com.stevecampos.infraestructure.share.ParkingDatabase
import com.stevecampos.infraestructure.register.entity.CarRegisterSpaceEntity
import com.stevecampos.infraestructure.register.entity.ParkingSpaceEntity
import com.stevecampos.infraestructure.register.entity.RegisterStateEntity
import com.stevecampos.infraestructure.register.exception.RegisterSpaceNotSavedException
import com.stevecampos.infraestructure.register.repository.CarRegisterRoom
import com.stevecampos.infraestructure.vehicle.entity.CarEntity
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
    private lateinit var carRegisterRoom: CarRegisterRoom

    private val carRegisterSpaceTranslator = CarRegisterSpaceTranslator()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ParkingDatabase::class.java
        ).build()
        val carRegisterSpaceDao = db.carRegisterSpaceDao
        carRegisterRoom = CarRegisterRoom(carRegisterSpaceDao)
    }

    @After
    fun close() {
        db.close()
    }

    @Test
    fun register_whenRegisterCar_shouldRegisteredCarsCountsBeOne() = runTest {

        //Arrange

        val carRegisterSpaceEntity = CarRegisterSpaceEntity(
            id = UUID.randomUUID().toString(),
            car = CarEntity("AAA000"),
            parkingSpaceEntity = ParkingSpaceEntity(1),
            startDate = Date(1656945638133),
            endDate = null,
            state = RegisterStateEntity.LOCKED
        )
        val carRegisterSpace = carRegisterSpaceTranslator.translateToDomain(carRegisterSpaceEntity)
        //Act
        carRegisterRoom.register(carRegisterSpace)
        //Assert
        val items = carRegisterRoom.getRegisteredSpaces(state = RegisteredState.Locked)
        Assert.assertEquals(items.size, 1)
    }

    @Test
    fun register_whenRegisterTwoCarsWithSamePlate_shouldFail() = runTest {

        //Arrange
        val car1 = CarEntity("AAA000")
        val car2 = CarEntity("AAA000")

        val carRegisterSpace1 = carRegisterSpaceTranslator.translateToDomain(
            CarRegisterSpaceEntity(
                "car1",
                car1,
                ParkingSpaceEntity(1),
                Date(1657005294106),
                null,
                state = RegisterStateEntity.LOCKED
            )
        )
        val carRegisterSpace2 = carRegisterSpaceTranslator.translateToDomain(
            CarRegisterSpaceEntity(
                "car2",
                car2,
                ParkingSpaceEntity(2),
                Date(1657005294106),
                null,
                state = RegisterStateEntity.LOCKED
            )
        )

        //Assert
        Assert.assertThrows(Throwable::class.java) {
            runBlocking {
                //Act
                carRegisterRoom.register(carRegisterSpace1)
                carRegisterRoom.register(carRegisterSpace2)
            }
        }
    }

    @Test
    fun register_whenRegisterTwoCarsOnSameSpace_shouldFail() = runTest {

        //Arrange
        val car1 = CarEntity("AAA000")
        val car2 = CarEntity("AAA001")

        val carRegisterSpace1 = carRegisterSpaceTranslator.translateToDomain(
            CarRegisterSpaceEntity(
                "car1",
                car1,
                ParkingSpaceEntity(1),
                Date(1657005294106),
                null,
                state = RegisterStateEntity.LOCKED
            )
        )
        val carRegisterSpace2 = carRegisterSpaceTranslator.translateToDomain(
            CarRegisterSpaceEntity(
                "car2",
                car2,
                ParkingSpaceEntity(1),
                Date(1657005294106),
                null,
                state = RegisterStateEntity.LOCKED
            )
        )

        //Assert
        Assert.assertThrows(Throwable::class.java) {
            kotlinx.coroutines.runBlocking {
                //Act
                carRegisterRoom.register(carRegisterSpace1)
                carRegisterRoom.register(carRegisterSpace2)
            }
        }
    }

    @Test
    fun getRegisteredSpaces_whenNoItemsRegistered_shouldGetRegisteredSpacesBeEmtpy() = runTest {
        //Arrange

        //Act
        val items = carRegisterRoom.getRegisteredSpaces(RegisteredState.Locked)
        //Assert

        Assert.assertEquals(items.size, 0)
    }

    @Test
    fun finishRegisterSpaced_whenOneRegisterActiveFinshed_shouldRegisteredSpacesBeEmpty() =
        runTest {

            //Arrange

            val car1 = CarEntity("AAA000")
            val carRegisterSpace1Entity = CarRegisterSpaceEntity(
                "car1",
                car1,
                ParkingSpaceEntity(1),
                Date(1657005294106),
                null,
                state = RegisterStateEntity.LOCKED
            )
            val carRegisterSpace1 =
                carRegisterSpaceTranslator.translateToDomain(carRegisterSpace1Entity)
            carRegisterRoom.register(carRegisterSpace1)

            //Act

            carRegisterRoom.finishRegisterSpaced(carRegisterSpace1)
            //Assert

            val items = carRegisterRoom.getRegisteredSpaces(state = RegisteredState.Locked)
            Assert.assertEquals(0, items.size)
        }
}