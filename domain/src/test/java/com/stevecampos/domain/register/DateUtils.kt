package com.stevecampos.domain.register

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.*


 fun getNextDayOfWeek(dayOfWeek: DayOfWeek): Date {
    val localDate = LocalDateTime
        .now().with(TemporalAdjusters.next(dayOfWeek))

    return Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant())
}