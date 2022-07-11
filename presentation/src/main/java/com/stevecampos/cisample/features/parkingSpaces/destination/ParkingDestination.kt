package com.stevecampos.cisample.features.parkingSpaces.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stevecampos.cisample.features.parkingSpaces.ui.ParkingRoute
import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.domain.vehicle.entity.Motorcycle

class ParkingDestination {
    companion object {
        const val route = "parking_destination"
    }
}

fun NavGraphBuilder.parkingRegisterGraph(
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit,
    onMotorcycleRegisteredSpaceSelected: (RegisteredSpace<Motorcycle>) -> Unit,
    onMotorcycleEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) {
    composable(route = ParkingDestination.route) {
        ParkingRoute(
            onCarRegisteredSpaceSelected = onCarRegisteredSpaceSelected,
            onCarEmptyParkSpaceSelected = onCarEmptyParkSpaceSelected,
            onMotorcycleRegisteredSpaceSelected = onMotorcycleRegisteredSpaceSelected,
            onMotorcycleEmptyParkSpaceSelected = onMotorcycleEmptyParkSpaceSelected,
        )
    }
}