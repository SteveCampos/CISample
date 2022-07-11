package com.stevecampos.cisample.register.motorcycle

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.stevecampos.cisample.features.register.motorcycle.ui.RegisterMotorcycleScreen
import com.stevecampos.cisample.features.register.motorcycle.ui.RegisterMotorcycleUiState
import com.stevecampos.cisample.ui.theme.CISampleTheme
import org.junit.Rule
import org.junit.Test

class RegisterMotorcycleScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun registerMotorcycleScreen_whenLoadingState_souldLoadingWidgetBeDisplayed() {
        // Start the app
        composeTestRule.setContent {
            CISampleTheme {
                RegisterMotorcycleScreen(
                    registerMotorcycleState = RegisterMotorcycleUiState.RegisterMotorcycleLoading,
                    onBackClick = {},
                    onRegisterVehicleClicked = { _,_,_ ->

                    }
                )
            }
        }

        composeTestRule.onNodeWithTag("LoadingWidgetTestTag").assertIsDisplayed()
    }
    @Test
    fun registerMotorcycleScreen_whenErrorState_souldFailedToLoadWidgetBeDisplayed() {
        // Start the app
        composeTestRule.setContent {
            CISampleTheme {
                RegisterMotorcycleScreen(
                    registerMotorcycleState = RegisterMotorcycleUiState.RegisterMotorcycleError("errorMsg"),
                    onBackClick = {},
                    onRegisterVehicleClicked = { _,_,_ ->

                    }
                )
            }
        }

        composeTestRule.onNodeWithTag("FailedToLoadWidgetTestTag").assertIsDisplayed()
    }
}