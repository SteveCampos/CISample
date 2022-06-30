package com.stevecampos.domain.register.valueobject

import com.stevecampos.domain.register.getNextDayOfWeek
import org.junit.Assert
import org.junit.Test
import java.time.DayOfWeek

class DateExtTest {
    @Test
    fun isSunday_whenDateIsSunday_thenShouldBeTrue() {
        //Arrange
        val sundayDate = getNextDayOfWeek(DayOfWeek.SUNDAY)
        //Act
        val isSunday = sundayDate.isSunday()
        //Assert
        Assert.assertEquals(true, isSunday)
    }

    @Test
    fun isSunday_whenDateIsSaturday_thenShouldBeFalse() {
        //Arrange
        val sundayDate = getNextDayOfWeek(DayOfWeek.SATURDAY)
        //Act
        val isSunday = sundayDate.isSunday()
        //Assert
        Assert.assertEquals(false, isSunday)
    }
    @Test
    fun isSunday_whenDateIsMonday_thenShouldBeTrue() {
        //Arrange
        val mondayDate = getNextDayOfWeek(DayOfWeek.MONDAY)
        //Act
        val isMonday = mondayDate.isMonday()
        //Assert
        Assert.assertEquals(true, isMonday)
    }

    @Test
    fun isMonday_whenDateIsSaturday_thenShouldBeFalse() {
        //Arrange
        val mondayDate = getNextDayOfWeek(DayOfWeek.SATURDAY)
        //Act
        val isMonday = mondayDate.isMonday()
        //Assert
        Assert.assertEquals(false, isMonday)
    }
}