package com.stevecampos.cisample.features.register.car.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.stevecampos.cisample.R
import com.stevecampos.cisample.features.parking.ui.FailedToLoadWidget
import com.stevecampos.cisample.features.parking.ui.LoadingWidget
import com.stevecampos.cisample.features.register.car.vm.RegisterCarViewModel
import org.koin.androidx.compose.get

@Composable
fun RegisterCarRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterCarViewModel = get()
) {
    val uiState: RegisterCarUiState by viewModel.registerCarViewState.collectAsState()

    AuthorScreen(
        registerCarState = uiState,
        onBackClick = onBackClick,
        modifier = modifier,
        onRegisterVehicleClicked = { plate: String, starDate: String ->
            viewModel.registerCar(
                plate,
                starDate
            )
        }
    )
}

@VisibleForTesting
@Composable
internal fun AuthorScreen(
    registerCarState: RegisterCarUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onRegisterVehicleClicked: (String, String) -> Unit
) {


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                title = {
                    Text(text = stringResource(R.string.activity_register_car_title))
                },
            )
        }
    ) {

        when (registerCarState) {
            is RegisterCarUiState.RegisterCarInit -> RegisterCarForm(
                registerCarState.parkingSpaceId,
                modifier,
                onRegisterVehicleClicked
            )
            is RegisterCarUiState.RegisterCarLoading -> LoadingWidget()
            is RegisterCarUiState.RegisterCarError -> FailedToLoadWidget(errorTxt = registerCarState.errorMsg) {}
            is RegisterCarUiState.RegisterCarSuccess -> Text("Success")
        }
    }
}

@Composable
fun RegisterCarForm(
    parkingSpaceId: Int,
    modifier: Modifier,
    onRegisterVehicleClicked: (String, String) -> Unit
) {
    val carPlateText = rememberSaveable { mutableStateOf("") }
    val startDateText = rememberSaveable { mutableStateOf("") }
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("Parking Space Position: $parkingSpaceId")
        }
        item {
            TextField(
                value = carPlateText.value,
                onValueChange = { carPlateText.value = it },
                label = {
                    Text(text = "Car's Plate")
                }
            )
        }
        item {
            TextField(
                value = startDateText.value,
                onValueChange = { startDateText.value = it },
                label = {
                    Text(text = "startDate")
                }
            )
        }
        item {
            TextButton(onClick = {
                onRegisterVehicleClicked.invoke(carPlateText.value, startDateText.value)
            }) {
                Text(text = ("Register"))
            }
        }
    }
}
