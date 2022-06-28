package com.stevecampos.cisample.features.parking.viewstate

import com.stevecampos.domain.aggregate.ParkingSpace
import com.stevecampos.domain.entity.Vehicle

interface ParkingViewModelActions {
    fun getParkingSpaces()
    fun addVehicleToParking(vehicle: Vehicle)
    fun resetParkingSpaceFor(vehicle: Vehicle)
    fun calculateParkingCost(vehicle: Vehicle, days: Int, hours: Int)
}

sealed class ParkingViewState {

    object GetParkingSpacesLoading : ParkingViewState()
    data class GetParkingSpacesFailed(val message: String) : ParkingViewState()
    data class GetParkingSpacesSuccess(val spaces: List<ParkingSpace>) : ParkingViewState()

    object AddVehicleToParkingLoading : ParkingViewState()
    data class AddVehicleToParkingFailed(val message: String) : ParkingViewState()
    object AddVehicleToParkingSuccess : ParkingViewState()


    object DeleteVehicleFromParkingSpaceLoading : ParkingViewState()
    data class DeleteVehicleFromParkingSpaceFailed(val message: String) : ParkingViewState()
    object DeleteVehicleFromParkingSpaceSuccess : ParkingViewState()

    object CalculateParkingCostLoading : ParkingViewState()
    data class CalculateParkingCostFailed(val message: String) : ParkingViewState()
    data class CalculateParkingCostSuccess(val cost: Double) : ParkingViewState()

}