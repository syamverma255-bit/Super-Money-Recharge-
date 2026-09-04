package com.example.data.model

data class RechargePlan(
    val id: String,
    val operator: Operator,
    val price: Int,
    val validity: String,
    val data: String,
    val voice: String = "Truly Unlimited Calls",
    val sms: String = "100 SMS/Day",
    val perks: List<String>,
    val category: PlanCategory,
    val isBestSeller: Boolean = false,
    val promoTag: String? = null,
    val description: String
)

data class Coupon(
    val code: String,
    val discountDescription: String,
    val minAmount: Int,
    val discountAmount: Int,
    val isPercentage: Boolean = false,
    val percentValue: Double = 0.0,
    val maxDiscount: Int = 0
)
