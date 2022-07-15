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
import com.stevecampos.domain.vehicle.entity.Motorcycle

fun LazyListScope.motorcycleSpacesBody(
    motorcycleSpacesFilled: List<Pair<ParkingSpace, RegisteredSpace<Motorcycle>?>>,
    onMotorcycleRegisteredSpaceSelected: (RegisteredSpace<Motorcycle>) -> Unit,
    onMotorcycleEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) {
    item {
        MotorcycleHeader()
    }
    motorcycleSpacesItems(
        items = motorcycleSpacesFilled,
        onMotorcycleRegisteredSpaceSelected = onMotorcycleRegisteredSpaceSelected,
        onMotorcycleEmptyParkSpaceSelected = onMotorcycleEmptyParkSpaceSelected
    )
}

@Composable
fun MotorcycleHeader() {
    Text(text = "MotorcycleSpaces")
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
            FilledMotorcycleParkingSpace(item.first, item.second!!, onMotorcycleRegisteredSpaceSelected)
        } ?: kotlin.run {
            EmptyParkingSpace(item.first, onMotorcycleEmptyParkSpaceSelected)
        }
    }
)

@Composable
fun FilledMotorcycleParkingSpace(
    first: ParkingSpace,
    second: RegisteredSpace<Motorcycle>,
    onMotorcycleRegisteredSpaceSelected: (RegisteredSpace<Motorcycle>) -> Unit
) {
    CardItem("FilledParkingSpace: ${second.vehicle.plate}") {
        onMotorcycleRegisteredSpaceSelected.invoke(second)
    }
}