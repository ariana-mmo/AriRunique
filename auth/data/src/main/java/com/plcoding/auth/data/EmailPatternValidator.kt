package com.plcoding.auth.data

import android.util.Patterns
import com.plcoding.auth.domain.PatternValidator

//how jau
object EmailPatternValidator: PatternValidator{

    override fun matches(value: String): Boolean {
        //Duda: Que es Patterns (clase)
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}
