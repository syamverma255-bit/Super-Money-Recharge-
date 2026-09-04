package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.SuperMoneyPurple

/**
 * Super Money Brand Logo
 * Features the signature electric purple (#5E17EB) faceted diamond mark with
 * an interlocking stylized Indian Rupee (₹) and recharge lightning cut.
 */
@Composable
fun SuperMoneyLogo(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    showGlow: Boolean = true,
    useContainer: Boolean = true,
    containerColor: Color = Color.White
) {
    val infiniteTransition = rememberInfiniteTransition(label = "super_money_glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .testTag("super_money_logo")
    ) {
        if (showGlow) {
            // Ambient electric purple outer glow
            Box(
                modifier = Modifier
                    .size(size * 1.18f)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                SuperMoneyPurple.copy(alpha = glowAlpha * 0.45f),
                                SuperMoneyPurple.copy(alpha = glowAlpha * 0.15f),
                                Color.Transparent
                            )
                        ),
                        shape = RoundedCornerShape(percent = 45)
                    )
            )
        }

        if (useContainer) {
            // Crisp rounded tile container ensuring high contrast on any background
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(size)
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(size * 0.28f))
                    .clip(RoundedCornerShape(size * 0.28f))
                    .background(containerColor)
                    .border(
                        width = 1.dp,
                        color = SuperMoneyPurple.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(size * 0.28f)
                    )
                    .padding(size * 0.12f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_super_money_brand),
                    contentDescription = "Super Money Logo",
                    modifier = Modifier.size(size * 0.76f)
                )
            }
        } else {
            // Direct transparent vector mark
            Image(
                painter = painterResource(id = R.drawable.ic_super_money_brand),
                contentDescription = "Super Money Logo",
                modifier = Modifier.size(size)
            )
        }
    }
}

/**
 * Super Money Wordmark (SUPERMONEY)
 * Rendered in bold geometric uppercase tracking matching the brand guidelines.
 */
@Composable
fun SuperMoneyWordmark(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 18.sp,
    color: Color = SuperMoneyPurple,
    letterSpacing: TextUnit = 3.5.sp
) {
    Text(
        text = "SUPERMONEY",
        modifier = modifier.testTag("super_money_wordmark"),
        color = color,
        fontSize = fontSize,
        fontWeight = FontWeight.Black,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = letterSpacing
    )
}

/**
 * Full Brand Lockup (Logo Mark + "SUPERMONEY" Wordmark)
 * Perfectly replicates the exact layout from the reference image:
 * Centered electric purple diamond mark with tracked SUPERMONEY text below it.
 */
@Composable
fun SuperMoneyFullBrandLockup(
    modifier: Modifier = Modifier,
    markSize: Dp = 80.dp,
    wordmarkFontSize: TextUnit = 20.sp,
    spacing: Dp = 14.dp
) {
    Column(
        modifier = modifier.testTag("super_money_full_brand_lockup"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SuperMoneyLogo(
            size = markSize,
            showGlow = true,
            useContainer = true,
            containerColor = Color.White
        )
        Spacer(modifier = Modifier.height(spacing))
        SuperMoneyWordmark(
            fontSize = wordmarkFontSize,
            color = SuperMoneyPurple,
            letterSpacing = 4.sp
        )
    }
}
