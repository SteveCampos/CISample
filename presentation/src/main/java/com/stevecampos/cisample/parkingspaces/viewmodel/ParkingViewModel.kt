package com.stevecampos.cisample.parkingspaces.viewmodel

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
    private val carParkingService: ParkingSpaceService<Car>,
    private val motorcycleParkingSpaceService: ParkingSpaceService<Motorcycle>,
    private val carRegisterService: CarRegisterService,
    private val motorcycleRegisterService: MotorcycleRegisterService
) : ViewModel(), ParkingActions {

    private val _parkingUiState =
        MutableStateFlow<ParkingUiState>(ParkingUiState.ParkingSuccessState(listOf(), listOf()))
    val parkingUiState: StateFlow<ParkingUiState>
        get() = _parkingUiState

    init {
        getParkingSpaces()
    }

    private fun getParkingSpaces() {
        executeTask(
            ::onGetParkingSpacesSuccess,
            ::onGetParkingSpacesFailed
        ) {
            getCombinedResult()
        }
    }

    private fun onGetParkingSpacesFailed(throwable: Throwable) {
        _parkingUiState.value = ParkingUiState.ParkingErrorState(throwable.message ?: "error")
    }

    private fun onGetParkingSpacesSuccess(parkingSuccessState: ParkingUiState.ParkingSuccessState) {
        _parkingUiState.value = parkingSuccessState
    }


    private suspend fun getCombinedResult(): ParkingUiState.ParkingSuccessState {
        val carSpaces = carParkingService.getParkingSpaces()
        val motoSpaces = motorcycleParkingSpaceService.getParkingSpaces()
        val carRegisteredSpaces = carRegisterService.getRegisteredSpaces()
        val motoRegisteredSpaces = motorcycleRegisterService.getRegisteredSpaces()
        Log.d(TAG, "carSpaces: ${carSpaces.size}")
        Log.d(TAG, "motoSpaces: ${motoSpaces.size}")
        Log.d(TAG, "carRegisteredSpaces: ${carRegisteredSpaces.size}")
        Log.d(TAG, "motoRegisteredSpaces: ${motoRegisteredSpaces.size}")
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