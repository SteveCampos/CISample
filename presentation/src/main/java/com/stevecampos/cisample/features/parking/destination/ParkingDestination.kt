package com.stevecampos.cisample.features.parking.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stevecampos.cisample.features.parking.ui.ParkingRoute
import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Car

class ParkingDestination {
    companion object {
        const val route = "parking_destination"
    }
}

fun NavGraphBuilder.parkingRegisterGraph(
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit) {
    composable(route = ParkingDestination.route) {
        ParkingRoute(
            onCarRegisteredSpaceSelected = onCarRegisteredSpaceSelected,
            onCarEmptyParkSpaceSelected = onCarEmptyParkSpaceSelected
        )
    }
}