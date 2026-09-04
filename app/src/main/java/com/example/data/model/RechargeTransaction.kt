package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recharge_transactions")
data class RechargeTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val mobileNumber: String,
    val contactName: String? = null,
    val operatorName: String,
    val circleName: String,
    val planPrice: Double,
    val discount: Double = 0.0,
    val amountPaid: Double,
    val validity: String,
    val dataBenefit: String,
    val planDescription: String,
    val paymentMethod: String,
    val transactionId: String,
    val upiUtr: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "SUCCESS", // "SUCCESS", "PROCESSING"
    val couponApplied: String? = null
)
