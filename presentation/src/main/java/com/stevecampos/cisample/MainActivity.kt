package com.stevecampos.cisample

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stevecampos.cisample.features.parking.ParkingViewModel
import com.stevecampos.cisample.features.parking.viewstate.ParkingViewState
import com.stevecampos.cisample.ui.theme.CISampleTheme
import com.stevecampos.domain.entity.Car
import com.stevecampos.domain.entity.Motorcycle
import com.stevecampos.domain.entity.Vehicle
import org.koin.androidx.compose.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CISampleTheme {
                MyParkingApp()
            }
        }
    }


}

@Composable
fun MyParkingApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) {
        HandlingParkingViewStates()
    }
}

@Composable
fun HandlingParkingViewStates(parkingViewModel: ParkingViewModel = get()) {
    val uiState =
        parkingViewModel.viewState.observeAsState(ParkingViewState.GetParkingSpacesLoading)
    val posibleStates = uiState.value

    when (posibleStates) {
        is ParkingViewState.GetParkingSpacesLoading,
        ParkingViewState.CalculateParkingCostLoading,
        ParkingViewState.AddVehicleToParkingLoading,
        ParkingViewState.DeleteVehicleFromParkingSpaceLoading -> LoadingWidget()

        is ParkingViewState.GetParkingSpacesFailed -> FailedToLoadWidget(posibleStates.message, {})
        is ParkingViewState.CalculateParkingCostFailed -> FailedToLoadWidget(
            posibleStates.message,
            {})
        is ParkingViewState.AddVehicleToParkingFailed -> FailedToLoadWidget(
            posibleStates.message,
            {})
        is ParkingViewState.DeleteVehicleFromParkingSpaceFailed -> FailedToLoadWidget(posibleStates.message,
            {})
        is ParkingViewState.GetParkingSpacesSuccess -> VehicleListWidget(
            posibleStates.spaces.map { it.vehicle }
        ) {
            parkingViewModel.calculateParkingCost(it, 1, 3)
        }
        is ParkingViewState.AddVehicleToParkingSuccess -> AddVehicleToParkingSuccessWidget()
        is ParkingViewState.DeleteVehicleFromParkingSpaceSuccess -> DeleteVehicleFromParkingSpaceSuccessWidget()
        is ParkingViewState.CalculateParkingCostSuccess -> CalculateParkingCostSuccessWidget(
            posibleStates.cost
        )
    }
}

@Composable
fun DeleteVehicleFromParkingSpaceSuccessWidget() {
    Text(text = "DeleteVehicleFromParkingSpaceSuccessWidget")
}

@Composable
fun AddVehicleToParkingSuccessWidget() {
    Text(text = "AddVehicleToParkingSuccessWidget")
}

@Composable
fun CalculateParkingCostSuccessWidget(pricingCost: Double) {
    Text(text = "ParkingCost: $pricingCost")
}


@Composable
fun VehicleListWidget(items: List<Vehicle>, onCalculateClicked: (Vehicle) -> Unit) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(items) { v ->
            VehicleItem(
                title = "PLATE: ${v.plate()}",
                desc = if (v is Motorcycle) "Motorcycle cc: ${v.cylinderCapacity}" else "Car",
                onCalculateClicked = {
                    onCalculateClicked.invoke(v)
                }
            )
        }
    }
}

@Composable
fun VehicleItem(title: String, desc: String, onCalculateClicked: () -> Unit = {}) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = title,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h6
            )
            Text(text = desc, modifier = Modifier.padding(start = 8.dp))
            TextButton(
                onClick = onCalculateClicked
            ) {
                Text(
                    text = "CalculateParkingCost"
                )
            }
            /*Row {
                Icon(
                    Icons.Outlined.Phone,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = stringResource(id = R.string.activity_list_user_cd_phone)
                )
                Text(text = user.phone, modifier = Modifier.padding(start = 8.dp))
            }
            Row {
                Icon(
                    Icons.Outlined.Email,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = stringResource(id = R.string.activity_list_user_cd_email)
                )
                Text(text = user.email, modifier = Modifier.padding(start = 8.dp))
            }
            if (showBttn) TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = onClick
            ) {
                Text(
                    text = stringResource(id = R.string.activity_list_user_msg_show_post).toUpperCase(
                        Locale.current
                    )
                )
            }*/
        }
    }
}

@Composable
fun LoadingWidget() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun FailedToLoadWidget(errorTxt: String, onRefreshBttnClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(R.drawable.ic_launcher_background),
            contentDescription = errorTxt,
            modifier = Modifier.fillMaxSize(.4f)
        )
        Text(text = errorTxt)
        TextButton(onClick = onRefreshBttnClicked) {
            Text(stringResource(id = R.string.activity_msg_reload))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CISampleTheme {
        MyParkingApp()
    }
}