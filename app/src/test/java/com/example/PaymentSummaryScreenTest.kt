package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.Operator
import com.example.data.model.PlanCategory
import com.example.data.model.RechargePlan
import com.example.ui.screens.CheckoutScreen
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.RechargeUiState
import com.example.ui.viewmodel.RechargeViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class PaymentSummaryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `payment summary displays phone number, plan details, and UPI payment options`() {
        val testPlan = RechargePlan(
            id = "test_jio_299",
            operator = Operator.JIO,
            price = 299,
            validity = "28 Days",
            data = "1.5 GB/Day",
            voice = "Unlimited",
            sms = "100 SMS/Day",
            perks = listOf("JioCinema", "Unlimited 5G Data"),
            category = PlanCategory.POPULAR,
            promoTag = "BESTSELLER",
            description = "Unlimited true 5G data with unlimited calls and 100 SMS/day"
        )

        val viewModel = RechargeViewModel(ApplicationProvider.getApplicationContext())
        viewModel.onMobileNumberChanged("9876543210")
        viewModel.onOperatorChanged(Operator.JIO)
        viewModel.onPlanSelected(testPlan)

        val uiState = RechargeUiState(
            mobileNumber = "9876543210",
            selectedOperator = Operator.JIO,
            selectedCircle = "Delhi NCR",
            selectedPlan = testPlan,
            selectedPaymentMethod = "Google Pay UPI",
            currentScreen = AppScreen.CHECKOUT
        )

        composeTestRule.setContent {
            CheckoutScreen(
                viewModel = viewModel,
                uiState = uiState
            )
        }

        // 1. Verify Payment Summary Header
        composeTestRule.onNodeWithText("Payment Summary").assertExists()

        // 2. Verify Phone Number is displayed formatted (+91 98765 43210)
        composeTestRule.onNodeWithTag("target_number_card").assertExists()
        composeTestRule.onNodeWithText("+91 98765 43210").assertExists()

        // 3. Verify Chosen Plan Details are displayed
        composeTestRule.onNodeWithTag("chosen_plan_summary_card").assertExists()
        composeTestRule.onAllNodesWithText("₹299")[0].assertExists()
        composeTestRule.onNodeWithText("1.5 GB/Day").assertExists()
        composeTestRule.onNodeWithText("28 Days").assertExists()

        // 4. Scroll to and verify Itemized Payment Breakdown
        composeTestRule.onNodeWithTag("payment_summary_scroll_list").performScrollToIndex(3)
        composeTestRule.onNodeWithTag("payment_breakdown_card").assertExists()
        composeTestRule.onNodeWithText("Total Payable Amount").assertExists()

        // 5. Scroll to and verify UPI Payment Options (GPay, PhonePe, Paytm, etc.) exist
        composeTestRule.onNodeWithTag("payment_summary_scroll_list").performScrollToIndex(4)
        composeTestRule.onNodeWithTag("upi_option_google_pay_upi").assertExists()
        composeTestRule.onNodeWithTag("upi_option_phonepe_upi").assertExists()
        composeTestRule.onNodeWithTag("upi_option_paytm_upi").assertExists()

        // 6. Verify Pay Button exists
        composeTestRule.onNodeWithTag("pay_recharge_button").assertExists()

        // 7. Verify clicking PhonePe selects PhonePe
        composeTestRule.onNodeWithTag("upi_option_phonepe_upi").performClick()
        assertEquals("PhonePe UPI", viewModel.uiState.value.selectedPaymentMethod)
    }
}
