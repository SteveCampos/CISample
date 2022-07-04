package com.stevecampos.infraestructure.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Motorcycle
import java.util.*

@Entity(tableName = "moto_register_space")
data class MotoRegisterSpaceEntity(
    @PrimaryKey
    val id: String,
    @Embedded
    val moto: MotorcycleEntity,
    @Embedded
    val parkingSpaceEntity: ParkingSpaceEntity,
    val startDate: Date,
    val endDate: Date?,
    val state: RegisterStateEntity
)

fun MotoRegisterSpaceEntity.asDomain(): RegisteredSpace<Motorcycle> {
    return RegisteredSpace<Motorcycle>(
        id = this.id,
        vehicle = Motorcycle(this.moto.plate, this.moto.cylinderCapacity),
        parkingSpace = ParkingSpace(this.parkingSpaceEntity.id),
        startDate = this.startDate,
        finishDate = this.endDate,
        state = this.state.asDomain()
    )
}