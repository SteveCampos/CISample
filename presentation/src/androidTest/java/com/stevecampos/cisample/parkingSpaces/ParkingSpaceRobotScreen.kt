package com.stevecampos.cisample.parkingSpaces

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import com.stevecampos.cisample.parkingspaces.ui.ParkingScreen
import com.stevecampos.cisample.parkingspaces.viewmodel.ParkingUiState
import com.stevecampos.cisample.share.robot.BaseAndroidComposeTest
import com.stevecampos.cisample.share.robot.BaseRobotScreen
import com.stevecampos.cisample.shared.theme.CISampleTheme

internal fun BaseAndroidComposeTest.parkingSpaceRobotScreen(
    func: ParkingSpaceRobotScreen.() -> Unit
) = ParkingSpaceRobotScreen(this).apply(func)


internal class ParkingSpaceRobotScreen(
    baseAndroidComposeTest: BaseAndroidComposeTest
) : BaseRobotScreen(baseAndroidComposeTest.composeTestRule) {


    private val loadingIndicator by lazy {
        composeTestRule.onNode(
            SemanticsMatcher.expectValue(
                SemanticsProperties.ProgressBarRangeInfo, ProgressBarRangeInfo.Indeterminate
            )
        )
    }

    private val errorWidget by lazy {
        composeTestRule.onNode(
            SemanticsMatcher.expectValue(
                SemanticsProperties.ContentDescription, listOf("Error Widget")
            )
        )
    }

    fun setLoadingState() {
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
    }

    fun setErrorState() {
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
    }

    fun isLoadingIndicatorEnabled(enabled: Boolean) =
        loadingIndicator.assert(if (enabled) hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate) else isEnabled().not())

    fun isErrorDisplayed() = errorWidget.assertIsDisplayed()
}