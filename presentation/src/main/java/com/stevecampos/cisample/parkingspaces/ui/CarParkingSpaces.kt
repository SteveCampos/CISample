package com.stevecampos.cisample.parkingspaces.ui

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stevecampos.cisample.shared.composecomponents.CardItem
import com.stevecampos.cisample.shared.composecomponents.EmptyParkingSpace
import com.stevecampos.domain.register.aggregate.RegisteredSpace
import com.stevecampos.domain.register.entity.ParkingSpace
import com.stevecampos.domain.vehicle.entity.Car

 fun LazyListScope.carSpacesBody(
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

 fun LazyListScope.carSpacesItems(
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
            EmptyParkingSpace(item.first, onCarEmptyParkSpaceSelected)
        }
    }
)

@Composable
fun FilledCarParkingSpace(
    first: ParkingSpace,
    second: RegisteredSpace<Car>,
    onCarRegisteredSpaceSelected: (RegisteredSpace<Car>) -> Unit
) {
    CardItem("FilledParkingSpace: ${second.vehicle.plate}") {
        onCarRegisteredSpaceSelected.invoke(second)
    }
}