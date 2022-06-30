package com.stevecampos.domain.valueobject

import com.stevecampos.domain.entity.Vehicle
import com.stevecampos.domain.exception.InvalidHoursException
import com.stevecampos.domain.exception.NegativeDaysException

class ParkCalculatorImpl : ParkCalculator {
    override fun calculateParkingCost(vehicle: Vehicle, days: Int, hours: Int): Double {
        if (days < 0) {
            throw NegativeDaysException()
        }
        if (hours >= 24 || hours <= 0) {
            throw InvalidHoursException()
        }

        return vehicle.dailyCost() * days + vehicle.hourCost() * hours
    }
}