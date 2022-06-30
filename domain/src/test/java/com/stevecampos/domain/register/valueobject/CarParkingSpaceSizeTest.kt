package com.stevecampos.domain.register.valueobject

import org.junit.Assert
import org.junit.Test

class CarParkingSpaceSizeTest {
    @Test
    fun init_whenCreates_ShouldSizeBeTwenty() {

        //Act
        val carParkingSpaceSize = CarParkingSpaceSize()
        //Assert
        Assert.assertEquals(20, carParkingSpaceSize.size)
    }
}