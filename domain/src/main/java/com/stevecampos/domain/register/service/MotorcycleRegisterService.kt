package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.repository.RegisterRepository
import com.stevecampos.domain.vehicle.entity.Motorcycle

open class MotorcycleRegisterService(registerRepository: RegisterRepository<Motorcycle>) :
    RegisterService<Motorcycle>(registerRepository, MotorcycleParkingSpaceService())