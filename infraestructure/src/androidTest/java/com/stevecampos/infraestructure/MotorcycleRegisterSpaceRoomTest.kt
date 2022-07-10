package com.stevecampos.infraestructure

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.infraestructure.register.anticorrupt.CarRegisterSpaceTranslator
import com.stevecampos.infraestructure.register.anticorrupt.MotoRegisterSpaceTranslator
import com.stevecampos.infraestructure.share.ParkingDatabase
import com.stevecampos.infraestructure.register.entity.MotoRegisterSpaceEntity
import com.stevecampos.infraestructure.register.entity.ParkingSpaceEntity
import com.stevecampos.infraestructure.register.entity.RegisterStateEntity
import com.stevecampos.infraestructure.register.exception.RegisterSpaceNotSavedException
import com.stevecampos.infraestructure.register.repository.MotorcycleRegisterRoom
import com.stevecampos.infraestructure.vehicle.entity.MotorcycleEntity
import kotlinx.coroutines.runBlocking
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
    private lateinit var motorcycleRegisterRoom: MotorcycleRegisterRoom

    private val motoRegisterSpaceTranslator = MotoRegisterSpaceTranslator()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ParkingDatabase::class.java
        ).build()
        val motoRegisterSpaceDao = db.motoRegisterSpaceDao
        motorcycleRegisterRoom = MotorcycleRegisterRoom(motoRegisterSpaceDao)
    }

    @After
    fun close() {
        db.close()
    }

    @Test
    fun register_whenRegisterMoto_shouldRegisteredMotosCountsBeOne() = runTest {

        //Arrange

        val motoRegisterSpace = motoRegisterSpaceTranslator.translateToDomain(
            MotoRegisterSpaceEntity(
                id = UUID.randomUUID().toString(),
                moto = MotorcycleEntity("AAA000", 400),
                parkingSpaceEntity = ParkingSpaceEntity(1),
                startDate = Date(1656945638133),
                endDate = null,
                state = RegisterStateEntity.LOCKED
            )
        )
        //Act
        motorcycleRegisterRoom.register(motoRegisterSpace)
        //Assert
        val items = motorcycleRegisterRoom.getRegisteredSpaces(state = RegisteredState.Locked)
        Assert.assertEquals(items.size, 1)
    }

    @Test
    fun register_whenRegisterTwoMotosWithSamePlate_shouldFail() = runTest {

        //Arrange
        val moto1 = MotorcycleEntity("AAA000", 400)
        val moto2 = MotorcycleEntity("AAA000", 400)

        val motoRegisterSpace1 = motoRegisterSpaceTranslator.translateToDomain(
            MotoRegisterSpaceEntity(
                "moto1",
                moto1,
                ParkingSpaceEntity(1),
                Date(1657005294106),
                null,
                state = RegisterStateEntity.LOCKED
            )
        )
        val motoRegisterSpace2 = motoRegisterSpaceTranslator.translateToDomain(
            MotoRegisterSpaceEntity(
                "moto2",
                moto2,
                ParkingSpaceEntity(2),
                Date(1657005294106),
                null,
                state = RegisterStateEntity.LOCKED
            )
        )

        //Assert
        Assert.assertThrows(RegisterSpaceNotSavedException::class.java) {
            runBlocking {
                //Act
                motorcycleRegisterRoom.register(motoRegisterSpace1)
                motorcycleRegisterRoom.register(motoRegisterSpace2)
            }
        }
    }

    @Test
    fun register_whenRegisterTwoMotosOnSameSpace_shouldFail() = runTest {

        //Arrange
        val moto1 = MotorcycleEntity("AAA000", 400)
        val moto2 = MotorcycleEntity("AAA001", 400)

        val motoRegisterSpace1 = motoRegisterSpaceTranslator.translateToDomain(
            MotoRegisterSpaceEntity(
                "moto1",
                moto1,
                ParkingSpaceEntity(1),
                Date(1657005294106),
                null,
                state = RegisterStateEntity.LOCKED
            )
        )
        val motoRegisterSpace2 = motoRegisterSpaceTranslator.translateToDomain(
            MotoRegisterSpaceEntity(
                "moto2",
                moto2,
                ParkingSpaceEntity(1),
                Date(1657005294106),
                null,
                state = RegisterStateEntity.LOCKED
            )
        )

        //Assert
        Assert.assertThrows(RegisterSpaceNotSavedException::class.java) {
            runBlocking {
                //Act
                motorcycleRegisterRoom.register(motoRegisterSpace1)
                motorcycleRegisterRoom.register(motoRegisterSpace2)
            }
        }
    }

    @Test
    fun getRegisteredSpaces_whenNoItemsRegistered_shouldGetRegisteredSpacesBeEmtpy() = runTest {
        //Arrange

        //Act
        val items = motorcycleRegisterRoom.getRegisteredSpaces(RegisteredState.Locked)
        //Assert

        Assert.assertEquals(items.size, 0)
    }

    @Test
    fun finishRegisterSpaced_whenOneRegisterActiveFinshed_shouldRegisteredSpacesBeEmpty() =
        runTest {

            //Arrange

            val moto1 = MotorcycleEntity("AAA000", 400)
            val motoRegisterSpace1 = motoRegisterSpaceTranslator.translateToDomain(
                MotoRegisterSpaceEntity(
                    "moto1",
                    moto1,
                    ParkingSpaceEntity(1),
                    Date(1657005294106),
                    null,
                    state = RegisterStateEntity.LOCKED
                )
            )
            motorcycleRegisterRoom.register(motoRegisterSpace1)

            //Act

            motorcycleRegisterRoom.finishRegisterSpaced(motoRegisterSpace1)
            //Assert

            val items = motorcycleRegisterRoom.getRegisteredSpaces(state = RegisteredState.Locked)
            Assert.assertEquals(0, items.size)
        }
}