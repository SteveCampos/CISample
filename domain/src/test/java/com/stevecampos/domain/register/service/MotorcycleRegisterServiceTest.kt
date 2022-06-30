package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.exception.NotParkingSpacesAvailableException
import com.stevecampos.domain.register.exception.UnAuthorizedException
import com.stevecampos.domain.register.getNextDayOfWeek
import com.stevecampos.domain.vehicle.entity.Motorcycle
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.time.DayOfWeek

class MotorcycleRegisterServiceTest {
    @Test
    fun register_whenPlateMotoBeginsWithAAndStartDateIsSunday_shouldThrowUnauthorizedException() {

        //Arrange
        val motorcycle = Motorcycle("AAA000", 200)
        val parkingSpace = ParkingSpace(0)
        val sunday = getNextDayOfWeek(DayOfWeek.SUNDAY)

        val motorcycleRegisterService = MotorcycleRegisterService(MotorcycleRegisterRepositoryFakeImpl(0))

        //Assert
        Assert.assertThrows(UnAuthorizedException::class.java) {
            //Act
            runBlocking {
                motorcycleRegisterService.register(motorcycle, parkingSpace, sunday)
            }
        }
    }
    @Test
    fun register_whenPlateMotorcycleBeginsWithAAndStartDateIsMonday_shouldThrowUnauthorizedException() {

        //Arrange
        val motorcycle = Motorcycle("AAA000", 400)
        val parkingSpace = ParkingSpace(0)
        val sunday = getNextDayOfWeek(DayOfWeek.SUNDAY)

        val motorcycleRegisterService = MotorcycleRegisterService(MotorcycleRegisterRepositoryFakeImpl(8))

        //Assert
        Assert.assertThrows(UnAuthorizedException::class.java) {
            //Act
            runBlocking {
                motorcycleRegisterService.register(motorcycle, parkingSpace, sunday)
            }
        }
    }

    @Test
    fun register_whenPlateMotorcycleBeginsWithAAndStartDateIsSaturday_shouldNotThrowUnauthorizedException() {

        //Arrange
        val motorcycle = Motorcycle("AAA000", 400)
        val parkingSpace = ParkingSpace(0)
        val sunday = getNextDayOfWeek(DayOfWeek.SATURDAY)

        val motorcycleRegisterService = MotorcycleRegisterService(MotorcycleRegisterRepositoryFakeImpl(8))

        //Act
        runBlocking {
            motorcycleRegisterService.register(motorcycle, parkingSpace, sunday)
        }
    }

    @Test
    fun register_whenPlateMotorcycleBeginsWithBAndStartDateIsSunday_shouldNotThrowUnauthorizedException() {

        //Arrange
        val motorcycle = Motorcycle("BAA000", 400)
        val parkingSpace = ParkingSpace(0)
        val sunday = getNextDayOfWeek(DayOfWeek.SUNDAY)

        val motorcycleRegisterService = MotorcycleRegisterService(MotorcycleRegisterRepositoryFakeImpl(8))

        //Act
        runBlocking {
            motorcycleRegisterService.register(motorcycle, parkingSpace, sunday)
        }
    }

    @Test
    fun register_whenNoParkingSpaceLeft_shouldThrowNotParkingSpacesAvailableException() {

        //Arrange
        val motorcycle = Motorcycle("AAA000", 400)
        val parkingSpace = ParkingSpace(0)
        val sunday = getNextDayOfWeek(DayOfWeek.WEDNESDAY)

        val motorcycleRegisterService = MotorcycleRegisterService(MotorcycleRegisterRepositoryFakeImpl(10))

        //Assert
        Assert.assertThrows(NotParkingSpacesAvailableException::class.java) {
            //Act
            runBlocking {
                motorcycleRegisterService.register(motorcycle, parkingSpace, sunday)
            }
        }
    }

    @Test
    fun register_whenParkingSpaceAvailable_shouldNotThrowNotParkingSpacesAvailableException() {

        //Arrange
        val motorcycle = Motorcycle("AAA000", 400)
        val parkingSpace = ParkingSpace(0)
        val sunday = getNextDayOfWeek(DayOfWeek.WEDNESDAY)

        val motorcycleRegisterService = MotorcycleRegisterService(MotorcycleRegisterRepositoryFakeImpl(0))

        //Act
        runBlocking {
            motorcycleRegisterService.register(motorcycle, parkingSpace, sunday)
        }
    }
}