package com.stevecampos.cisample.features.register.car.ui

sealed class RegisterCarUiState {
    data class RegisterCarInit(val parkingSpaceId: Int) : RegisterCarUiState()
    object RegisterCarLoading : RegisterCarUiState()
    data class RegisterCarError(val errorMsg: String) : RegisterCarUiState()
    object RegisterCarSuccess : RegisterCarUiState()
}