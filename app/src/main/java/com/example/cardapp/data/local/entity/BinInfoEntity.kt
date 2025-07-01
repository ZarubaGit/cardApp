package com.example.cardapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_info")
data class BinInfoEntity(
    @PrimaryKey
    val bin: String,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val numberLength: Int?,
    val numberLuhn: Boolean?,
    val countryNumeric: String?,
    val countryAlpha2: String?,
    val countryName: String?,
    val countryEmoji: String?,
    val countryCurrency: String?,
    val countryLatitude: Int?,
    val countryLongitude: Int?,
    val bankName: String?,
    val bankUrl: String?,
    val bankPhone: String?,
    val bankCity: String?,
    val timestamp: Long = System.currentTimeMillis()
) 