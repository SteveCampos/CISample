package com.stevecampos.domain.vehicle

import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.domain.vehicle.exception.InvalidPlateException
import org.junit.Assert
import org.junit.Test

class VehicleTest {

    @Test
    fun init_onValidCommercialPlate_shouldSucceed() {
        //Arrange
        val plate = "ABC123"
        //Act
        val car = Car(plate)
        //Assert
        Assert.assertNotNull(car)
    }

    @Test
    fun init_onInvalidCommercialPlate_shouldThrowException() {
        //Arrange
        val plate = "000000"
        //Assert
        Assert.assertThrows(InvalidPlateException::class.java) {
            //Act
            Car(plate)
        }
    }

    @Test
    fun init_onValidTowingPlate_shouldSucceed() {
        //Arrange
        val plate = "R12345"
        //Act
        val car = Car(plate)
        //Assert
        Assert.assertNotNull(car)
    }

    @Test
    fun init_onInvalidTowingPlate_shouldThrowException() {
        //Arrange
        val plate = "R123456"
        //Assert
        Assert.assertThrows(InvalidPlateException::class.java) {
            //Act
            Car(plate)
        }
    }

    @Test
    fun init_onValidTrailerPlate_shouldSucceed() {
        //Arrange
        val plate = "T1234"
        //Act
        val car = Car(plate)
        //Assert
        Assert.assertNotNull(car)
    }

    @Test
    fun init_onInvalidTrailerPlate_shouldThrowException() {
        //Arrange
        val plate = "T12345"
        //Assert
        Assert.assertThrows(InvalidPlateException::class.java) {
            //Act
            Car(plate)
        }
    }

    @Test
    fun plateBeginsWithA_whenPlateBeginsWithA_shouldBeTrue() {
        //Act
        val plate = "AAA000"
        val car = Car(plate)
        //Arrange
        Assert.assertEquals(true, car.plateBeginsWithA())
    }
    @Test
    fun plateBeginsWithA_whenPlateNotBeginsWithA_shouldBeFalse() {
        //Act
        val plate = "BAA000"
        val car = Car(plate)
        //Arrange
        Assert.assertEquals(false, car.plateBeginsWithA())
    }
}