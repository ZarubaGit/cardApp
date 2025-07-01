package com.example.cardapp.data.local.mapper

import com.example.cardapp.data.local.entity.BinInfoEntity
import com.example.cardapp.domain.model.Bank
import com.example.cardapp.domain.model.BinInfo
import com.example.cardapp.domain.model.CardNumber
import com.example.cardapp.domain.model.Country

fun BinInfo.toEntity(): BinInfoEntity {
    return BinInfoEntity(
        bin = bin,
        scheme = scheme,
        type = type,
        brand = brand,
        prepaid = prepaid,
        numberLength = number?.length,
        numberLuhn = number?.luhn,
        countryNumeric = country?.numeric,
        countryAlpha2 = country?.alpha2,
        countryName = country?.name,
        countryEmoji = country?.emoji,
        countryCurrency = country?.currency,
        countryLatitude = country?.latitude,
        countryLongitude = country?.longitude,
        bankName = bank?.name,
        bankUrl = bank?.url,
        bankPhone = bank?.phone,
        bankCity = bank?.city
    )
}

fun BinInfoEntity.toDomain(): BinInfo {
    return BinInfo(
        bin = bin,
        scheme = scheme,
        type = type,
        brand = brand,
        prepaid = prepaid,
        number = if (numberLength != null || numberLuhn != null) {
            CardNumber(
                length = numberLength,
                luhn = numberLuhn
            )
        } else null,
        country = if (countryName != null || countryAlpha2 != null) {
            Country(
                numeric = countryNumeric,
                alpha2 = countryAlpha2,
                name = countryName,
                emoji = countryEmoji,
                currency = countryCurrency,
                latitude = countryLatitude,
                longitude = countryLongitude
            )
        } else null,
        bank = if (bankName != null || bankUrl != null || bankPhone != null || bankCity != null) {
            Bank(
                name = bankName,
                url = bankUrl,
                phone = bankPhone,
                city = bankCity
            )
        } else null
    )
} 