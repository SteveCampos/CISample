package com.stevecampos.cisample.register.car

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stevecampos.cisample.share.robot.BaseAndroidComposeTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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