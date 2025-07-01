package com.example.cardapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cardapp.data.local.entity.BinInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BinInfoDao {
    
    @Query("SELECT * FROM bin_info ORDER BY timestamp DESC")
    fun getAllBinInfo(): Flow<List<BinInfoEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBinInfo(binInfo: BinInfoEntity)
    
    @Query("DELETE FROM bin_info WHERE bin = :bin")
    suspend fun deleteBinInfo(bin: String)
    
    @Query("SELECT * FROM bin_info WHERE bin = :bin")
    suspend fun getBinInfo(bin: String): BinInfoEntity?
} 