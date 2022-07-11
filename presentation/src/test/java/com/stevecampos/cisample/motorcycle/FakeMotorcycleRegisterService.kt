package com.stevecampos.cisample.motorcycle

import com.stevecampos.domain.register.service.MotorcycleRegisterService
import com.stevecampos.domain.register.service.RegisterService
import com.stevecampos.domain.vehicle.entity.Motorcycle

// FakeMotorcycleRegisterRepository(),FakeMotorcycleParkingSpaceService()
class FakeMotorcycleRegisterService :
    MotorcycleRegisterService(FakeMotorcycleRegisterRepository()) {

}
