package com.stevecampos.cisample.parkingSpaces

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.stevecampos.cisample.parkingspaces.ui.ParkingScreen
import com.stevecampos.cisample.parkingspaces.vm.ParkingUiState
import com.stevecampos.cisample.shared.theme.CISampleTheme
import org.junit.Rule
import org.junit.Test

class ParkingScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun parkingScreen_whenLoadingState_shouldLoadingWidgetBeDisplayed() {
        // Start the app
        composeTestRule.setContent {
            CISampleTheme {
                ParkingScreen(
                    parkingState = ParkingUiState.ParkingLoadingState,
                    onCarEmptyParkSpaceSelected = {},
                    onCarRegisteredSpaceSelected = {},
                    onMotorcycleRegisteredSpaceSelected = {},
                    onMotorcycleEmptyParkSpaceSelected = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("LoadingWidgetTestTag").assertIsDisplayed()
    }
    @Test
    fun parkingScreen_whenErrorState_shouldFailedToLoadWidgetBeDisplayed() {
        // Start the app
        composeTestRule.setContent {
            CISampleTheme {
                ParkingScreen(
                    parkingState = ParkingUiState.ParkingErrorState("errorMsg"),
                    onCarEmptyParkSpaceSelected = {},
                    onCarRegisteredSpaceSelected = {},
                    onMotorcycleRegisteredSpaceSelected = {},
                    onMotorcycleEmptyParkSpaceSelected = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("FailedToLoadWidgetTestTag").assertIsDisplayed()
    }
}