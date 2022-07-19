package com.stevecampos.cisample.register.motorcycle.dependencyinjection

import com.stevecampos.domain.register.repository.RegisterRepository
import com.stevecampos.domain.register.service.MotorcycleRegisterService
import com.stevecampos.domain.vehicle.entity.Motorcycle
import com.stevecampos.infraestructure.register.dao.MotoRegisterSpaceDao
import com.stevecampos.infraestructure.register.repository.MotorcycleRegisterRoom
import com.stevecampos.infraestructure.share.ParkingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterMotorcycleModule {

    @Provides
    fun provideMotoRegisterSpaceDao(parkingDatabase: ParkingDatabase): MotoRegisterSpaceDao =
        parkingDatabase.motoRegisterSpaceDao

    @Singleton
    @Provides
    fun provideMotorcycleRegisterRepository(motoRegisterSpaceDao: MotoRegisterSpaceDao): RegisterRepository<Motorcycle> =
        MotorcycleRegisterRoom(motoRegisterSpaceDao)

    @Singleton
    @Provides
    fun provideMotorcycleRegisterService(registerRepository: RegisterRepository<Motorcycle>) =
        MotorcycleRegisterService(registerRepository)

}
/*
val registerMotorcycleModule = module {

    single<MotoRegisterSpaceDao> {
        get<ParkingDatabase>().motoRegisterSpaceDao
    }
    single<MotorcycleRegisterRepository> {
        MotorcycleRegisterRoom(get())
    }
    single {
        MotorcycleRegisterService(get<MotorcycleRegisterRepository>())
    }
}*/