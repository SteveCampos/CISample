package com.stevecampos.domain.register.service

import com.stevecampos.domain.register.repository.MotorcycleRegisterRepository
import com.stevecampos.domain.vehicle.entity.Motorcycle

class MotorcycleRegisterService(registerRepository: MotorcycleRegisterRepository) :
    RegisterService<Motorcycle>(registerRepository, MotorcycleParkingSpaceService())