package com.example.cardapp.domain.usecase

import com.example.cardapp.domain.model.BinInfo
import com.example.cardapp.domain.repository.BinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBinHistoryUseCase @Inject constructor(
    private val repository: BinRepository
) {
    operator fun invoke(): Flow<List<BinInfo>> {
        return repository.getBinHistory()
    }
} 