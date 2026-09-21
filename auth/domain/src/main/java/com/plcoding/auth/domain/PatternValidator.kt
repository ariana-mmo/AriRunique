package com.plcoding.auth.domain
//what i need
interface PatternValidator {
    fun matches(value: String): Boolean
}