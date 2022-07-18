package com.stevecampos.cisample.parkingSpaces

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import com.stevecampos.cisample.parkingspaces.ui.ParkingScreen
import com.stevecampos.cisample.parkingspaces.viewmodel.ParkingUiState
import com.stevecampos.cisample.shared.theme.CISampleTheme
import org.junit.Rule
import org.junit.Test

class ParkingSpaceScreenTest {
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

        val semanticsMatcher = SemanticsMatcher.expectValue(
            SemanticsProperties.ProgressBarRangeInfo, ProgressBarRangeInfo.Indeterminate
        )
        composeTestRule.onNode(semanticsMatcher).assertIsDisplayed()
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

        val matcher = SemanticsMatcher.expectValue(
            SemanticsProperties.ContentDescription, listOf("Error Widget")
        )
        composeTestRule.onNode(matcher).assertIsDisplayed()
    }
}