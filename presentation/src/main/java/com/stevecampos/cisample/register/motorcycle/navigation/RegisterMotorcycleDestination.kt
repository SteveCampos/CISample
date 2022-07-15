package com.stevecampos.cisample.register.motorcycle.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stevecampos.cisample.register.motorcycle.ui.RegisterMotorcycleRoute
import com.stevecampos.cisample.register.motorcycle.vm.RegisterMotorcycleViewModel
import org.koin.androidx.compose.get

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
        val parkingSpaceId = it.arguments?.getInt(RegisterMotorcycleDestination.parkingSpaceIdArg) ?: 0
        Log.d("registerMotorcycleGraph", "parkingSpaceIdArg ${it.arguments}, ${it.savedStateHandle}")
        RegisterMotorcycleRoute(
            onBackClick = onBackClick,
            viewModel = RegisterMotorcycleViewModel(
                parkingSpaceId,
                //savedStateHandle = it.savedStateHandle,
                get()
            )
        )
    }
}