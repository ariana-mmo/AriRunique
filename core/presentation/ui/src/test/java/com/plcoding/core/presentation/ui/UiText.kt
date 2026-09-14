package com.plcoding.core.presentation.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText{
    //(unwrapped)
    data class DynamicString(val value: String): UiText

    class StringResource(
        //string resource id (used in the viewmodel)
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ): UiText

    @Composable
    fun asString(): String{
        when(this){
            is DynamicString -> value
                                //because is StringRes      //al arguments optional
            is StringResource -> stringResource(id = id, *args)
        }
    }


    fun asString(context: Context):String{
        when(this){
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}