package com.stevecampos.cisample.features.register.car.navigation

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stevecampos.cisample.features.register.car.ui.RegisterCarRoute
import com.stevecampos.cisample.features.register.car.vm.RegisterCarViewModel
import org.koin.androidx.compose.get

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
        val parkingSpaceId = it.arguments?.getInt(RegisterCarDestination.parkingSpaceIdArg) ?: 0
        Log.d("registerCarGraph", "parkingSpaceIdArg ${it.arguments}, ${it.savedStateHandle}")
        RegisterCarRoute(
            onBackClick = onBackClick,
            viewModel = RegisterCarViewModel(
                parkingSpaceId,
                //savedStateHandle = it.savedStateHandle,
                get()
            )
        )
    }
}