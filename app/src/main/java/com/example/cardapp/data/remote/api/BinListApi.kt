package com.example.cardapp.data.remote.api

import com.example.cardapp.data.remote.dto.BinInfoDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface BinListApi {
    
    @Headers("Accept-Version: 3")
    @GET("{bin}")
    suspend fun getBinInfo(@Path("bin") bin: String): BinInfoDto
    
    companion object {
        const val BASE_URL = "https://lookup.binlist.net/"
    }
} 