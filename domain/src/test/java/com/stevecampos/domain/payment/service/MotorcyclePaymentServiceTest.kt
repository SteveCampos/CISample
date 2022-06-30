package com.stevecampos.domain.payment.service

import com.stevecampos.domain.exception.InvalidHoursException
import com.stevecampos.domain.exception.NegativeDaysException
import com.stevecampos.domain.vehicle.entity.Motorcycle
import org.junit.Assert
import org.junit.Test

class MotorcyclePaymentServiceTest {

    private val motorcyclePaymentService = MotorcyclePaymentService()

    @Test
    fun calculateParkCost_whenMotoHighCc_shouldAddExtraCost() {
        //Arrange
        val moto = Motorcycle("AAA000", 650)
        //Act
        val parkCost = motorcyclePaymentService.calculateParkingCost(moto, 0, 10)
        //Assert
        val epsilon = 0.000001
        Assert.assertEquals(6000.0, parkCost, epsilon)
    }

    @Test
    fun calculateParkCost_whenMotoLowCc_shouldNotAddExtraCost() {
        //Arrange
        val moto = Motorcycle("AAA000", 200)
        //Act
        val parkCost = motorcyclePaymentService.calculateParkingCost(moto, 0, 10)
        //Assert
        val epsilon = 0.000001
        Assert.assertEquals(4000.0, parkCost, epsilon)
    }

    @Test
    fun calculateParkCost_whenNegativeDays_shouldThrowNegativeDaysException() {
        //Arrange
        val moto = Motorcycle("AAA000", 650)
        //Assert
        Assert.assertThrows(NegativeDaysException::class.java) {
            //Act
            motorcyclePaymentService.calculateParkingCost(moto, -1, 10)
        }
    }

    @Test
    fun calculateParkCost_whenNegativeHours_shouldThrowInvalidHoursException() {
        //Arrange
        val moto = Motorcycle("AAA000", 650)
        //Assert
        Assert.assertThrows(InvalidHoursException::class.java) {
            //Act
            motorcyclePaymentService.calculateParkingCost(moto, 1, -10)
        }
    }
    @Test
    fun calculateParkCost_whenInvalidHighHour_shouldThrowInvalidHoursException() {
        //Arrange
        val moto = Motorcycle("AAA000", 650)
        //Assert
        Assert.assertThrows(InvalidHoursException::class.java) {
            //Act
            motorcyclePaymentService.calculateParkingCost(moto, 1, 3444)
        }
    }

    @Test
    fun calculateParkCost_whenMotoLowCcParkingMoreThanOneDay_shouldNotAddExtraCost() {
        //Arrange
        val moto = Motorcycle("AAA000", 200)
        //Act
        val parkCost = motorcyclePaymentService.calculateParkingCost(moto, 2, 0)
        //Assert
        val epsilon = 0.000001
        Assert.assertEquals(8000.0, parkCost, epsilon)
    }
    @Test
    fun calculateParkCost_whenMotoHighCcParkingMoreThanOneDay_shouldAddExtraCost() {
        //Arrange
        val moto = Motorcycle("AAA000", 600)
        //Act
        val parkCost = motorcyclePaymentService.calculateParkingCost(moto, 2, 0)
        //Assert
        val epsilon = 0.000001
        Assert.assertEquals(10000.0, parkCost, epsilon)
    }

}