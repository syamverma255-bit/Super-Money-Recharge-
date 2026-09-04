package com.example.ui.screens

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ChosenPlanSummaryCard
import com.example.ui.components.PaymentBreakdownCard
import com.example.ui.components.RechargeTargetNumberCard
import com.example.ui.components.SuperMoneyLogo
import com.example.ui.components.UpiPaymentOptionsList
import com.example.ui.components.UpiPinDialog
import com.example.ui.theme.BharatBlue
import com.example.ui.theme.BharatEmerald
import com.example.ui.theme.BharatGreen
import com.example.ui.theme.BharatNavy
import com.example.ui.theme.BharatSaffron
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.RechargeUiState
import com.example.ui.viewmodel.RechargeViewModel

/**
 * Payment Summary Screen UI
 * Displays the chosen plan, subscriber phone number, itemized payment breakdown,
 * and prominent UPI payment options (Google Pay, PhonePe, Paytm, BHIM UPI).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    viewModel: RechargeViewModel,
    uiState: RechargeUiState,
    modifier: Modifier = Modifier
) {
    val plan = uiState.selectedPlan ?: return
    var showOtherPaymentMethods by remember { mutableStateOf(false) }

    val discount = if (uiState.appliedCoupon != null) {
        if (uiState.appliedCoupon.isPercentage) {
            (plan.price * uiState.appliedCoupon.percentValue).toInt()
                .coerceAtMost(uiState.appliedCoupon.maxDiscount).toDouble()
        } else {
            uiState.appliedCoupon.discountAmount.toDouble()
        }
    } else 0.0

    val finalAmount = (plan.price - discount).coerceAtLeast(0.0)

    // UPI PIN Dialog for realistic payment simulation
    if (uiState.showUpiPinDialog) {
        UpiPinDialog(
            amount = finalAmount,
            upiAppName = uiState.selectedPaymentMethod,
            onConfirm = { viewModel.confirmUpiPin(it) },
            onDismiss = { viewModel.dismissUpiPinDialog() }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight)
    ) {
        // App Bar
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Payment Summary",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Review plan & complete recharge",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.75f)
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = { viewModel.openHelpSheet() },
                    modifier = Modifier.testTag("help_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = "Help & Support",
                        tint = Color.White
                    )
                }
                SuperMoneyLogo(size = 32.dp, showGlow = false)
                Spacer(modifier = Modifier.width(8.dp))
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = BharatNavy)
        )

        // Scrollable Content
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .testTag("payment_summary_scroll_list"),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. Target Phone Number & Operator
            item {
                RechargeTargetNumberCard(
                    mobileNumber = uiState.mobileNumber,
                    operator = uiState.selectedOperator,
                    circle = uiState.selectedCircle,
                    contactName = uiState.contactName,
                    onChangeClick = { viewModel.navigateTo(AppScreen.HOME) }
                )
            }

            // 2. Chosen Plan Details
            item {
                ChosenPlanSummaryCard(
                    plan = plan,
                    onChangePlanClick = { viewModel.navigateTo(AppScreen.BROWSE_PLANS) }
                )
            }

            // 3. Coupon Promo Offer Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocalOffer,
                                    contentDescription = null,
                                    tint = BharatSaffron,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Offers & Coupons",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BharatNavy
                                )
                            }

                            if (uiState.appliedCoupon != null) {
                                Text(
                                    text = "Remove",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red,
                                    modifier = Modifier.clickable { viewModel.removeCoupon() }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        if (uiState.appliedCoupon != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(BharatGreen.copy(alpha = 0.1f))
                                    .border(1.dp, BharatGreen.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = "Coupon '${uiState.appliedCoupon.code}' applied: Extra ₹${"%.0f".format(discount)} savings!",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = BharatGreen
                                )
                            }
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                viewModel.availableCoupons.forEach { coupon ->
                                    val isApplicable = plan.price >= coupon.minAmount
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isApplicable) Color(0xFFFFF9E6) else Color(0xFFF8FAFC))
                                            .border(
                                                1.dp,
                                                if (isApplicable) Color(0xFFFFD54F) else SurfaceBorder,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 12.dp, vertical = 10.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = coupon.code,
                                                fontWeight = FontWeight.ExtraBold,
                                                fontSize = 13.sp,
                                                color = if (isApplicable) BharatNavy else TextSecondary
                                            )
                                            Text(
                                                text = coupon.discountDescription,
                                                fontSize = 11.sp,
                                                color = TextSecondary
                                            )
                                        }
                                        if (isApplicable) {
                                            Button(
                                                onClick = { viewModel.applyCoupon(coupon) },
                                                colors = ButtonDefaults.buttonColors(containerColor = BharatSaffron),
                                                shape = RoundedCornerShape(6.dp),
                                                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                                                    horizontal = 12.dp,
                                                    vertical = 4.dp
                                                ),
                                                modifier = Modifier.height(30.dp)
                                            ) {
                                                Text(text = "APPLY", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                            }
                                        } else {
                                            Text(
                                                text = "Min ₹${coupon.minAmount}",
                                                fontSize = 10.sp,
                                                color = TextSecondary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 4. Itemized Bill Summary Breakdown
            item {
                PaymentBreakdownCard(
                    planPrice = plan.price,
                    discount = discount,
                    finalAmount = finalAmount,
                    appliedCoupon = uiState.appliedCoupon,
                    onRemoveCoupon = { viewModel.removeCoupon() }
                )
            }

            // 5. Featured UPI Payment Options (Google Pay, PhonePe, Paytm, BHIM)
            item {
                UpiPaymentOptionsList(
                    selectedMethod = uiState.selectedPaymentMethod,
                    onSelectMethod = { viewModel.setPaymentMethod(it) }
                )
            }

            // 6. Collapsible Alternative Payment Modes (Cards, NetBanking, Wallet)
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showOtherPaymentMethods = !showOtherPaymentMethods },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Other Payment Modes (Cards, Net Banking)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextSecondary
                            )
                            Icon(
                                imageVector = if (showOtherPaymentMethods) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = "Toggle other payment options",
                                tint = TextSecondary
                            )
                        }

                        AnimatedVisibility(
                            visible = showOtherPaymentMethods,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            Column(
                                modifier = Modifier.padding(top = 12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf(
                                    Triple("Debit / Credit Card", "RuPay, Visa, Mastercard", Icons.Default.CreditCard),
                                    Triple("Net Banking", "SBI, HDFC, ICICI, Axis Bank", Icons.Default.AccountBalance),
                                    Triple("Bharat Wallet", "Balance: ₹${uiState.walletBalance.toInt()}", Icons.Default.AccountBalanceWallet)
                                ).forEach { (mode, desc, icon) ->
                                    val isSelected = uiState.selectedPaymentMethod == mode
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isSelected) BharatBlue.copy(alpha = 0.08f) else Color(0xFFFAFBFC),
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            if (isSelected) BharatBlue else SurfaceBorder
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { viewModel.setPaymentMethod(mode) }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = icon,
                                                contentDescription = null,
                                                tint = if (isSelected) BharatBlue else TextSecondary,
                                                modifier = Modifier.size(22.dp)
                                            )
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = mode,
                                                    fontSize = 13.sp,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                    color = TextPrimary
                                                )
                                                Text(
                                                    text = desc,
                                                    fontSize = 11.sp,
                                                    color = TextSecondary
                                                )
                                            }
                                            RadioButton(
                                                selected = isSelected,
                                                onClick = { viewModel.setPaymentMethod(mode) },
                                                colors = RadioButtonDefaults.colors(selectedColor = BharatBlue)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 7. Security Guarantee
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "100% Secure 256-bit Encryption • NPCI UPI Certified",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        // Bottom Fixed Payment CTA Bar
        Surface(
            color = SurfaceCard,
            tonalElevation = 8.dp,
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total to Pay",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondary
                    )
                    Text(
                        text = "₹${"%.0f".format(finalAmount)}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = BharatNavy
                    )
                }

                // Short user-friendly UPI app name for CTA
                val actionLabel = when {
                    uiState.selectedPaymentMethod.contains("Google Pay") -> "Pay via GPay"
                    uiState.selectedPaymentMethod.contains("PhonePe") -> "Pay via PhonePe"
                    uiState.selectedPaymentMethod.contains("Paytm") -> "Pay via Paytm"
                    uiState.selectedPaymentMethod.contains("UPI") -> "Pay with UPI"
                    else -> "Pay ₹${"%.0f".format(finalAmount)}"
                }

                Button(
                    onClick = { viewModel.onInitiatePayment() },
                    enabled = !uiState.isProcessingPayment,
                    colors = ButtonDefaults.buttonColors(containerColor = BharatGreen),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .height(50.dp)
                        .testTag("pay_recharge_button")
                ) {
                    if (uiState.isProcessingPayment) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Processing...", fontWeight = FontWeight.Bold)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = actionLabel,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
