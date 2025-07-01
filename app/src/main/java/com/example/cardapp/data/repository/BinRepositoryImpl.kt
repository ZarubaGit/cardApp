package com.example.cardapp.data.repository

import com.example.cardapp.data.local.dao.BinInfoDao
import com.example.cardapp.data.local.mapper.toDomain
import com.example.cardapp.data.local.mapper.toEntity
import com.example.cardapp.data.remote.api.BinListApi
import com.example.cardapp.data.remote.mapper.toDomain
import com.example.cardapp.domain.model.BinInfo
import com.example.cardapp.domain.repository.BinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class BinRepositoryImpl @Inject constructor(
    private val api: BinListApi,
    private val dao: BinInfoDao
) : BinRepository {

    override suspend fun getBinInfo(bin: String): Result<BinInfo> {
        return try {
            val response = api.getBinInfo(bin)
            val binInfo = response.toDomain(bin)
            Result.success(binInfo)
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                429 -> "Слишком много запросов. Пожалуйста, подождите 2-3 минуты и попробуйте снова."
                404 -> "BIN номер не найден в базе данных."
                500 -> "Ошибка сервера. Попробуйте позже."
                else -> "Ошибка сети: ${e.code()}"
            }
            Result.failure(Exception(errorMessage))
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Нет подключения к интернету. Проверьте соединение."))
        } catch (e: Exception) {
            Result.failure(Exception("Произошла ошибка: ${e.message}"))
        }
    }

    override suspend fun saveBinInfo(binInfo: BinInfo) {
        dao.insertBinInfo(binInfo.toEntity())
    }

    override suspend fun getCachedBinInfo(bin: String): BinInfo? {
        return dao.getBinInfo(bin)?.toDomain()
    }

    override fun getBinHistory(): Flow<List<BinInfo>> {
        return dao.getAllBinInfo().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun deleteBinFromHistory(bin: String) {
        dao.deleteBinInfo(bin)
    }

    override suspend fun deleteBinInfo(binInfo: BinInfo) {
        dao.deleteBinInfo(binInfo.bin)
    }
} 