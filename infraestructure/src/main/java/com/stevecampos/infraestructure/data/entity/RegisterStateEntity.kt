package com.stevecampos.infraestructure.data.entity

import com.stevecampos.domain.register.aggregate.RegisteredState

enum class RegisterStateEntity {
    LOCKED, FINISHED
}

fun RegisteredState.toExternal(): RegisterStateEntity =
    when (this) {
        is RegisteredState.Locked -> RegisterStateEntity.LOCKED
        is RegisteredState.Finished -> RegisterStateEntity.FINISHED
        else -> RegisterStateEntity.FINISHED
    }

fun RegisterStateEntity.asDomain(): RegisteredState {
    return when (this) {
        RegisterStateEntity.LOCKED -> RegisteredState.Locked
        RegisterStateEntity.FINISHED -> RegisteredState.Finished
    }
}
