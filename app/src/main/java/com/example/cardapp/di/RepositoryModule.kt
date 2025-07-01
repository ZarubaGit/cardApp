package com.example.cardapp.di

import com.example.cardapp.data.local.dao.BinInfoDao
import com.example.cardapp.data.remote.api.BinListApi
import com.example.cardapp.data.repository.BinRepositoryImpl
import com.example.cardapp.domain.repository.BinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBinRepository(
        api: BinListApi,
        dao: BinInfoDao
    ): BinRepository {
        return BinRepositoryImpl(api, dao)
    }
} 