package com.stevecampos.cisample.features.parking.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.stevecampos.cisample.R
import com.stevecampos.cisample.features.parking.destination.ParkingDestination
import com.stevecampos.cisample.features.parking.destination.parkingRegisterGraph
import com.stevecampos.cisample.features.parking.vm.ParkingUiState
import com.stevecampos.cisample.features.parking.vm.ParkingViewModel
import com.stevecampos.cisample.features.register.car.navigation.RegisterCarDestination
import com.stevecampos.cisample.features.register.car.navigation.registerCarGraph
import com.stevecampos.cisample.ui.theme.CISampleTheme
import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Car
import com.stevecampos.domain.vehicle.entity.Motorcycle
import org.koin.androidx.compose.get


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
        parkingRegisterGraph(onCarEmptyParkSpaceSelected = {
            navController.navigate("${RegisterCarDestination.route}/${it.id}")
        }, onCarRegisteredSpaceSelected = {

        })
        registerCarGraph {}
    }
}

@Composable
fun ParkingRoute(
    modifier: Modifier = Modifier,
    viewModel: ParkingViewModel = get(),
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) {
    val uiState: ParkingUiState by viewModel.parkingUiState.collectAsState()


    ParkingScreen(
        parkingState = uiState,
        modifier = modifier,
        onCarRegisteredSpaceSelected = onCarRegisteredSpaceSelected,
        onCarEmptyParkSpaceSelected = onCarEmptyParkSpaceSelected,
    )
}

@Composable
internal fun ParkingScreen(
    parkingState: ParkingUiState,
    modifier: Modifier = Modifier,
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) {
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
        when (parkingState) {
            is ParkingUiState.ParkingLoadingState -> LoadingWidget()
            is ParkingUiState.ParkingErrorState -> FailedToLoadWidget(parkingState.errorMsg) {}
            is ParkingUiState.ParkingSuccessState -> ParkingBody(
                modifier = modifier,
                carSpacesFilled = parkingState.carSpacesFilled,
                motoSpacesFilled = parkingState.motoSpacesFilled,
                onCarRegisteredSpaceSelected = onCarRegisteredSpaceSelected,
                onCarEmptyParkSpaceSelected = onCarEmptyParkSpaceSelected
            )
        }
    }
}

@Composable
fun ParkingBody(
    modifier: Modifier = Modifier,
    carSpacesFilled: List<Pair<ParkingSpace, RegisteredSpace<Car>?>>,
    motoSpacesFilled: List<Pair<ParkingSpace, RegisteredSpace<Motorcycle>?>>,
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit
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
            LazyColumn(modifier) {
                carSpacesBody(
                    carSpacesFilled = carSpacesFilled,
                    onCarRegisteredSpaceSelected = onCarRegisteredSpaceSelected,
                    onCarEmptyParkSpaceSelected = onCarEmptyParkSpaceSelected
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(Yellow)
                .weight(1f)
                .padding(8.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            LazyColumn(modifier) {
                carSpacesBody(
                    carSpacesFilled = carSpacesFilled,
                    onCarRegisteredSpaceSelected = onCarRegisteredSpaceSelected,
                    onCarEmptyParkSpaceSelected = onCarEmptyParkSpaceSelected
                )
            }
        }
    }
}

private fun LazyListScope.carSpacesBody(
    carSpacesFilled: List<Pair<ParkingSpace, RegisteredSpace<Car>?>>,
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) {
    item {
        CarHeader()
    }
    carSpacesItems(
        items = carSpacesFilled,
        onCarRegisteredSpaceSelected = onCarRegisteredSpaceSelected,
        onCarEmptyParkSpaceSelected = onCarEmptyParkSpaceSelected
    )
}

@Composable
fun CarHeader() {
    Text(text = "CarSpaces")
}

private fun LazyListScope.carSpacesItems(
    items: List<Pair<ParkingSpace, RegisteredSpace<Car>?>>,
    itemModifier: Modifier = Modifier,
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) = items(
    items = items,
    //key = {},
    itemContent = { item ->
        item.second?.let {
            FilledParkingSpace(item.first, item.second!!, onCarRegisteredSpaceSelected)
        } ?: kotlin.run {
            EmptyParkingSpace(item.first, onCarEmptyParkSpaceSelected)
        }
    }
)

@Composable
fun FilledParkingSpace(
    first: ParkingSpace,
    second: RegisteredSpace<Car>,
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit
) {
    VehicleItem("FilledParkingSpace: ${second.vehicle.plate}") {
        onCarRegisteredSpaceSelected.invoke(second)
    }
}

@Composable
fun EmptyParkingSpace(
    parkingSpace: ParkingSpace,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) {
    VehicleItem("EmptyParkingSpace: ${parkingSpace.id}") {
        onCarEmptyParkSpaceSelected.invoke(parkingSpace)
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
        /*Image(
            painterResource(R.drawable.ic_launcher_background),
            contentDescription = errorTxt,
            modifier = Modifier.fillMaxSize(.4f)
        )*/
        Text(text = errorTxt)
        TextButton(onClick = onRefreshBttnClicked) {
            Text(stringResource(id = R.string.activity_msg_reload))
        }
    }
}

@Composable
fun ParkingApp() {
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
        CarParkingSpacesListWidget()
        //ParkingSize()
    }
}

@Composable
fun CarParkingSpacesListWidget(parkingViewModel: ParkingViewModel = get()) {
    val carParkingSpaces = parkingViewModel.carParkingSpaces.observeAsState().value ?: return
    LazyColumn {
        items(items = carParkingSpaces) { space ->
            VehicleItem(title = "C00${space.id}")
        }
    }
}

@Composable
fun VehicleItem(title: String, onItemClicked: () -> Unit = {}) {
    Card(

        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth()
            .clickable {
                onItemClicked()
            },
        elevation = 4.dp
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = title,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h6
            )
        }
    }
}


@Composable
fun ParkingSize() {
    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                //.background(Blue)
                .weight(1f)
                .padding(8.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRoundRect(
                    color = Blue, style = Stroke(
                        width = 2f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                )
            }
            CarParkingSpacesListWidget()
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(Yellow)
                .weight(1f)
                .padding(8.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Column() {
                Text(text = "Example", fontSize = 16.sp)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(Red)
                .weight(1f)
                .padding(8.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Column {

                Text(
                    text = "Example",
                    textAlign = TextAlign.End,
                    color = DarkGray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun EmptyParkSpaceWidget(position: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth(.7f)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(text = "P 10$position")
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
            {}, {}
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
            {},
            {}
        )
    }
}