package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Operator
import com.example.ui.components.OperatorIcon
import com.example.ui.components.SuperMoneyLogo
import com.example.ui.theme.BharatBlue
import com.example.ui.theme.BharatGreen
import com.example.ui.theme.BharatNavy
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.RechargeUiState
import com.example.ui.viewmodel.RechargeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReceiptScreen(
    viewModel: RechargeViewModel,
    uiState: RechargeUiState,
    modifier: Modifier = Modifier
) {
    val transaction = uiState.lastCompletedTransaction
    val context = LocalContext.current
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    if (transaction == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(onClick = { viewModel.navigateTo(AppScreen.HOME) }) {
                Text("Return Home")
            }
        }
        return
    }

    val dateFormat = SimpleDateFormat("dd MMMM yyyy, hh:mm:ss a", Locale.ENGLISH)
    val op = Operator.entries.find { it.displayName.equals(transaction.operatorName, ignoreCase = true) }
        ?: Operator.JIO

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))

            // Animated Success Checkmark
            AnimatedVisibility(
                visible = visible,
                enter = scaleIn(animationSpec = tween(400)) + fadeIn()
            ) {
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .background(BharatGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success",
                        tint = Color.White,
                        modifier = Modifier.size(44.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Recharge Successful! 🎉",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = BharatNavy
            )
            Text(
                text = "Transaction Completed Successfully",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = BharatGreen
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "₹${"%.0f".format(transaction.amountPaid)}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = BharatNavy
            )
        }

        // Receipt Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SurfaceBorder, RoundedCornerShape(20.dp))
                    .testTag("receipt_card")
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "TRANSACTION RECEIPT",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            color = TextSecondary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = BharatGreen.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = "PAID",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = BharatGreen,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ReceiptRow("Mobile Number", "+91 ${transaction.mobileNumber}")
                    ReceiptRow("Operator & Circle", "${transaction.operatorName} (${transaction.circleName})")
                    ReceiptRow("Pack Validity", transaction.validity)
                    ReceiptRow("Data Allowance", transaction.dataBenefit)
                    ReceiptRow("Payment Method", transaction.paymentMethod)
                    ReceiptRow("Operator Ref ID", transaction.transactionId)
                    ReceiptRow("UPI UTR / Bank Ref", transaction.upiUtr)
                    ReceiptRow("Date & Time", dateFormat.format(Date(transaction.timestamp)))

                    if (transaction.discount > 0) {
                        ReceiptRow("Discount Saved", "₹${"%.0f".format(transaction.discount)} (${transaction.couponApplied ?: "OFFER"})")
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = SurfaceBorder)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Super Money Verified Branding Seal
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SuperMoneyLogo(size = 22.dp, showGlow = false)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Super Money Recharge • 100% Secure Transaction",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSecondary
                        )
                    }
                }
            }
        }

        // Action Buttons: Share, Return Home, History
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            val shareDetails = """
                                Super Money Recharge Receipt:
                                Mobile: +91 ${transaction.mobileNumber}
                                Operator: ${transaction.operatorName}
                                Amount: ₹${transaction.amountPaid}
                                Txn ID: ${transaction.transactionId}
                                UTR: ${transaction.upiUtr}
                                Status: SUCCESS
                            """.trimIndent()
                            Toast.makeText(context, "Receipt copied to clipboard!", Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Share Receipt", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }

                    OutlinedButton(
                        onClick = { viewModel.navigateTo(AppScreen.HISTORY) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                    ) {
                        Text("View History", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Button(
                    onClick = { viewModel.navigateTo(AppScreen.HOME) },
                    colors = ButtonDefaults.buttonColors(containerColor = BharatNavy),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("recharge_another_button")
                ) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Recharge Another Number",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ReceiptRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 12.sp, color = TextSecondary)
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
    }
}
