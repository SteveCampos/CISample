package com.stevecampos.cisample.features.parking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.register.service.*
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.domain.vehicle.entity.Motorcycle

class ParkingViewModel(
    private val carParkingService: CarParkingSpaceService,
    private val motorcycleParkingSpaceService: MotorcycleParkingSpaceService,
    private val carRegisterService: CarRegisterService,
    private val motorcycleRegisterService: MotorcycleRegisterService
) : ViewModel(), ParkingActions {


    //private val _viewState =
    //    MutableLiveData<ParkingViewState>(ParkingViewState.GetParkingSpacesLoading)
    //val viewState: LiveData<ParkingViewState> = _viewState

    val carParkingSpaces = MutableLiveData<List<ParkingSpace>>(listOf())

    companion object {
        const val TAG = "ParkingViewModel"
    }

    init {
        Log.d(TAG, "init")
        getCarParkingSpaces()
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
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onFailure.invoke(e)
            }
        }
    }
}