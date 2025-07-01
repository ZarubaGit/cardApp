package com.example.cardapp.data.remote.mapper

import com.example.cardapp.data.remote.dto.BankDto
import com.example.cardapp.data.remote.dto.BinInfoDto
import com.example.cardapp.data.remote.dto.CardNumberDto
import com.example.cardapp.data.remote.dto.CountryDto
import com.example.cardapp.domain.model.Bank
import com.example.cardapp.domain.model.BinInfo
import com.example.cardapp.domain.model.CardNumber
import com.example.cardapp.domain.model.Country

fun BinInfoDto.toDomain(bin: String): BinInfo {
    return BinInfo(
        bin = bin,
        scheme = scheme,
        type = type,
        brand = brand,
        prepaid = prepaid,
        number = number?.toDomain(),
        country = country?.toDomain(),
        bank = bank?.toDomain()
    )
}

fun CardNumberDto.toDomain(): CardNumber {
    return CardNumber(
        length = length,
        luhn = luhn
    )
}

fun CountryDto.toDomain(): Country {
    return Country(
        numeric = numeric,
        alpha2 = alpha2,
        name = name,
        emoji = emoji,
        currency = currency,
        latitude = latitude,
        longitude = longitude
    )
}

fun BankDto.toDomain(): Bank {
    return Bank(
        name = name,
        url = url,
        phone = phone,
        city = city
    )
} 