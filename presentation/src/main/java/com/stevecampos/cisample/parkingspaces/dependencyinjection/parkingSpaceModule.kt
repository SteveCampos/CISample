package com.stevecampos.cisample.parkingspaces.dependencyinjection

import com.stevecampos.cisample.parkingspaces.vm.ParkingViewModel
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