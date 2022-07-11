package com.stevecampos.cisample.car

import com.stevecampos.domain.register.service.CarRegisterService
import com.stevecampos.domain.register.service.RegisterService
import com.stevecampos.domain.vehicle.entity.Car
// RegisterService<Car>(FakeCarRegisterRepository(), FakeCarParkingSpaceService())
class FakeCarRegisterService :
    CarRegisterService(FakeCarRegisterRepository()){

}
