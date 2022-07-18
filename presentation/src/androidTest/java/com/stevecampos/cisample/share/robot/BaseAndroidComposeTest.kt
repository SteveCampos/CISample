package com.stevecampos.cisample.share.robot

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import com.stevecampos.cisample.parkingspaces.ui.ParkingActivity
import org.junit.Rule

abstract class BaseAndroidComposeTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ParkingActivity>()
}