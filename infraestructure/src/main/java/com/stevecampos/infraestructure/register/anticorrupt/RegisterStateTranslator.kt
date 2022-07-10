package com.stevecampos.infraestructure.register.anticorrupt

import com.stevecampos.domain.register.aggregate.RegisteredState
import com.stevecampos.infraestructure.register.entity.RegisterStateEntity

class RegisterStateTranslator {

    fun translateToDomain(origin: RegisterStateEntity): RegisteredState {
        return when (origin) {
            RegisterStateEntity.LOCKED -> RegisteredState.Locked
            RegisterStateEntity.FINISHED -> RegisteredState.Finished
        }
    }

    fun translateToInfrastructure(origin: RegisteredState): RegisterStateEntity =
        when (origin) {
            is RegisteredState.Locked -> RegisterStateEntity.LOCKED
            is RegisteredState.Finished -> RegisterStateEntity.FINISHED
            else -> RegisterStateEntity.FINISHED
        }
}