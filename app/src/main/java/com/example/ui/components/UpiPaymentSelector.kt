package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BharatBlue
import com.example.ui.theme.BharatBlueLight
import com.example.ui.theme.BharatGreen
import com.example.ui.theme.BharatGreenLight
import com.example.ui.theme.BharatNavy
import com.example.ui.theme.BharatSaffron
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

/**
 * Data model for a UPI payment option.
 */
data class UpiOptionItem(
    val id: String,
    val name: String,
    val subtitle: String,
    val badgeText: String? = null,
    val badgeColor: Color = BharatGreen,
    val badgeBgColor: Color = BharatGreenLight,
    val isCustomVpa: Boolean = false
)

/**
 * Branded Icon Composables for Indian UPI Apps
 */
@Composable
fun GPayBrandIcon(modifier: Modifier = Modifier, size: Int = 38) {
    Box(
        modifier = modifier
            .size(size.dp)
            .shadow(1.dp, CircleShape)
            .clip(CircleShape)
            .background(Color.White)
            .border(1.dp, Color(0xFFE2E8F0), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Stylized Google "G" representation
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size((size * 0.58).dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(
                        Brush.sweepGradient(
                            listOf(
                                Color(0xFF4285F4), // Google Blue
                                Color(0xFFEA4335), // Google Red
                                Color(0xFFFBBC05), // Google Yellow
                                Color(0xFF34A853), // Google Green
                                Color(0xFF4285F4)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size((size * 0.35).dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "G",
                        fontSize = (size * 0.32).sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF4285F4)
                    )
                }
            }
        }
    }
}

@Composable
fun PhonePeBrandIcon(modifier: Modifier = Modifier, size: Int = 38) {
    Box(
        modifier = modifier
            .size(size.dp)
            .shadow(1.dp, CircleShape)
            .clip(CircleShape)
            .background(Color(0xFF5F259F)), // PhonePe Signature Purple
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Pe",
            fontSize = (size * 0.42).sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            fontFamily = FontFamily.SansSerif
        )
    }
}

@Composable
fun PaytmBrandIcon(modifier: Modifier = Modifier, size: Int = 38) {
    Box(
        modifier = modifier
            .size(size.dp)
            .shadow(1.dp, RoundedCornerShape((size * 0.28).dp))
            .clip(RoundedCornerShape((size * 0.28).dp))
            .background(Color(0xFF002970)), // Paytm Royal Navy
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Pay",
                fontSize = (size * 0.26).sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = (size * 0.28).sp
            )
            Text(
                text = "tm",
                fontSize = (size * 0.24).sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF00BAF2), // Paytm Cyan
                lineHeight = (size * 0.26).sp
            )
        }
    }
}

@Composable
fun BhimUpiBrandIcon(modifier: Modifier = Modifier, size: Int = 38) {
    Box(
        modifier = modifier
            .size(size.dp)
            .shadow(1.dp, RoundedCornerShape((size * 0.28).dp))
            .clip(RoundedCornerShape((size * 0.28).dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF9933), // Indian Saffron
                        Color(0xFFFFFFFF), // White
                        Color(0xFF138808)  // Green
                    )
                )
            )
            .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape((size * 0.28).dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size((size * 0.72).dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFF071438)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "UPI",
                fontSize = (size * 0.24).sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFFFB300)
            )
        }
    }
}

@Composable
fun CustomVpaBrandIcon(modifier: Modifier = Modifier, size: Int = 38) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(Color(0xFFF1F5F9))
            .border(1.dp, Color(0xFFCBD5E1), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.QrCodeScanner,
            contentDescription = null,
            tint = BharatBlue,
            modifier = Modifier.size((size * 0.52).dp)
        )
    }
}

/**
 * High-fidelity UPI Payment Options Selector Component.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UpiPaymentOptionsList(
    selectedMethod: String,
    onSelectMethod: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var customVpaText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val upiOptions = remember {
        listOf(
            UpiOptionItem(
                id = "Google Pay UPI",
                name = "Google Pay (GPay)",
                subtitle = "Fast 1-tap recharge with UPI PIN",
                badgeText = "Fastest • Recommended",
                badgeColor = Color(0xFF137333),
                badgeBgColor = Color(0xFFE6F4EA)
            ),
            UpiOptionItem(
                id = "PhonePe UPI",
                name = "PhonePe UPI",
                subtitle = "Direct bank debit via PhonePe",
                badgeText = "Most Popular",
                badgeColor = Color(0xFF5F259F),
                badgeBgColor = Color(0xFFF3E8FF)
            ),
            UpiOptionItem(
                id = "Paytm UPI",
                name = "Paytm UPI",
                subtitle = "Instant authorization via Paytm app",
                badgeText = "Zero Extra Fee",
                badgeColor = Color(0xFF007A99),
                badgeBgColor = Color(0xFFE0F7FA)
            ),
            UpiOptionItem(
                id = "BHIM UPI / Any UPI ID",
                name = "BHIM UPI / Other Apps",
                subtitle = "CRED, Amazon Pay, WhatsApp, or any UPI app"
            ),
            UpiOptionItem(
                id = "Enter UPI ID",
                name = "Enter UPI ID / VPA",
                subtitle = "Pay using your @okhdfcbank, @ybl, or @upi handle",
                isCustomVpa = true
            )
        )
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(BharatGreen.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ElectricBolt,
                            contentDescription = null,
                            tint = BharatGreen,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Select UPI Payment Mode",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = BharatNavy
                        )
                        Text(
                            text = "Instant & 100% Free via Unified Payments Interface",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }

                // Verified UPI Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFE8F5E9))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = BharatGreen,
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "NPCI UPI",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black,
                            color = BharatGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // UPI Options List
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                upiOptions.forEach { option ->
                    val isSelected = selectedMethod == option.id || (option.isCustomVpa && selectedMethod == "Enter UPI ID")

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) BharatBlueLight.copy(alpha = 0.5f) else Color(0xFFFAFBFC),
                        border = androidx.compose.foundation.BorderStroke(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) BharatBlue else SurfaceBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onSelectMethod(option.id) }
                            .testTag("upi_option_${option.id.replace(" ", "_").lowercase()}")
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Branded App Icon
                                when (option.id) {
                                    "Google Pay UPI" -> GPayBrandIcon(size = 38)
                                    "PhonePe UPI" -> PhonePeBrandIcon(size = 38)
                                    "Paytm UPI" -> PaytmBrandIcon(size = 38)
                                    "BHIM UPI / Any UPI ID" -> BhimUpiBrandIcon(size = 38)
                                    else -> CustomVpaBrandIcon(size = 38)
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                // Title & Description
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = option.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = if (isSelected) BharatNavy else TextPrimary
                                        )
                                        if (option.badgeText != null) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(option.badgeBgColor)
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = option.badgeText,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = option.badgeColor
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = option.subtitle,
                                        fontSize = 11.sp,
                                        color = TextSecondary,
                                        lineHeight = 14.sp
                                    )
                                }

                                // Selection Indicator
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { onSelectMethod(option.id) },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = BharatBlue,
                                        unselectedColor = TextTertiary
                                    ),
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            // Expandable Custom VPA input
                            if (option.isCustomVpa) {
                                AnimatedVisibility(
                                    visible = isSelected,
                                    enter = fadeIn() + expandVertically(),
                                    exit = fadeOut() + shrinkVertically()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 12.dp)
                                    ) {
                                        OutlinedTextField(
                                            value = customVpaText,
                                            onValueChange = { customVpaText = it.lowercase().trim() },
                                            label = { Text("Virtual Payment Address (UPI ID)", fontSize = 12.sp) },
                                            placeholder = { Text("e.g. mobile@okhdfcbank or name@ybl", fontSize = 12.sp) },
                                            singleLine = true,
                                            shape = RoundedCornerShape(10.dp),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = BharatBlue,
                                                unfocusedBorderColor = SurfaceBorder,
                                                focusedContainerColor = Color.White,
                                                unfocusedContainerColor = Color.White
                                            ),
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Email,
                                                imeAction = ImeAction.Done
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onDone = { focusManager.clearFocus() }
                                            ),
                                            trailingIcon = {
                                                if (customVpaText.contains("@") && customVpaText.length > 5) {
                                                    Icon(
                                                        imageVector = Icons.Default.CheckCircle,
                                                        contentDescription = "Valid UPI ID",
                                                        tint = BharatGreen,
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .testTag("custom_vpa_input")
                                        )

                                        Spacer(modifier = Modifier.height(6.dp))

                                        // Quick Handle Chips
                                        Text(
                                            text = "Popular bank handles:",
                                            fontSize = 10.sp,
                                            color = TextSecondary
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        FlowRow(
                                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            listOf("@okhdfcbank", "@okicici", "@oksbi", "@ybl", "@paytm").forEach { handle ->
                                                Surface(
                                                    shape = RoundedCornerShape(12.dp),
                                                    color = Color.White,
                                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1)),
                                                    modifier = Modifier.clickable {
                                                        val prefix = customVpaText.substringBefore("@").ifEmpty { "user" }
                                                        customVpaText = "$prefix$handle"
                                                    }
                                                ) {
                                                    Text(
                                                        text = handle,
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        color = BharatNavy,
                                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
