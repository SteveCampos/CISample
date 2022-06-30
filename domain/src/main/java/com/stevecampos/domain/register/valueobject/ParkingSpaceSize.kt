package com.stevecampos.domain.register.valueobject

import com.stevecampos.domain.vehicle.entity.Vehicle

abstract class ParkingSpaceSize<V: Vehicle>(val size: Int)