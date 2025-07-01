package com.example.cardapp.di

import android.content.Context
import androidx.room.Room
import com.example.cardapp.data.local.dao.BinInfoDao
import com.example.cardapp.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "card_app_database"
        ).build()
    }

    @Provides
    fun provideBinInfoDao(database: AppDatabase): BinInfoDao {
        return database.binInfoDao()
    }
} 