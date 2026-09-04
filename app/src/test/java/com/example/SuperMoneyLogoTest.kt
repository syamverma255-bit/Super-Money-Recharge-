package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import com.example.ui.components.SuperMoneyFullBrandLockup
import com.example.ui.components.SuperMoneyLogo
import com.example.ui.components.SuperMoneyWordmark
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class SuperMoneyLogoTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `super money logo renders with test tag`() {
        composeTestRule.setContent {
            SuperMoneyLogo(size = 48.dp, showGlow = true, useContainer = true)
        }

        composeTestRule.onNodeWithTag("super_money_logo").assertExists()
    }

    @Test
    fun `super money wordmark renders SUPERMONEY text`() {
        composeTestRule.setContent {
            SuperMoneyWordmark()
        }

        composeTestRule.onNodeWithTag("super_money_wordmark").assertExists()
        composeTestRule.onNodeWithText("SUPERMONEY").assertExists()
    }

    @Test
    fun `super money full brand lockup renders both mark and wordmark`() {
        composeTestRule.setContent {
            SuperMoneyFullBrandLockup(markSize = 64.dp)
        }

        composeTestRule.onNodeWithTag("super_money_full_brand_lockup").assertExists()
        composeTestRule.onNodeWithTag("super_money_logo").assertExists()
        composeTestRule.onNodeWithTag("super_money_wordmark").assertExists()
        composeTestRule.onNodeWithText("SUPERMONEY").assertExists()
    }
}
