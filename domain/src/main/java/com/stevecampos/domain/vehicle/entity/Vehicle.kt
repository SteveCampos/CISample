package com.stevecampos.domain.vehicle.entity

import com.stevecampos.domain.vehicle.exception.InvalidPlateException


abstract class Vehicle(val plate: String) {

    private val plateRegExp = Regex("([A-Z]{3}[0-9]{3})|([R][0-9]{5})|([T][0-9]{4})")

    init {
        if (!plateRegExp.matches(plate))
            throw InvalidPlateException()
    }

    fun plateBeginsWithA(): Boolean = this.plate.first() == 'A'

}