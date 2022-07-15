package com.stevecampos.cisample.register.motorcycle.ui

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
import com.stevecampos.cisample.register.motorcycle.vm.RegisterMotorcycleViewModel
import com.stevecampos.cisample.shared.composecomponents.FailedToLoadWidget
import com.stevecampos.cisample.shared.composecomponents.LoadingWidget
import org.koin.androidx.compose.get

@Composable
fun RegisterMotorcycleRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterMotorcycleViewModel = get()
) {
    val uiState: RegisterMotorcycleUiState by viewModel.registerMotorcycleViewState.collectAsState()

    RegisterMotorcycleScreen(
        registerMotorcycleState = uiState,
        onBackClick = onBackClick,
        modifier = modifier,
        onRegisterVehicleClicked = { plate: String, cylinderCapacity: String, starDate: String ->
            viewModel.registerMotorcycle(
                plate,
                cylinderCapacity,
                starDate
            )
        }
    )
}

@VisibleForTesting
@Composable
internal fun RegisterMotorcycleScreen(
    registerMotorcycleState: RegisterMotorcycleUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onRegisterVehicleClicked: (String, String, String) -> Unit
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
                    Text(text = "Register Motorcycle")
                },
            )
        }
    ) {

        when (registerMotorcycleState) {
            is RegisterMotorcycleUiState.RegisterMotorcycleInit -> RegisterMotorcycleForm(
                registerMotorcycleState.parkingSpaceId,
                modifier,
                onRegisterVehicleClicked
            )
            is RegisterMotorcycleUiState.RegisterMotorcycleLoading -> LoadingWidget()
            is RegisterMotorcycleUiState.RegisterMotorcycleError -> FailedToLoadWidget(errorTxt = registerMotorcycleState.errorMsg) {}
            is RegisterMotorcycleUiState.RegisterMotorcycleSuccess -> Text("Success")
        }
    }
}

@Composable
fun RegisterMotorcycleForm(
    parkingSpaceId: Int,
    modifier: Modifier,
    onRegisterVehicleClicked: (String, String, String) -> Unit
) {
    val motorcyclePlateText = rememberSaveable { mutableStateOf("") }
    val motorcycleCylinderCapacityText = rememberSaveable { mutableStateOf("") }
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
                value = motorcyclePlateText.value,
                onValueChange = { motorcyclePlateText.value = it },
                label = {
                    Text(text = "Motorcycle's Plate")
                }
            )
        }
        item {
            TextField(
                value = motorcycleCylinderCapacityText.value,
                onValueChange = { motorcycleCylinderCapacityText.value = it },
                label = {
                    Text(text = "Motorcycle's Cylinder Capacity")
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
                onRegisterVehicleClicked.invoke(
                    motorcyclePlateText.value,
                    motorcycleCylinderCapacityText.value,
                    startDateText.value
                )
            }) {
                Text(text = ("Register"))
            }
        }
    }
}
