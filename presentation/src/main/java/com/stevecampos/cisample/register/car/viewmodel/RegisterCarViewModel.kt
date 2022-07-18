package com.stevecampos.cisample.register.car.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.stevecampos.cisample.parkingspaces.viewmodel.executeTask
import com.stevecampos.cisample.register.car.ui.RegisterCarUiState
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.service.CarRegisterService
import com.stevecampos.domain.vehicle.entity.Car
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant
import java.util.*

class RegisterCarViewModel(
    //savedStateHandle: SavedStateHandle,
    private val parkingSpaceId: Int,
    private val carRegisterService: CarRegisterService
) : ViewModel() {

    private val _registerCarViewState =
        MutableStateFlow<RegisterCarUiState>(RegisterCarUiState.RegisterCarInit(parkingSpaceId))
    val registerCarViewState: StateFlow<RegisterCarUiState>
        get() = _registerCarViewState

    /*private val parkingSpaceId: Int = checkNotNull(
        savedStateHandle[RegisterCarDestination.parkingSpaceIdArg]
    )*/

    fun registerCar(plate: String, dateStr: String) {
        Log.d(TAG, "registerCar")
        _registerCarViewState.value = RegisterCarUiState.RegisterCarLoading
        executeTask(
            ::onRegisterCarSuccess,
            ::onRegisterCarFailed
        ) {

            val car = Car(plate)
            val parkingSpace = ParkingSpace(parkingSpaceId)
            val date = Calendar.getInstance().time

            carRegisterService.register(car, parkingSpace, date)
        }

    }

    private fun onRegisterCarFailed(throwable: Throwable) {
        Log.d(TAG, "onRegisterCarFailed: $throwable")
        _registerCarViewState.value = RegisterCarUiState.RegisterCarError(throwable.toString())
    }

    private fun onRegisterCarSuccess(unit: Unit) {
        Log.d(TAG, "onRegisterCarSuccess")
        _registerCarViewState.value = RegisterCarUiState.RegisterCarSuccess
    }

    companion object {
        const val TAG = "RegisterCarViewModel"
    }

}


