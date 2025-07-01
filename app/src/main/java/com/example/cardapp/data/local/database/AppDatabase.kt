package com.example.cardapp.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.cardapp.data.local.dao.BinInfoDao
import com.example.cardapp.data.local.entity.BinInfoEntity

@Database(
    entities = [BinInfoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun binInfoDao(): BinInfoDao
} 