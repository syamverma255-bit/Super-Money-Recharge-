package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.AirtelRed
import com.example.ui.theme.AirtelRedLight
import com.example.ui.theme.BsnlTeal
import com.example.ui.theme.BsnlTealLight
import com.example.ui.theme.JioBlue
import com.example.ui.theme.JioBlueLight
import com.example.ui.theme.ViCrimson
import com.example.ui.theme.ViCrimsonLight

enum class Operator(
    val displayName: String,
    val brandTag: String,
    val primaryColor: Color,
    val lightBgColor: Color,
    val speedTag: String,
    val fullName: String = displayName,
    val startingPrice: Int = 199,
    val keyFeatures: List<String> = emptyList()
) {
    JIO(
        displayName = "Jio",
        brandTag = "True 5G",
        primaryColor = JioBlue,
        lightBgColor = JioBlueLight,
        speedTag = "India's Fastest 5G",
        fullName = "Reliance Jio Infocomm",
        startingPrice = 199,
        keyFeatures = listOf("True 5G Unlimited", "JioCinema & TV", "VoLTE & VoWiFi")
    ),
    AIRTEL(
        displayName = "Airtel",
        brandTag = "Wynk & 5G Plus",
        primaryColor = AirtelRed,
        lightBgColor = AirtelRedLight,
        speedTag = "Unlimited 5G Included",
        fullName = "Bharti Airtel Limited",
        startingPrice = 199,
        keyFeatures = listOf("5G Plus Ready", "Wynk Free Music", "Apollo 24|7 Circle")
    ),
    VI(
        displayName = "Vi",
        brandTag = "Hero Unlimited",
        primaryColor = ViCrimson,
        lightBgColor = ViCrimsonLight,
        speedTag = "Binge All Night (12-6 AM)",
        fullName = "Vodafone Idea (Vi)",
        startingPrice = 179,
        keyFeatures = listOf("Binge All Night", "Weekend Rollover", "Data Delights 2GB")
    ),
    BSNL(
        displayName = "BSNL",
        brandTag = "Bharat Sanchar",
        primaryColor = BsnlTeal,
        lightBgColor = BsnlTealLight,
        speedTag = "Budget Value Packs",
        fullName = "Bharat Sanchar Nigam Ltd",
        startingPrice = 107,
        keyFeatures = listOf("Affordable 4G/3G", "Long Term Validity", "National Roaming")
    )
}

object TelecomCircles {
    val ALL = listOf(
        "Delhi NCR",
        "Mumbai",
        "Maharashtra & Goa",
        "Gujarat",
        "UP East",
        "UP West",
        "Karnataka",
        "Tamil Nadu & Chennai",
        "Andhra Pradesh & Telangana",
        "Bihar & Jharkhand",
        "Rajasthan",
        "Punjab & Haryana",
        "West Bengal & Kolkata",
        "Kerala",
        "Madhya Pradesh & CG"
    )
}

enum class PlanCategory(val displayName: String, val badgeText: String) {
    POPULAR("Popular", "Trending"),
    TRUE_5G("True 5G", "Unlimited 5G"),
    DAILY_DATA("1.5 - 3 GB/Day", "Daily Data"),
    LONG_VALIDITY("84 - 365 Days", "Long Term"),
    DATA_BOOSTER("Data Add-on", "Instant Boost"),
    ENTERTAINMENT("OTT Bundles", "Hotstar/Prime"),
    TALKTIME("Top-Up", "Full Value")
}
