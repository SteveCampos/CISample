package com.stevecampos.cisample.car

import com.stevecampos.domain.register.service.ParkingSpaceService
import com.stevecampos.domain.vehicle.entity.Car

class FakeCarParkingSpaceService : ParkingSpaceService<Car>(20)