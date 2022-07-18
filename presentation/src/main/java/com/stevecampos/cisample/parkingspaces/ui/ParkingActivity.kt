package com.stevecampos.cisample.parkingspaces.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.stevecampos.cisample.parkingspaces.destination.ParkingDestination
import com.stevecampos.cisample.parkingspaces.destination.parkingRegisterGraph
import com.stevecampos.cisample.register.car.navigation.RegisterCarDestination
import com.stevecampos.cisample.register.car.navigation.registerCarGraph
import com.stevecampos.cisample.register.motorcycle.navigation.RegisterMotorcycleDestination
import com.stevecampos.cisample.register.motorcycle.navigation.registerMotorcycleGraph
import com.stevecampos.cisample.shared.theme.CISampleTheme

class ParkingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CISampleTheme {
                ParkingNavController()
            }
        }
    }
}

@Composable
fun ParkingNavController() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ParkingDestination.route
    ) {
        parkingRegisterGraph(
            onCarEmptyParkSpaceSelected = {
                navController.navigate("${RegisterCarDestination.route}/${it.id}")
            }, onCarRegisteredSpaceSelected = {

            },
            onMotorcycleEmptyParkSpaceSelected = {
                navController.navigate("${RegisterMotorcycleDestination.route}/${it.id}")
            },
            onMotorcycleRegisteredSpaceSelected = {}
        )
        registerCarGraph {
            navController.popBackStack()
        }
        registerMotorcycleGraph {
            navController.popBackStack()
        }
    }
}
