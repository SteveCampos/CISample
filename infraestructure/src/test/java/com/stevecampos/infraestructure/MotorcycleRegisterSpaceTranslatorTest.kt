package com.stevecampos.infraestructure

import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Motorcycle
import com.stevecampos.infraestructure.register.anticorrupt.MotoRegisterSpaceTranslator
import org.junit.Assert
import org.junit.Test
import java.util.*

class MotorcycleRegisterSpaceTranslatorTest {
    @Test
    fun map_whenTranslateDomainToInfrastructure_shouldIdValuesBeTheSame() {
        //Arrange
        val id = UUID.randomUUID().toString()
        val domainEntity = RegisteredSpace<Motorcycle>(
            id = id,
            vehicle = Motorcycle("AAA000", 500),
            parkingSpace = ParkingSpace(1),
            startDate = Date(1657129622),
            endDate = null,
            state = RegisteredState.Locked
        )
        val translator = MotoRegisterSpaceTranslator()
        //Act
        val infrastructureEntity = translator.translateToInfrastructure(domainEntity)
        //Assert
        Assert.assertEquals(id, infrastructureEntity.id)
    }
}