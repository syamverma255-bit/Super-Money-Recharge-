package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.example.ui.components.HelpSupportBottomSheet
import com.example.ui.screens.HomeScreen
import com.example.ui.viewmodel.RechargeViewModel
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class HelpSupportBottomSheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `help button on home screen triggers openHelpSheet`() {
        val viewModel = RechargeViewModel(ApplicationProvider.getApplicationContext())
        val uiState = viewModel.uiState.value

        composeTestRule.setContent {
            HomeScreen(
                viewModel = viewModel,
                uiState = uiState,
                recentTransactions = emptyList()
            )
        }

        // Verify Help button exists and can be clicked
        composeTestRule.onNodeWithTag("help_button").assertExists()
        composeTestRule.onNodeWithTag("help_button").performClick()

        assertTrue("showHelpSheet should be true after tapping Help button", viewModel.uiState.value.showHelpSheet)
    }

    @Test
    fun `help bottom sheet renders FAQs and customer support contact options`() {
        var dismissed = false

        composeTestRule.setContent {
            HelpSupportBottomSheet(
                onDismiss = { dismissed = true },
                initialContextInfo = "Plan ₹299 for 9876543210 (Jio)"
            )
        }

        // 1. Verify Header
        composeTestRule.onNodeWithText("Help & Support").assertExists()
        composeTestRule.onNodeWithText("24x7 Active").assertExists()

        // 2. Verify Customer Support Contact Card and Buttons
        composeTestRule.onNodeWithTag("customer_support_card").assertExists()
        composeTestRule.onNodeWithTag("chat_support_button").assertExists()
        composeTestRule.onNodeWithTag("email_support_button").assertExists()
        composeTestRule.onNodeWithText("Live Chat").assertExists()
        composeTestRule.onNodeWithText("Email Us").assertExists()

        // 3. Verify FAQs Title & Search
        composeTestRule.onNodeWithText("Frequently Asked Questions").assertExists()
        composeTestRule.onNodeWithTag("faq_search_input").assertExists()

        // 4. Verify Common FAQ Items (scroll down to FAQ cards)
        composeTestRule.onNodeWithTag("help_faq_lazy_column").performScrollToIndex(2)
        composeTestRule.onNodeWithText("Recharge is successful, but plan or balance is not updated?").assertExists()

        // 5. Test FAQ Search Filtering
        composeTestRule.onNodeWithTag("help_faq_lazy_column").performScrollToIndex(1)
        composeTestRule.onNodeWithTag("faq_search_input").performTextInput("refund")
        composeTestRule.onNodeWithTag("help_faq_lazy_column").performScrollToIndex(2)
        composeTestRule.onNodeWithText("Money was deducted from my bank/UPI, but recharge failed?").assertExists()

        // 6. Test Dismiss Button
        composeTestRule.onNodeWithTag("close_help_button").assertExists()
        composeTestRule.onNodeWithTag("close_help_button").performClick()
        assertTrue("onDismiss should be called when close button is clicked", dismissed)
    }
}
