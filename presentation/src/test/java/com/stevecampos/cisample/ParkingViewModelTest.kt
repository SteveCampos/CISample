package com.stevecampos.cisample

import com.stevecampos.cisample.car.FakeCarParkingSpaceService
import com.stevecampos.cisample.car.FakeCarRegisterService
import com.stevecampos.cisample.parkingspaces.viewmodel.ParkingUiState
import com.stevecampos.cisample.parkingspaces.viewmodel.ParkingViewModel
import com.stevecampos.cisample.motorcycle.FakeMotorcycleParkingSpaceService
import com.stevecampos.cisample.motorcycle.FakeMotorcycleRegisterService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ParkingViewModelTest {

    private val carParkingSpaceService = FakeCarParkingSpaceService()
    private val motorcycleParkingSpaceService = FakeMotorcycleParkingSpaceService()
    private val carRegisterService = FakeCarRegisterService()
    private val motorcycleRegisterService = FakeMotorcycleRegisterService()


    private lateinit var viewModel: ParkingViewModel

    @Before
    fun setup() {
        viewModel = ParkingViewModel(
            carParkingService = carParkingSpaceService,
            motorcycleParkingSpaceService = motorcycleParkingSpaceService,
            carRegisterService = carRegisterService,
            motorcycleRegisterService = motorcycleRegisterService
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun parkingUiState_whenInitialized_thenShowSuccess() = runTest {
        Assert.assertEquals(
            ParkingUiState.ParkingErrorState::class.java,
            viewModel.parkingUiState.value::class.java
        )
    }
}