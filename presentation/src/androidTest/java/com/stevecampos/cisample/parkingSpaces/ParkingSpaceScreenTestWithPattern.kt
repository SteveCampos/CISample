package com.stevecampos.cisample.parkingSpaces

import com.stevecampos.cisample.share.robot.BaseAndroidComposeTest
import org.junit.Test

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