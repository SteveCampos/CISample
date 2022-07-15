package com.stevecampos.cisample.register.motorcycle.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.stevecampos.cisample.parkingspaces.vm.executeTask
import com.stevecampos.cisample.register.motorcycle.ui.RegisterMotorcycleUiState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.service.MotorcycleRegisterService
import com.stevecampos.domain.vehicle.entity.Motorcycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class RegisterMotorcycleViewModel(
    //savedStateHandle: SavedStateHandle,
    private val parkingSpaceId: Int,
    private val motorcycleRegisterService: MotorcycleRegisterService
) : ViewModel() {

    private val _registerMotorcycleViewState =
        MutableStateFlow<RegisterMotorcycleUiState>(
            RegisterMotorcycleUiState.RegisterMotorcycleInit(
                parkingSpaceId
            )
        )
    val registerMotorcycleViewState: StateFlow<RegisterMotorcycleUiState>
        get() = _registerMotorcycleViewState

    /*private val parkingSpaceId: Int = checkNotNull(
        savedStateHandle[RegisterMotorcycleDestination.parkingSpaceIdArg]
    )*/

    fun registerMotorcycle(plate: String, cylinderCapacity: String, dateStr: String) {
        Log.d(TAG, "registerMotorcycle")
        _registerMotorcycleViewState.value = RegisterMotorcycleUiState.RegisterMotorcycleLoading
        executeTask(
            ::onRegisterMotorcycleSuccess,
            ::onRegisterMotorcycleFailed
        ) {

            val motorcycle = Motorcycle(plate, cylinderCapacity.toInt())
            val parkingSpace = ParkingSpace(parkingSpaceId)
            val date = Date(1657477970)
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


