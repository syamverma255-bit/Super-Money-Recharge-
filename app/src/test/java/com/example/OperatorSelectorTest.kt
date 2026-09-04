package com.example

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.example.data.model.Operator
import com.example.ui.components.OperatorCardLayout
import com.example.ui.components.OperatorSelectorCardList
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class OperatorSelectorTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `displays all operators with cards and allows selection in list mode`() {
        var selectedOp by mutableStateOf(Operator.JIO)

        composeTestRule.setContent {
            Box(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                OperatorSelectorCardList(
                    selectedOperator = selectedOp,
                    onOperatorSelected = { selectedOp = it },
                    layoutMode = OperatorCardLayout.LIST
                )
            }
        }

        // Verify all 4 operators exist with their cards
        composeTestRule.onNodeWithTag("operator_card_jio").assertExists()
        composeTestRule.onNodeWithTag("operator_card_airtel").assertExists()
        composeTestRule.onNodeWithTag("operator_card_vi").assertExists()
        composeTestRule.onNodeWithTag("operator_card_bsnl").assertExists()

        // Select Airtel
        composeTestRule.onNodeWithTag("operator_card_airtel").performScrollTo().performClick()
        assertEquals(Operator.AIRTEL, selectedOp)

        // Select Vi
        composeTestRule.onNodeWithTag("operator_card_vi").performScrollTo().performClick()
        assertEquals(Operator.VI, selectedOp)

        // Select BSNL
        composeTestRule.onNodeWithTag("operator_card_bsnl").performScrollTo().performClick()
        assertEquals(Operator.BSNL, selectedOp)

        // Select Jio
        composeTestRule.onNodeWithTag("operator_card_jio").performScrollTo().performClick()
        assertEquals(Operator.JIO, selectedOp)
    }

    @Test
    fun `displays operators in grid mode and updates selection correctly`() {
        var selectedOp by mutableStateOf(Operator.AIRTEL)

        composeTestRule.setContent {
            OperatorSelectorCardList(
                selectedOperator = selectedOp,
                onOperatorSelected = { selectedOp = it },
                layoutMode = OperatorCardLayout.GRID
            )
        }

        composeTestRule.onNodeWithTag("operator_card_jio").assertExists()
        composeTestRule.onNodeWithTag("operator_card_airtel").assertExists()
        composeTestRule.onNodeWithTag("operator_card_vi").assertExists()
        composeTestRule.onNodeWithTag("operator_card_bsnl").assertExists()

        composeTestRule.onNodeWithTag("operator_card_vi").performClick()
        assertEquals(Operator.VI, selectedOp)
    }
}

