package com.stevecampos.cisample.parkingspaces.dependencyinjection

import com.stevecampos.domain.register.service.*
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.domain.vehicle.entity.Motorcycle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ParkingSpaceModule {
    @Provides
    fun provideCarParkingSpaceService(): ParkingSpaceService<Car> = CarParkingSpaceService()

    @Provides
    fun provideMotorcycleParkingSpaceService(): ParkingSpaceService<Motorcycle> =
        MotorcycleParkingSpaceService()
}