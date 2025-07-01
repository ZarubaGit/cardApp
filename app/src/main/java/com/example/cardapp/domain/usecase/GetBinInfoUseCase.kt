package com.example.cardapp.domain.usecase

import com.example.cardapp.domain.model.BinInfo
import com.example.cardapp.domain.repository.BinRepository
import javax.inject.Inject

class GetBinInfoUseCase @Inject constructor(
    private val repository: BinRepository
) {
    suspend operator fun invoke(bin: String): Result<BinInfo> {
        return try {
            if (bin.length < 6) {
                return Result.failure(IllegalArgumentException("BIN должен содержать минимум 6 цифр"))
            }
            
            val result = repository.getBinInfo(bin)
            
            if (result.isSuccess) {
                result.getOrNull()?.let { binInfo ->
                    repository.saveBinInfo(binInfo)
                }
                result
            } else {
                // Если получили ошибку (например, HTTP 429), попробуем найти в кэше
                val cachedInfo = repository.getCachedBinInfo(bin)
                if (cachedInfo != null) {
                    Result.success(cachedInfo)
                } else {
                    result // Возвращаем исходную ошибку
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 