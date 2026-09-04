package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Operator
import com.example.ui.theme.AirtelRed
import com.example.ui.theme.BharatBlue
import com.example.ui.theme.BharatEmerald
import com.example.ui.theme.BharatGreen
import com.example.ui.theme.BharatNavy
import com.example.ui.theme.BharatSaffron
import com.example.ui.theme.BsnlTeal
import com.example.ui.theme.JioBlue
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ViCrimson
import com.example.ui.theme.ViGold

/**
 * Layout styles for the Operator Selector Card component.
 */
enum class OperatorCardLayout {
    /** Vertical stacked cards with full details, feature tags, and pricing */
    LIST,
    /** 2x2 grid of modern compact branded cards */
    GRID,
    /** Horizontal scrollable / wrap row of cards */
    COMPACT_ROW
}

/**
 * Authentically rendered brand logos for the Indian telecom operators:
 * - Jio: Royal blue roundel with white bold brand typography and signature red dots.
 * - Airtel: Iconic red rounded brand mark with custom fluid wave emblem.
 * - Vi: Signature crimson shield with bold white "V" and warm golden-yellow dot on the "i".
 * - BSNL: Deep teal roundel with national satellite orbit swooshes and crisp "BSNL" emblem.
 */
@Composable
fun OperatorBrandLogo(
    operator: Operator,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.28f)),
        contentAlignment = Alignment.Center
    ) {
        when (operator) {
            Operator.JIO -> JioBrandLogo(size = size)
            Operator.AIRTEL -> AirtelBrandLogo(size = size)
            Operator.VI -> ViBrandLogo(size = size)
            Operator.BSNL -> BsnlBrandLogo(size = size)
        }
    }
}

@Composable
private fun JioBrandLogo(size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(Color(0xFF1445D1), JioBlue, Color(0xFF061A5C)),
                    radius = size.value * 2.2f
                )
            )
            .border(1.5.dp, Color.White.copy(alpha = 0.35f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "J",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = (size.value * 0.38f).sp,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = (-0.5).sp
            )
            // 'i' with iconic red circular dot
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 1.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size((size.value * 0.11f).dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE53935))
                )
                Spacer(modifier = Modifier.height((size.value * 0.03f).dp))
                Box(
                    modifier = Modifier
                        .width((size.value * 0.09f).dp)
                        .height((size.value * 0.22f).dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(Color.White)
                )
            }
            Text(
                text = "o",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = (size.value * 0.38f).sp,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = (-0.5).sp
            )
        }
    }
}

@Composable
private fun AirtelBrandLogo(size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.28f))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFFFF1744), AirtelRed, Color(0xFFB70000))
                )
            )
            .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(size * 0.28f)),
        contentAlignment = Alignment.Center
    ) {
        // Airtel signature swoosh wave canvas and typography
        Canvas(modifier = Modifier.size(size * 0.62f)) {
            val w = this.size.width
            val h = this.size.height
            val path = Path().apply {
                moveTo(w * 0.15f, h * 0.65f)
                cubicTo(
                    w * 0.2f, h * 0.25f,
                    w * 0.75f, h * 0.2f,
                    w * 0.85f, h * 0.55f
                )
                cubicTo(
                    w * 0.9f, h * 0.85f,
                    w * 0.5f, h * 0.9f,
                    w * 0.35f, h * 0.75f
                )
            }
            drawPath(
                path = path,
                color = Color.White,
                style = Stroke(width = w * 0.18f, cap = StrokeCap.Round)
            )
        }

        // Mini "airtel" text overlay at the bottom
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = (size.value * 0.06f).dp)
        ) {
            Text(
                text = "airtel",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = (size.value * 0.22f).sp,
                letterSpacing = (-0.4).sp
            )
        }
    }
}

@Composable
private fun ViBrandLogo(size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.28f))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFFE53935), ViCrimson, Color(0xFF7F0000))
                )
            )
            .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(size * 0.28f)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Bold White "V"
            Text(
                text = "V",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = (size.value * 0.46f).sp,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.width(1.dp))
            // 'i' with warm yellow/gold dot
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Inverted triangle/droplet gold dot for Vi
                Box(
                    modifier = Modifier
                        .size((size.value * 0.13f).dp)
                        .clip(CircleShape)
                        .background(ViGold)
                )
                Spacer(modifier = Modifier.height((size.value * 0.04f).dp))
                Box(
                    modifier = Modifier
                        .width((size.value * 0.11f).dp)
                        .height((size.value * 0.26f).dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(Color.White)
                )
            }
        }
    }
}

@Composable
private fun BsnlBrandLogo(size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(Color(0xFF00897B), BsnlTeal, Color(0xFF00363A))
                )
            )
            .border(1.5.dp, Color.White.copy(alpha = 0.35f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Satellite orbital curves
        Canvas(modifier = Modifier.size(size * 0.85f)) {
            val w = this.size.width
            val h = this.size.height
            // Orbit 1: Indian Saffron accent
            drawArc(
                color = Color(0xFFFF9933),
                startAngle = 40f,
                sweepAngle = 130f,
                useCenter = false,
                style = Stroke(width = w * 0.08f, cap = StrokeCap.Round)
            )
            // Orbit 2: White/Cyan accent
            drawArc(
                color = Color.White.copy(alpha = 0.9f),
                startAngle = 220f,
                sweepAngle = 130f,
                useCenter = false,
                style = Stroke(width = w * 0.08f, cap = StrokeCap.Round)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "BSNL",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = (size.value * 0.28f).sp,
                letterSpacing = 0.5.sp
            )
        }
    }
}

/**
 * A rich detailed card representing an individual mobile operator.
 * Accessible with 48dp+ interactive touch area and clear semantic role.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OperatorCard(
    operator: Operator,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    layout: OperatorCardLayout = OperatorCardLayout.LIST
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) operator.primaryColor else SurfaceBorder,
        animationSpec = tween(250),
        label = "border_color"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) operator.lightBgColor.copy(alpha = 0.7f) else SurfaceCard,
        animationSpec = tween(250),
        label = "bg_color"
    )

    val elevation by animateDpAsState(
        targetValue = if (isSelected) 4.dp else 1.dp,
        animationSpec = tween(250),
        label = "elevation"
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
        modifier = modifier
            .fillMaxWidth()
            .testTag("operator_card_${operator.name.lowercase()}")
            .semantics {
                role = Role.RadioButton
                selected = isSelected
                contentDescription = "${operator.displayName} operator, ${if (isSelected) "selected" else "not selected"}. ${operator.speedTag}"
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = operator.primaryColor),
                onClick = onClick
            )
    ) {
        when (layout) {
            OperatorCardLayout.LIST -> {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            OperatorBrandLogo(operator = operator, size = 48.dp)
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = operator.displayName,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp,
                                        color = BharatNavy
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    // Brand tag pill
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(operator.primaryColor.copy(alpha = 0.12f))
                                            .padding(horizontal = 7.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = operator.brandTag,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = operator.primaryColor
                                        )
                                    }
                                }

                                Text(
                                    text = operator.fullName,
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    maxLines = 1
                                )
                            }
                        }

                        // Radio / Checkmark Selection indicator
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) operator.primaryColor else Color.Transparent
                                )
                                .border(
                                    2.dp,
                                    if (isSelected) operator.primaryColor else SurfaceBorder,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Tagline & starting price
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = operator.primaryColor,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = operator.speedTag,
                                fontSize = 12.sp,
                                color = TextPrimary,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Text(
                            text = "Plans from ₹${operator.startingPrice}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = operator.primaryColor
                        )
                    }

                    // Feature Badges
                    if (operator.keyFeatures.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            operator.keyFeatures.forEach { feature ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xFFF1F5F9))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = feature,
                                        fontSize = 10.sp,
                                        color = TextSecondary,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            OperatorCardLayout.GRID -> {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(operator.primaryColor.copy(alpha = 0.12f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = operator.brandTag,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = operator.primaryColor
                            )
                        }

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Selected",
                                tint = operator.primaryColor,
                                modifier = Modifier.size(18.dp)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .border(1.5.dp, SurfaceBorder, CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    OperatorBrandLogo(operator = operator, size = 44.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = operator.displayName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = BharatNavy
                    )

                    Text(
                        text = operator.speedTag,
                        fontSize = 11.sp,
                        color = TextSecondary,
                        maxLines = 1,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "From ₹${operator.startingPrice}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = operator.primaryColor
                    )
                }
            }

            OperatorCardLayout.COMPACT_ROW -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OperatorBrandLogo(operator = operator, size = 38.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = operator.displayName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = BharatNavy
                        )
                        Text(
                            text = operator.brandTag,
                            fontSize = 11.sp,
                            color = operator.primaryColor,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Selected",
                            tint = operator.primaryColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * A complete, reusable Compose component that allows users to select their mobile operator
 * (Jio, Airtel, Vi, or BSNL) from a list of cards with brand logos.
 *
 * @param selectedOperator Currently active operator.
 * @param onOperatorSelected Callback invoked when the user selects an operator card.
 * @param modifier Modifier for styling the container.
 * @param title Optional title displayed above the list of cards.
 * @param subtitle Optional explanatory subtitle.
 * @param layoutMode Layout style (LIST, GRID, or COMPACT_ROW).
 */
@Composable
fun OperatorSelectorCardList(
    selectedOperator: Operator,
    onOperatorSelected: (Operator) -> Unit,
    modifier: Modifier = Modifier,
    title: String? = "Select Mobile Operator",
    subtitle: String? = "Choose from India's leading telecom networks",
    layoutMode: OperatorCardLayout = OperatorCardLayout.LIST
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("operator_selector_card_list")
    ) {
        if (title != null) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = BharatNavy
            )
        }

        if (subtitle != null) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = TextSecondary
            )
        }

        if (title != null || subtitle != null) {
            Spacer(modifier = Modifier.height(12.dp))
        }

        when (layoutMode) {
            OperatorCardLayout.LIST -> {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Operator.entries.forEach { operator ->
                        OperatorCard(
                            operator = operator,
                            isSelected = operator == selectedOperator,
                            onClick = { onOperatorSelected(operator) },
                            layout = OperatorCardLayout.LIST
                        )
                    }
                }
            }

            OperatorCardLayout.GRID -> {
                val operators = Operator.entries
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OperatorCard(
                            operator = operators[0],
                            isSelected = operators[0] == selectedOperator,
                            onClick = { onOperatorSelected(operators[0]) },
                            layout = OperatorCardLayout.GRID,
                            modifier = Modifier.weight(1f)
                        )
                        OperatorCard(
                            operator = operators[1],
                            isSelected = operators[1] == selectedOperator,
                            onClick = { onOperatorSelected(operators[1]) },
                            layout = OperatorCardLayout.GRID,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OperatorCard(
                            operator = operators[2],
                            isSelected = operators[2] == selectedOperator,
                            onClick = { onOperatorSelected(operators[2]) },
                            layout = OperatorCardLayout.GRID,
                            modifier = Modifier.weight(1f)
                        )
                        OperatorCard(
                            operator = operators[3],
                            isSelected = operators[3] == selectedOperator,
                            onClick = { onOperatorSelected(operators[3]) },
                            layout = OperatorCardLayout.GRID,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            OperatorCardLayout.COMPACT_ROW -> {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Operator.entries.forEach { operator ->
                        OperatorCard(
                            operator = operator,
                            isSelected = operator == selectedOperator,
                            onClick = { onOperatorSelected(operator) },
                            layout = OperatorCardLayout.COMPACT_ROW
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OperatorSelectorCardListPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            OperatorSelectorCardList(
                selectedOperator = Operator.JIO,
                onOperatorSelected = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OperatorSelectorCardGridPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            OperatorSelectorCardList(
                selectedOperator = Operator.AIRTEL,
                onOperatorSelected = {},
                layoutMode = OperatorCardLayout.GRID
            )
        }
    }
}
