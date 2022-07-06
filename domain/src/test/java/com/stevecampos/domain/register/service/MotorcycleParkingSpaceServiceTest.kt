package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.service.MotorcycleParkingSpaceService
import org.junit.Assert
import org.junit.Test

class MotorcycleParkingSpaceServiceTest {
    @Test
    fun init_whenCreates_ShouldSizeBeTen() {

        //Act
        val motorcycleParkingSize = MotorcycleParkingSpaceService()
        //Assert
        Assert.assertEquals(10, motorcycleParkingSize.size)
    }
}