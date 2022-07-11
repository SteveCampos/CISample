package com.stevecampos.cisample.features.parkingSpaces.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.stevecampos.cisample.features.parkingSpaces.destination.ParkingDestination
import com.stevecampos.cisample.features.parkingSpaces.destination.parkingRegisterGraph
import com.stevecampos.cisample.features.register.car.navigation.RegisterCarDestination
import com.stevecampos.cisample.features.register.car.navigation.registerCarGraph
import com.stevecampos.cisample.features.register.motorcycle.navigation.RegisterMotorcycleDestination
import com.stevecampos.cisample.features.register.motorcycle.navigation.registerMotorcycleGraph
import com.stevecampos.cisample.ui.theme.CISampleTheme

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
        registerCarGraph {}
        registerMotorcycleGraph { }
    }
}
