package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.BharatBlue
import com.example.ui.theme.BharatNavy
import com.example.ui.theme.BharatNavyDark
import com.example.ui.theme.BharatSaffron
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceLight

private val DarkColorScheme =
  darkColorScheme(
    primary = BharatBlue,
    secondary = BharatNavy,
    tertiary = BharatSaffron,
    background = BharatNavyDark,
    surface = Color(0xFF101C38)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = BharatBlue,
    secondary = BharatNavy,
    tertiary = BharatSaffron,
    background = SurfaceLight,
    surface = SurfaceCard,
    onPrimary = Color.White,
    onSecondary = Color.White
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
