package com.stevecampos.cisample.shared.dependencyinjection

import android.content.Context
import com.stevecampos.infraestructure.share.ParkingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context):ParkingDatabase = ParkingDatabase.provideDatabase(appContext)
}