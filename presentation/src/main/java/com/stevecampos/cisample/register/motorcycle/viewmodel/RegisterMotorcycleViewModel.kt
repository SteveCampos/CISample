package com.stevecampos.cisample.register.motorcycle.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.stevecampos.cisample.parkingspaces.viewmodel.executeTask
import com.stevecampos.cisample.register.motorcycle.navigation.RegisterMotorcycleDestination
import com.stevecampos.cisample.register.motorcycle.ui.RegisterMotorcycleUiState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.service.MotorcycleRegisterService
import com.stevecampos.domain.vehicle.entity.Motorcycle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RegisterMotorcycleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val motorcycleRegisterService: MotorcycleRegisterService
) : ViewModel() {

    private val _registerMotorcycleViewState =
        MutableStateFlow<RegisterMotorcycleUiState>(
            RegisterMotorcycleUiState.RegisterMotorcycleInit(
                checkNotNull(
                    savedStateHandle[RegisterMotorcycleDestination.parkingSpaceIdArg]
                )
            )
        )
    val registerMotorcycleViewState: StateFlow<RegisterMotorcycleUiState>
        get() = _registerMotorcycleViewState

    private val parkingSpaceId: Int = checkNotNull(
        savedStateHandle[RegisterMotorcycleDestination.parkingSpaceIdArg]
    )

    fun registerMotorcycle(plate: String, cylinderCapacity: String, dateStr: String) {
        Log.d(TAG, "registerMotorcycle")
        _registerMotorcycleViewState.value = RegisterMotorcycleUiState.RegisterMotorcycleLoading
        executeTask(
            ::onRegisterMotorcycleSuccess,
            ::onRegisterMotorcycleFailed
        ) {

            val motorcycle = Motorcycle(plate, cylinderCapacity.toInt())
            val parkingSpace = ParkingSpace(parkingSpaceId)
            val date = Calendar.getInstance().time
            motorcycleRegisterService.register(motorcycle, parkingSpace, date)
        }

    }

    private fun onRegisterMotorcycleFailed(throwable: Throwable) {
        Log.d(TAG, "onRegisterMotorcycleFailed: $throwable")
        _registerMotorcycleViewState.value =
            RegisterMotorcycleUiState.RegisterMotorcycleError(throwable.toString())
    }

    private fun onRegisterMotorcycleSuccess(unit: Unit) {
        Log.d(TAG, "onRegisterMotorcycleSuccess")
        _registerMotorcycleViewState.value = RegisterMotorcycleUiState.RegisterMotorcycleSuccess
    }

    companion object {
        const val TAG = "RegisterMotorcycleViewModel"
    }

}


