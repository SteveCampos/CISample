package com.stevecampos.cisample.register.motorcycle

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import com.stevecampos.cisample.parkingSpaces.ParkingSpaceRobotScreen
import com.stevecampos.cisample.register.motorcycle.ui.RegisterMotorcycleScreen
import com.stevecampos.cisample.register.motorcycle.ui.RegisterMotorcycleUiState
import com.stevecampos.cisample.share.robot.BaseAndroidComposeTest
import com.stevecampos.cisample.share.robot.BaseRobotScreen
import com.stevecampos.cisample.shared.theme.CISampleTheme

internal fun BaseAndroidComposeTest.registerMotorcycleRobotScreen(
    func: RegisterMotorcycleRobotScreen.() -> Unit
) = RegisterMotorcycleRobotScreen(this).apply(func)


internal class RegisterMotorcycleRobotScreen(
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
                RegisterMotorcycleScreen(
                    registerMotorcycleState = RegisterMotorcycleUiState.RegisterMotorcycleLoading,
                    onBackClick = {},
                    onRegisterVehicleClicked = { _, _, _ ->

                    }
                )
            }
        }
    }

    fun isDisplayed(displayed: Boolean) =
        loadingIndicator.assert(if (displayed) hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate) else isEnabled().not())

    fun setErrorState() {
        composeTestRule.setContent {
            CISampleTheme {
                RegisterMotorcycleScreen(
                    registerMotorcycleState = RegisterMotorcycleUiState.RegisterMotorcycleError("errorMsg"),
                    onBackClick = {},
                    onRegisterVehicleClicked = { _, _, _ ->

                    }
                )
            }
        }
    }


    fun isErrorDisplayed() = errorWidget.assertIsDisplayed()
}