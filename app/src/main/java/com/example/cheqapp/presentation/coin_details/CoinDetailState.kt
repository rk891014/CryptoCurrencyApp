package com.example.cheqapp.presentation.coin_details

import com.example.cheqapp.domain.model.Coin
import com.example.cheqapp.domain.model.CoinDetail

data class CoinDetailState (
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String = ""
)