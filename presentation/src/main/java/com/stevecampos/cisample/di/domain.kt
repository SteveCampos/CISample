package com.stevecampos.cisample.di


import com.stevecampos.domain.service.ParkingService
import com.stevecampos.domain.valueobject.ParkCalculator
import com.stevecampos.domain.valueobject.ParkCalculatorImpl
import org.koin.dsl.module

val domainModule = module {

    single<ParkCalculator> {
        ParkCalculatorImpl()
    }

    single {
        ParkingService(
            parkingCalculator = get(),
            parkingRepository = get()
        )
    }
}