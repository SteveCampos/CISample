package com.stevecampos.cisample.register.car

import com.stevecampos.cisample.share.robot.BaseAndroidComposeTest
import org.junit.Test

class RegisterCarScreenTestWithPattern : BaseAndroidComposeTest() {

    @Test
    fun testLoadingIndicatorIsDisplayed() {
        registerCarRobotScreen {
            setLoadingState()
            isDisplayed(true)
        }
    }

    @Test
    fun testErrorIsDisplayed() {
        registerCarRobotScreen {
            setErrorState()
            isErrorDisplayed()
        }
    }

}