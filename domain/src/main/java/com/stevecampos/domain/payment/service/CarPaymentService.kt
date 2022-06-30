package com.stevecampos.domain.payment.service

import com.stevecampos.domain.exception.InvalidHoursException
import com.stevecampos.domain.exception.NegativeDaysException
import com.stevecampos.domain.vehicle.entity.Car

class CarPaymentService : ParkingPaymentService<Car> {

    companion object {
        const val HOUR_PRICE = 1000.0
        const val DAILY_PRICE = 8000.0
    }

    override fun calculateParkingCost(v: Car, days: Int, hours: Int): Double {

        if (days < 0) {
            throw NegativeDaysException()
        }
        if (hours < 0 || hours > 24) {
            throw InvalidHoursException()
        }

        var payableDays = days
        var payableHours = hours
        if (hours >= 9) {
            ++payableDays
            payableHours = 0
        }
        return (payableDays * DAILY_PRICE) + (payableHours * HOUR_PRICE)
    }
}