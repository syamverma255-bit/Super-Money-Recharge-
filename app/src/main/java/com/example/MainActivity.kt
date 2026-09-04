package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.HelpSupportBottomSheet
import com.example.ui.screens.BrowsePlansScreen
import com.example.ui.screens.CheckoutScreen
import com.example.ui.screens.DthRechargeScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ReceiptScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.RechargeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val rechargeViewModel: RechargeViewModel = viewModel()
                BharatRechargeApp(viewModel = rechargeViewModel)
            }
        }
    }
}

@Composable
fun BharatRechargeApp(viewModel: RechargeViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val transactions by viewModel.transactionHistory.collectAsStateWithLifecycle()

    // Handle system back navigation smoothly
    BackHandler(enabled = uiState.currentScreen != AppScreen.HOME) {
        viewModel.navigateTo(AppScreen.HOME)
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        AnimatedContent(
            targetState = uiState.currentScreen,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "screen_transition",
            modifier = Modifier.padding(innerPadding)
        ) { screen ->
            when (screen) {
                AppScreen.HOME -> HomeScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    recentTransactions = transactions
                )
                AppScreen.BROWSE_PLANS -> BrowsePlansScreen(
                    viewModel = viewModel,
                    uiState = uiState
                )
                AppScreen.CHECKOUT -> CheckoutScreen(
                    viewModel = viewModel,
                    uiState = uiState
                )
                AppScreen.RECEIPT -> ReceiptScreen(
                    viewModel = viewModel,
                    uiState = uiState
                )
                AppScreen.HISTORY -> HistoryScreen(
                    viewModel = viewModel,
                    transactions = transactions
                )
                AppScreen.DTH -> DthRechargeScreen(
                    viewModel = viewModel,
                    uiState = uiState
                )
            }
        }

        if (uiState.showHelpSheet) {
            val contextInfo = when {
                uiState.selectedPlan != null -> "Plan ₹${uiState.selectedPlan?.price} for ${uiState.mobileNumber} (${uiState.selectedOperator.displayName})"
                uiState.mobileNumber.isNotEmpty() -> "Recharge for +91 ${uiState.mobileNumber} (${uiState.selectedOperator.displayName})"
                uiState.dthSubscriberId.isNotEmpty() -> "DTH ID ${uiState.dthSubscriberId}"
                else -> "Mobile Recharge General Support"
            }
            HelpSupportBottomSheet(
                onDismiss = { viewModel.dismissHelpSheet() },
                initialContextInfo = contextInfo
            )
        }
    }
}

// Kept for screenshot and regression tests
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme { Greeting("Android") }
}
