package com.example.ui.components

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Coupon
import com.example.data.model.Operator
import com.example.data.model.RechargePlan
import com.example.ui.theme.BharatBlue
import com.example.ui.theme.BharatEmerald
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
 * Card displaying the mobile phone number and operator subscription details.
 */
@Composable
fun RechargeTargetNumberCard(
    mobileNumber: String,
    operator: Operator,
    circle: String,
    contactName: String?,
    onChangeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
            .testTag("target_number_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OperatorIcon(operator = operator, size = 42)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        // Formatted Phone Number (+91 XXXXX XXXXX)
                        val formattedNumber = if (mobileNumber.length == 10) {
                            "+91 ${mobileNumber.substring(0, 5)} ${mobileNumber.substring(5)}"
                        } else {
                            "+91 $mobileNumber"
                        }
                        Text(
                            text = formattedNumber,
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp,
                            color = BharatNavy,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${operator.displayName} Prepaid",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                            Text(
                                text = " • $circle",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }

                // Change Number Button
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF1F5F9),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.clickable { onChangeClick() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Change Number",
                            tint = BharatBlue,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Change",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BharatBlue
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = SurfaceBorder.copy(alpha = 0.7f))
            Spacer(modifier = Modifier.height(8.dp))

            // Sub-status strip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(BharatGreen)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (!contactName.isNullOrBlank()) "Contact: $contactName" else "Prepaid SIM • 5G Active",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = "Instant 60s Activation",
                    fontSize = 11.sp,
                    color = BharatGreen,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

/**
 * Card displaying the chosen recharge plan with comprehensive benefit highlights.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChosenPlanSummaryCard(
    plan: RechargePlan,
    onChangePlanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
            .testTag("chosen_plan_summary_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Plan Header with Price & Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "₹${plan.price}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = BharatNavy
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "(${plan.validity})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSecondary,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }

                    if (plan.promoTag != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(BharatSaffron.copy(alpha = 0.14f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = plan.promoTag,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = BharatSaffron
                            )
                        }
                    }
                }

                // Change Plan Link
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF1F5F9),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.clickable { onChangePlanClick() }
                ) {
                    Text(
                        text = "Change Plan",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BharatBlue,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 4-Pillar Benefit Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PlanBenefitPillar(
                    icon = Icons.Default.DataUsage,
                    label = "Data",
                    value = plan.data,
                    tintColor = BharatBlue,
                    modifier = Modifier.weight(1f)
                )
                PlanBenefitPillar(
                    icon = Icons.Default.Schedule,
                    label = "Validity",
                    value = plan.validity,
                    tintColor = BharatSaffron,
                    modifier = Modifier.weight(1f)
                )
                PlanBenefitPillar(
                    icon = Icons.Default.Call,
                    label = "Voice",
                    value = plan.voice,
                    tintColor = BharatGreen,
                    modifier = Modifier.weight(1.1f)
                )
                PlanBenefitPillar(
                    icon = Icons.AutoMirrored.Filled.Message,
                    label = "SMS",
                    value = plan.sms,
                    tintColor = Color(0xFF7C3AED),
                    modifier = Modifier.weight(0.9f)
                )
            }

            // Description / Perks
            if (plan.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = plan.description,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )
            }

            if (plan.perks.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    plan.perks.forEach { perk ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFF1F5F9))
                                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = BharatSaffron,
                                    modifier = Modifier.size(11.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = perk,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = BharatNavy
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlanBenefitPillar(
    icon: ImageVector,
    label: String,
    value: String,
    tintColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = tintColor.copy(alpha = 0.08f),
        border = androidx.compose.foundation.BorderStroke(1.dp, tintColor.copy(alpha = 0.2f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tintColor,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = label,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecondary
                )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = value,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = BharatNavy,
                maxLines = 1
            )
        }
    }
}

/**
 * Itemized Payment Summary & Bill Breakdown Card.
 */
@Composable
fun PaymentBreakdownCard(
    planPrice: Int,
    discount: Double,
    finalAmount: Double,
    appliedCoupon: Coupon?,
    onRemoveCoupon: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
            .testTag("payment_breakdown_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ReceiptLong,
                        contentDescription = null,
                        tint = BharatNavy,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Payment Breakdown",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BharatNavy
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFE3F2FD))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "GST Included",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = BharatBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Plan Base Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Plan MRP",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
                Text(
                    text = "₹$planPrice",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
            }

            // Coupon Discount
            if (discount > 0 && appliedCoupon != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Coupon (${appliedCoupon.code})",
                            fontSize = 13.sp,
                            color = BharatGreen,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Remove",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red,
                            modifier = Modifier.clickable { onRemoveCoupon() }
                        )
                    }
                    Text(
                        text = "- ₹${"%.0f".format(discount)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = BharatGreen
                    )
                }
            }

            // Platform Fee
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Platform Fee",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(BharatGreen.copy(alpha = 0.12f))
                            .padding(horizontal = 5.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "FREE",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = BharatGreen
                        )
                    }
                }
                Text(
                    text = "₹0",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BharatGreen
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = SurfaceBorder)
            Spacer(modifier = Modifier.height(12.dp))

            // Total Payable
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total Payable Amount",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BharatNavy
                    )
                    Text(
                        text = "Zero hidden charges",
                        fontSize = 11.sp,
                        color = BharatGreen
                    )
                }
                Text(
                    text = "₹${"%.0f".format(finalAmount)}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = BharatNavy
                )
            }
        }
    }
}
