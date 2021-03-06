package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.repository.RegisterRepository
import com.stevecampos.domain.vehicle.entity.Car

open class CarRegisterService(
    registerRepository: RegisterRepository<Car>
) : RegisterService<Car>(registerRepository, CarParkingSpaceService())