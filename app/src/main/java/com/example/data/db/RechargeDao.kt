package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.RechargeTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface RechargeDao {
    @Query("SELECT * FROM recharge_transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<RechargeTransaction>>

    @Query("SELECT * FROM recharge_transactions ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentTransactions(limit: Int = 5): Flow<List<RechargeTransaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: RechargeTransaction): Long

    @Delete
    suspend fun deleteTransaction(transaction: RechargeTransaction)

    @Query("DELETE FROM recharge_transactions")
    suspend fun clearAll()
}
