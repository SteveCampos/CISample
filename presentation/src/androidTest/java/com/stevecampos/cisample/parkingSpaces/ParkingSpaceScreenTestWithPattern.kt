package com.stevecampos.cisample.parkingSpaces

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stevecampos.cisample.share.robot.BaseAndroidComposeTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ParkingSpaceScreenTestWithPattern : BaseAndroidComposeTest() {

    @Test
    fun testLoadingIndicatorIsDisplayed() {
        parkingSpaceRobotScreen {
            setLoadingState()
            isLoadingIndicatorEnabled(true)
        }
    }

    @Test
    fun testErrorIsDisplayed() {
        parkingSpaceRobotScreen {
            setErrorState()
            isErrorDisplayed()
        }
    }

}