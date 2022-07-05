package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.exception.NotParkingSpacesAvailableException
import com.stevecampos.domain.register.exception.UnAuthorizedException
import com.stevecampos.domain.register.getNextDayOfWeek
import com.stevecampos.domain.vehicle.entity.Car
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.time.DayOfWeek
import java.time.Instant
import java.util.*

class CarRegisterServiceTest {

    @Test
    fun register_whenPlateCarBeginsWithAAndStartDateIsSunday_shouldThrowUnauthorizedException() {

        //Arrange
        val car = Car("AAA000")
        val parkingSpace = ParkingSpace(0)
        val sunday = getNextDayOfWeek(DayOfWeek.SUNDAY)

        val carRegisterService = CarRegisterService(CarRegisterRepositoryFake(0))

        //Assert
        Assert.assertThrows(UnAuthorizedException::class.java) {
            //Act
            runBlocking {
                carRegisterService.register(car, parkingSpace, sunday)
            }
        }
    }

    @Test
    fun register_whenPlateCarBeginsWithAAndStartDateIsMonday_shouldThrowUnauthorizedException() {

        //Arrange
        val car = Car("AAA000")
        val parkingSpace = ParkingSpace(0)
        val sunday = getNextDayOfWeek(DayOfWeek.SUNDAY)

        val carRegisterService = CarRegisterService(CarRegisterRepositoryFake(0))

        //Assert
        Assert.assertThrows(UnAuthorizedException::class.java) {
            //Act
            runBlocking {
                carRegisterService.register(car, parkingSpace, sunday)
            }
        }
    }

    @Test
    fun register_whenPlateCarBeginsWithAAndStartDateIsSaturday_shouldNotThrowUnauthorizedException() {

        //Arrange
        val car = Car("XAA000")
        val parkingSpace = ParkingSpace(0)
        val sunday = getNextDayOfWeek(DayOfWeek.SATURDAY)

        val carRegisterService = CarRegisterService(CarRegisterRepositoryFake(0))

        //Act
        runBlocking {
            carRegisterService.register(car, parkingSpace, sunday)
        }
    }

    @Test
    fun register_whenPlateCarBeginsWithBAndStartDateIsSunday_shouldNotThrowUnauthorizedException() {

        //Arrange
        val car = Car("BAA000")
        val parkingSpace = ParkingSpace(0)
        val sunday = getNextDayOfWeek(DayOfWeek.SUNDAY)

        val carRegisterService = CarRegisterService(CarRegisterRepositoryFake(0))

        //Act
        runBlocking {
            carRegisterService.register(car, parkingSpace, sunday)
        }
    }

    @Test
    fun register_whenNoParkingSpaceLeft_shouldThrowNotParkingSpacesAvailableException() {

        //Arrange
        val car = Car("AAA000")
        val parkingSpace = ParkingSpace(0)
        val sunday = getNextDayOfWeek(DayOfWeek.WEDNESDAY)

        val carRegisterService = CarRegisterService(CarRegisterRepositoryFake(20))

        //Assert
        Assert.assertThrows(NotParkingSpacesAvailableException::class.java) {
            //Act
            runBlocking {
                carRegisterService.register(car, parkingSpace, sunday)
            }
        }
    }

    @Test
    fun register_whenParkingSpaceAvailable_shouldNotThrowNotParkingSpacesAvailableException() {

        //Arrange
        val car = Car("AAA000")
        val parkingSpace = ParkingSpace(0)
        val sunday = getNextDayOfWeek(DayOfWeek.WEDNESDAY)

        val carRegisterService = CarRegisterService(CarRegisterRepositoryFake(0))

        //Act
        runBlocking {
            carRegisterService.register(car, parkingSpace, sunday)
        }
    }
}