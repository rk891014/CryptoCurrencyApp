package com.example.cheqapp.presentation.coin_list

import com.example.cheqapp.domain.model.Coin

data class CoinListState (
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)