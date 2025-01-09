package com.example.cheqapp.domain.use_case.get_coins

import android.util.Log
import com.example.cheqapp.common.Resources
import com.example.cheqapp.data.remote.dto.toCoin
import com.example.cheqapp.domain.model.Coin
import com.example.cheqapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resources<List<Coin>>> = flow {
        try {
            emit(Resources.Loading<List<Coin>>())
            val coins = repository.getCoins().map { it.toCoin() }
            emit(Resources.Success<List<Coin>>(coins))
        } catch(e: HttpException) {
            Log.e("GetCoinsUseCase", "HTTP error: ${e.message()}")
            emit(Resources.Error<List<Coin>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            Log.e("GetCoinsUseCase1", "Network error: ${e.message}")
            emit(Resources.Error<List<Coin>>("Couldn't reach server. Check your internet connection."))
        }
    }
}