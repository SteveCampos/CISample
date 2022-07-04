package com.stevecampos.infraestructure.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Car
import java.util.*

@Entity(tableName = "car_register_space")
data class CarRegisterSpaceEntity(
    @PrimaryKey
    val id: String,
    @Embedded
    val car: CarEntity,
    @Embedded
    val parkingSpaceEntity: ParkingSpaceEntity,
    val startDate: Date,
    val endDate: Date?,
    val state: RegisterStateEntity
)

fun CarRegisterSpaceEntity.asDomain(): RegisteredSpace<Car> {
    return RegisteredSpace<Car>(
        id = this.id,
        vehicle = Car(this.car.plate),
        parkingSpace = ParkingSpace(this.parkingSpaceEntity.id),
        startDate = this.startDate,
        finishDate = this.endDate,
        state = this.state.asDomain()
    )
}