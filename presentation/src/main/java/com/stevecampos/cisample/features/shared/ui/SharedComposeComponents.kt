package com.stevecampos.cisample.features.shared.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.stevecampos.cisample.R

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