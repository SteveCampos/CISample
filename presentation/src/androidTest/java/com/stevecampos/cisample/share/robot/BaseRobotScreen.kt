package com.stevecampos.cisample.share.robot


import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.stevecampos.cisample.parkingspaces.ui.ParkingActivity

internal abstract class BaseRobotScreen constructor(
    protected val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ParkingActivity>, ParkingActivity>
) {

    protected fun getString(@StringRes resourceId: Int) =
        composeTestRule.activity.getString(resourceId)

}