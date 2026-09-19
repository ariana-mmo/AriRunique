package com.plcoding.core.presentation.designsystem

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    //Just define the most important
    primary = RuniqueGreen,
    background = RuniqueBlack,
    //ex: dialogs
    surface = RuniqueDarkGray,
    secondary = RuniqueWhite,
    tertiary = RuniqueWhite,
    primaryContainer = RuniqueGreen30,
    //top of
    onPrimary = RuniqueBlack,
    onBackground = RuniqueWhite,
    onSurface = RuniqueWhite,
    onSurfaceVariant = RuniqueGray,
    error = RuniqueDarkRed
)

@Composable
fun RuniqueTheme(
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
){
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        //bloque no composable dentro del composable
        SideEffect {
            val window = (view.context as Activity).window
            //false -> items like battery keep bright
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}