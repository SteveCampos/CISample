package com.stevecampos.cisample.motorcycle

import com.stevecampos.domain.register.service.ParkingSpaceService
import com.stevecampos.domain.vehicle.entity.Motorcycle

class FakeMotorcycleParkingSpaceService : ParkingSpaceService<Motorcycle>(10)