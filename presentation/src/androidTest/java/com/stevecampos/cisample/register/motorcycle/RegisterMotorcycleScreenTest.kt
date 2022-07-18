package com.stevecampos.cisample.register.motorcycle

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stevecampos.cisample.register.motorcycle.ui.RegisterMotorcycleScreen
import com.stevecampos.cisample.register.motorcycle.ui.RegisterMotorcycleUiState
import com.stevecampos.cisample.shared.theme.CISampleTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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

        val progressIndeterminateMatcher = SemanticsMatcher.expectValue(
            SemanticsProperties.ProgressBarRangeInfo, ProgressBarRangeInfo.Indeterminate
        )

        composeTestRule.onNode(progressIndeterminateMatcher).assertIsDisplayed()
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

        val errorSemanticsMatcher = SemanticsMatcher.expectValue(
            SemanticsProperties.ContentDescription, listOf("Error Widget")
        )

        composeTestRule.onNode(errorSemanticsMatcher).assertIsDisplayed()
    }
}