package com.stevecampos.infraestructure.data.anticorrupt

interface Translator<in T, out R> {
    fun map(origin: T): R

    fun map(origin: List<T>): List<R> =
        origin.map(::map)

}