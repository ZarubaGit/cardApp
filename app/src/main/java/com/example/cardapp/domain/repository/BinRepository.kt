package com.example.cardapp.domain.repository

import com.example.cardapp.domain.model.BinInfo
import kotlinx.coroutines.flow.Flow

interface BinRepository {
    suspend fun getBinInfo(bin: String): Result<BinInfo>
    suspend fun saveBinInfo(binInfo: BinInfo)
    suspend fun getCachedBinInfo(bin: String): BinInfo?
    fun getBinHistory(): Flow<List<BinInfo>>
    suspend fun deleteBinFromHistory(bin: String)
} 