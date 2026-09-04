package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BharatBlue
import com.example.ui.theme.BharatBlueLight
import com.example.ui.theme.BharatEmerald
import com.example.ui.theme.BharatGreen
import com.example.ui.theme.BharatGreenLight
import com.example.ui.theme.BharatNavy
import com.example.ui.theme.BharatSaffron
import com.example.ui.theme.BharatSaffronLight
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

/**
 * Data class representing a Frequently Asked Question.
 */
data class FaqItem(
    val id: String,
    val question: String,
    val answer: String,
    val category: String,
    val badge: String? = null
)

/**
 * Common FAQs curated for mobile recharge and bill payments.
 */
val CommonFaqs = listOf(
    FaqItem(
        id = "faq_1",
        question = "Recharge is successful, but plan or balance is not updated?",
        answer = "Telecom operators (Jio, Airtel, Vi, BSNL) typically activate prepaid plans within 60 to 120 seconds. Please toggle Airplane Mode ON/OFF or restart your handset. Your 12-digit Operator Reference ID is in your receipt under the History tab.",
        category = "Recharge Status",
        badge = "Most Common"
    ),
    FaqItem(
        id = "faq_2",
        question = "Money was deducted from my bank/UPI, but recharge failed?",
        answer = "Your money is 100% safe. When a payment is debited but the telecom operator fails to credit the plan, the bank initiates an automated refund to your source account within 2 hours (up to 24 hours per RBI guidelines).",
        category = "Refunds & Payments",
        badge = "Auto-Refund"
    ),
    FaqItem(
        id = "faq_3",
        question = "How do I download or share my tax invoice / receipt?",
        answer = "Go to the 'History' tab on the home screen. Tap any completed recharge to open the full GST-compliant receipt. You can download the PDF or share it directly via WhatsApp.",
        category = "Receipts"
    ),
    FaqItem(
        id = "faq_4",
        question = "Can I recharge a number from another circle or ported SIM?",
        answer = "Yes! If you recently ported your mobile number via MNP, tap on the Operator/Circle badge on the home screen or plans page to select your current operator and active telecom circle.",
        category = "Ported SIM"
    ),
    FaqItem(
        id = "faq_5",
        question = "How do I claim coupon discounts and cashback?",
        answer = "On the Payment Summary screen, look for 'Offers & Coupons'. Tap 'APPLY' on available promo codes (like SUPER50 or BHARAT20) to deduct savings immediately from your payable amount.",
        category = "Offers"
    ),
    FaqItem(
        id = "faq_6",
        question = "DTH Recharge: How long does TV signal reconnection take?",
        answer = "Make sure your Set-Top Box is kept switched ON while recharging. Signal restores automatically within 5 to 15 minutes of successful payment.",
        category = "DTH"
    )
)

/**
 * Modal Bottom Sheet for Customer Support & FAQs.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HelpSupportBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    initialContextInfo: String? = null
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val focusManager = LocalFocusManager.current

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var expandedFaqId by remember { mutableStateOf<String?>("faq_1") }
    var showChatDialog by remember { mutableStateOf(false) }

    val categories = remember {
        listOf("All", "Recharge Status", "Refunds & Payments", "Receipts", "Ported SIM", "Offers", "DTH")
    }

    val filteredFaqs = remember(searchQuery, selectedCategory) {
        CommonFaqs.filter { faq ->
            val matchesCategory = selectedCategory == "All" || faq.category == selectedCategory
            val query = searchQuery.trim().lowercase()
            val matchesSearch = query.isEmpty() ||
                    faq.question.lowercase().contains(query) ||
                    faq.answer.lowercase().contains(query) ||
                    faq.category.lowercase().contains(query)
            matchesCategory && matchesSearch
        }
    }

    // Interactive In-App Chat Dialog
    if (showChatDialog) {
        SupportChatDialog(
            contextInfo = initialContextInfo,
            onDismiss = { showChatDialog = false }
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = SurfaceCard,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        modifier = modifier.testTag("help_bottom_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(BharatBlue.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SupportAgent,
                            contentDescription = null,
                            tint = BharatBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Help & Support",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = BharatNavy
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(BharatGreenLight)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "24x7 Active",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BharatGreen
                                )
                            }
                        }
                        Text(
                            text = "Instant answers & customer care assistance",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("close_help_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Help",
                        tint = TextSecondary
                    )
                }
            }

            HorizontalDivider(color = SurfaceBorder, modifier = Modifier.padding(vertical = 4.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("help_faq_lazy_column"),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Customer Support Actions Card (Initiate Chat / Email)
                item {
                    CustomerSupportContactCard(
                        onChatClick = {
                            // Try launching WhatsApp/chat intent, or open in-app chat dialog
                            val supportNumber = "919876543210"
                            val message = "Hello SuperMoney Support, I need help with my recharge. Context: ${initialContextInfo ?: "App Help"}"
                            val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("https://api.whatsapp.com/send?phone=$supportNumber&text=${Uri.encode(message)}")
                            }
                            try {
                                context.startActivity(whatsappIntent)
                            } catch (_: Exception) {
                                // Fallback to in-app instant chat assistant
                                showChatDialog = true
                            }
                        },
                        onEmailClick = {
                            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:support@supermoneyrecharge.in")
                                putExtra(Intent.EXTRA_SUBJECT, "Customer Support: Recharge Enquiry")
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Dear Support Team,\n\nI need assistance with my recharge transaction.\nDetails: ${initialContextInfo ?: "Mobile Recharge"}\n\nThank you."
                                )
                            }
                            try {
                                context.startActivity(Intent.createChooser(emailIntent, "Send Email via"))
                            } catch (_: Exception) {
                                clipboardManager.setText(AnnotatedString("support@supermoneyrecharge.in"))
                                Toast.makeText(context, "Support email copied to clipboard!", Toast.LENGTH_LONG).show()
                            }
                        }
                    )
                }

                // FAQ Section Title & Search Bar
                item {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Frequently Asked Questions",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = BharatNavy
                            )
                            Text(
                                text = "${filteredFaqs.size} answers",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // FAQ Search Input
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search recharge issues, refunds, receipts...", fontSize = 13.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = TextSecondary,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Clear search",
                                            tint = TextSecondary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BharatBlue,
                                unfocusedBorderColor = SurfaceBorder,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = SurfaceLight
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("faq_search_input")
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Category Filters
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 2.dp)
                        ) {
                            items(categories) { cat ->
                                val isSelected = selectedCategory == cat
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = if (isSelected) BharatNavy else Color.White,
                                    border = androidx.compose.foundation.BorderStroke(
                                        width = 1.dp,
                                        color = if (isSelected) BharatNavy else SurfaceBorder
                                    ),
                                    modifier = Modifier.clickable { selectedCategory = cat }
                                ) {
                                    Text(
                                        text = cat,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color.White else TextPrimary,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // FAQ Accordion Cards
                if (filteredFaqs.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                                    contentDescription = null,
                                    tint = TextTertiary,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "No questions match '$searchQuery'",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 13.sp,
                                    color = TextSecondary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Tap 'Chat with Support' above for custom help",
                                    fontSize = 11.sp,
                                    color = BharatBlue
                                )
                            }
                        }
                    }
                } else {
                    items(filteredFaqs, key = { it.id }) { faq ->
                        val isExpanded = expandedFaqId == faq.id

                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isExpanded) BharatBlueLight.copy(alpha = 0.35f) else SurfaceCard
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = if (isExpanded) BharatBlue.copy(alpha = 0.4f) else SurfaceBorder,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    expandedFaqId = if (isExpanded) null else faq.id
                                }
                                .testTag("faq_item_${faq.id}")
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        if (faq.badge != null) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(BharatSaffronLight)
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = faq.badge,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = BharatSaffron
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                        }

                                        Text(
                                            text = faq.question,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 13.sp,
                                            color = if (isExpanded) BharatNavy else TextPrimary,
                                            lineHeight = 18.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Icon(
                                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                                        tint = if (isExpanded) BharatBlue else TextSecondary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                AnimatedVisibility(
                                    visible = isExpanded,
                                    enter = fadeIn() + expandVertically(),
                                    exit = fadeOut() + shrinkVertically()
                                ) {
                                    Column(modifier = Modifier.padding(top = 10.dp)) {
                                        HorizontalDivider(
                                            color = SurfaceBorder,
                                            modifier = Modifier.padding(bottom = 10.dp)
                                        )
                                        Text(
                                            text = faq.answer,
                                            fontSize = 12.sp,
                                            color = TextSecondary,
                                            lineHeight = 18.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Trust Footer
                item {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = BharatGreen,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Bharat BillPay Verified • Toll-Free Help: 1800-889-9999",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        }
    }
}

/**
 * Card containing prominent Customer Support action buttons (Chat & Email).
 */
@Composable
fun CustomerSupportContactCard(
    onChatClick: () -> Unit,
    onEmailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
            .testTag("customer_support_card")
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFF8FAFC), Color.White)
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Need Personalized Assistance?",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = BharatNavy
                    )
                    Text(
                        text = "Our support specialists are ready to help you",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(BharatGreenLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Headphones,
                        contentDescription = null,
                        tint = BharatGreen,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Two Action Buttons: Chat Support & Email Support
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 1. Chat Support Button
                Button(
                    onClick = onChatClick,
                    colors = ButtonDefaults.buttonColors(containerColor = BharatGreen),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("chat_support_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Chat,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Live Chat",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // 2. Email Support Button
                OutlinedButton(
                    onClick = onEmailClick,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BharatBlue),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, BharatBlue),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("email_support_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = BharatBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Email Us",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = BharatBlue
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Contact details subline
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "support@supermoneyrecharge.in",
                    fontSize = 10.sp,
                    color = TextSecondary
                )
                Text(
                    text = "Avg. Reply: < 2 mins",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BharatGreen
                )
            }
        }
    }
}

/**
 * Interactive In-App Support Chat Assistant Dialog (for instant responses and automated resolution).
 */
@Composable
fun SupportChatDialog(
    contextInfo: String?,
    onDismiss: () -> Unit
) {
    var userMessage by remember { mutableStateOf("") }
    var chatMessages by remember {
        mutableStateOf(
            listOf(
                "SuperMoney Bot" to "Hello! Welcome to SuperMoney Customer Support. How can we help with your recharge today?",
                "SuperMoney Bot" to if (contextInfo != null) "We noticed you're inquiring regarding: $contextInfo" else "You can ask about pending recharges, failed debits, or coupon issues."
            )
        )
    }

    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("support_chat_dialog")
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(BharatGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SupportAgent,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Live Support Chat",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = BharatNavy
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(BharatGreen)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Agent Online",
                                    fontSize = 11.sp,
                                    color = BharatGreen,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Chat",
                            tint = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = SurfaceBorder)
                Spacer(modifier = Modifier.height(12.dp))

                // Chat Messages
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    chatMessages.forEach { (sender, text) ->
                        val isUser = sender == "You"
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 14.dp,
                                            topEnd = 14.dp,
                                            bottomStart = if (isUser) 14.dp else 2.dp,
                                            bottomEnd = if (isUser) 2.dp else 14.dp
                                        )
                                    )
                                    .background(if (isUser) BharatBlue else Color(0xFFF1F5F9))
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = text,
                                    fontSize = 12.sp,
                                    color = if (isUser) Color.White else BharatNavy,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Quick Prompt Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("Status Check", "Refund Issue", "Receipt Request").forEach { prompt ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFF1F5F9),
                            modifier = Modifier.clickable {
                                chatMessages = chatMessages + ("You" to prompt) +
                                        ("SuperMoney Bot" to "Thanks! For '$prompt', telecom operators take 1-2 minutes to activate. If debited and failed, your bank initiates an automated refund.")
                            }
                        ) {
                            Text(
                                text = prompt,
                                fontSize = 10.sp,
                                color = BharatBlue,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Input Box
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = userMessage,
                        onValueChange = { userMessage = it },
                        placeholder = { Text("Type your question...", fontSize = 12.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("chat_input_field")
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (userMessage.isNotBlank()) {
                                val msg = userMessage.trim()
                                userMessage = ""
                                chatMessages = chatMessages + ("You" to msg) +
                                        ("SuperMoney Bot" to "Got it. A support ticket #SM-${(1000..9999).random()} has been created. A specialist will follow up shortly!")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BharatNavy),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("chat_send_button")
                    ) {
                        Text(text = "Send", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
