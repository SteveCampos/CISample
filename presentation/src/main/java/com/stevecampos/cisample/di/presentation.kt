package com.stevecampos.cisample.di

import com.stevecampos.cisample.features.parking.ParkingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        ParkingViewModel(get())
    }
}