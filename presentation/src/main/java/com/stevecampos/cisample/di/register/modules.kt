package com.stevecampos.cisample.di.register

import androidx.lifecycle.SavedStateHandle
import com.stevecampos.cisample.features.register.car.vm.RegisterCarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registerCarModules = module {
    /*viewModel { (handle: SavedStateHandle) ->
        RegisterCarViewModel(
            savedStateHandle = handle,
            carRegisterService = get()
        )
    }*/
}