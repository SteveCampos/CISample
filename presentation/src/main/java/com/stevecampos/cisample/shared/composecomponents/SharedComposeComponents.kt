package com.stevecampos.cisample.shared.composecomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.stevecampos.cisample.R
import com.stevecampos.domain.register.entity.ParkingSpace

@Composable
fun LoadingWidget() {
    Box(modifier = Modifier.fillMaxSize().testTag("LoadingWidgetTestTag")) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun FailedToLoadWidget(errorTxt: String, onRefreshBttnClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().testTag("FailedToLoadWidgetTestTag"),
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
fun CardItem(title: String, onItemClicked: () -> Unit = {}) {
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
fun EmptyParkingSpace(
    parkingSpace: ParkingSpace,
    onCarEmptyParkSpaceSelected: (ParkingSpace) -> Unit
) {
    CardItem("EmptyParkingSpace: ${parkingSpace.id}") {
        onCarEmptyParkSpaceSelected.invoke(parkingSpace)
    }
}

