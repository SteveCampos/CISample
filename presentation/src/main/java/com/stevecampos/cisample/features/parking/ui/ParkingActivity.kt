package com.stevecampos.cisample.features.parking.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
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
import com.stevecampos.cisample.R
import com.stevecampos.cisample.features.parking.vm.ParkingViewModel
import com.stevecampos.cisample.ui.theme.CISampleTheme
import org.koin.androidx.compose.get


class ParkingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CISampleTheme {
                ParkingApp()
            }
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
fun VehicleItem(title: String) {
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CISampleTheme {
        ParkingApp()
    }
}