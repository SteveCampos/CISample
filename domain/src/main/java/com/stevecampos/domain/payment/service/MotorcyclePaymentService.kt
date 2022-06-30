package com.stevecampos.domain.payment.service

import com.stevecampos.domain.payment.exception.InvalidHoursException
import com.stevecampos.domain.payment.exception.NegativeDaysException
import com.stevecampos.domain.vehicle.entity.Motorcycle

class MotorcyclePaymentService : ParkingPaymentService<Motorcycle> {
    override fun calculateParkingCost(motorcycle: Motorcycle, days: Int, hours: Int): Double {
        if (days < 0) {
            throw NegativeDaysException()
        }
        if (hours < 0 || hours > 24) {
            throw InvalidHoursException()
        }
        val extraCost = if (motorcycle.cylinderCapacity > 500) 2000.0 else 0.0

        var payableDays = days
        var payableHours = hours
        if (hours >= 9) {
            ++payableDays
            payableHours = 0
        }
        return (payableDays * DAILY_PRICE) + (payableHours * HOUR_PRICE) + extraCost
    }


    companion object {
        const val HOUR_PRICE = 500.0
        const val DAILY_PRICE = 4000.0
    }
}