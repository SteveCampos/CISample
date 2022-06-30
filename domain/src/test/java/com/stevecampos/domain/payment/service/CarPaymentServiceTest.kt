package com.stevecampos.domain.payment.service

import com.stevecampos.domain.payment.exception.InvalidHoursException
import com.stevecampos.domain.payment.exception.NegativeDaysException
import com.stevecampos.domain.vehicle.entity.Car
import org.junit.Assert
import org.junit.Test

class CarPaymentServiceTest {

    private val carPaymentService = CarPaymentService()

    @Test
    fun calculateParkCost_whenParkingForHours_shouldCalculateCostByHours() {
        //Arrange
        val car = Car("AAA000")
        //Act
        val parkCost = carPaymentService.calculateParkingCost(car, 0, 4)
        //Assert
        val epsilon = 0.000001
        Assert.assertEquals(4000.0, parkCost, epsilon)
    }

    @Test
    fun calculateParkCost_whenParkingForMoreThanEightHours_shouldCalculateCostByDay() {
        //Arrange
        val car = Car("AAA000")
        //Act
        val parkCost = carPaymentService.calculateParkingCost(car, 0, 10)
        //Assert
        val epsilon = 0.000001
        Assert.assertEquals(8000.0, parkCost, epsilon)
    }
    @Test
    fun calculateParkCost_whenParkingForDays_shouldCalculateCostByDays() {
        //Arrange
        val car = Car("AAA000")
        //Act
        val parkCost = carPaymentService.calculateParkingCost(car, 8, 0)
        //Assert
        val epsilon = 0.000001
        Assert.assertEquals(64000.0, parkCost, epsilon)
    }
    @Test
    fun calculateParkCost_whenParkingForDaysAndHours_shouldCalculateCostByDaysAndHours() {
        //Arrange
        val car = Car("AAA000")
        //Act
        val parkCost = carPaymentService.calculateParkingCost(car, 8, 4)
        //Assert
        val epsilon = 0.000001
        Assert.assertEquals(68000.0, parkCost, epsilon)
    }

    @Test
    fun calculateParkCost_whenParkingForEightDaysAndTenHours_shouldCalculateCostByNineDays() {
        //Arrange
        val car = Car("AAA000")
        //Act
        val parkCost = carPaymentService.calculateParkingCost(car, 8, 10)
        //Assert
        val epsilon = 0.000001
        Assert.assertEquals(72000.0, parkCost, epsilon)
    }

    @Test
    fun calculateParkCost_whenNegativeDays_shouldThrowNegativeDaysException() {
        //Arrange
        val car = Car("AAA000")
        //Assert
        Assert.assertThrows(NegativeDaysException::class.java) {
            //Act
            carPaymentService.calculateParkingCost(car, -1, 10)
        }
    }

    @Test
    fun calculateParkCost_whenNegativeHours_shouldThrowInvalidHoursException() {
        //Arrange
        val car = Car("AAA000")
        //Assert
        Assert.assertThrows(InvalidHoursException::class.java) {
            //Act
            carPaymentService.calculateParkingCost(car, 1, -10)
        }
    }

    @Test
    fun calculateParkCost_whenInvalidHighHour_shouldThrowInvalidHoursException() {
        //Arrange
        val car = Car("AAA000")
        //Assert
        Assert.assertThrows(InvalidHoursException::class.java) {
            //Act
            carPaymentService.calculateParkingCost(car, 1, 3444)
        }
    }

}