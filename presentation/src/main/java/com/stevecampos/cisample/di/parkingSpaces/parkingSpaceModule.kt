package com.stevecampos.cisample.di.parkingSpaces

import com.stevecampos.cisample.features.parkingSpaces.vm.ParkingViewModel
import com.stevecampos.domain.register.service.RegisterService
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.domain.vehicle.entity.Motorcycle
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val parkingSpaceModule = module {
    viewModel {
        ParkingViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
}