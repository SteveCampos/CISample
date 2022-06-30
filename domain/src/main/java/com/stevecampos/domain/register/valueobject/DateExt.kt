package com.stevecampos.domain.register.valueobject

import java.text.SimpleDateFormat
import java.util.*


fun Date.isSunday(): Boolean {
    val sdf = SimpleDateFormat("EEEE", Locale.US)
    return sdf.format(this).equals("Sunday")
}

fun Date.isMonday(): Boolean {
    val sdf = SimpleDateFormat("EEEE", Locale.US)
    return sdf.format(this).equals("Monday")
}