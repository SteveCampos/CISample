@file:OptIn(ExperimentalFoundationApi::class)

package com.stevecampos.cisample.parkingspaces.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyGridScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.stevecampos.cisample.R
import com.stevecampos.cisample.shared.composecomponents.EmptyParkingSpace
import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Car


fun LazyGridScope.carSpacesBody(
    carSpacesFilled: List<Pair<ParkingSpace, RegisteredSpace<Car>?>>,
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) {
    carSpacesItems(
        items = carSpacesFilled,
        onCarRegisteredSpaceSelected = onCarRegisteredSpaceSelected,
        onCarEmptyParkSpaceSelected = onCarEmptyParkSpaceSelected
    )
}

@Composable
fun CarHeader() {
    Text(text = "Cars")
}

fun LazyGridScope.carSpacesItems(
    items: List<Pair<ParkingSpace, RegisteredSpace<Car>?>>,
    itemModifier: Modifier = Modifier,
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) = items(
    items = items,
    //key = {},
    itemContent = { item ->
        item.second?.let {
            FilledCarParkingSpace(item.first, item.second!!, onCarRegisteredSpaceSelected)
        } ?: kotlin.run {
            //EmptyParkingSpace(item.first, onCarEmptyParkSpaceSelected)
            EmptyCarSpace(item.first) {
                onCarEmptyParkSpaceSelected.invoke(item.first)
            }
        }
    }
)

@Composable
fun EmptyCarSpace(parkingSpace: ParkingSpace, onItemClicked: () -> Unit) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp)
            .fillMaxWidth()
            .clickable {
                onItemClicked.invoke()
            },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.car),
                contentDescription = stringResource(id = R.string.activity_parking_msg_empyty_car_space),
                contentScale = ContentScale.FillWidth,
                alpha = .1f
            )
            Text(
                text = "C:${parkingSpace.id}",
                color = Color.LightGray,
                style = MaterialTheme.typography.subtitle2,
            )
        }
    }
}

@Composable
fun FilledCarParkingSpace(
    parkingSpace: ParkingSpace,
    registeredCar: RegisteredSpace<Car>,
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit
) {

    val plate = registeredCar.vehicle.plate
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp)
            .fillMaxWidth()
            .clickable {
                onCarRegisteredSpaceSelected.invoke(registeredCar)
            },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.car),
                contentDescription = stringResource(id = R.string.activity_parking_msg_filled_car_space),
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = plate,
                color = Color.DarkGray,
                style = MaterialTheme.typography.subtitle1,
            )
        }
    }
}
