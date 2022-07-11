package com.stevecampos.cisample.features.parking.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.service.*
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.domain.vehicle.entity.Motorcycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ParkingViewModel(
    private val carParkingService: CarParkingSpaceService,
    private val motorcycleParkingSpaceService: MotorcycleParkingSpaceService,
    private val carRegisterService: CarRegisterService,
    private val motorcycleRegisterService: MotorcycleRegisterService
) : ViewModel(), ParkingActions {

    private val _parkingUiState =
        MutableStateFlow<ParkingUiState>(ParkingUiState.ParkingLoadingState)
    val parkingUiState: StateFlow<ParkingUiState>
        get() = _parkingUiState

    init {
        Log.d(TAG, "init")
        getParkingSpaces()
    }

    private fun getParkingSpaces() {
        Log.d(TAG, "getParkingSpaces")
        executeTask(
            ::onGetParkingSpacesSuccess,
            ::onGetParkingSpacesFailed
        ) {
            getCombinedResult()
        }
    }

    private fun onGetParkingSpacesFailed(throwable: Throwable) {
        Log.d(TAG, "onGetParkingSpacesFailed: $throwable")
        _parkingUiState.value = ParkingUiState.ParkingErrorState(throwable.message ?: "error")
    }

    private fun onGetParkingSpacesSuccess(parkingSuccessState: ParkingUiState.ParkingSuccessState) {
        Log.d(TAG, "onGetParkingSpacesSuccess: $parkingSuccessState")
        _parkingUiState.value = parkingSuccessState
    }


    private suspend fun getCombinedResult(): ParkingUiState.ParkingSuccessState {
        Log.d(TAG, "getCombinedResult")
        val carSpaces = carParkingService.getParkingSpaces()
        val motoSpaces = motorcycleParkingSpaceService.getParkingSpaces()
        val carRegisteredSpaces = carRegisterService.getRegisteredSpaces()
        val motoRegisteredSpaces = motorcycleRegisterService.getRegisteredSpaces()


        val carSpacesFilled: List<Pair<ParkingSpace, RegisteredSpace<Car>?>> =
            carSpaces.map { parkingSpace ->
                val registeredSpace =
                    carRegisteredSpaces.firstOrNull { it.parkingSpace.id == parkingSpace.id }
                Pair(parkingSpace, registeredSpace)
            }
        val motoSpacesFilled: List<Pair<ParkingSpace, RegisteredSpace<Motorcycle>?>> =
            motoSpaces.map { parkingSpace ->
                val registeredSpace =
                    motoRegisteredSpaces.firstOrNull { it.parkingSpace.id == parkingSpace.id }
                Pair(parkingSpace, registeredSpace)
            }

        return ParkingUiState.ParkingSuccessState(
            carSpacesFilled,
            motoSpacesFilled
        )
    }

    //private val _viewState =
    //    MutableLiveData<ParkingViewState>(ParkingViewState.GetParkingSpacesLoading)
    //val viewState: LiveData<ParkingViewState> = _viewState

    val carParkingSpaces = MutableLiveData<List<ParkingSpace>>(listOf())

    companion object {
        const val TAG = "ParkingViewModel"
    }

    private fun getCarParkingSpaces() {
        executeTask(
            ::onGetCarParkingSpacesSuccess,
            ::onGetCarParkingSpacesFailed
        ) {
            carParkingService.getParkingSpaces()
        }
    }

    private fun onGetCarParkingSpacesFailed(throwable: Throwable) {
        Log.d(TAG, "onGetCarParkingSpacesFailed: $throwable")
    }

    private fun onGetCarParkingSpacesSuccess(spaces: List<ParkingSpace>) {
        Log.d(TAG, "onGetCarParkingSpacesSuccess: $spaces")
        carParkingSpaces.value = spaces
    }
}

sealed class ParkingUiState {
    object ParkingLoadingState : ParkingUiState()
    data class ParkingErrorState(val errorMsg: String) : ParkingUiState()
    data class ParkingSuccessState(
        val carSpacesFilled: List<Pair<ParkingSpace, RegisteredSpace<Car>?>>,
        val motoSpacesFilled: List<Pair<ParkingSpace, RegisteredSpace<Motorcycle>?>>,
    ) : ParkingUiState()
}

interface ParkingActions {

}

fun <T> ViewModel.executeTask(
    onSuccess: (T) -> Unit,
    onFailure: (Throwable) -> Unit,
    task: suspend () -> T
) {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            val result = task.invoke()
            withContext(Dispatchers.Main) {
                onSuccess.invoke(result)
            }
        } catch (e: Throwable) {
            withContext(Dispatchers.Main) {
                onFailure.invoke(e)
            }
        }
    }
}