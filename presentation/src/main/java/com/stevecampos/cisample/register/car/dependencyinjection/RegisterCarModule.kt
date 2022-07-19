package com.stevecampos.cisample.register.car.dependencyinjection

import com.stevecampos.domain.register.repository.RegisterRepository
import com.stevecampos.domain.register.service.CarRegisterService
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.infraestructure.register.dao.CarRegisterSpaceDao
import com.stevecampos.infraestructure.register.repository.CarRegisterRoom
import com.stevecampos.infraestructure.share.ParkingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RegisterCarModule {

    @Provides
    fun provideCarRegisterSpaceDao(parkingDatabase: ParkingDatabase): CarRegisterSpaceDao =
        parkingDatabase.carRegisterSpaceDao

    @Singleton
    @Provides
    fun provideCarRegisterRepository(carRegisterSpaceDao: CarRegisterSpaceDao): RegisterRepository<Car> =
        CarRegisterRoom(carRegisterSpaceDao)

    @Singleton
    @Provides
    fun provideCarRegisterService(registerRepository: RegisterRepository<Car>) =
        CarRegisterService(registerRepository)

}