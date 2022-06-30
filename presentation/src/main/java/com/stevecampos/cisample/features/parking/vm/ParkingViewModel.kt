package com.stevecampos.cisample.features.parking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stevecampos.cisample.features.parking.viewstate.ParkingViewModelActions
import com.stevecampos.cisample.features.parking.viewstate.ParkingViewState
import com.stevecampos.domain.aggregate.ParkingSpace
import com.stevecampos.domain.entity.Motorcycle
import com.stevecampos.domain.entity.Vehicle
import com.stevecampos.domain.service.ParkingService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParkingViewModel(private val parkingService: ParkingService) : ViewModel(),
    ParkingViewModelActions {


    private val _viewState =
        MutableLiveData<ParkingViewState>(ParkingViewState.GetParkingSpacesLoading)
    val viewState: LiveData<ParkingViewState> = _viewState

    companion object {
        const val TAG = "ParkingViewModel"
    }

    init {
        Log.d(TAG, "init")
        getParkingSpaces()
    }

    override fun getParkingSpaces() {
        _viewState.value = ParkingViewState.GetParkingSpacesLoading
        executeTask(
            ::onGetParkingSpaceSuccess,
            ::onGetParkingSpaceFailed
        ) {
            parkingService.getParkingSpaces()
        }
    }

    private fun onGetParkingSpaceFailed(throwable: Throwable) {
        Log.d(TAG, "onGetParkingSpaceFailed: $throwable")
        _viewState.value = ParkingViewState.GetParkingSpacesFailed(throwable.message ?: "error")
    }

    private fun onGetParkingSpaceSuccess(list: List<ParkingSpace>) {
        Log.d(TAG, "onGetParkingSpaceSuccess: $list")
        _viewState.value = ParkingViewState.GetParkingSpacesSuccess(list)
    }

    override fun addVehicleToParking(vehicle: Vehicle) {
        _viewState.value = ParkingViewState.AddVehicleToParkingLoading
        executeTask(
            ::onAddVehicleToParkingSuccess,
            ::onAddVehicleToParkingFailed
        ) {
            parkingService.addVehicleToParking(vehicle)
        }
    }

    private fun onAddVehicleToParkingFailed(throwable: Throwable) {
        Log.d(TAG, "onAddVehicleToParkingFailed: $throwable")
        _viewState.value = ParkingViewState.AddVehicleToParkingFailed(throwable.message ?: "error")
    }

    private fun onAddVehicleToParkingSuccess(unit: Unit) {
        Log.d(TAG, "onAddVehicleToParkingSuccess")
        _viewState.value = ParkingViewState.AddVehicleToParkingSuccess
    }

    override fun resetParkingSpaceFor(vehicle: Vehicle) {
        _viewState.value = ParkingViewState.DeleteVehicleFromParkingSpaceLoading
        executeTask(
            ::onResetParkingSpaceSuccess,
            ::onResetParkingSpaceFailed
        ) {
            parkingService.resetParkingSpace(vehicle)
        }
    }

    private fun onResetParkingSpaceFailed(throwable: Throwable) {
        Log.d(TAG, "onResetParkingSpaceFailed: $throwable")
        _viewState.value =
            ParkingViewState.DeleteVehicleFromParkingSpaceFailed(throwable.message ?: "error")
    }

    private fun onResetParkingSpaceSuccess(unit: Unit) {
        Log.d(TAG, "onResetParkingSpaceSuccess")
        _viewState.value = ParkingViewState.DeleteVehicleFromParkingSpaceSuccess

    }

    override fun calculateParkingCost(vehicle: Vehicle, days: Int, hours: Int) {
        _viewState.value = ParkingViewState.CalculateParkingCostLoading
        executeTask(
            ::onCalculateParkingCostSuccess,
            ::onCalculateParkingCostFailed,
        ) {
            parkingService.getParkingCostForVehicle(vehicle, days, hours)
        }
    }

    private fun onCalculateParkingCostFailed(throwable: Throwable) {
        Log.d(TAG, "onCalculateParkingCostFailed: $throwable")
        _viewState.value = ParkingViewState.CalculateParkingCostFailed(throwable.message ?: "error")
    }

    private fun onCalculateParkingCostSuccess(parkingCost: Double) {
        Log.d(TAG, "onCalculateParkingCostSuccess: $parkingCost")
        _viewState.value = ParkingViewState.CalculateParkingCostSuccess(parkingCost)
    }

}


*/fun <T> ViewModel.executeTask(
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