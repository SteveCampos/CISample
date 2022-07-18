package com.stevecampos.cisample.parkingspaces.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
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
import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Motorcycle

fun LazyListScope.motorcycleSpacesBody(
    motorcycleSpacesFilled: List<Pair<ParkingSpace, RegisteredSpace<Motorcycle>?>>,
    onMotorcycleRegisteredSpaceSelected: (RegisteredSpace<Motorcycle>) -> Unit,
    onMotorcycleEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) {
    motorcycleSpacesItems(
        items = motorcycleSpacesFilled,
        onMotorcycleRegisteredSpaceSelected = onMotorcycleRegisteredSpaceSelected,
        onMotorcycleEmptyParkSpaceSelected = onMotorcycleEmptyParkSpaceSelected
    )
}

@Composable
fun MotorcycleHeader() {
    Text(text = "Motos")
}

fun LazyListScope.motorcycleSpacesItems(
    items: List<Pair<ParkingSpace, RegisteredSpace<Motorcycle>?>>,
    itemModifier: Modifier = Modifier,
    onMotorcycleRegisteredSpaceSelected: (RegisteredSpace<Motorcycle>) -> Unit,
    onMotorcycleEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) = items(
    items = items,
    //key = {},
    itemContent = { item ->
        item.second?.let {
            FilledMotorcycleParkingSpace(
                item.first,
                item.second!!,
                onMotorcycleRegisteredSpaceSelected
            )
        } ?: kotlin.run {
            EmptyMotorcycleSpace(item.first) {
                onMotorcycleEmptyParkSpaceSelected.invoke(item.first)
            }
        }
    }
)


@Composable
fun EmptyMotorcycleSpace(parkingSpace: ParkingSpace, onItemClicked: () -> Unit) {
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
                painter = painterResource(R.drawable.motorcycle),
                contentDescription = stringResource(id = R.string.activity_parking_msg_empyty_motorcycle_space),
                contentScale = ContentScale.FillWidth,
                alpha = .1f
            )
            Text(
                text = "M:${parkingSpace.id}",
                color = Color.LightGray,
                style = MaterialTheme.typography.subtitle2,
            )
        }
    }
}

@Composable
fun FilledMotorcycleParkingSpace(
    parkingSpace: ParkingSpace,
    registeredMotorcycle: RegisteredSpace<Motorcycle>,
    onMotorcycleRegisteredSpaceSelected: (RegisteredSpace<Motorcycle>) -> Unit
) {

    val plate = registeredMotorcycle.vehicle.plate
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp)
            .fillMaxWidth()
            .clickable {
                onMotorcycleRegisteredSpaceSelected.invoke(registeredMotorcycle)
            },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.motorcycle),
                contentDescription = stringResource(id = R.string.activity_parking_msg_filled_motorcycle_space),
                contentScale = ContentScale.FillWidth,
            )
            Text(
                text = plate,
                color = Color.DarkGray,
                style = MaterialTheme.typography.subtitle1,
            )
        }
    }
}
