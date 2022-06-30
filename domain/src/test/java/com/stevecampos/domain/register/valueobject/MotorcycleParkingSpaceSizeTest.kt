package com.stevecampos.domain.register.valueobject

import org.junit.Assert
import org.junit.Test

class MotorcycleParkingSpaceSizeTest {
    @Test
    fun init_whenCreates_ShouldSizeBeTen() {

        //Act
        val motorcycleParkingSize = MotorcycleParkingSpaceSize()
        //Assert
        Assert.assertEquals(10, motorcycleParkingSize.size)
    }
}