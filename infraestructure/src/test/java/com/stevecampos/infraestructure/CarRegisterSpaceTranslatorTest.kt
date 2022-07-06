package com.stevecampos.infraestructure

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.infraestructure.data.anticorrupt.CarRegisterSpaceTranslator
import org.junit.Assert
import org.junit.Test
import java.util.*

class CarRegisterSpaceTranslatorTest {
    @Test
    fun map_whenTranslateDomainToInfrastructure_shouldIdValuesBeTheSame() {
        //Arrange
        val id = UUID.randomUUID().toString()
        val domainEntity = RegisteredSpace<Car>(
            id = id,
            vehicle = Car("AAA000"),
            parkingSpace = ParkingSpace(1),
            startDate = Date(1657129622),
            endDate = null,
            state = RegisteredState.Locked
        )
        val translator = CarRegisterSpaceTranslator()
        //Act
        val infrastructureEntity = translator.map(domainEntity)
        //Assert
        Assert.assertEquals(id, infrastructureEntity.id)
    }
}