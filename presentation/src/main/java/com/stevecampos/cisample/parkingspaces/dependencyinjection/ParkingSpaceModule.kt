package com.stevecampos.cisample.parkingspaces.dependencyinjection

import com.stevecampos.cisample.parkingspaces.viewmodel.ParkingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val parkingSpaceModule = module {
    viewModel {
        ParkingViewModel(
            get(named("CarParkingSpaceService")),
            get(named("MotorcycleParkingSpaceService")),
            get(),
            get()
        )
    }
}