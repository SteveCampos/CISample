package com.stevecampos.cisample.register.car

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import com.stevecampos.cisample.parkingSpaces.ParkingSpaceRobotScreen
import com.stevecampos.cisample.register.car.ui.RegisterCarScreen
import com.stevecampos.cisample.register.car.ui.RegisterCarUiState
import com.stevecampos.cisample.share.robot.BaseAndroidComposeTest
import com.stevecampos.cisample.share.robot.BaseRobotScreen
import com.stevecampos.cisample.shared.theme.CISampleTheme

internal fun BaseAndroidComposeTest.registerCarRobotScreen(
    func: RegisterCarRobotScreen.() -> Unit
) = RegisterCarRobotScreen(this).apply(func)


internal class RegisterCarRobotScreen(
    baseAndroidComposeTest: BaseAndroidComposeTest
) : BaseRobotScreen(baseAndroidComposeTest.composeTestRule) {

    private val loadingIndicator by lazy {
        composeTestRule.onNode(
            SemanticsMatcher.expectValue(
                SemanticsProperties.ProgressBarRangeInfo,
                ProgressBarRangeInfo.Indeterminate
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
                RegisterCarScreen(
                    registerCarState = RegisterCarUiState.RegisterCarLoading,
                    onBackClick = {},
                    onRegisterVehicleClicked = { _, _ ->

                    }
                )
            }
        }
    }

    fun isDisplayed(displayed: Boolean) =
        loadingIndicator.assert(if (displayed) hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate) else isEnabled().not())

    fun setErrorState(){
        composeTestRule.setContent {
            CISampleTheme {
                RegisterCarScreen(
                    registerCarState = RegisterCarUiState.RegisterCarError("errorMsg"),
                    onBackClick = {},
                    onRegisterVehicleClicked = { _, _ ->

                    }
                )
            }
        }
    }


    fun isErrorDisplayed() = errorWidget.assertIsDisplayed()
}