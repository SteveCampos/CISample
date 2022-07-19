package com.stevecampos.cisample.register.motorcycle.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stevecampos.cisample.register.motorcycle.ui.RegisterMotorcycleRoute

class RegisterMotorcycleDestination {
    companion object {
        const val route = "motorcycle_register_route"
        const val parkingSpaceIdArg = "parkingSpaceId"
    }
}

fun NavGraphBuilder.registerMotorcycleGraph(
    onBackClick: () -> Unit,
) {
    composable(
        route = "${RegisterMotorcycleDestination.route}/{${RegisterMotorcycleDestination.parkingSpaceIdArg}}",
        arguments = listOf(
            navArgument(RegisterMotorcycleDestination.parkingSpaceIdArg) {
                type = NavType.IntType
            }
        )
    ) {
        RegisterMotorcycleRoute(
            onBackClick = onBackClick
        )
    }
}