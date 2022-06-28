package com.stevecampos.domain.aggregate

import com.stevecampos.domain.entity.Vehicle

data class ParkingSpace(val vehicle: Vehicle, val startTimestamp: Long)
