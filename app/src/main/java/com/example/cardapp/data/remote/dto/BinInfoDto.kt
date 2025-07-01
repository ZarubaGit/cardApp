package com.example.cardapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BinInfoDto(
    @SerializedName("scheme")
    val scheme: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("prepaid")
    val prepaid: Boolean?,
    @SerializedName("number")
    val number: CardNumberDto?,
    @SerializedName("country")
    val country: CountryDto?,
    @SerializedName("bank")
    val bank: BankDto?
)

data class CardNumberDto(
    @SerializedName("length")
    val length: Int?,
    @SerializedName("luhn")
    val luhn: Boolean?
)

data class CountryDto(
    @SerializedName("numeric")
    val numeric: String?,
    @SerializedName("alpha2")
    val alpha2: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("emoji")
    val emoji: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("latitude")
    val latitude: Int?,
    @SerializedName("longitude")
    val longitude: Int?
)

data class BankDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("city")
    val city: String?
) 