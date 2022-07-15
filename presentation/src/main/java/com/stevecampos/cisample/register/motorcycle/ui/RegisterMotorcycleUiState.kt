package com.stevecampos.cisample.register.motorcycle.ui

sealed class RegisterMotorcycleUiState {
    data class RegisterMotorcycleInit(val parkingSpaceId: Int) : RegisterMotorcycleUiState()
    object RegisterMotorcycleLoading : RegisterMotorcycleUiState()
    data class RegisterMotorcycleError(val errorMsg: String) : RegisterMotorcycleUiState()
    object RegisterMotorcycleSuccess : RegisterMotorcycleUiState()
}