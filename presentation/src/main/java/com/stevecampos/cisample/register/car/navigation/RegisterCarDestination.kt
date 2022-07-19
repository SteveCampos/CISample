package com.stevecampos.cisample.register.car.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stevecampos.cisample.register.car.ui.RegisterCarRoute

class RegisterCarDestination {
    companion object {
        const val route = "car_register_route"
        const val parkingSpaceIdArg = "parkingSpaceId"
    }
}

fun NavGraphBuilder.registerCarGraph(
    onBackClick: () -> Unit,
) {
    composable(
        route = "${RegisterCarDestination.route}/{${RegisterCarDestination.parkingSpaceIdArg}}",
        arguments = listOf(
            navArgument(RegisterCarDestination.parkingSpaceIdArg) {
                type = NavType.IntType
            }
        )
    ) {
        RegisterCarRoute(
            onBackClick = onBackClick
        )
    }
}