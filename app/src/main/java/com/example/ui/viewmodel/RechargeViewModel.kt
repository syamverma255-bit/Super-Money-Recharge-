package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.model.Coupon
import com.example.data.model.Operator
import com.example.data.model.PlanCategory
import com.example.data.model.RechargePlan
import com.example.data.model.RechargeTransaction
import com.example.data.repository.DthOperator
import com.example.data.repository.DthPlan
import com.example.data.repository.RechargeRepository
import com.example.data.repository.SavedContact
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class AppScreen {
    HOME,
    BROWSE_PLANS,
    CHECKOUT,
    RECEIPT,
    HISTORY,
    DTH
}

data class RechargeUiState(
    val mobileNumber: String = "",
    val contactName: String? = null,
    val selectedOperator: Operator = Operator.JIO,
    val selectedCircle: String = "Delhi NCR",
    val selectedCategory: PlanCategory = PlanCategory.POPULAR,
    val searchQuery: String = "",
    val selectedPlan: RechargePlan? = null,
    val appliedCoupon: Coupon? = null,
    val currentScreen: AppScreen = AppScreen.HOME,
    val isProcessingPayment: Boolean = false,
    val showUpiPinDialog: Boolean = false,
    val selectedPaymentMethod: String = "Google Pay UPI",
    val lastCompletedTransaction: RechargeTransaction? = null,
    val walletBalance: Double = 150.0,
    val errorMessage: String? = null,
    // DTH State
    val selectedDthOperator: DthOperator? = null,
    val dthSubscriberId: String = "",
    val selectedDthPlan: DthPlan? = null,
    val showHelpSheet: Boolean = false
)

class RechargeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RechargeRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = RechargeRepository(db.rechargeDao())
    }

    private val _uiState = MutableStateFlow(
        RechargeUiState(
            selectedDthOperator = repository.dthOperators.firstOrNull()
        )
    )
    val uiState: StateFlow<RechargeUiState> = _uiState.asStateFlow()

    val transactionHistory: StateFlow<List<RechargeTransaction>> =
        repository.allTransactions.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val quickContacts: List<SavedContact> = repository.defaultContacts
    val availableCoupons: List<Coupon> = repository.availableCoupons
    val dthOperators: List<DthOperator> = repository.dthOperators

    fun onMobileNumberChanged(rawNumber: String) {
        val digits = rawNumber.filter { it.isDigit() }.take(10)
        _uiState.update { state ->
            val (detectedOp, detectedCircle) = repository.detectOperator(digits)
            state.copy(
                mobileNumber = digits,
                errorMessage = null,
                selectedOperator = if (digits.length >= 2) detectedOp else state.selectedOperator,
                selectedCircle = if (digits.length >= 2) detectedCircle else state.selectedCircle,
                contactName = null
            )
        }
    }

    fun onSelectContact(contact: SavedContact) {
        _uiState.update {
            it.copy(
                mobileNumber = contact.number,
                contactName = contact.name,
                selectedOperator = contact.operator,
                selectedCircle = contact.circle,
                errorMessage = null
            )
        }
    }

    fun onOperatorChanged(operator: Operator) {
        _uiState.update { it.copy(selectedOperator = operator, selectedPlan = null) }
    }

    fun onCircleChanged(circle: String) {
        _uiState.update { it.copy(selectedCircle = circle) }
    }

    fun onCategorySelected(category: PlanCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onPlanSelected(plan: RechargePlan) {
        _uiState.update {
            it.copy(
                selectedPlan = plan,
                currentScreen = AppScreen.CHECKOUT,
                errorMessage = null
            )
        }
    }

    fun applyCoupon(coupon: Coupon) {
        val plan = _uiState.value.selectedPlan ?: return
        if (plan.price < coupon.minAmount) {
            _uiState.update { it.copy(errorMessage = "Minimum recharge amount for ${coupon.code} is ₹${coupon.minAmount}") }
            return
        }
        _uiState.update { it.copy(appliedCoupon = coupon, errorMessage = null) }
    }

    fun removeCoupon() {
        _uiState.update { it.copy(appliedCoupon = null) }
    }

    fun setPaymentMethod(method: String) {
        _uiState.update { it.copy(selectedPaymentMethod = method) }
    }

    fun onInitiatePayment() {
        val state = _uiState.value
        if (state.mobileNumber.length != 10) {
            _uiState.update { it.copy(errorMessage = "Please enter a valid 10-digit mobile number") }
            return
        }
        if (state.selectedPlan == null) {
            _uiState.update { it.copy(errorMessage = "Please select a recharge plan") }
            return
        }

        // If UPI method is selected, show UPI PIN prompt for authenticity
        if (state.selectedPaymentMethod.contains("UPI")) {
            _uiState.update { it.copy(showUpiPinDialog = true) }
        } else {
            processPayment()
        }
    }

    fun confirmUpiPin(pin: String) {
        _uiState.update { it.copy(showUpiPinDialog = false) }
        processPayment()
    }

    fun dismissUpiPinDialog() {
        _uiState.update { it.copy(showUpiPinDialog = false) }
    }

    private fun processPayment() {
        val state = _uiState.value
        val plan = state.selectedPlan ?: return

        val discount = if (state.appliedCoupon != null) {
            if (state.appliedCoupon.isPercentage) {
                (plan.price * state.appliedCoupon.percentValue).toInt().coerceAtMost(state.appliedCoupon.maxDiscount).toDouble()
            } else {
                state.appliedCoupon.discountAmount.toDouble()
            }
        } else 0.0

        val finalAmount = (plan.price - discount).coerceAtLeast(0.0)

        _uiState.update { it.copy(isProcessingPayment = true) }

        viewModelScope.launch {
            // Realistic payment gateway simulation
            delay(1500)

            val txnNumber = Random.nextLong(10000000, 99999999)
            val utrNumber = "${Random.nextLong(400000, 499999)}${Random.nextLong(100000, 999999)}"
            val txnId = "BHARAT-${state.selectedOperator.name.take(3)}-$txnNumber"

            val transaction = RechargeTransaction(
                mobileNumber = state.mobileNumber,
                contactName = state.contactName,
                operatorName = state.selectedOperator.displayName,
                circleName = state.selectedCircle,
                planPrice = plan.price.toDouble(),
                discount = discount,
                amountPaid = finalAmount,
                validity = plan.validity,
                dataBenefit = plan.data,
                planDescription = plan.description,
                paymentMethod = state.selectedPaymentMethod,
                transactionId = txnId,
                upiUtr = utrNumber,
                timestamp = System.currentTimeMillis(),
                status = "SUCCESS",
                couponApplied = state.appliedCoupon?.code
            )

            // Persist to Room Database
            repository.saveTransaction(transaction)

            _uiState.update {
                it.copy(
                    isProcessingPayment = false,
                    lastCompletedTransaction = transaction,
                    currentScreen = AppScreen.RECEIPT,
                    appliedCoupon = null
                )
            }
        }
    }

    fun repeatRecharge(transaction: RechargeTransaction) {
        val matchingOp = Operator.entries.find { it.displayName.equals(transaction.operatorName, ignoreCase = true) }
            ?: Operator.JIO

        val plans = repository.getAllPlans(matchingOp)
        val matchingPlan = plans.find { it.price == transaction.planPrice.toInt() }
            ?: plans.firstOrNull()

        _uiState.update {
            it.copy(
                mobileNumber = transaction.mobileNumber,
                contactName = transaction.contactName,
                selectedOperator = matchingOp,
                selectedCircle = transaction.circleName,
                selectedPlan = matchingPlan,
                currentScreen = AppScreen.CHECKOUT,
                errorMessage = null
            )
        }
    }

    fun quickRechargeAmount(amount: Int) {
        val plans = repository.getAllPlans(_uiState.value.selectedOperator)
        val plan = plans.find { it.price == amount } ?: plans.firstOrNull()
        if (plan != null) {
            onPlanSelected(plan)
        }
    }

    fun navigateTo(screen: AppScreen) {
        _uiState.update { it.copy(currentScreen = screen, errorMessage = null) }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }

    // DTH Actions
    fun onDthOperatorSelected(dthOp: DthOperator) {
        _uiState.update { it.copy(selectedDthOperator = dthOp, selectedDthPlan = null) }
    }

    fun onDthSubscriberIdChanged(id: String) {
        _uiState.update { it.copy(dthSubscriberId = id) }
    }

    fun onDthPlanSelected(dthPlan: DthPlan) {
        val op = _uiState.value.selectedDthOperator ?: return
        val fakeMobile = if (_uiState.value.dthSubscriberId.isNotEmpty()) _uiState.value.dthSubscriberId else op.sampleId

        val fakePlan = RechargePlan(
            id = "dth_${dthPlan.price}",
            operator = Operator.JIO,
            price = dthPlan.price,
            validity = dthPlan.validity,
            data = dthPlan.channels,
            voice = "DTH Subscription",
            sms = "HD + SD Channels",
            perks = listOf(dthPlan.channels, op.name),
            category = PlanCategory.POPULAR,
            description = dthPlan.description
        )

        _uiState.update {
            it.copy(
                mobileNumber = fakeMobile,
                selectedPlan = fakePlan,
                currentScreen = AppScreen.CHECKOUT
            )
        }
    }

    fun getFilteredPlans(): List<RechargePlan> {
        val state = _uiState.value
        val allPlans = repository.getAllPlans(state.selectedOperator)

        return allPlans.filter { plan ->
            val matchesCategory = when (state.selectedCategory) {
                PlanCategory.POPULAR -> plan.isBestSeller || plan.category == PlanCategory.POPULAR
                else -> plan.category == state.selectedCategory
            }

            val query = state.searchQuery.trim().lowercase()
            val matchesQuery = if (query.isEmpty()) true else {
                plan.price.toString().contains(query) ||
                        plan.validity.lowercase().contains(query) ||
                        plan.data.lowercase().contains(query) ||
                        plan.description.lowercase().contains(query) ||
                        plan.perks.any { it.lowercase().contains(query) }
            }

            matchesCategory && matchesQuery
        }
    }

    fun openHelpSheet() {
        _uiState.update { it.copy(showHelpSheet = true) }
    }

    fun dismissHelpSheet() {
        _uiState.update { it.copy(showHelpSheet = false) }
    }
}
