package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.service.CarParkingSpaceService
import org.junit.Assert
import org.junit.Test

class CarParkingSpaceServiceTest {
    @Test
    fun init_whenCreates_ShouldSizeBeTwenty() {

        //Act
        val carParkingSpaceSize = CarParkingSpaceService()
        //Assert
        Assert.assertEquals(20, carParkingSpaceSize.size)
    }
}