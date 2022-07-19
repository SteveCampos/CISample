@file:OptIn(ExperimentalFoundationApi::class)

package com.stevecampos.cisample.parkingspaces.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.stevecampos.cisample.R
import com.stevecampos.cisample.parkingspaces.viewmodel.ParkingUiState
import com.stevecampos.cisample.parkingspaces.viewmodel.ParkingViewModel
import com.stevecampos.cisample.shared.composecomponents.FailedToLoadWidget
import com.stevecampos.cisample.shared.composecomponents.LoadingWidget
import com.stevecampos.cisample.shared.theme.CISampleTheme
import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.domain.vehicle.entity.Motorcycle


@Composable
fun ParkingRoute(
    modifier: Modifier = Modifier,
    viewModel: ParkingViewModel = hiltViewModel(),
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit,
    onMotorcycleRegisteredSpaceSelected: (RegisteredSpace<Motorcycle>) -> Unit,
    onMotorcycleEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) {
    val uiState: ParkingUiState by viewModel.parkingUiState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(LocalLifecycleOwner.current) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getParkingSpaces()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    ParkingScreen(
        parkingState = uiState,
        modifier = modifier,
        onCarRegisteredSpaceSelected = onCarRegisteredSpaceSelected,
        onCarEmptyParkSpaceSelected = onCarEmptyParkSpaceSelected,
        onMotorcycleRegisteredSpaceSelected = onMotorcycleRegisteredSpaceSelected,
        onMotorcycleEmptyParkSpaceSelected = onMotorcycleEmptyParkSpaceSelected,
    )
}

@Composable
internal fun ParkingScreen(
    parkingState: ParkingUiState,
    modifier: Modifier = Modifier,
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit,
    onMotorcycleRegisteredSpaceSelected: (RegisteredSpace<Motorcycle>) -> Unit,
    onMotorcycleEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                }
            )
        }
    ) {
        when (parkingState) {
            is ParkingUiState.ParkingLoadingState -> LoadingWidget()
            is ParkingUiState.ParkingErrorState -> FailedToLoadWidget(parkingState.errorMsg) {}
            is ParkingUiState.ParkingSuccessState -> ParkingBody(
                modifier = modifier,
                carSpacesFilled = parkingState.carSpacesFilled,
                motorcycleSpacesFilled = parkingState.motoSpacesFilled,
                onCarRegisteredSpaceSelected = onCarRegisteredSpaceSelected,
                onCarEmptyParkSpaceSelected = onCarEmptyParkSpaceSelected,
                onMotorcycleRegisteredSpaceSelected = onMotorcycleRegisteredSpaceSelected,
                onMotorcycleEmptyParkSpaceSelected = onMotorcycleEmptyParkSpaceSelected,
            )
        }
    }
}

@Composable
fun ParkingBody(
    modifier: Modifier = Modifier,
    carSpacesFilled: List<Pair<ParkingSpace, RegisteredSpace<Car>?>>,
    motorcycleSpacesFilled: List<Pair<ParkingSpace, RegisteredSpace<Motorcycle>?>>,
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit,
    onMotorcycleRegisteredSpaceSelected: (RegisteredSpace<Motorcycle>) -> Unit,
    onMotorcycleEmptyParkSpaceSelected: (ParkingSpace) -> Unit

) {
    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                //.background(Blue)
                .weight(1f)
                .padding(8.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                MotorcycleHeader()
                LazyColumn(
                    modifier, horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(top = 8.dp)
                ) {
                    motorcycleSpacesBody(
                        motorcycleSpacesFilled = motorcycleSpacesFilled,
                        onMotorcycleRegisteredSpaceSelected = onMotorcycleRegisteredSpaceSelected,
                        onMotorcycleEmptyParkSpaceSelected = onMotorcycleEmptyParkSpaceSelected
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.White)
                .weight(2f)
                .padding(8.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CarHeader()
                LazyVerticalGrid(
                    modifier = modifier,
                    cells = GridCells.Fixed(2),
                    contentPadding = PaddingValues(top = 8.dp)
                ) {
                    carSpacesBody(
                        carSpacesFilled = carSpacesFilled,
                        onCarRegisteredSpaceSelected = onCarRegisteredSpaceSelected,
                        onCarEmptyParkSpaceSelected = onCarEmptyParkSpaceSelected
                    )
                }
            }

        }
    }
}

@Preview(name = "phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Preview(name = "landscape", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480")
@Preview(name = "foldable", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480")
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
@Composable
fun ParkingScreenLoading() {
    CISampleTheme {
        ParkingScreen(
            parkingState = ParkingUiState.ParkingLoadingState,
            modifier = Modifier,
            {}, {}, {}, {}
        )
    }
}


@Preview(name = "phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Preview(name = "landscape", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480")
@Preview(name = "foldable", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480")
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
@Composable
fun ParkingScreenError() {
    CISampleTheme {
        ParkingScreen(
            parkingState = ParkingUiState.ParkingErrorState(errorMsg = stringResource(id = R.string.activity_parking_msg_error)),
            modifier = Modifier,
            {}, {}, {}, {}
        )
    }
}