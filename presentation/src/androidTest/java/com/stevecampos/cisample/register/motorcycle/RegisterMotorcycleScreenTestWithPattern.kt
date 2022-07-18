package com.stevecampos.cisample.register.motorcycle

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stevecampos.cisample.share.robot.BaseAndroidComposeTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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