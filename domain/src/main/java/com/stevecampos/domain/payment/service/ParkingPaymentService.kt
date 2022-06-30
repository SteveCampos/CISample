package com.stevecampos.domain.payment.service

import com.stevecampos.domain.vehicle.entity.Vehicle

interface ParkingPaymentService<V: Vehicle> {
    fun calculateParkingCost(v: V, days: Int, hours: Int): Double
}