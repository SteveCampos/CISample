package com.stevecampos.cisample.register.car

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.stevecampos.cisample.register.car.ui.RegisterCarScreen
import com.stevecampos.cisample.register.car.ui.RegisterCarUiState
import com.stevecampos.cisample.shared.theme.CISampleTheme
import org.junit.Rule
import org.junit.Test

class RegisterCarScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun registerCarScreen_whenLoadingState_souldLoadingWidgetBeDisplayed() {
        // Start the app
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

        composeTestRule.onNodeWithTag("LoadingWidgetTestTag").assertIsDisplayed()
    }
    @Test
    fun registerCarScreen_whenErrorState_souldFailedToLoadWidgetBeDisplayed() {
        // Start the app
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

        composeTestRule.onNodeWithTag("FailedToLoadWidgetTestTag").assertIsDisplayed()
    }
}