package com.example.cheqapp.domain.repository

import com.example.cheqapp.data.remote.dto.CoinDetailDto
import com.example.cheqapp.data.remote.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinRepository {

    suspend fun getCoins(): List<CoinDto>

    suspend fun getCoinById(coinId: String): CoinDetailDto
}