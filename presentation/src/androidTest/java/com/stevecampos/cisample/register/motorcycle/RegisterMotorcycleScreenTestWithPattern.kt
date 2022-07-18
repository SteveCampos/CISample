package com.stevecampos.cisample.register.motorcycle

import com.stevecampos.cisample.parkingSpaces.parkingSpaceRobotScreen
import com.stevecampos.cisample.share.robot.BaseAndroidComposeTest
import org.junit.Test

class RegisterMotorcycleScreenTestWithPattern : BaseAndroidComposeTest() {

    @Test
    fun testLoadingIndicatorIsDisplayed() {
        registerMotorcycleRobotScreen {
            setLoadingState()
            isDisplayed(true)
        }
    }

    @Test
    fun testErrorIsDisplayed() {
        registerMotorcycleRobotScreen {
            setErrorState()
            isErrorDisplayed()
        }
    }

}